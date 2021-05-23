// Copyright (C) 2020 即时通讯网(52im.net) & Jack Jiang.
// The RainbowChat Project. All rights reserved.
// 
// 【本产品为著作权产品，合法授权后请放心使用，禁止外传！】
// 【本次授权给：<唐林峰（身份证号：360727198911110910）>，授权编号：<NT200909102407>，代码指纹：<A.599618247.472>，技术对接人QQ：<582901605>】
// 【授权寄送：<收件：唐林峰、地址：上海市浦东新区金豫路100号禹州国际3期2号楼、电话：18717758990>】
// 
// 【本系列产品在国家版权局的著作权登记信息如下】：
// 1）国家版权局登记名(简称)和权证号：RainbowChat    （证书号：软著登字第1220494号、登记号：2016SR041877）
// 2）国家版权局登记名(简称)和权证号：RainbowChat-Web（证书号：软著登字第3743440号、登记号：2019SR0322683）
// 3）国家版权局登记名(简称)和权证号：RainbowAV      （证书号：软著登字第2262004号、登记号：2017SR676720）
// 4）国家版权局登记名(简称)和权证号：MobileIMSDK-Web（证书号：软著登字第2262073号、登记号：2017SR676789）
// 5）国家版权局登记名(简称)和权证号：MobileIMSDK    （证书号：软著登字第1220581号、登记号：2016SR041964）
// * 著作权所有人：江顺/苏州网际时代信息科技有限公司
// 
// 【违法或违规使用投诉和举报方式】：
// 联系邮件：jack.jiang@52im.net
// 联系微信：hellojackjiang
// 联系QQ号：413980957
// 授权说明：http://www.52im.net/thread-1115-1-1.html
// 官方社区：http://www.52im.net
package com.eva.android;

import com.eva.epc.common.util.CommonUtils;
import com.eva.epc.common.util.EException;
import com.eva.framework.dto.DataFromClient;
import com.eva.framework.dto.DataFromServer;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * <p>
 *  <b>一个封装了与指定servlet通信及相关操作方法的Http操作类.</b><br>
 *  使用本类即可完成与任一远程Servlet通信的功能.<br><br>
 *  
 *  <b>特别注意：</b>现时HttpService的设计思路（cookie0作全局静态变量），允许多个servlet服务（MVC控制器）存在，但必须确保它们
 *	 处于同一个WEB模块下（不允许连接到不同的WEB应用模块上）。
 * </p>
 * 
 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
 * @version 1.0
 */
// 本类来自EVA.MVC工程中的同包同名文件，复制至EVA.Andrid工程中是希
// 望给本类实现一些Android端的特殊设定等
public class HttpService4A
{
	/** 
	 * 一个cookie应该只对应一个会话（也就是务端会在首次getSession(true)创建会话时才会返回cookie值，
	 * 这个值得由客户端保存，下次如果是同一个会话过来的请求它是不再传回cookie值的，
	 * 理论上，多个HttpService如果不是使用的同一会话，则cookie肯定不同.
	 * 
	 * <b>特别注意：</b>现时HttpService的设计思路（cookie0作全局静态变量），允许多个servlet服务（MVC控制器）存在，但必须确保它们
	 * 处于同一个WEB模块下（不允许连接到不同的WEB应用模块上）。
	 */
	private static CookieHolder cookie0;
	private static CookieHolder cookie2;
	
	/** 服务端Servlet的根路径（就是完整Servlet的URL除servlet名字以外的部分，注意需要splash） */
	private String servletRootURL="http://127.0.0.1:2683/";
	/** Servlet的名字，记住仅仅是名字 （注意：不需要slash） */
	private String servletName = "";
	/** 本servlet服务对象对应的名称（目前本字段尚未有关键用处，仅作标识） */
	private String serviceName = null;
	
    /**
     * 服务构造方法.
     * 
     * @param serviceName 服务名
     * @param servletRootURL 服务root URL，形如: http://127.0.0.1:8080/ （需要slash结尾） 
     * @param servletName 服务名（servlet名称），形如: MyController （不需要slash开头）
     */
	public HttpService4A(String serviceName, String servletRootURL, String servletName)
	{
		this.serviceName = serviceName;
		this.servletRootURL = servletRootURL;
		this.servletName = servletName;
	}
	
