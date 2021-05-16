package com.eva.android;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.eva.epc.common.util.CommonUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

/**
 * 大文件下载实用类（支持断点续传）。
 * 
 * @author JackJiang
 * @since 4.3
 */
public class HttpBigFileDownloadHelper
{
    private final static String TAG = HttpBigFileDownloadHelper.class.getSimpleName();
    
    private static final int BUFFER_SIZE = 4096;

    /**
     * 从指定URL处下载文件数据（如果文件已经存在，使用附加模式来写数据，从而实现断点续写）。
     *
     * @param fileURL HTTP URL of the file to be downloaded
     * @param saveDir path of the directory to save the file（目录末尾无需分隔符"/"）
     * @param fileSize 本次下载文件大小，如果此参数<=0则将在方法内尝试取Content-Length的
     * 		http头信息作为文件大小
     * @param progressObserver 文件在下载过程中，将会按文件下载比便0~100范围作为进度值通知观察者
     * @param dependAsyncTask 本次下载所依附的AsyncTask类。本参数可为null。因为AsyncTask在有些android版本
     *                        下并不能通过AsyncTask.cancel()通出后台线程的执行，所以可以通过在下载循环里通过AsyncTask.isCannceld()
     *                        方法来判定AsyncTask是否已被标记cancel，这样就可以实现后台线程的中止执行了，否则没别的办法能
     *                        保证所有的android机型上都能AsyncTask.cancel()掉后台线程，这是android官方的设计缺陷，没有办法哦
     * @throws IOException
     */
    public static String  downloadFile(String fileURL, String saveDir, String fileName, long fileSize
            , Observer progressObserver, AsyncTask dependAsyncTask) throws Exception
    {

        Log.i(TAG, "【大文件下载】传入的参数：fileURL="+fileURL+", saveDir="+saveDir+", fileName="+fileName
                +", fileSize="+fileSize+", progressObserver="+progressObserver);
        
        if(CommonUtils.isStringEmpty(fileURL, true)
                || CommonUtils.isStringEmpty(saveDir, true)
                || CommonUtils.isStringEmpty(fileName, true)
                || fileSize <= 0)
        {
            throw new Exception("无效的参数，本次下载无法继续！");
        }
        
        File savedFile = null;
//        int fileLength = 0;
//        String fileName = "";

        HttpURLConnection httpConn = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try
        {
            // http连接
            URL url = new URL(fileURL);
            httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
            
            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                String disposition = httpConn.getHeaderField("Content-Disposition");
//                String contentType = httpConn.getContentType();
                
                // 注意：如果服务端没有设置本http头，则返回值是-1
                int contentLength = httpConn.getContentLength();
                //
//                fileLength = contentLength;
                if(contentLength > 0)
                {
//                    if (disposition != null)
//                    {
//                        // extracts file name from header field
//                        int index = disposition.indexOf("filename=");
//                        if (index > 0)
//                            // 注意：此过程中已经去掉了包含在文件名首尾的双引号哦
//                            // 有时候服务端并不一定返回文件名
//                            fileName = disposition.substring(index + 10, disposition.length() - 1);
//                    }
//                    else
//                        // extracts file name from URL
//                        // TODO 有些情况下文件是动态返回数据，URL的末尾不一定是文件名，所以此处不严谨，以后可优化(此种情况下可通过参数传到本方法中即可)
//                        fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1, fileURL.length());

//                    Log.w(TAG, "【大文件下载】Content-Type = " + contentType);
                    Log.w(TAG, "【大文件下载】Content-Disposition = " + disposition);
                    Log.w(TAG, "【大文件下载】Content-Length = " + contentLength);
//                    System.out.println("fileName = " + fileName);

                    // opens input stream from the HTTP connection
                    inputStream = httpConn.getInputStream();
//					saveFilePath = saveDir + File.separator + fileName;
                    savedFile = new File(saveDir + File.separator + fileName);
                    // 如果父目录不存在则建立之
                    if(savedFile.getParent() != null && !savedFile.getParentFile().exists())
                        savedFile.getParentFile().mkdirs();

                    Log.w(TAG, "【大文件下载】Dowload saveFilePath="+savedFile.getAbsolutePath());

                    // opens an output stream to save into file
                    // 注意：此处的写入数据是append模式（即下载的数据是续定到末尾而不是重头开始保存）
                    outputStream = new FileOutputStream(savedFile, true);
                    long cumulationSize = 0; // 当前读取的总大小：仅用于进度报告
                    
                    // 如果该文件已经被创建，则读取它已被下载过的长度
                    if(savedFile.exists())
                        cumulationSize = savedFile.length();

                    Log.w(TAG, "【大文件下载】"+fileName+"已被下载的长度为："+(cumulationSize+"/"+fileSize));
                    
                    int bytesRead = -1;
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while ((dependAsyncTask != null && !dependAsyncTask.isCancelled()) // 注意此判断，否则在某些较老androi机型上是不能实现退出后台下载的哦！
                            && (bytesRead = inputStream.read(buffer)) != -1)
                    {
                        outputStream.write(buffer, 0, bytesRead);
                        outputStream.flush();//!!!!
                        
                        // 累加已下载字节数
                        cumulationSize += bytesRead;

//                        Log.d(TAG, "【大文件下载】"+fileName+"正在下载中，累计下载总长度为："+cumulationSize+"/"+fileSize);

                        //** 通知下载进度观察者
                        if(progressObserver != null)
                            progressObserver.update(null, (int)((cumulationSize*100)/fileSize));
                    }

//                    // 如果服务端没有返回数据，这个空文件就不要留了，删除之
//                    if(cumulationSize <= 0)
//                    {
//                        if(savedFile.exists())
//                            savedFile.delete();
//                    }
                    
                    if(cumulationSize > fileSize)
                        Log.w(TAG, "【大文件下载】"+fileName+"下载已完成，累计下载总长度为："
                                +cumulationSize+"/"+fileSize+"，此累计下载长度已超过文件原始总长，服务端文件数据被破坏？？");
                    
                    // 本次下载成功完成或提前结束
                }
                else
                {
                    String log = "地址：下载"
                            +fileURL+"的请求从服务端正常返回但本次下载数据大小是0" 
                            + "，本次responseCode="+responseCode+"，fileURL="+fileURL;
                    Log.w(TAG, log);
                    throw new Exception(log);
                }
            }
            else
            {
                throw new Exception("文件下载失败，responseCode="+responseCode+"！");
            }

            Log.i(TAG, "【大文件下载】[END]code="+responseCode+", 文件"+fileName
                    +"本次下载结束，当前文件度为："+savedFile.length()+"/"+fileSize);
        }
        catch (Exception e)
        {
            String log = "【大文件下载】本次下载已停止，停止原因是："+e.getMessage()+"(地址："+fileURL+")";
			Log.w(TAG, log);
            throw new Exception(log);
        }
        finally
        {
            try{
                // 关闭流，释放资源
                if(outputStream != null)
                    outputStream.close();
                
                if(inputStream != null)
                    inputStream.close();
                
                if(httpConn != null)
                    httpConn.disconnect();
                
            } catch(Exception eee){
                Log.w(TAG, eee.getMessage());
            }
        }

//        return new Object[]{savedFile == null?null:savedFile.getAbsolutePath(), fileLength, fileName};
        
