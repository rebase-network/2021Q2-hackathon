<template>
  <div class="sellInfo">
    <div class="sellInfo-Conter">
      <header>
        <div class="header-img-box">
          <div class="header-left">
            <div class="header-left-cont" @click="cardInfoBtn">
              <div class="img-left">
                <img src="../assets/img/29.png" class="notice-img" />
              </div>
              <span>返回</span>
            </div>
          </div>
          <div class="header-c">卡片信息</div>
        </div>
        <div class="header-img-h">
          <img src="../assets/head/head.png" class="notice-img" />
        </div>
      </header>
      <div class="deposit-num">
        <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
        <div class="img-h">
          <img src="../assets/img/44.png" class="head-img" />
        </div>
        <div class="img-b24">
          <img src="../assets/img/42.png" class="head-img" />
        </div>
        <div class="img-f">
          <img src="../assets/img/43.png" class="head-img" />
        </div>
        <div class="img-min">
          <img src="../assets/img/41.png" class="head-img" />
        </div>
        <div class="deposit-num-cont">
          <div class="home-cont-h-l">
            <div class="home-cont-h-t">卡片信息</div>
            <img src="../assets/img/3.png" class="head-img" />
          </div>
          <div class="cardInfo-conter">
            <div class="cardInfo-img">
              <img :src="imgUrl" class="head-img" />
            </div>
          </div>
          <div class="cardInfo-ts">卡片属性</div>
          <div class="cardInfo-ts-box">
            <div class="upgrade-b-c">
              <div class="upgrade-left">卡片属性</div>
              <div class="upgrade-right" style="color: #f94f1b">
                {{ cardType }}
              </div>
            </div>
            <div class="upgrade-b-c">
              <div class="upgrade-left">卡片价格(金条)</div>
              <div class="upgrade-right">{{ OktV }}</div>
            </div>
            <div class="upgrade-b-c">
              <div class="upgrade-left">战力</div>
              <div class="upgrade-right">{{ CardInfo.power }}</div>
            </div>
          </div>
          <div class="cardInfo-ts-box1">
            <div class="cardInfo-ts-box1-T">输入卡片价格（金条）:</div>
            <div class="cardInfo-ts-box1-input">
              <input
                type="Number"
                v-model="OktV"
                placeholder="请输入挂单金额"
                :disabled="disabled"
              />
            </div>
            <!-- <div class="cardInfo-ts-box1-btn">
              <img src="../assets/img/2.png" class="head-img" />
              <div class="cardInfo-ts-box1-btn-c" @click="OktvBtn">确定</div>
            </div> -->
          </div>
          <div class="info-btn">
            <div class="info-btn-box" @click="warrantBtn" v-if="showBuy1">
              <div class="info-text-c">授权</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div>
            <!-- <div class="info-btn-box" v-if="!showBuy1">
              <div class="info-text-c">授权</div>
              <img src="../assets/img/4.png" class="head-img" />
            </div> -->
            <!-- <div class="info-btn-box1" v-if="!showBuy">
              <div class="info-text-c" @click="sellInfoBtn1">点击挂单</div>
              <img src="../assets/img/4.png" class="head-img" />
            </div> -->
            <div class="info-btn-box" v-if="showSate">
              <div class="info-text-c">
                <div class="info-text-c1">Pending</div>
                <div class="info-text-c2">
                  <div></div>
                  <div></div>
                  <div></div>
                </div>
              </div>
              <img src="../assets/img/4.png" class="head-img" />
            </div>
            <div class="info-btn-box1" v-if="showBuy">
              <!-- 我的背包 -->
              <div class="info-text-c" @click="sellInfoBtn">点击挂单</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// import Exchangec from "../../build/Exchange.json";