	/**
	 * 给服务端发送一个对象返回服务端返回的对象，默认需要读取返回值.
	 *
	 * @param _obj 客户端发送出去的对象
	 * @return 服务端返回的对象DataFromServer,可据此对象的isSucess方法判断有否成功处理本次请求
	 */
	public DataFromServer sendObjToServer(DataFromClient _obj)
	{
		return sendObjToServer(_obj,true);
	}
	/**
	 * 给服务端发送一个对象返回服务端返回的对象，默认需要读取返回值.
	 *
	 * @param _obj 客户端发送出去的对象
	 * @param neadReceive true表示发送数据后需要读取servlet返回的数据
	 * ，否则不读取（无论servlet是否返回数据）
	 * @return 服务端返回的对象DataFromServer,可据此对象的isSucess方法判断有否成功处理本次请求
	 */
	public DataFromServer sendObjToServer(DataFromClient _obj, boolean neadReceive)
	{
		return sendObjToServer(_obj,neadReceive,0);
	}
	/**
	 * <p>
	 * 给服务端发送一个对象返回服务端返回的对象.<br>
	 * 数据发送的核心实现方法是 {@link #processSendData(DataFromClient, OutputStream)}，数据返回接收的
	 * 核心实现方法是 {@link #processReceiveData(InputStream, DataFromServer[])}，子类可重写这2个方法实现不一样
	 * 的数据发送和接收方式（默认实现是发送和接收的都是Java序列化对象），比如直接发送和接收2进制数据等.
	 * </p>
	 *
	 * @param _obj 客户端发送出去的对象
	 * @param neadReceive true表示发送数据后需要读取servlet返回的数据
	 * ，否则不读取（无论servlet是否返回数据）
	 * @param readTimeout 读超时时间，本值仅在neadReceive==true且不等于0时才会设置URLConnection连接对象的该字段否则忽略之，
	 * 详情请参见 {@link HttpURLConnection#setReadTimeout(int)}
	 * @return 服务端返回的对象，通过此对象的isSucess方法即可判断本次数据发送是否成功（不成功
	 * 的情况包括：客户端出的错和客户端没出错但服务端出现了对应的错误（错误意味着可能是故障、也
	 * 可能是逻辑问题，比如：发送给服务端的请求是更新一组数据但更新条件不符，这就是指逻辑问题；网
	 * 络连接出现问题而导致的失败则就称作故障）等，总之isSucess()==false意味着本次操作失败， 出错的
	 * 具体内容就存在放于getRetruneValue()里，它是一个{@link EException}对象 ）
	 * 
	 * @see #processSendData(DataFromClient, OutputStream)
	 * @see #processReceiveData(InputStream, DataFromServer[])
	 * @see #getServerConnection(boolean)
	 * @see #storeCookie(HttpURLConnection)
	 * @see #processRunningException(Exception)
	 * @see #processFinallyException(Exception)
	 */
	public 
		// TODO 2012-08-14 13:52由js把同步的限制去掉了，以期提高程序并发性能，目前用于测试时！
//		//！！是否要进行同步限制，有待进一步研究（目的是为了解决大量出现的网络不通问题！！）
//    	//*** 这样改后，性能有严重影响，这主要是因为在loadTableData()前要先renderColumns()，而很多时候
//    	//*** renderColumns（）很耗时，所以性能有影响，但这也使得数据获取更严谨，斟酌！！
//		synchronized//要还是要求同步吧，很可能不同步时多次进行URLConnection边接时前面未完成的
//				//处理就会被后面的弄乱以致出错（如网络不通），URLConnection应该必须要同步调用吧!?
    DataFromServer sendObjToServer(DataFromClient _obj, boolean neadReceive, int readTimeout)
//		throws Exception
	{
		//作好是否需要服务端写回数据的标识，服务端将据此决定是否要写回数据
		_obj.setDoInput(neadReceive);
		
		//服务端返回对象
		DataFromServer fs = null;
		//最终要返回给客户端的字符串
		String errorStr = null;
		//出错时把异常也记录下来
		Exception exception = null;
		//Http连接
		HttpURLConnection conn = null;
		//出 流对象
//		ObjectOutputStream objOut = null;
		OutputStream objOut = null;
		//入 流对象
//		ObjectInputStream objIn = null;
		InputStream objIn = null;
		try
		{
			conn = getServerConnection(neadReceive);

			//************** (1)先发出数据
//			objOut = new ObjectOutputStream(conn.getOutputStream());
//			//开始写数据
//			objOut.writeObject(_obj);
//			objOut.flush();
			objOut = processSendData(_obj,conn.getOutputStream());
			
			//************** (2)再读取数据（如果需要）
			if(neadReceive)
			{
				//设置读超时时间
				if(readTimeout!=0)
					conn.setReadTimeout(readTimeout);
//				// 读
//				objIn = new ObjectInputStream(conn.getInputStream());
//				fs = (DataFromServer)((ObjectInputStream)objIn).readObject();
				
				//这个数组是为了能保存在方法processReceiveData处理的DataFromServer对象，
				//试想，如果直接把fs作为参数传入，本想在processReceiveData中被赋值，但
				//该方法会把它当局部变量，因而无法把processReceiveData中的DataFromServer
				//保存下来，所以想办法弄了个1元数组，仅此用途而已（否则没有其它更好的实现方法了）
				DataFromServer[] dfses = new DataFromServer[1];
				objIn = processReceiveData(conn.getInputStream(), dfses);
				//将processReceiveData方法中收到的DataFromServer对象保存下来
				fs = dfses[0];
			}
			//无需读取返回数据
			else
			{
				fs = new DataFromServer();
				fs.setSuccess(true);
			}
		}
		catch (Exception e)
		{
//			errorStr="在sendObjToServer中出错了，原因可能是网络连接有问题，"+e.getMessage();
			errorStr="May be due to network connectivity problems, "+e.getMessage();
			exception = e;
			
			processRunningException(e);
		}
		finally
		{
			try
			{
				if (objOut != null)
				{
					objOut.close();
					objOut = null;
				}
				
				//** 对注释了以下代码作的特别说明：
				//在J2SE平台，以下代码用于关闭可能的Http连接的输入流读取通道，没有发现有任何问题。
				//但在Android2.1（应该与Android版本没多在关系）平台下，以下语句的存在将会使得与服务端的
				//通信在第1次能成功第2次会失败，第3次成功第4次失败，经测试认为就是调用了objIn.close()的原因，
				//出现的错误是：StreamCorruptedException，目前就行把以下代码注释掉，Android上运行一切正常，
				//不知对J2SE平台和Android的消耗有多大影响，目前只能先这样了！以后再好好看看objIn.close()源码
				//到底干了什么！
//				if (objIn != null)
//				{
//					objIn.close();
//					objIn = null;
//				}

				//注意：所有的对connection进行的读必须在write完成后才行
				//否则就是异常：java.lang.IllegalStateException: Write attempted after request finished
				//第一次连接时存储连接信息头信息（主要是cookie信息）-- 应用时这很可能就是登陆时
				
				//*** 2010-08-07这将有一个问题，用firstConnect来标识，当用户第一次登陆错误时
				//*** firstConnect也会被设置也false，则第二次正确登陆时cookie就不能正确保存了
				
				//*** OK,2010-08-07 15:10
				//*** 注：服务端返回的conn应该只有第一次登陆成功时，调用getSession(true)新建会话时，
				//*** 才会传给客户端Set-Cookie头信息，这时候保存下来才行，一里弄登陆成功后，就把这个保存的
				//*** cookie值再发给服务端，此时服务端据此调用getSession(false)就能取得原先的这个session了
				//*** ，且这时取得的conn（URLConnection）里是没有Set-Cookie头信息的。
				//*** 综上：服务务端只在新建会话时给客户端发Set-Cookie头信息（包含cookie字符串），非新建的会话
				//*** 再次接时服务端是不是会返回Set-Cookie头信息的，所以保存cookie值在第一次登陆成功后是合理且正确的.
//				if(firstConnect)
				{
					storeCookie(conn);
//					firstConnect = false;
				}
				if (conn != null)
				{
					conn.disconnect();
					conn = null;
				}
			}
			catch (Exception e)
			{
				processFinallyException(e);
			}
		}

        //到了这一步还是null表示起码与服务端交互的过程中出错了
        //那么接下来实例化的这个对象主要就是存放错误信息了（用于提示用户）
        if(fs==null)
        {
        	fs = new DataFromServer();
        	fs.setSuccess(false);
//        	fs.setReturnValue(new EException("数据发往服务端的过程中出现问题，本次操作失败.",errorStr,exception.getCause()));//errorStr);
        	fs.setReturnValue(new EException(
        			CommonUtils.isStringEmpty(HttpServiceFactory4A.defaultTipMsgIfFail)?
        					"Exception cause for sending datas.":HttpServiceFactory4A.defaultTipMsgIfFail
        		,errorStr,exception.getCause()));//errorStr);
        }
		return fs;
	}
	
