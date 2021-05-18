package com.tang;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tang.blockchain.ContractMethod;
import com.tang.blockchain.RankingChain;
import com.tang.blockchain.TutorialToken;
import com.tang.util.AppFilePath;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;

import java.math.BigInteger;
import java.util.List;

import greendao.gen.DaoMaster;
import greendao.gen.DaoSession;
import greendao.gen.GreenDaoHelper;
import okhttp3.OkHttpClient;
import io.realm.Realm;

import static android.support.constraint.Constraints.TAG;

public class AllEvaluateChainApplication extends Application{
    private static AllEvaluateChainApplication sInstance;

    private RefWatcher refWatcher;

    private DaoSession daoSession;
    /**
     * 获取greenDao的daoSession
     *
     * @return daoSession
     */
    public DaoSession getDaoSession() {
        if (daoSession == null) {
            GreenDaoHelper helper = new GreenDaoHelper(this);
            DaoMaster daoMaster = new DaoMaster(helper.getWritableDb()); //创建数据库，并且所有对应有实体的数据类都在该类中创建了对应的数据表
            daoSession = daoMaster.newSession(); //获取dao对象管理者
        }
        return daoSession;
    }

    private static OkHttpClient httpClient;
//    public static RepositoryFactory repositoryFactory;
//    public static SharedPreferenceRepository sp;
//
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }

    public static RefWatcher getRefWatcher(Context context) {
        AllEvaluateChainApplication application = (AllEvaluateChainApplication) context.getApplicationContext();
        return application.refWatcher;
    }


    TutorialToken lqktoken;
    public TutorialToken getLqktoken(){
        return lqktoken;
    }


    RankingChain rankingChain_sol;
    public RankingChain getRankingChain_sol() {
        return rankingChain_sol;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    //    init();

        Realm.init(this);

        refWatcher = LeakCanary.install(this);

        AppFilePath.init(this);


        //**********************************************************
        //测试区块链
        //注意高版本andoroid中网络请求基本都需要放到非主线程中

//        ContractMethod contractMethod = ContractMethod.getInstance();
//
        Web3j web3j;
//        if(ContractMethod.web3j!=null){
//             web3j=ContractMethod.web3j;
//
//        }else{
            //这地方代码比较乱，需要优化一下
            String url="http://10.0.2.2:9545";
            web3j= Web3jFactory.build(new HttpService(url));
//        }



        String user1="0x7c5fdc81096cce9144cc5f542e3a15c1da798c28";
        String user1PrivateKey="b331d320d9aaa1761a5718534572dca63b9175e0ff48eed70592e366bb7196e6";


        int chainTestNum=0;

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {

                    /*
                     * 原来模拟器默认把127.0.0.1和localhost当做本身了，在模拟器上可以用10.0.2.2代替127.0.0.1和localhost，
                     * 另外如果是在局域网环境可以用 192.168.0.x或者192.168.1.x(根据具体配置)连接本机,这样应该就不会报错了。
                     *
                     * */
                    if(web3j!=null){




                    }

                    Request<?,Web3ClientVersion> request= web3j.web3ClientVersion();
                    Web3ClientVersion response=request.send();
                    String version=response.getWeb3ClientVersion();
                    Log.e(TAG,"web3j version:" +version);

                    EthAccounts ethAccounts=web3j.ethAccounts().sendAsync().get();
                    List<String> accountList=ethAccounts.getAccounts();
                    for(int i=0;i<accountList.size();i++){
                        Log.e(TAG,"web3j account:"+i+":" +accountList.get(i));
                    }

                    Credentials credentials= Credentials.create(user1PrivateKey);
                    Log.e(TAG,"web3j credentials address:"+credentials.getAddress());
                    //智能合约部署，这里的部署只能管android APP,链上已经部署了，这里其实是加载。也可以用load函数
                    lqktoken=TutorialToken.deploy(web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT).send();
                    Log.e(TAG,"web3j lqktoken contract address:"+lqktoken.getContractAddress());



                    rankingChain_sol= RankingChain.deploy(web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT,"0x7c5fdc81096cce9144cc5f542e3a15c1da798c28",BigInteger.valueOf(1000)).send();
                    Log.e(TAG,"web3j rankingChain_sol contract address:"+rankingChain_sol.getContractAddress());


                    //查询余额
                    BigInteger userBalances=lqktoken.balanceOf(user1).send();
                    Log.e(TAG,"user1 balances:"+userBalances.toString());

                    //转账
                    String user2="0x1b3f6cb3ef515757c486150497a5fef44bc3a10d";
                    TransactionReceipt transactionReceipt=lqktoken.transfer(user2,BigInteger.valueOf(3000)).send();
                    String status=transactionReceipt.getStatus();

                    //转账结果
                    userBalances=lqktoken.balanceOf(user1).send();
                    Log.e(TAG,"user1 balances:"+userBalances.toString());
                    userBalances=lqktoken.balanceOf(user2).send();
                    Log.e(TAG,"user2 balances:"+userBalances.toString());





                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
//***************************************************************







    }


    public static AllEvaluateChainApplication getsInstance() {
        return sInstance;
    }

//   protected void init() {
//
////        sp = SharedPreferenceRepository.init(getApplicationContext());
////
////        httpClient = new OkHttpClient.Builder()
////                .addInterceptor(new LogInterceptor())
////                .build();
////
////        Gson gson = new Gson();
////
////        repositoryFactory = RepositoryFactory.init(sp, httpClient, gson);
////
////
////        //创建数据库表
////        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "wallet", null);//这里是数据库名称，如果需要多个数据表，实际上只要生成不同的Entity, xxDao就行了
////        SQLiteDatabase db = mHelper.getWritableDatabase();
////        daoSession = new DaoMaster(db).newSession();
//
//
//    }


//    public static OkHttpClient okHttpClient() {
//        return httpClient;
//    }
//
//    public static RepositoryFactory repositoryFactory() {
//        return  repositoryFactory;
//    }




}
