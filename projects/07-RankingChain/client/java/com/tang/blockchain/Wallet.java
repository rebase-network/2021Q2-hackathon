package com.tang.blockchain;

import android.content.Context;
import android.util.Log;

import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.tang.AllEvaluateChainApplication;
import com.tang.blockchain.WalletBean;
import com.tang.data_entity.FilmEvaluateDataToSave;
import com.tang.util.PreferencesListDataSave;

import greendao.gen.TokenDao;

/**
 * Created by x on 2018/6/8.
 */

//这里钱包文件保存在文件中，不是保存在本地数据库中。最好改为保存到本地数据库中
    //由于本应用不是钱包所以不需要，导出私钥等操作。

    //参考钱包创建数据的简单方式来实现。
public class Wallet {

    private static final Wallet INSTANCE = new Wallet();

    private static Credentials credentials;

    public Credentials getCredentials() {
        return credentials;
    }

    private static List<WalletBean> walletBeanList =new ArrayList<>();  //所有钱包文件

    //token相关数据保存在greenDao中，各种操作也从这里实现
    private static ArrayList<Token> tokenList=new ArrayList<>();
    public static TokenDao tokenDao= AllEvaluateChainApplication.getsInstance().getDaoSession().getTokenDao();


    private String currentName = "";
    private String currentAddress="";
    private String currentPassword="";
    private WalletBean currentWalletBean;

    private Wallet() {}

    public static Wallet getInstance() {

        return INSTANCE;


    }

    public static void main(String[] args) {
        Wallet wallet = getInstance();
    }


    public String getCurrentName() {
        return currentName;
    }
    public String getCurrentAddress(){
        return currentAddress;
    }
    public String getCurrentPassword(){return currentPassword;
    }
    public WalletBean getCurrnetWalletBean(){return currentWalletBean;}

    private void setCurrentWalletInfo(String currentName){

        for(int i=0;i<walletBeanList.size();i++){
            if(walletBeanList.get(i).getName().equals(currentName)){
                currentAddress= walletBeanList.get(i).getAddress();
                currentPassword=walletBeanList.get(i).getPassword();
                currentWalletBean=walletBeanList.get(i);
            }
        }

    }


    //下述方法实在有点烂
    private List<String> names = new ArrayList<>();
    private List<String> passwords = new ArrayList<>();
    private List<String> filepaths = new ArrayList<>();
    private List<String> addresses = new ArrayList<>();
    public List getNames(){
        return names;
    }
    public List getPasswords(){
        return passwords;
    }
    public List getFilepaths(){
        return filepaths;
    }
    public List getAddresses(){
        return addresses;
    }

    //ListDataSave dataSave;
    //private List namelists1 = dataSave.getDataList("namelists");
    //private List lists1 = dataSave.getDataList("lists");


    public List getWalletBeanList(String filepath) throws Exception {  //这里根据路径获得了所有钱包文件及所有钱包文件的信息列表
        File file1 = new File("walletBean.adt");
        File f = new File(filepath+"/"+file1);
        if (f.exists()){
            List<WalletBean> temp_lists =DeserializeWallet(filepath);
            walletBeanList.clear();
            walletBeanList.addAll(temp_lists);

            for (WalletBean walletBean:walletBeanList){
                String tn = walletBean.getName();
                if (!names.contains(tn)) {

                    String tp = walletBean.getPassword();
                    String tf = walletBean.getPath();
                    String ad = walletBean.getAddress();
                    names.add(tn);
                    passwords.add(tp);
                    filepaths.add(tf);
                    addresses.add(ad);
                }
            }
        }
        return walletBeanList;
    }