	/**
	 * <p>
	 * 发送数据给服务端servlet的核心实现方法.<br>
	 * 
	 * 默认发送的是Java序列化对象，子类重写本方法可实现自已的数据发送方式，比如直接发送2进制数据等.
	 * </p>
	 * 
	 * @param _obj 要发送给服务端的数据对象
	 * @param connectionOutputStream 用于数据通信的HttpURLConnection对象所对应的输出流对象，能 过此流完成数据发送
	 * @return 返回输出流对象引用，以便外层调用者统计进行关闭
	 * @throws Exception 过程中产生的任何异常
	 * @see ObjectOutputStream#writeObject(Object)
	 */
	protected OutputStream processSendData(DataFromClient _obj, OutputStream connectionOutputStream) throws Exception
	{
		ObjectOutputStream objOut = new ObjectOutputStream(connectionOutputStream);
		// 写数据
		objOut.writeObject(_obj);
		objOut.flush();
		return objOut;
	}
	
	/**
	 * <p>
	 * 接收服务端servlet数据的核心实现方法.<br>
	 * 默认发送的是Java序列化对象，子类重写本方法可实现自已的数据接收方式，比如直接接收2进制数据等.
	 * <br>
	 * <br>
	 * <b>特别说明：</b>接收的数据将被封装成DataFromServer对象，并请务必确保将此对象放入参数dfsesHandle数组的第0索引位置，
	 * 这是保存接收数据的唯一途径，否则无法保证逻辑的正确性！
	 * </p>
	 * 
	 * @param connectionInputStream 用于数据通信的HttpURLConnection对象所对应的输入流对象，通过此流完成数据读取
	 * @param dfsesHandle
	 * @return 返回输入流对象引用，以便外层调用者统计进行关闭
	 * @throws Exception 过程中产生的任何异常
	 * @see ObjectInputStream#readObject()
	 */
	protected InputStream processReceiveData(InputStream connectionInputStream, DataFromServer[] dfsesHandle) throws Exception
	{
		// 读数据
		ObjectInputStream objIn = new ObjectInputStream(connectionInputStream);
		dfsesHandle[0] = (DataFromServer)objIn.readObject();
		return objIn;
	}
	
