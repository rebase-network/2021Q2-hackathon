package com.tang.blockchain;


import android.widget.TextView;

import com.tang.AllEvaluateChainApplication;
import com.tang.blockchain.config.Configuration;
import com.tang.blockchain.config.Web3jUtil;
import com.tang.util.LogUtils;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jFactory;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

//封装操作智能合约的方法
public class ContractMethod {
    public static Web3j web3j;

//  private static FishContracts_sol_FishCore fishCore;

    //连评合约

    public static RankingChain rankingChain_sol;




    public static Credentials getCredentials() {
        return credentials;
    }


    private static Credentials credentials;

    private static final ContractMethod INSTANCE = new ContractMethod();

    public static ContractMethod getInstance(){

        if (web3j == null){
            initialize();
        }

        return INSTANCE;
    }

    private ContractMethod(){}

    static void initialize(){
        connect();
        initCredentials();
    }




    private static void connect(){  //这里与远程进行了连接。可以添加一个本地连接，这样就能
//        web3j = Web3jUtil.buildHttpClient(); //这是远程连接

        //本地连接
        String url="http://10.0.2.2:9545";
        web3j= Web3jFactory.build(new HttpService(url));

    }

    static void initCredentials(){
        Wallet wallet = Wallet.getInstance();
        credentials = wallet.getCredentials();


        //这里智能合约有问题，部署时不能获得对象，目前放到本应用的启动界面。


        //获得自己的智能合约。把调用智能合约的业务放到下面
//      fishCore = FishContracts_sol_FishCore.load(Configuration.contractAddress,web3j,credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);

//        TutorialToken tutorialToken=TutorialToken.load("0xa1fee17E29f76840EfCCDCfe37bEf9cC4B7DFCf4",web3j,credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//        TutorialToken.deploy(web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT).send();

        //这里出问题了
//        rankingChain_sol=RankingChain.load(Configuration.contractAddress,web3j,credentials, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT);
//        try {
//            rankingChain_sol=RankingChain.deploy(web3j,credentials, Contract.GAS_PRICE,Contract.GAS_LIMIT,BigInteger.valueOf(10),"0x7c5fdc81096cce9144cc5f542e3a15c1da798c28",BigInteger.valueOf(1000)).send();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        int a=0;

    }


    //签名方法