// import { Exchange} from "../common/config";
import { Toast } from "mint-ui";
export default {
  data() {
    return {
      CardInfo: {},
      OktV: null,
      imgUrl: null,
      cardType: "",
      showBuy: false,
      showBuy1: true,
      disabled: false,
      account: "",
      Web3: "",
      addrsType: {},
      showSate: false,
    };
  },
  async mounted() {
    await this.$getWbe3.Init(
      (addr) => {
        //得到相应的钱包地址
        this.account = addr;
        // console.log(this.account);
      },
      (provider) => {
        this.Web3 = provider;
        // console.log(this.Web3);
      }
    );
    await this.$store.mutations.initWeb3Account();
    await this.GETCONTRACTS();
    // let addrsType = localStorage.getItem("addrsType");
    // if (addrsType) {
    //   this.addrsType = JSON.parse(addrsType);

    // } else {
    //   this.addrsType.index = 2;
    // }
  },
  methods: {
    async GETCONTRACTS() {
      var CardInfo = await JSON.parse(sessionStorage.getItem("carToken"));
      if (CardInfo[0].imgUrl) {
        this.imgUrl = require("../assets/cardimg/" +
          CardInfo[0].imgUrlC +
          ".png");
        this.CardInfo = CardInfo[0];
        console.log(this.CardInfo);
        if (this.CardInfo.genre == "1") {
          this.cardType = "金卡";
        }
        if (this.CardInfo.genre == "2") {
          this.cardType = "橙卡";
        }
        if (this.CardInfo.genre == "3") {
          this.cardType = "紫卡";
        }
        if (this.CardInfo.genre == "4") {
          this.cardType = "蓝卡";
        }
        if (this.CardInfo.genre == "5") {
          this.cardType = "白卡";
        }
      }

      this.web3 = this.$store.state.web3;
      this.account = sessionStorage["account"]; //取出缓存中的钱包地址;
      this.ExchangecFun = this.$store.state.ExchangecFun;
      this.CardTokenExFun = this.$store.state.CardTokenFun;
    },
    cardInfoBtn() {
      this.$router.push("/business");
    },
    // OktvBtn() {
    //   console.log(this.OktV);
    // },
    async warrantBtn() {
      console.log(this.OktV);
      this.disabled = true;

      if (this.OktV) {
        this.showSate = true;
        this.showBuy1 = false;
        let addr = this.$Fun.Exchange().options.address;
        let contract = await this.$Fun.CardToken();
        const gasPrice = await this.Web3.eth.getGasPrice();
        await this.$getWbe3.Approve(
          addr,
          this.CardInfo.tokenId,
          contract,
          gasPrice,
          this.account,
          (res) => {
            if (res) {
              // Toast("授权成功");
              Toast({
                message: "授权成功",
                position: "middle",
                duration: 1000,
              });
              this.showBuy1 = false;
              this.showBuy = true;
              this.showSate = false;
            }
          }
        );

        // const gas = await this.CardTokenExFun.methods
        //   .approve(this.ExchangecFun.options.address, this.CardInfo.tokenId)
        //   .estimateGas({ from: this.account });

        // console.log(gas);
        // const gasPrice = await this.web3.eth.getGasPrice();
        // await this.CardTokenExFun.methods
        //   .approve(this.ExchangecFun.options.address, this.CardInfo.tokenId)
        //   .send({ from: this.account, gas: gas, gasPrice: gasPrice })
        //   .then((res) => {
        //     console.log(res);
        //     // Toast("授权成功");
        //     Toast({
        //       message: "授权成功",
        //       position: "middle",
        //       duration: 1000,
        //     });
        //     this.showBuy = true;
        //   });
      } else {
        // Toast("单价不能为空");
        Toast({
          message: "单价不能为空",
          position: "middle",
          duration: 1000,
        });
        this.disabled = false;
      }
    },
    sellInfoBtn1() {
      // Toast("请先授权");
      Toast({
        message: "请先授权",
        position: "middle",
        duration: 1000,
      });
    },
    async sellInfoBtn() {
      this.showBuy1 = false;
      this.showBuy = false;
      this.showSate = true;
      let contract = this.$Fun.Exchange();
      const gasPrice = await this.Web3.eth.getGasPrice();
      var oktV = this.Web3.utils.toWei(this.OktV.toString(), "ether");
      //let Mttcontract = this.$Fun.MttToken().options.address;
      // this.$router.push("/sellEnd");
      await this.$getWbe3.put(
        contract,
        this.CardInfo.tokenId,
        this.account,
        gasPrice,
        "RttToken",
        oktV,
        (res) => {
          if (res) {
            this.showSate = false;
            sessionStorage.setItem("objList", JSON.stringify(this.CardInfo));
            this.$router.push("/sellEnd");
          }
        }
      );

      // console.log(this.ExchangecFun);
      // this.showBuy1 = false;
      // this.showBuy = false;
      // const gas = await this.ExchangecFun.methods
      //   .put(
      //     this.CardInfo.tokenId,
      //     this.$store.state.M3Tfun.options.address,
      //     this.web3.utils.toWei(this.OktV.toString(), "ether")
      //   )
      //   .estimateGas({ from: this.account });
      // const gasPrice = await this.web3.eth.getGasPrice();

      // await this.ExchangecFun.methods
      //   .put(
      //     this.CardInfo.tokenId,
      //     this.$store.state.M3Tfun.options.address,
      //     this.web3.utils.toWei(this.OktV.toString(), "ether")
      //   )
      //   .send({ from: this.account, gas: gas, gasPrice: gasPrice })
      //   .then((res) => {
      //     if (res) {
      //       sessionStorage.setItem("objList", JSON.stringify(this.CardInfo));
      //       this.$router.push("/sellEnd");
      //     }
      //   });
    },
  },
};
</script>

