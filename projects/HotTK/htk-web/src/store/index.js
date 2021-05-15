import Vue from 'vue'
import vuex from 'vuex'
Vue.use(vuex)
import Web3 from "web3";

import m3t from "../../build/MttToken.json";
import CardToken from "../../build/HeroCard.json";
import CardShopExS from "../../build/CardShop.json";
import Mortgage from "../../build/Mortgage.json";
import Exchangec from "../../build/Exchange.json";
import RttToken from "../../build/RttToken.json";
import OktTokens from "../../build/OktMortgage.json";
// import USDKTokens from "../../build/USDKToken.json";
import {
    CardTokens,
    MttToken,
    CardShopEx,
    Mortgages,
    Exchange,
    RttTokenS,
    OktToken,
    USDKToken
} from "../common/config";
const state = {
    CardTokenFun: null, //初始化CardToken合约方法
    M3Tfun: null, //初始化MTT合约
    RttTokenfun: null, //初始化RTT合约
    CardShopExfun: null, //初始化CardShopEx合约
    ExchangecFun: null, //初始化ExchangecFun合约
    OktTokenFun:null,//初始化OktToken合约
    USDKTokens:null,//初始化USDKToken合约
    MortgageFun: null,
    account: null,
    web3: null,
    cardInfo: [],
    cardToken: null,
    cardType: 0,
    televise: '',
    televise1: '',
    televise2: '',
    televise3: ''
}
const actions = {}
const mutations = {
    async initWeb3Account() { //初始化web3并且初始化合约方法

        if (window.ethereum) {
            this.provider = window.ethereum;
            try {
                await window.ethereum.enable();
            } catch (error) {
                console.log("User denied account access");
            }
        } else if (window.web3) {
            this.provider = window.web3.currentProvider;
        } else {
            this.provider = new Web3.providers.HttpProvider(
                "http://127.0.0.1:7545"
            );
        }
        const web3 = new Web3(this.provider);
        state.web3 = web3 //全局中
        web3.eth.getAccounts().then((accs) => {
            const account = accs[0];
            sessionStorage.setItem("account", account);
        });

        state.CardTokenFun = new web3.eth.Contract(
            CardToken.abi,
            CardTokens
        ); //初始化CardToken合约ABI
        state.M3Tfun = new web3.eth.Contract(
            m3t.abi,
            MttToken
        ); //初始化m3t合约ABI
        state.CardShopExfun = new web3.eth.Contract(
            CardShopExS.abi,
            CardShopEx
        ); //初始化CardShopExfun合约ABI
        state.MortgageFun = new web3.eth.Contract(
            Mortgage.abi,
            Mortgages
        ); //初始化Mortgage合约ABI
        state.ExchangecFun = new web3.eth.Contract(
            Exchangec.abi,
            Exchange
        ); //初始化Exchange合约ABI;
        state.RttTokenfun = new web3.eth.Contract(
            RttToken.abi,
            RttTokenS
        );
        state.OktTokenFun = new web3.eth.Contract(
            OktTokens.abi,
            OktToken
        );
        state.USDKTokenFun = new web3.eth.Contract(
            m3t.abi,
            USDKToken
        );
        // console.log(state,999)
    }
}
const store = {
    actions,
    state,
    mutations,
}
export default store;