	/**
	 * <p>
	 * 负责处理方法 {@link #sendObjToServer(DataFromClient, boolean)}中与服务端数
	 * 据交互过程中的任何异常：异常可能是网络有问题、服务端在处理请求中出现任何故障
	 * 或逻辑错误等，这些故障或错误是关键性的，它们的出现一般情况下就标志着本次客户端
	 * 与服务端间的数据交互失败了.<br>
	 * 默认方法只是 调用{@link Exception#printStackTrace()}.
	 * </p>
	 * 
	 * @param e 与服务端数据交互过程中产生的异常对象
	 */
	protected void processRunningException(Exception e)
	{
		e.printStackTrace();
	}
	/**
	 * <p>
	 * 负责处理方法 {@link #sendObjToServer(DataFromClient, boolean)}的finally部
	 * 分产生的异常：如finally中关闭输入输出流、关闭http连接等过程中出现的错误，它们都
	 * 不能算作关键错误，但本方法存在目的就是提供一个方法调用者监测到这些异常的途径.<br>
	 * 默认方法只是 调用{@link Exception#printStackTrace()}.
	 * </p>
	 * 
	 * @param e
	 */
	protected void processFinallyException(Exception e)
	{
		e.printStackTrace();
	}

    /**
	 * 获得与服务端servlet的连接.
	 *
	 * @param neadReceive true表示发送完数据后要接收servlet返回的数据，
	 * 	否由不接收返回的数据（无论其是否返回数据）
	 * @return HttpConnection 返回一个建立好的连接对象
	 * 
	 * @see URL#openConnection()
	 * @see HttpURLConnection
	 * @see #setCookieHttpHeader(HttpURLConnection)
	 * @see #setOtherHttpHeader(HttpURLConnection)
	 */
	protected HttpURLConnection getServerConnection(boolean neadReceive) throws Exception
	{
		String servletURL = getServletFullQualifyURL();
		//可读/写
		HttpURLConnection c = (HttpURLConnection)new URL(servletURL).openConnection();
		
		/// 可输出信息 
		c.setDoOutput(true);
		
		//** JS 2011-09-07 17:08注：如果不需要读取服务端返回数据时，则不需要显示设置c.setDoInput(false)，
		//** 换句话说如果需要读取则需要c.setDoInput(true)，一定要注意这一点！
		//** [附言]如果不需要读取服务端数据时，且错误地显示设置c.setDoInput(false)则服务端将无法接收到客户
		//** 端请求（正确的做法是不需要显示设置它！），具体c.setDoInput(..)做了什么因无源码无从知晓，以后再详细研究！
		if(neadReceive)
		{
			// 需传回信息 
			c.setDoInput(neadReceive);
			//以下代码是Android上使用HttpURLConnection时的例演示例子，但真加上后读返回值时会报stream closed错误！不加就没事！
//			c.setChunkedStreamingMode(0);
		}
	    
		// 不需要缓存（这是HTTP协议的一个标识，本开发平台不适用这种缓存机制）
		c.setUseCaches(false);
		
		//把原先第一次连接（很可能就是登陆时）获得的cookie信息放入头，以便服务端
		//能取得先前的cookie,并便于服务端据此cookie取出会话判断是安全合法的连接
		//（如能取出这个会话当然就表示它是成功登陆后的会话，这当然是合法的，琐则即不合法）
		setCookieHttpHeader(c);
		//额外的头信息（这个可由子类实现，自由放入其它信息）
		setOtherHttpHeader(c);
		
		// 
		c.setConnectTimeout(30 * 1000); // 20130704 by js
		return c;
	}
	
