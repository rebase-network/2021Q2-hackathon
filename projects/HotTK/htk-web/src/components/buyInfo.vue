<template>
  <div class="updateSuccessed">
    <div class="updateSuccessed-conter">
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
          <div class="header-c">购买信息</div>
        </div>
        <div class="header-img-h">
          <img src="../assets/head/head.png" class="notice-img" />
        </div>
      </header>
      <div class="updateSd">
        <div class="updateSd-conter">
          <div class="deposit-num">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="notice-img" />
            </div>
            <div class="img-b12">
              <img src="../assets/img/42.png" class="notice-img" />
            </div>
            <div class="img-f">
              <img src="../assets/img/43.png" class="notice-img" />
            </div>
            <div class="img-min">
              <img src="../assets/img/41.png" class="notice-img" />
            </div>
            <div class="deposit-num-cont">
              <div class="deposit-num-cont-box">
                <div class="home-cont-h-l" style="top: 20px">
                  <div class="home-cont-h-t">卡片信息</div>
                  <img src="../assets/img/3.png" class="notice-img" />
                </div>
                <div class="updateSd-c-info">
                  <div class="updateSd-info-img">
                    <img :src="CardInfo.imgUrl" class="notice-img" />
                  </div>
                  <div class="updateSd-info-text-box">
                    <div class="updateSd-info-text">
                      <div
                        style="
                          float: left;
                          font-size: 15px;
                          font-weight: 700;
                          color: #db522b;
                        "
                      >
                        {{ CardInfo.name }}
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #584147"
                      >
                        {{ CardInfo.name1 }}
                      </div>
                    </div>
                    <div class="updateSd-info-text1">
                      <div style="float: left; font-size: 13px; color: #db522b">
                        卡片属性
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #db522b"
                      >
                        {{ CardInfo.cardType }}
                      </div>
                    </div>
                    <div class="updateSd-info-text1">
                      <div style="float: left; font-size: 13px; color: #db522b">
                        卡片价格(金条)
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #584147"
                      >
                        {{ CardInfo.price }}
                      </div>
                    </div>
                    <div class="updateSd-info-text1">
                      <div style="float: left; font-size: 13px; color: #db522b">
                        战力
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #584147"
                      >
                        {{ CardInfo.power }}
                      </div>
                    </div>
                    <div class="updateSd-btn-box">
                      <!-- <div class="updateSd-btn" v-if="!showBuy1">
                        <img src="../assets/img/4.png" class="notice-img" />
                        <div class="updateSd-btn-text">授权</div>
                      </div> -->
                      <div
                        class="updateSd-btn"
                        @click="updateSdBtn1"
                        v-if="showBuy1"
                      >
                        <img src="../assets/img/2.png" class="notice-img" />
                        <div class="updateSd-btn-text">授权</div>
                      </div>
                      <div class="updateSd-btn" v-if="showSate">
                        <img src="../assets/img/4.png" class="notice-img" />
                        <div class="updateSd-btn-text">
                          <div class="info-text-c1">Pending</div>
                          <div class="info-text-c2">
                            <div></div>
                            <div></div>
                            <div></div>
                          </div>
                        </div>
                      </div>
                      <!-- <div
                        class="updateSd-btn"
                        style="margin-top: 5px"
                        v-if="!showBuy"
                      >
                        <img src="../assets/img/4.png" class="notice-img" />
                        <div class="updateSd-btn-text">购买</div>
                      </div> -->
                      <div
                        class="updateSd-btn"
                        @click="updateSdBtn2"
                        style="margin-top: 5px"
                        v-if="showBuy"
                      >
                        <img src="../assets/img/2.png" class="notice-img" />
                        <div class="updateSd-btn-text">购买</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Toast } from "mint-ui";
