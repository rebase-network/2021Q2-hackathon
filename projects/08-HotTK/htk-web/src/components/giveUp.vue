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
              <img :src="CardInfo.imgUrl" class="head-img" />
            </div>
            <!-- <div class="cardInfo-bottom" @click="giveBtn">
              <div class="cardInfo-bottom1">
                <img src="../assets/img/2.png" class="head-img" />
              </div>
              <div class="cardInfo-bottom-ts">取消挂单</div>
            </div> -->
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
              <div class="upgrade-left">卡片价格(OKT)</div>
              <div class="upgrade-right">{{ CardInfo.price }}</div>
            </div>
            <div class="upgrade-b-c">
              <div class="upgrade-left">战力</div>
              <div class="upgrade-right">{{ CardInfo.power }}</div>
            </div>
          </div>
          <!-- <div class="cardInfo-ts-box1">
            <div class="cardInfo-ts-box1-T">输入卡片价格（OKT）:</div>
            <div class="cardInfo-ts-box1-input">
              {{ CardInfo.cardType }}<input type="text" />
            </div>
            <div class="cardInfo-ts-box1-btn">
             
              <div class="cardInfo-ts-box1-btn-c">修改</div>
            </div>
          </div> -->
          <div class="info-btn">
            <!-- <div class="info-btn-box" @>
           
              <div class="info-text-c">授权</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div> -->
            <!-- <div class="info-btn-box1" v-if="showBuy">
              
              <div class="info-text-c" @click="sellInfoBtn">点击挂单</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div> -->
            <div class="info-btn-box1" @click="giveBtn">
              <div class="info-text-c">取消挂单</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
// import { Toast } from "mint-ui";
export default {
  data() {
    return {
      CardInfo: {},
      showBuy: false,
      cardType: "",
      account: "",
      Web3: "",
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
    if (CardInfo[0]) {
      this.imgUrl = require("../assets/cardimg/" +
        CardInfo[0].imgUrlC +
        ".png");
      this.CardInfo = CardInfo[0];
      // console.log(this.CardInfo);
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
  },
  methods: {
    cardInfoBtn() {
      this.$router.push("/business");
    },
    sellInfoBtn() {
      this.$router.push("/editEnd");
    },
    async giveBtn() {
      let contract = this.$Fun.Exchange();
      const gasPrice = await this.Web3.eth.getGasPrice();
      await this.$getWbe3.pull(
        contract,
        this.CardInfo.tokenId[0],
        this.account,
        gasPrice,
        (res) => {
          if (res) {
            this.$router.push("/giveUpInfo");
            sessionStorage.setItem("objList", JSON.stringify(this.CardInfo));
          }
        }
      );
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
  float: left;
  width: 95px;
  height: 35px;
  margin-top: 8px;
  margin-left: 20px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  position: relative;
}
.info-btn-box1 {
  width: 95px;
  height: 35px;
  margin-top: 8px;
  margin-right: 20px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  position: relative;
  margin: auto;
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
  position: relative;
}
.cardInfo-bottom {
  position: absolute;
  top: 0;
  right: 0;
  width: 80px;
  height: 35px;
}
.cardInfo-bottom1 {
  width: 100%;
  height: 100%;
  position: relative;
}
.cardInfo-bottom-ts {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  text-align: center;
  line-height: 35px;
  font-size: 12px;
  color: #584147;
  font-weight: 700;
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
.info-btn-box {
  float: left;
  width: 95px;
  height: 35px;
  margin-top: 8px;
  margin-left: 20px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  position: relative;
}
.info-btn-box1 {
  width: 95px;
  height: 35px;
  margin-top: 8px;
  margin-right: 20px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  position: relative;
  margin: auto;
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