	/**
	 * 本方法用来设置HttpConnection连接的其它头字段信息，子类若要设置则重写本方法即可（
	 * 如子类可将用户名等信息放入以便服务端取出）。
	 * 注：本方法默认什么也没做，以后用的到的时候再视具体情况自行实现。
	 * @param servletConn
	 * 
	 * @see HttpURLConnection#setRequestProperty(String, String)
	 */
	protected void setOtherHttpHeader(HttpURLConnection servletConn) throws Exception
	{
		//本头字段暂未用到（留待备用）
		servletConn.setRequestProperty("hello_word", "eva.epc_v1.0");
	}

	/**
	 * 连接时设置cookie头，本cookie信息是在第一次连接时获得，以例以后每次都能发往服务端，便于服务端据此
	 * 取出之前的会话.
	 * 
	 * @param servletConn
	 * @throws Exception
	 * @see HttpURLConnection#setRequestProperty(String, String)
	 */
	protected void setCookieHttpHeader(HttpURLConnection servletConn) throws Exception
	{
		//设置cookie
		//** 注：cookie的作用原本是用在网页浏览时因HTTP是无状态的，当想保留用户数据时是没有办法标识的，
		//** 因为不是任何时候都要用户登陆才行（登陆了靠提交过来的用户名也就解决了这个区分不同用户的问题），
		//** 这样的话比如在电子商务网站时将货物加入购物车时你很可能是没登陆的，这时就要用到cookie以便
		//** 据它里的sessionID来标识你提交过来的货物是你的（而不是别人的），就这个作用，不要太当回事。
		//**
		//** 在此的作用：因为服务端要据req.getSession(false)方法获得session为空则表示会话已过期（比如服务重启或超时了，要重登罗）
		//** 这里的获得原始会话方法就得据客户端发送过去的在此处设置的cookie了。
		//**
		//** js,2010-07-03
		if (cookie0 != null)
			servletConn.setRequestProperty(cookie0.key, cookie0.value);
		else if (cookie2 != null)
			servletConn.setRequestProperty(cookie2.key, cookie2.value);
	}
    