    //***********交易
    // transfer ether
    public Single<String> createEthTransaction(String from, String to, BigInteger amount, BigInteger gasPrice, BigInteger gasLimit, String password) {



        return getLastTransactionNonce(web3j, from)
                .flatMap(nonce -> Single.fromCallable( () -> {

//                    Credentials credentials = WalletUtils.loadCredentials(password,  from.getKeystorePath());
                    Credentials credentials=ContractMethod.credentials;
                    RawTransaction rawTransaction = RawTransaction.createEtherTransaction(nonce, gasPrice, gasLimit, to, amount);
                    byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);

                    String hexValue = Numeric.toHexString(signedMessage);
                    EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

                    return ethSendTransaction.getTransactionHash();

                } ).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread()));
    }

    // transfer ERC20
    public Single<String>  createERC20Transfer(String from,  String to, String contractAddress, BigInteger amount, BigInteger gasPrice, BigInteger gasLimit, String password) {

        String callFuncData = createTokenTransferData(to, amount);


        return getLastTransactionNonce(web3j, from)
                .flatMap(nonce -> Single.fromCallable( () -> {

//                    Credentials credentials = WalletUtils.loadCredentials(password,  from.getKeystorePath());

                    Credentials credentials=ContractMethod.credentials;//直接获取数据
                    RawTransaction rawTransaction = RawTransaction.createTransaction(
                            nonce, gasPrice, gasLimit, contractAddress, callFuncData);

                    LogUtils.d("rawTransaction:" + rawTransaction);

                    byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);

                    String hexValue = Numeric.toHexString(signedMessage);
                    EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();

                    return ethSendTransaction.getTransactionHash();

                } ).subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread()));
    }






    //对于Token的转账，是通过调用Token的合约来进行相关交易就行了。

    //******获得智能合约的余额
    public BigDecimal getBalance(String walletAddress, TokenInfo tokenInfo) throws Exception {
        org.web3j.abi.datatypes.Function function = balanceOf(walletAddress);
        String responseValue = callSmartContractFunction(function, tokenInfo.address, walletAddress);

        List<Type> response = FunctionReturnDecoder.decode(
                responseValue, function.getOutputParameters());
        if (response.size() == 1) {
            return new BigDecimal(((Uint256) response.get(0)).getValue());
        } else {
            return null;
        }
    }

    private static org.web3j.abi.datatypes.Function balanceOf(String owner) {
        return new org.web3j.abi.datatypes.Function(
                "balanceOf",
                Collections.singletonList(new Address(owner)),
                Collections.singletonList(new TypeReference<Uint256>() {}));
    }

    private String callSmartContractFunction(org.web3j.abi.datatypes.Function function, String contractAddress, String walletAddress) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);

        org.web3j.protocol.core.methods.response.EthCall response = web3j.ethCall(
                Transaction.createEthCallTransaction(walletAddress, contractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();

        return response.getValue();
    }
    //****************







    public Single<String> create(String from, String to, BigInteger subunitAmount, BigInteger gasPrice, BigInteger gasLimit,  String data, String pwd)
    {
        return createTransaction(from, to, subunitAmount, gasPrice, gasLimit, data, pwd)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<String> createTransaction(String from, String toAddress, BigInteger subunitAmount, BigInteger gasPrice, BigInteger gasLimit, String data, String password) {


        return getLastTransactionNonce(web3j, from)
                .flatMap(nonce -> getRawTransaction(nonce, gasPrice, gasLimit,toAddress, subunitAmount,  data))
                .flatMap(rawTx -> signEncodeRawTransaction(rawTx, password, from  /*, networkRepository.getDefaultNetwork().chainId*/ ))
                .flatMap(signedMessage -> Single.fromCallable( () -> {
                    EthSendTransaction raw = web3j
                            .ethSendRawTransaction(Numeric.toHexString(signedMessage))
                            .send();
                    if (raw.hasError()) {
                        throw new Exception(raw.getError().getMessage());
                    }
                    return raw.getTransactionHash();
                })).subscribeOn(Schedulers.io());
    };



    public Single<String> createContract(String from, BigInteger gasPrice, BigInteger gasLimit, String data, String pwd) {
        return createTransaction(from, gasPrice, gasLimit, data, pwd)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
    }

    // for DApp create contract transaction
    public Single<String> createTransaction(String from, BigInteger gasPrice, BigInteger gasLimit, String data, String password) {


        return getLastTransactionNonce(web3j, from)
                .flatMap(nonce -> getRawTransaction(nonce, gasPrice, gasLimit, BigInteger.ZERO, data))
                .flatMap(rawTx -> signEncodeRawTransaction(rawTx, password, from  /*, networkRepository.getDefaultNetwork().chainId*/ ))
                .flatMap(signedMessage -> Single.fromCallable( () -> {
                    EthSendTransaction raw = web3j
                            .ethSendRawTransaction(Numeric.toHexString(signedMessage))
                            .send();
                    if (raw.hasError()) {
                        throw new Exception(raw.getError().getMessage());
                    }
                    return raw.getTransactionHash();
                })).subscribeOn(Schedulers.io());

    }


    // for DApp  create contract  transaction
    private Single<RawTransaction> getRawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, BigInteger value, String data)
    {
        return Single.fromCallable(() ->
                RawTransaction.createContractTransaction(
                        nonce,
                        gasPrice,
                        gasLimit,
                        value,
                        data));
    }

    private Single<RawTransaction> getRawTransaction(BigInteger nonce, BigInteger gasPrice, BigInteger gasLimit, String to , BigInteger value, String data)
    {
        return Single.fromCallable(() ->
                RawTransaction.createTransaction(
                        nonce,
                        gasPrice,
                        gasLimit,
                        to,
                        value,
                        data));
    }

    private  Single<byte[]> signEncodeRawTransaction(RawTransaction rtx, String password, String from  /*, int chainId*/) {

        return Single.fromCallable(() -> {
//            Credentials credentials = WalletUtils.loadCredentials(password, wallet.getKeystorePath());
            Credentials credentials=ContractMethod.credentials;
            byte[] signedMessage = TransactionEncoder.signMessage(rtx, credentials);
            return signedMessage;
        });
    }




    //**相关辅助方法

    //获得最新的的交易次数
    public Single<BigInteger> getLastTransactionNonce(Web3j web3j, String walletAddress)
    {
        return Single.fromCallable(() -> {
            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(walletAddress, DefaultBlockParameterName.PENDING)   // or DefaultBlockParameterName.LATEST
                    .send();
            return ethGetTransactionCount.getTransactionCount();
        });
    }

    public static String createTokenTransferData(String to, BigInteger tokenAmount) {
        List<Type> params = Arrays.<Type>asList(new Address(to), new Uint256(tokenAmount));

        List<TypeReference<?>> returnTypes = Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {
        });

        Function function = new Function("transfer", params, returnTypes);
        return FunctionEncoder.encode(function);
    }

    /////////////////////////////////下述对以太币的转账
    /**
     *
     * @param to       String     receiver address
     * @param ETHValue Double     ETH!!!  not wei!!!
     */

    public boolean sendETH(String to, Double ETHValue) {

        try {
            TransactionReceipt transactionReceipt = Transfer.sendFunds(
                    web3j, credentials, to,
                    BigDecimal.valueOf(ETHValue), Convert.Unit.ETHER)
                    .send();
            // get tx hash and tx fees
            String txHash = transactionReceipt.getTransactionHash();
            BigInteger txFees = transactionReceipt
                    .getCumulativeGasUsed()
                    .multiply(Configuration.GAS_PRICE);

            System.out.println("hash: " + txHash);
            System.out.println("fees: " + Web3jUtil.weiToEther(txFees));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     * @return your wallet's eth balance
     */

    public  BigDecimal getETHBalance(){

        try {
            return Web3jUtil.getBalanceEther(web3j,credentials.getAddress());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return new BigDecimal("0");

    }


    //自身Token的转账实现

//////////////////////////////////////////////////////////////////////////////


//下述实现业务智能合约的相关调用。







}