        return savedFile.getAbsolutePath();
    }

    /**
     * 一个实现了异步下载的封装类.
     *
     * @author Jack Jiang
     * @since 4.3
     */
    public abstract static class DownloadAsyncRoot extends AsyncTask<Object, Integer, Object>
    {
        protected Context activity = null;

        protected String fileURL;
        protected String saveDir;
        protected String fileName;
        protected long fileSize;
//    private Observer progressObserver;
        
        private boolean complete = false;

        /**
         * 构造方法.
         *
         * @param activity
         * @param fileURL
         * @param saveDir 文件保存目录（目录末尾无需分隔符"/"）
         */
        public DownloadAsyncRoot(Context activity
                , String fileURL, String saveDir, String fileName, long fileSize)
        {
            this.activity = activity;

            this.fileURL = fileURL;
            this.saveDir = saveDir;
            this.fileName = fileName;
            this.fileSize = fileSize;
        }

        @Override
        protected Object doInBackground(Object... params)
        {
            String fileSavedPath = null;
            try
            {
                Observer progrssObserver = new Observer(){
                    @Override
                    public void update(Observable observable, Object data)
                    {
                        // 传过来的进度值是0~100的值
                        int progressOf100 = (Integer)data;
                        publishProgress(progressOf100);
                    }
                };

//                fileSavedPath = HttpBigFileDownloadHelper.downloadFile(fileURL, saveDir, fileSize, progrssObserver);
                fileSavedPath = HttpBigFileDownloadHelper.downloadFile(fileURL, saveDir
                        , fileName, fileSize, progrssObserver
                        // 注意此参数，否则在某些较老androi机型上是不能实现退出后台下载的哦！
                        , this);
                if(fileSavedPath != null)
                    return fileSavedPath;
                else
                    return new Exception("No data retrun from server or save to SDCard failed (fileSavedPath is null)!");
            }
            catch (Exception e)
            {
                return e;
            }
        }

        @Override
        protected void onPostExecute(Object result)
        {
            // doInBackground执行发生异常了
            if(result instanceof Exception)
                onPostExecute_onException((Exception)result);
                // String 
            else
                // 参数(String)result将被本类保证不为null!（onPostExecute_onSucess的调用者无需判断null情况！）
                onPostExecute_onSucess((String)result);

            complete = true;
        }

        public boolean isComplete()
        {
            return complete;
        }

        // 下载发生错误后调用本方法
        protected abstract void onPostExecute_onException(Exception exception);
        
        // 下载完成后调用本方法
        protected abstract void onPostExecute_onSucess(String fileSavedPath);

        // 获得文件最终的保存路径（将方法应确保与HttpBigFileDownloadHelper.downloadFile(..)方法的返回结果保持一致）
        private String getFileSavedPath()
        {
            String fp = null;
            try{
                 fp = new File(saveDir + File.separator + fileName).getAbsolutePath();
            }
            catch(Exception e)
            {
                Log.w(TAG, e);
            }

            return fp;
        }

        /**
         * 强制性地走下载完成这个流程。
         * <p>
         * 因为有些情况下，文件明明已经下载完成，而被拉起了一次任务，那么此次任务就不需要
         * 真的从网络请求数据，只需要强制调用这个完成方法就能保持正常一致的表现了。
         */
        public void forceComplete()
        {
            String fileSavedPath = getFileSavedPath();
            if(fileSavedPath != null)
            {
                onPostExecute(fileSavedPath);
            }
        }
    }
}