	/**
	 * 登陆时存Cookie信息，以便之后的与servlet交互时能判断该用户是已经登陆的用户(否则服务端会报告让重新登陆).
	 * 
	 *  注：服务端返回的conn应该只有第一次登陆成功时，调用getSession(true)新建会话时，
	 *  	才会传给客户端Set-Cookie头信息，这时候保存下来才行，一里弄登陆成功后，就把这个保存的
	 *  	cookie值再发给服务端，此时服务端据此调用getSession(false)就能取得原先的这个session了
	 * 	 	，且这时取得的conn（URLConnection）里是没有Set-Cookie头信息的。
	 *  	综上：服务务端只在新建会话时给客户端发Set-Cookie头信息（包含cookie字符串），非新建的会话
	 * 		再次接时服务端是不是会返回Set-Cookie头信息的，所以保存cookie值在第一次登陆成功后是合理且正确的.
	 * @param conn
	 * @throws Exception
	 * @see {@link CookieHolder#pareseCookieValue(String)}
	 */
	protected void storeCookie(HttpURLConnection conn) throws Exception
    {
        String value0 = CookieHolder.pareseCookieValue(conn.getHeaderField("Set-Cookie"));
        String value2;
        //本值只有在服务端新建会话时才会传过来，老会话交互时服务端再不会传过来，所以要保存cookie
        if(value0 != null)
        {
            if(cookie0 == null)
                cookie0 = new CookieHolder("Cookie", value0);
            else
                cookie0.value = value0;
        }
        else if((value2 = CookieHolder.pareseCookieValue(conn.getHeaderField("Set-Cookie2"))) != null)
        {
            if(cookie2 == null)
                cookie2 = new CookieHolder("Cookie2", value2);
            else
                cookie2.value = value2;
        }
    }
	
	/**
	 * 获得该Servlet的完整URL形式.
	 * 如：http://192.168.1.2:8080/AnyServlet"
	 * 
	 * @return
	 * @see #getServletRootURL()
	 * @see #getServletName()
	 */
	public String getServletFullQualifyURL()
	{
		String rootURL = getServletRootURL();
		return (rootURL.lastIndexOf("/")!=-1?rootURL:"/"+rootURL)+getServletName();
	}
	/**
	 * 获得本servlet服务端封装对象的URL root字符串，形如：http://192.168.1.2:8080
	 * ，注意：它没有slash.
	 * 
	 * @return
	 */
	public String getServletRootURL()
	{
		return this.servletRootURL;
	}
	/**
	 * 获得本servlet服务端封装对象的ServletName字符串，对应于一个服务端web.xml配置
	 * 的servlet-name，形如：MyControler.
	 * 
	 * @return
	 */
	public String getServletName()
	{
		return this.servletName;
	}
	
	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName()
	{
		return this.serviceName;
	}

	/**
	 * 一个Cookie信息封装类.
	 * 
	 * @author Jack Jiang(http://www.52im.net/space-uid-1.html)
	 * @version 1.0
	 */
    private static class CookieHolder
    {
    	public String key;
    	public String value;
    	/**
    	 * key - 标识名称
    	 * value - 值
    	 */
    	public CookieHolder(String key, String value)
    	{
    		this.key = key;
    		this.value = value;
    	}
    	
    	/** 
    	 * 提取HTTP头中Cookie对应值中的起始部分。
    	 *     
    	 * @param cookieHeader HTTP协议头中Cookie对应值
    	 * @return cookieHeader字符串中 ，首个分隔符 ';'前的子字符串。
    	 *     cookieHeader为null ，则返回null否则直接返回cookieHeader字符串作为cookie值
    	 */
        public static String pareseCookieValue(String cookieHeader)
        {
            if (cookieHeader == null)
                return null;
            else
            {
                int delim = cookieHeader.indexOf(";");
                return delim == -1 ? cookieHeader : cookieHeader.substring(0, delim);
            }
        }
        
        @Override
        public String toString()
        {
        	return "key="+key+", value="+value;
        }
    }
}
