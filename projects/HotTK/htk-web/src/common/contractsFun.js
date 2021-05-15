import {
    CardShopEx,
    MttToken,
    CardTokens,
    RttTokenS,
    Exchange,
    CardDetailS,
    CardTokenY,
    Mortgages,
    OktToken,
    USDKToken
} from "../common/config"
import NCWeb3 from "@/common/globals.dbug";
import CardShop from "../../build/CardShop.json"
import MttTokens from "../../build/MttToken.json"
import HeroCard from "../../build/HeroCard.json"
import RttToken from "../../build/RttToken.json"
import Exchanges from "../../build/Exchange.json"
import CardDetail from "../../build/CardDetail.json"
import CardToken from "../../build/CardToken.json"
import Mortgage from "../../build/Mortgage.json"
import OktTokens from "../../build/OktMortgage.json";
// import USDKTokens from "../../build/USDKToken.json";
let web3;
NCWeb3.Init(addr => {
    //得到相应的钱包地址
    console.log(addr);
}, provider => {
    web3 = provider
});
export default {
    CardShop() {
        const res = new web3.eth.Contract(
            CardShop.abi,
            CardShopEx
        );
        return res
    },
    MttToken() {
        const res = new web3.eth.Contract(
            MttTokens.abi,
            MttToken
        );
        return res
    },
    HeroCard() {
        const res = new web3.eth.Contract(
            HeroCard.abi,
            CardTokens
        );
        //console.log(res)
        return res
    },
    RttToken() {
        const res = new web3.eth.Contract(
            RttToken.abi,
            RttTokenS
        );
        //console.log(res)
        return res
    },
    Exchange() {
        const res = new web3.eth.Contract(
            Exchanges.abi,
            Exchange
        );
        //console.log(res)
        return res
    },
    CardDetail() {
        const res = new web3.eth.Contract(
            CardDetail.abi,
            CardDetailS
        );
        //console.log(res)
        return res
    },
    CardToken() {
        const res = new web3.eth.Contract(
            CardToken.abi,
            CardTokenY
        );
        //console.log(res)
        return res
    },
    Mortgage() {
        const res = new web3.eth.Contract(
            Mortgage.abi,
            Mortgages
        );
        //console.log(res)
        return res
    },
    OktTokens() {
        const res = new web3.eth.Contract(
            OktTokens.abi,
            OktToken
        );
        //console.log(res)
        return res
    },
    USDKTokens() {

        const res = new web3.eth.Contract(
            MttTokens.abi,
            USDKToken
        );
        console.log(USDKToken)
        return res
    }
}