    /**
     * @param name      current name of wallet
     * @param password  password user once set
     * @param filename  including filepath+filename!!!!
     */
    public void useWallet(String name,String password, String filename){
        try {
            if (!filename.equals("")) {
                currentName = name;


                setCurrentWalletInfo(currentName);

                //这里面其实通过钱包获得对应的公钥和私钥进行了通证验证。
                credentials = WalletUtils.loadCredentials(password, filename);  //这里最慢，所有等待工作都在这里
                //这里来看钱包验证与获得鱼的步骤是没有关联，相互独立的。并且钱包创建与链无关。


                //这里需要引用对应的智能合约。并且智能合约的连接及配置在相应的封装包中。

//                FishAPI.initCredentials();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        }
    }

    public void importWallet(String name,String password,String privateKey,String filepath){ //导入钱包

        String pk = "";
        WalletBean walletBean = new WalletBean();
        if (privateKey.equals("")){
            pk = "65e080f727d9ddca08bff41f57283fc7d5e032bb5af8de963dade6a6caaa1ec4";
        }else {
            pk = privateKey;
        }
        ECKeyPair ecKeyPair = ECKeyPair.create(new BigInteger(pk,16));
        String pri = Numeric.toHexStringWithPrefix(ecKeyPair.getPrivateKey());
        credentials = Credentials.create(ecKeyPair);

        String address = credentials.getAddress();
        String pub = Numeric.toHexStringWithPrefix(ecKeyPair.getPublicKey());

        try {
            String filename = WalletUtils.generateWalletFile(password,ecKeyPair,new File(filepath),false);
            System.out.println("f: "+filepath+"/"+filename);
            String file=filepath+"/"+filename;

            //File file1 = new File("walletBean.adt");
            File f = new File(filepath+"/walletBean.adt");
            if (f.exists()){
                walletBeanList=DeserializeWallet(filepath);
            }

            //namelists.add(name);
            walletBean.setName(name);
            walletBean.setPassword(password);
            walletBean.setAddress(address);
            walletBean.setPath(file);
            walletBeanList.add(walletBean);
            SerializeWallet(filepath);
            //dataSave.setDataList("namelists",namelists);
            //dataSave.setDataList("lists",lists);

            //System.out.println("namelists: "+namelists);
            System.out.println("lists: "+walletBeanList);
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("original private key: "+pk);
        System.out.println("private key: "+pri+"\npublic key: "+pub+"\naddress: "+address);

    }


    public void createWallet(String name,String password,String filepath){
        try {
            WalletBean walletBean = new WalletBean();
            String filename = WalletUtils.generateLightNewWalletFile(password,new File(filepath));
            System.out.println("f: "+filepath+"/"+filename);
            String file=filepath+"/"+filename;
            credentials = WalletUtils.loadCredentials(password,filepath+"/"+filename);
            String address = credentials.getAddress();
            ECKeyPair ecKeyPair = credentials.getEcKeyPair();
            String pri = Numeric.toHexStringWithPrefix(ecKeyPair.getPrivateKey());
            String pub = Numeric.toHexStringWithPrefix(ecKeyPair.getPublicKey());
            //File file1 = new File("walletBean.adt");
            File f = new File(filepath+"/walletBean.adt");
            if (f.exists()){
                walletBeanList=DeserializeWallet(filepath);
            }

            walletBean.setName(name);
            walletBean.setPassword(password);
            walletBean.setAddress(address);
            walletBean.setPath(file);
            walletBeanList.add(walletBean);
            SerializeWallet(filepath);
            //dataSave.setDataList("namelists",namelists);
            //dataSave.setDataList("lists",lists);


            System.out.println("lists: "+walletBeanList);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (CipherException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void SerializeWallet(String filepath) throws IOException {
        //File file = new File("walletBean.adt");
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(filepath+"/walletBean.adt")));
        WalletBean[] walletBean = new WalletBean[walletBeanList.size()];
        walletBeanList.toArray(walletBean);
        out.writeObject(walletBean);
        System.out.println("walletBean对象序列化成功！");
        out.close();
    }

    private static List<WalletBean> DeserializeWallet(String filepath) throws Exception, IOException {
        //File file = new File("walletBean.adt");
        ObjectInputStream out = new ObjectInputStream(new FileInputStream(new File(filepath+"/walletBean.adt")));
        //执行反序列化读取
        WalletBean[] walletBean = (WalletBean[]) out.readObject();
        //将数组转换成List
        List<WalletBean> listObject = new ArrayList<WalletBean>(Arrays.asList(walletBean));
        System.out.println("walletBean对象反序列化成功！");
        return listObject;
    }
















    //下面暂时没有用到，后面如果需要该功能时可以直接用
    //-------------Token的greendao操作--------------//
    //TokenId与钱包Id一样用自增，没有问题。

    //添加Token，采用greenDao。先查找一下对应的钱包中是否有该Token,如果没有就插入，如果有就直接修改是否显示变量。
    public void insertToken(Token token,boolean IsDisplay){
        List<Token> tokens=tokenDao.loadAll();

        for(int i=0;i<tokens.size();i++){
            //查找同一钱包下的同一个Token
            if((tokens.get(i).getWalletAddrges().equals(token.getWalletAddrges()))&&(tokens.get(i).getName().equals(token.getName()))){
                tokens.get(i).setIsDisply(IsDisplay);
                return;
            }
        }
        tokenDao.insert(token); //没有找到就直接插入一个

    }

    //根据钱包名称获得相关的Token。在加载所有数据时只调用这个。应该要根据钱包地址来分类，钱包名称可以重复
    public List<Token>  getTokenListByWalletAddress(String walletAddress){
        List<Token> tokens=tokenDao.loadAll();
        List<Token> walletTokens=new LinkedList<>();

        for(int i=0;i<tokens.size();i++){
            //获得可以显示的token数据。
            if((walletAddress.equals(tokens.get(i).getWalletAddrges()))&&(tokens.get(i).getIsDisply())){
                walletTokens.add(tokens.get(i));

            }

        }
        return walletTokens;
    }


    public void deleteToken(Token token){


    }

    public void deleteTokenById(Long id){
        tokenDao.deleteByKey(id);

    }



    //不需要跟新，只需添加或删除。
    public void updateToken(Token token){





    }





}
