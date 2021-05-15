<template>
  <div class="store">
    <div class="store-cont">
      <header>
        <div class="header-img-box">
          <div class="header-left">
            <div class="header-left-cont" @click="backBtn">
              <div class="img-left">
                <img src="../assets/img/29.png" class="notice-img" />
              </div>
              <span>首页</span>
            </div>
          </div>
          <div class="header-c">卡牌商店</div>
        </div>
        <div class="header-img-h">
          <img src="../assets/head/head.png" class="notice-img" />
        </div>
      </header>
      <!-- <div class="body-box1">
        <div class="body-box-cont1">
          <div class="body-ul1">
            <div class="bg-img">
              <img src="../assets/img/35.png" class="notice-img" />
            </div>
          </div>
        </div>
      </div> -->
      <div class="body-box">
        <div class="body-box-cont">
          <div class="body-ul">
            <div class="store-img-box">
              <div class="store-img-c">
                <img src="../assets/img/51.png" class="notice-img" alt="" />
              </div>
            </div>
            <!-- <div class="body-ul-cont">
              倒计时: 2小时45分钟36秒
            </div> -->
          </div>
          <div class="buy-info">
            <!-- <img src="../assets/home/home1.png" class="notice-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="notice-img" />
            </div>
            <div class="img-b">
              <img src="../assets/img/42.png" class="notice-img" />
            </div>
            <div class="img-f">
              <img src="../assets/img/43.png" class="notice-img" />
            </div>
            <div class="img-min">
              <img src="../assets/img/41.png" class="notice-img" />
            </div>
            <div class="store-info">
              <div class="store-info-cont">
                <div class="home-cont-h-l" style="top:10px;">
                  <div class="home-cont-h-t">购买信息</div>
                  <img src="../assets/img/3.png" class="notice-img" />
                </div>
                <div class="store-buy-info" style="margin-top: 20px;">
                  <div class="store-buy-left">我的余额</div>
                  <div class="store-buy-right">{{ RTToverage }}(USDK)</div>
                </div>
                <div class="store-buy-info">
                  <div class="store-buy-left">需要支付</div>
                  <div class="store-buy-right">40(USDK)</div>
                </div>
                <!-- <div class="store-buy-info1">
                  <div class="store-buy-left">开始倒计时</div>
                  <div class="store-buy-right">00时00分00秒</div>
                </div> -->
                <div class="info-btn">
                  <div class="info-btn-box" @click="warrantBtn" v-if="showBuy1">
                    <!-- 卡牌商店 -->
                    <div class="info-text-c">授权</div>
                    <img src="../assets/img/2.png" class="notice-img" />
                  </div>
                  <div class="info-btn-box" v-if="showSate">
                    <!-- 卡牌商店 -->
                    <div class="info-text-c">
                      <div class="info-text-c1">Pending</div>
                      <div class="info-text-c2">
                        <div></div>
                        <div></div>
                        <div></div>
                      </div>
                    </div>
                    <img src="../assets/img/4.png" class="notice-img" />
                  </div>

                  <div class="info-btn-box1" v-if="showBuy">
                    <!-- 我的背包 -->
                    <div class="info-text-c" @click="buyBtn">点击购买</div>
                    <img src="../assets/img/2.png" class="notice-img" />
                  </div>
                  <div class="info-btn-box" v-if="showSate1">
                    <!-- 卡牌商店 -->
                    <div class="info-text-c">
                      <div class="info-text-c1">Pending</div>
                      <div class="info-text-c2">
                        <div></div>
                        <div></div>
                        <div></div>
                      </div>
                      <!-- <div class="info-text-c1">...</div> -->
                    </div>
                    <img src="../assets/img/4.png" class="notice-img" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="store-car">
            <!-- <img src="../assets/home/home.png" class="notice-img" alt="" /> -->
            <div class="img-left-b">
              <img src="../assets/img/45.png" class="notice-img" />
            </div>
            <div class="img-mid">
              <img src="../assets/img/46.png" class="notice-img" />
            </div>
            <div class="img-right-b">
              <img src="../assets/img/45.png" class="notice-img" />
            </div>
            <div class="store-info">
              <div class="store-info-cont">
                <div class="home-cont-h-l">
                  <div class="home-cont-h-t">卡包信息</div>
                  <img src="../assets/img/3.png" class="notice-img" />
                </div>
                <div class="store-info-text">
                  <span
                    >随机开出不同品质的卡牌，卡牌共具有五种品质，每个品质所具有的权益不相同</span
                  >
                  <!-- <span style="color: #092bf8">蓝</span>、
                  <span style="color: #8108fc">紫</span>、
                  <span style="color: #f96003">橙</span>、
                  <span style="color: #f9a605">金</span>五种品质其中一种 -->
                </div>
                <div class="store-card-info3">
                  <span>金卡几率:</span><span>{{ Probability3 }}</span>
                  <span>%</span>
                </div>
                <div class="store-card-info2">
                  <span>橙卡几率:</span><span>{{ Probability2 }}</span>
                  <span>%</span>
                </div>
                <div class="store-card-info1">
                  <span>紫卡几率:</span><span>{{ Probability1 }}</span>
                  <span>%</span>
                </div>
                <div class="store-card-info">
                  <span>蓝卡几率:</span><span>{{ Probability }}</span>
                  <span>%</span>
                </div>
                <div
                  class="store-card-info"
                  style="background-color: #fcfcfc87;"
                >
                  <span>白卡几率:</span><span>{{ Probability4 }}</span>
                  <span>%</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <submitModel
        @submitModelMethod="submitModelMethod"
        :Modelmsg="Modelmsg"
        v-if="showModel"
      />
    </div>
  </div>