<style scoped>
.sellInfo {
  width: 100%;
  height: 100%;
}
.sellInfo-Conter {
  max-width: 420px;
  height: 100%;
  background-color: #000;
  margin: auto;
  position: relative;
  overflow: hidden;
}
header {
  width: 100%;
  height: 56px;
  background: linear-gradient(180deg, #13232e, #2d496f);
  position: relative;
}
.header-img-box {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 999;
}
.header-img-h {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.header-c {
  width: 100%;
  height: 56px;
  line-height: 60px;
  text-align: center;
  color: #faf5e4;
}
.header-left {
  position: absolute;
  top: 0;
  width: 80px;
  height: 56px;
}
.header-left-cont {
  position: relative;
  width: 100%;
  height: 56px;
  text-align: right;
  color: #faf5e4;
  line-height: 56px;
  font-size: 12px;
}
.img-left {
  position: absolute;
  left: 30px;
  width: 15px;
  height: 24px;
  top: 15px;
}
.notice-img {
  width: 100%;
  height: 100%;
  display: block;
}
.head-img {
  width: 100%;
  height: 100%;
  display: block;
}
.deposit-num {
  width: 100%;
  margin-top: 20px;
  position: relative;
}
.deposit-num-cont {
  position: absolute;
  top: 20px;
  left: 30px;
  right: 30px;
  bottom: 14px;
  overflow: hidden;
}
.home-cont-h-l {
  width: 100px;
  height: 20px;
  position: relative;
}
.home-cont-h-t {
  position: absolute;
  top: 0;
  left: 8px;
  right: 0;
  bottom: 0;
  color: #faf5e4;
  font-size: 13px;
}
.deposit-num-c {
  width: 100%;
  height: 20px;
  background-color: rgba(95, 66, 57, 0.15);
  margin-top: 25px;
  overflow: hidden;
}
.deposit-num-cOne {
  width: 100%;
  height: 100px;
  background-color: rgba(95, 66, 57, 0.15);
  margin-top: 25px;
  overflow: hidden;
}
.deposit-left-t {
  float: left;
  line-height: 20px;
  font-size: 12px;
  color: #584147;
  font-family: Source Han Sans CN;
  font-weight: 700;
  margin-left: 30px;
}
.deposit-right-t {
  float: right;
  line-height: 20px;
  font-size: 12px;
  color: #db522b;
  font-family: Source Han Sans CN;
  font-weight: 700;
  margin-right: 5px;
}
.deposit-cont-box {
  width: 100%;
  height: 160px;
  background-color: rgba(95, 66, 57, 0.15);
  overflow: hidden;
}
.info-btn {
  width: 100%;
  height: 50px;
  overflow: hidden;
  float: left;
}
.info-btn-box {
  /* float: left; */
  width: 60%;
  height: 35px;
  margin: auto;
  margin-top: 8px;
  /* margin-left: 20px; */
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  position: relative;
}
.info-btn-box1 {
  width: 60%;
  height: 35px;
  margin: auto;
  margin-top: 8px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  position: relative;
}
.info-btn-box2 {
  width: 120px;
  height: 35px;
  margin-top: 8px;
  margin-right: 20px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  margin: auto;
  position: relative;
}
.info-text-c {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 35px;
}
@keyframes bouncing-loader {
  from {
    opacity: 1;
    transform: translateY(0);
  }

  to {
    opacity: 0.1;
    transform: translateY(-1rem);
  }
}
.info-text-c1 {
  width: 100%;
  height: 100%;
  position: relative;
}
.info-text-c2 {
  position: absolute;
  top: 0;
  right: 25px;
  width: 25%;
  height: 100%;
  display: flex;
  justify-content: center;
  /* background-color: #000; */
}
.info-text-c2 > div {
  width: 4px;
  height: 4px;
  margin: 25px 2px;
  background: #5d4037;
  border-radius: 50%;
  animation: bouncing-loader 0.6s infinite alternate;
}
.info-text-c2 > div:nth-child(2) {
  animation-delay: 0.2s;
}

.info-text-c2 > div:nth-child(3) {
  animation-delay: 0.4s;
}
.img-h {
  width: 100%;
  display: block;
}
.img-b {
  height: 197px;
  display: block;
  width: 93%;
  margin: auto;
}
.img-b24 {
  height: 555px;
  display: block;
  width: 93%;
  margin: auto;
}
.img-f {
  display: block;
  width: 93%;
  margin: auto;
}
.img-b3 {
  height: 220px;
  display: block;
  width: 93%;
  margin: auto;
}
.img-min {
  position: absolute;
  top: 7px;
  right: -4px;
  width: 30px;
  height: 45px;
}
.cardModel-text {
  width: 100%;
  height: 40px;
  margin-top: 20px;
  text-align: center;
  line-height: 40px;
  color: #584147;
}
.cardModel-text1 {
  width: 100%;
  height: 40px;
  line-height: 40px;
  text-align: center;
  color: #db522b;
  font-weight: 700;
}
.cardModel-img {
  width: 100%;
  overflow: hidden;
}
.cardModel-img-info {
  width: 40%;
  margin: auto;
}
.cardInfo-conter {
  width: 100%;
}
.cardInfo-img {
  width: 40%;
  margin: auto;
  margin-top: 20px;
}
.cardInfo-ts {
  width: 100%;
  height: 30px;
  line-height: 30px;
  font-size: 13px;
  color: #584147;
  font-weight: 700;
}
.cardInfo-ts-box {
  width: 100%;
  height: 100px;
  background-color: rgba(95, 66, 57, 0.15);
}
.upgrade-b-c {
  width: 100%;
  height: 30px;
  overflow: hidden;
}
.upgrade-right {
  float: right;
  line-height: 30px;
  font-size: 12px;
  margin-right: 10px;
  color: #5f4239;
  font-weight: 700;
}
.upgrade-left {
  float: left;
  line-height: 30px;
  font-size: 12px;
  margin-left: 10px;
  color: #5f4239;
  font-weight: 700;
}
.cardInfo-ts-box1 {
  width: 100%;
  height: 40px;
  background-color: rgba(95, 66, 57, 0.15);
  margin-top: 10px;
  overflow: hidden;
}
.cardInfo-ts-box1-T {
  float: left;
  line-height: 40px;
  font-size: 12px;
  color: #5f4239;
  font-weight: 700;
  margin-left: 10px;
}
.cardInfo-ts-box1-input {
  float: left;
  width: 30%;
  height: 40px;
}
input {
  width: 100%;
  height: 100%;
  outline: none;
  -webkit-appearance: button;
  -webkit-appearance: none;
  border-radius: 0;
  border: 0;
  background-color: rgb(95 66 57 / 0%);
  padding: 0;
  padding-left: 10px;
  padding-right: 10px;
}
.cardInfo-ts-box1-btn {
  float: right;
  width: 24%;
  height: 40px;
  position: relative;
}
.cardInfo-ts-box1-btn-c {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 40px;
  color: #5f4239;
  font-weight: 700;
}
.info-btn {
  width: 100%;
  height: 50px;
  overflow: hidden;
  float: left;
  margin-top: 30px;
}

.info-btn-box2 {
  width: 120px;
  height: 35px;
  margin-top: 8px;
  margin-right: 20px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  margin: auto;
  position: relative;
}
.info-text-c {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 35px;
}
</style>