export default {
  data() {
    return {
      CardInfo: {},
      showBuy: false,
      showBuy1: true,
      showSate: false,
      account: "",
      Web3: "",
      addrsType: {},
      RttbalanceOf: 0,
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
    var CardInfo = JSON.parse(sessionStorage.getItem("carToken"));
    CardInfo[0].imgUrl = require("../assets/cardimg/" +
      CardInfo[0].imgUrlC +
      ".png");
    if (CardInfo[0]) {
      if (CardInfo[0].genre == "5") {
        CardInfo[0].cardType = "白卡";
      }
      if (CardInfo[0].genre == "4") {
        CardInfo[0].cardType = "蓝卡";
      }
      if (CardInfo[0].genre == "3") {
        CardInfo[0].cardType = "紫卡";
      }
      if (CardInfo[0].genre == "2") {
        CardInfo[0].cardType = "橙卡";
      }
      if (CardInfo[0].genre == "1") {
        CardInfo[0].cardType = "金卡";
      }
      this.CardInfo = CardInfo[0];
      sessionStorage.setItem("objList", JSON.stringify(this.CardInfo));
    }

    // let addrsType = localStorage.getItem("addrsType");
    // if (addrsType) {
    //   this.addrsType = JSON.parse(addrsType);
    //   console.log(this.addrsType);
    // } else {
    //   this.addrsType.index = 2;
    // }
  },
  methods: {
    async updateSdBtn1() {
      //this.$router.push("/backpack");
      var arr = parseInt(this.CardInfo.price);
      let addr = this.$Fun.Exchange().options.address;
      let value = this.Web3.utils.toWei(arr.toString(), "ether");
      const gasPrice = await this.Web3.eth.getGasPrice();
      let contract = this.$Fun.RttToken();
      // if (this.addrsType.index == 1) {
      //   contract = this.$Fun.USDKTokens();
      // }
      // if (this.addrsType.index == 2) {
      //   contract = this.$Fun.OktTokens();
      // }
      this.showSate = true;
      this.showBuy1 = false;
      await this.$getWbe3.Approve(
        addr,
        value,
        contract,
        gasPrice,
        this.account,
        (res) => {
          // console.log(res);
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

      // const account = sessionStorage["account"]; //取出缓存中的钱包地址;
      // const web3 = this.$store.state.web3;
      // this.ExchangecFun = this.$store.state.ExchangecFun;
      // this.M3Tfun = this.$store.state.M3Tfun;
      // if (account != undefined && this.M3Tfun != undefined) {
      //   const gas = await this.M3Tfun.methods
      //     .approve(
      //       this.ExchangecFun.options.address,
      //       web3.utils.toWei(arr.toString(), "ether")
      //     )
      //     .estimateGas({ from: account });
      //   const gasPrice = await web3.eth.getGasPrice();
      //   await this.M3Tfun.methods
      //     .approve(
      //       this.ExchangecFun.options.address,
      //       web3.utils.toWei(arr.toString(), "ether")
      //     )
      //     .send({ from: account, gas: gas, gasPrice: gasPrice })
      //     .then((res) => {
      //       if (res) {
      //         // Toast("授权成功");
      //         Toast({
      //           message: "授权成功",
      //           position: "middle",
      //           duration: 1000,
      //         });
      //         this.showBuy = true;
      //         this.allProvider = arr;
      //       }
      //     });
      // }
    },
    async updateSdBtn2() {
      // console.log(this.CardInfo);

      let Rttcontract = this.$Fun.RttToken();
      await this.$getWbe3.RttbalanceOf(Rttcontract, this.account, (res) => {
        // console.log(res, 989);
        this.RttbalanceOf = this.Web3.utils.fromWei(res);
        this.RttbalanceOf = parseInt(this.RttbalanceOf);
        // this.CardInfo.price= parseFloat(this.CardInfo.price).toFixed(2);
      });
      console.log(this.RttbalanceOf < parseInt(this.CardInfo.price));
      if (this.RttbalanceOf < parseInt(this.CardInfo.price)) {
        Toast({
          message: "购买失败,金条数量不足",
          position: "middle",
          duration: 1000,
        });
        return false;
      }
      this.showSate = true;
      this.showBuy = false;
      const gasPrice = await this.Web3.eth.getGasPrice();
      let contract = this.$Fun.Exchange();
      this.$getWbe3.buyT(
        this.CardInfo.tokenId,
        contract,
        gasPrice,
        this.account,
        (res) => {
          if (res) {
            sessionStorage.setItem("cardType", 2);
            sessionStorage.setItem("objList", JSON.stringify(this.CardInfo));
            this.$router.push("/buyEnd");
          }
        }
      );
      // const account = sessionStorage["account"];
      // const web3 = this.$store.state.web3;
      // this.showBuy = false;
      // this.showBuy1 = false;
      // this.$store.state.cardType = 2;
      // var gas = await this.ExchangecFun.methods
      //   .buy("MttToken", this.CardInfo.tokenId[0])
      //   .estimateGas({ from: account });
      // const gasPrice = await web3.eth.getGasPrice(); //获取gas价格
      // await this.ExchangecFun.methods
      //   .buy("MttToken", this.CardInfo.tokenId[0])
      //   .send({ from: account, gas: gas, gasPrice: gasPrice })
      //   .then((res) => {
      //     console.log(res);
      //     if (res) {
      //       sessionStorage.setItem("cardType", 2);
      //       sessionStorage.setItem("objList", JSON.stringify(this.CardInfo));
      //       this.$router.push("/buyEnd");
      //     }
      //   });
    },
    cardInfoBtn() {
      this.$router.push("/business");
    },
  },
};
</script>

<style scoped>
.updateSuccessed {
  width: 100%;
  height: 100%;
  position: relative;
}
.updateSuccessed-conter {
  max-width: 420px;
  height: 100%;
  margin: auto;
  background-color: #000;
  position: relative;
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
.updateSd {
  position: absolute;
  top: 57px;
  left: 0;
  right: 0;
  overflow: auto;
}
.updateSd-conter {
  width: 100%;
  height: 100%;
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
}
.deposit-num-cont-box {
  width: 100%;
  height: 100%;
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
.img-h {
  width: 100%;
  display: block;
}
.img-b12 {
  height: 320px;
  display: block;
  width: 93%;
  margin: auto;
}
.img-f {
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
.updateSd-c-info {
  width: 100%;
  position: relative;
  margin-top: 35px;
}
.updateSd-info-img {
  float: left;
  width: 40%;
  border-radius: 3px;
}
.updateSd-info-text-box {
  float: left;
  width: 50%;
  margin-left: 5%;
}
.updateSd-info-text1 {
  width: 100%;
  height: 22px;
  overflow: hidden;
}
.updateSd-info-text {
  width: 100%;
  height: 40px;
  overflow: hidden;
}
.updateSd-btn-box {
  width: 100%;
  height: 40px;
  margin-top: 10px;
}
.updateSd-btn {
  position: relative;
  width: 80%;
  height: 40px;
  margin: auto;
}
.updateSd-btn-text {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  text-align: center;
  font-size: 14px;
  line-height: 40px;
  color: #5d4037;
  font-weight: 700;
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
  right: 7px;
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
</style>