</template>

<script>
// import { CardShopEx } from "../common/config";
import { Toast } from "mint-ui";
import heroList from "../common/heroIfon.json";
import submitModel from "../model/submitModel";
export default {
  name: "notice",
  data() {
    return {
      changec: null,
      M3Tfun: null,
      Probability: 0,
      Probability1: 0,
      Probability2: 0,
      Probability3: 0,
      Probability4: 0,
      test: null,
      showBuy: false,
      showSate: false,
      allProvider: 10000,
      editValue: 100,
      heroList: heroList.heroList,
      caderP: "",
      showBuy1: true,
      Web3: {},
      addrsType: {},
      contracts: {},
      typeIndex: "USDK",
      RTToverage: "",
      showModel: false,
      Modelmsg: "购买成功,快到背包看新卡片吧!",
      Year: 0,
      Month: 0,
      Day: 0,
      Hours: 0,
      Minutes: 0,
      Seconds: 0,
      showSate1: false,
    };
  },
  components: {
    submitModel,
    // homeListview
  },
  async mounted() {
    await this.$getWbe3.Init(
      (addr) => {
        //得到相应的钱包地址
        this.account = addr;
        //console.log(this.account, "111");
        this.getInfoList();
      },
      (provider) => {
        //console.log(provider);
        this.Web3 = provider;
      }
    );
    // let addrsType = localStorage.getItem("addrsType");
    // if (addrsType) {
    //   this.addrsType = JSON.parse(addrsType);
    //   console.log(this.addrsType);
    // } else {
    //   this.addrsType.index = 2;
    // }
  },
  methods: {
    backBtn() {
      this.$router.push("/home");
    },
    ToastBtn() {
      // Toast("授权金额不足，请去授权");
      Toast({
        message: "授权金额不足，请去授权",
        position: "middle",
        duration: 1000,
      });
    },
    async getInfoList() {
      let contract = this.$Fun.CardShop();
      await this.$getWbe3.getGenrePercent(contract, (res) => {
        // console.log(res, 99999);
        this.Probability3 = res[1] / 100;
        this.Probability2 = res[2] / 100;
        this.Probability1 = res[3] / 100;
        this.Probability = res[4] / 100;
        this.Probability4 = res[5] / 100;
      });
      let addr = this.$Fun.CardShop().options.address;
      // console.log(this.addrsType.index);
      this.contracts = this.$Fun.USDKTokens();

      await this.contracts.methods
        .allowance(this.account, addr)
        .call()
        .then((res) => {
          this.allProvider = this.Web3.utils.fromWei(res);
          // console.log(this.allProvider, 9912311199);
          if (this.allProvider >= this.editValue) {
            this.showBuy = true;
            this.showBuy1 = false;
          } else {
            this.showBuy1 = true;
            this.showBuy = false;
          }
        });
      contract = this.$Fun.USDKTokens();
      await this.$getWbe3.MttbalanceOf(contract, this.account, (res) => {
        // console.log(res, 989);
        var RTToverage = res / 10000000000;
        this.RTToverage = parseFloat(RTToverage).toFixed(2);
        // this.currencyList[0].num = this.RTToverage;
      });
      let contracts = this.$Fun.CardShop();
      this.$getWbe3.getCountDwon(contracts, (res) => {
        console.log(res);
        var timestamp = Date.parse(new Date());
        console.log(timestamp, 999988);
        var date = new Date(1619778600 * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var Year = date.getFullYear();
        var Month =
          date.getMonth() + 1 < 10
            ? 0 + (date.getMonth() + 1)
            : date.getMonth() + 1;
        var Day = date.getDate();
        var Hours = date.getHours();
        var Minutes = date.getMinutes();
        var Seconds = date.getSeconds();
        console.log(Year, Month, Day, Hours, Minutes, Seconds);

        var nowTime = new Date(1619683341 * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
        var NYear = nowTime.getFullYear();
        var NMonth =
          date.getMonth() + 1 < 10
            ? 0 + (nowTime.getMonth() + 1)
            : nowTime.getMonth() + 1;
        var NDay = nowTime.getDate();
        var NHours = nowTime.getHours();
        var NMinute = nowTime.getMinutes();
        var NSeconds = nowTime.getSeconds();
        var d = new Date(NYear, NMonth, 0);
        console.log(
          NYear,
          NMonth,
          NDay,
          NHours,
          NMinute,
          NSeconds,
          d.getDate()
        );
        console.log(Seconds, NSeconds);
        if (Seconds >= NSeconds) {
          this.Seconds = this.Seconds - NSeconds;
          this.Minutes = Minutes;
        } else {
          this.Minutes = Minutes - 1;
          this.Seconds = Seconds + 60 - NSeconds;
        }
        if (this.Minutes >= NMinute) {
          this.Minutes = this.Minutes - NMinute;
          this.Hours = Hours;
        } else {
          this.Hours = Hours - 1;
          this.Minutes = this.Minutes + 60 - NMinute;
        }
        if (this.Hours >= NHours) {
          //计算小时
          this.Hours = this.Hours - NHours;
          this.Day = Day;
        } else {
          this.Day = Day - 1;
          this.Hours = this.Hours + 24 - NHours;
        }
        if (this.Day >= NDay) {
          //计算天
          this.Day = this.Day - NDay;
          this.Month = Month;
        } else {
          this.Month = Month - 1;
          this.Day = this.Day + Day - NDay;
        }
        if (this.Month >= NMonth) {
          //计算月
          this.Month = this.Month - NMonth;
          this.Year = Year;
        } else {
          this.Year = Year - 1;
          this.Month = this.Month + 12 - NMonth;
        }
        if (this.Year >= NYear) {
          //计算年

          this.Year = this.Year - NYear;
        }
        console.log(
          this.Year +
            "-" +
            this.Month +
            "-" +
            this.Day +
            "-" +
            this.Hours +
            "-" +
            this.Minutes +
            "-" +
            this.Seconds
        );
      });
    },
    async warrantBtn() {
      var arr = 10000;
      let contract;
      // console.log(this.typeIndex);
      contract = this.$Fun.USDKTokens();
      // if (this.addrsType.index == 0) {

      //   console.log(contract, "R");
      // }
      // if (this.addrsType.index == 1) {
      //   contract = this.$Fun.USDKTokens();
      //   console.log(contract, "u");
      // }
      // if (this.addrsType.index == 2) {
      //   contract = this.$Fun.OktTokens();
      //   console.log(contract, "k");
      // }
      // console.log(contract);
      this.showSate = true;
      this.showBuy1 = false;
      if (this.account != undefined && contract != undefined) {
        let value = this.Web3.utils.toWei(arr.toString(), "ether");

        let addr = this.$Fun.CardShop().options.address;
        const gasPrice = await this.Web3.eth.getGasPrice();

        await this.$getWbe3.Approve(
          addr,
          value,
          contract,
          gasPrice,
          this.account,
          (res) => {
            if (res) {
              Toast({
                message: "授权成功",
                position: "middle",
                duration: 1000,
              });
              this.showBuy = true;
              this.showBuy1 = false;
              this.showSate = false;
              this.allProvider = arr;
            }
          }
        );
      }
    },
    async buyBtn() {
      await this.$getWbe3.MttbalanceOf(
        this.$Fun.USDKTokens(),
        this.account,
        (res) => {
          // console.log(res, 989);
          var RTToverage = res / 10000000000;
          this.RTToverage = parseFloat(RTToverage).toFixed(2);
          // this.currencyList[0].num = this.RTToverage;
        }
      );
      if (parseFloat(this.RTToverage) < 40) {
        Toast({
          message: "购卡余额不足",
          position: "middle",
          duration: 1000,
        });
        return false;
      }
      this.showSate1 = true;
      this.showBuy = false;
      if (this.allProvider >= this.editValue) {
        // this.showBuy = false;
        // this.showModel = true;
        let Token = this.typeIndex;
        // console.log(Token);
        let contract = this.$Fun.CardShop();
        const gasPrice = await this.Web3.eth.getGasPrice(); //获取gas价格
        await this.$getWbe3.buy(
          Token,
          contract,
          gasPrice,
          this.account,
          (res) => {
            // console.log(res);
            this.showSate1 = false;
            if (res) {
              // Toast({
              //   message: "购买成功,快到背包看新卡片吧!",
              //   position: "middle",
              //   duration: 1000,
              // });
              sessionStorage.setItem("cardType", 1);
              if (res.events.BuyDetail.returnValues) {
                var cardInfo = res.events.BuyDetail.returnValues;
                var obj = {};
                obj.genre = cardInfo[1];
                obj.imgUrlC =
                  parseInt(cardInfo[1]) * 100 + parseInt(cardInfo["_hero"]);
                obj.page = "store";
                obj.power = "";
                this.heroList.map((item) => {
                  if (obj.imgUrlC == item.no) {
                    obj.name = item.name;
                    obj.name1 = item.name1;
                    obj.doc = item.doc;
                  }
                });
                const account = sessionStorage["account"];
                if (obj.genre == "5") {
                  this.caderP = "白卡";
                }
                if (obj.genre == "4") {
                  this.caderP = "蓝卡";
                }
                if (obj.genre == "3") {
                  this.caderP = "紫卡";
                }
                if (obj.genre == "2") {
                  this.caderP = "橙卡";
                }
                if (obj.genre == "1") {
                  this.caderP = "金卡";
                }
                var accountT =
                  account.substr(0, 4) + "..." + account.substr(-4);
                this.$store.state.televise = "恭喜";
                this.$store.state.televise1 = accountT;
                this.$store.state.televise2 = this.caderP + " " + obj.name;
                sessionStorage.setItem("objList", JSON.stringify(obj));
                this.$router.push("/buyEnd");
                // Toast({
                //   message: "购买成功,快到背包看新卡片吧!",
                //   position: "middle",
                //   duration: 1000,
                // });
              }
            }
          }
        );
      } else {
        this.showBuy = true;
        this.showBuy1 = false;
        this.showSate1 = false;
      }
    },
    submitModelMethod() {
      this.showModel = false;
    },
  },
};
</script>

<style scoped>
.store {
  width: 100%;
  height: 100%;
}
.store-cont {
  max-width: 420px;
  height: 100%;
  background-color: #000;
  margin: auto;
  position: relative;
  background: url("../assets/img/60.jpg") no-repeat;
  background-size: cover;
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
.body-box {
  position: absolute;
  top: 56px;
  left: 0;
  right: 0;
  bottom: 5px;
  overflow: auto;
}
.body-box1 {
  position: absolute;
  top: 52px;
  left: 0;
  right: 0;
  bottom: 5px;
  overflow: auto;
}
.body-box-cont {
  position: relative;
  width: 95%;
  height: 100%;
  margin: auto;
}
.body-ul-cont {
  position: absolute;
  top: 50px;
  width: 100%;
  height: 100px;
  text-align: center;
  color: #fff;
  background: rgba(24, 24, 30, 0.75);
  border-radius: 10px;
  line-height: 100px;
}
.body-box-cont1 {
  position: relative;
  width: 100%;
  height: 100%;
}
.body-ul {
  width: 100%;
  height: 220px;
  overflow: hidden;
  position: relative;
}
.body-ul1 {
  width: 100%;
  height: 200px;
  overflow: hidden;
  background: url("../assets/img/36.jpg") no-repeat;
  background-size: cover;
  position: relative;
}
.bg-img {
  position: absolute;
  top: 0;
  left: -15px;
  right: 0;
  bottom: 0;
}
.notice-img {
  width: 100%;
  height: 100%;
  display: block;
}
.store-img-box {
  width: 100%;
  height: 240px;
  margin-top: 30px;
  -webkit-animation: shaking 2.5s linear infinite;
  animation: shaking 2.5s linear infinite;
}
.store-img-c {
  position: relative;
  width: 30%;
  margin: auto;
  border-radius: 5px;
  overflow: hidden;
}
.store-img-log {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  box-shadow: 0px 0px 6px 6px #ffe000 inset;
}

@keyframes shaking {
  50% {
    margin-top: 10px;
  }
  75% {
    margin-top: 20px;
  }
  100% {
    margin-top: 30px;
  }
}
.store-car {
  position: relative;
  width: 100%;
  height: 220px;
  margin-top: 20px;
}
.store-info {
  position: absolute;
  top: 10px;
  left: 15px;
  right: 15px;
  bottom: 20px;
}
.store-info-cont {
  width: 95%;
  height: 100%;
  margin: auto;
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
.store-info-text {
  font-size: 13px;
  line-height: 20px;
  padding-bottom: 5px;
  color: #5d4037;
}
.store-card-info {
  width: 100%;
  height: 20px;
  background-color: rgba(9, 43, 248, 0.15);
  margin-bottom: 5px;
  font-size: 14px;
  line-height: 20px;
  padding-left: 10px;
  color: #5d4037;
}
.store-card-info1 {
  width: 100%;
  height: 20px;
  background-color: rgba(129, 8, 252, 0.15);
  margin-bottom: 5px;
  font-size: 14px;
  line-height: 20px;
  padding-left: 10px;
  color: #5d4037;
}
.store-card-info2 {
  width: 100%;
  height: 20px;
  background-color: rgba(249, 96, 3, 0.15);
  margin-bottom: 5px;
  font-size: 14px;
  line-height: 20px;
  padding-left: 10px;
  color: #5d4037;
}
.store-card-info3 {
  width: 100%;
  height: 20px;
  background-color: rgba(249, 166, 5, 0.15);
  margin-bottom: 5px;
  font-size: 14px;
  line-height: 20px;
  padding-left: 10px;
  color: #5d4037;
}
.buy-info {
  width: 100%;
  margin-top: 10px;
  position: relative;
}
.store-buy-info {
  width: 100%;
  height: 20px;
  background-color: rgba(95, 66, 57, 0.15);
  margin-top: 5px;
  overflow: hidden;
}
.store-buy-left {
  float: left;
  line-height: 20px;
  font-size: 12px;
  font-weight: 700;
  margin-left: 5px;
  color: #584147;
}
.store-buy-right {
  float: right;
  line-height: 20px;
  font-size: 12px;
  font-weight: 700;
  margin-right: 5px;
  color: #584147;
}
.store-buy-info1 {
  width: 100%;
  height: 20px;
  margin-top: 10px;
  overflow: hidden;
}
.info-btn {
  width: 100%;
  height: 50px;
  overflow: hidden;
  float: left;
  margin-top: 10px;
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
  /* float: right; */
  width: 60%;
  height: 35px;
  margin: auto;
  margin-top: 8px;
  /* margin-right: 20px; */
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
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
.img-b1 {
  height: 155px;
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
.img-left-b {
  position: absolute;
  top: 0;
  left: 0;
  width: 8px;
  height: 100%;
}
.img-mid {
  position: absolute;
  top: 0;
  left: 8px;
  right: 8px;
  height: 100%;
}
.img-right-b {
  position: absolute;
  top: 0;
  right: 0;
  width: 8px;
  height: 100%;
}
</style>
