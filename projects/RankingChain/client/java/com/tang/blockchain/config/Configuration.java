package com.tang.blockchain.config;

import java.math.BigInteger;


//注意这里的配合要修改为我们自己的配置信息
public class Configuration {

    // see https://www.reddit.com/r/ethereum/comments/5g8ia6/attention_miners_we_recommend_raising_gas_limit/
    public static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);

    // http://ethereum.stackexchange.com/questions/1832/cant-send-transaction-exceeds-block-gas-limit-or-intrinsic-gas-too-low
    public static final BigInteger GAS_LIMIT_ETHER_TX = BigInteger.valueOf(21_000);
    public static final BigInteger GAS_LIMIT_GREETER_TX = BigInteger.valueOf(500_000L);
    public static String RPC_URL = "https://ropsten.infura.io/yCBWSUS7mbDeBBazCvvK";

    public static String contractAddress="0x85763ca7dd3b68efBd8A91C4b92532F86AF197E2"; //众评连合约地址
    public static String contractOwner = "0x7C5FDC81096CcE9144Cc5F542E3a15C1dA798C28";

    public static String[] rares = new String[]{"普通","稀有","卓越","史诗","神话","传说","超神","宇宙之主"};

    public static int getIndexOfRares(String str){
        int index = 0;
        for (String rare:rares){
            if (rare.equals(str)){
                return index;
            }
            index ++;
        }
        return index;
    }

    public static String getColorOfRare(String str){
        String color = "";
        if (str.equals("普通")) {color = "#C4C4C4";}  // 灰白
        else if(str.equals("稀有")) {color = "#32CD32";}  // 绿色
        else if(str.equals("卓越")) {color = "#00B2EE";}  // 浅蓝
        else if(str.equals("史诗")) {color = "#0000FF";} //深蓝
        else if(str.equals("神话")) {color = "#9B30FF";}  // 紫色
        else if(str.equals("传说")) {color = "#EE6AA7";}  // 粉色
        else if(str.equals("超神")) {color = "#EEB422";}  // 金色
        else if(str.equals("宇宙之主")) {color = "#EE0000";} // 红色

        return color;
    }
}
