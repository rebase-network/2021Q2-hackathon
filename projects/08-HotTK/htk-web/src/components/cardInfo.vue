<template>
  <div class="cardInfo">
    <div class="cardInfo-cont">
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
      <div class="cardInfo-c">
        <div class="cardInfo-c-box">
          <div class="deposit-num">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="notice-img" />
            </div>
            <div class="img-b9">
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
                <div class="carde-c-info">
                  <div class="carde-info-img">
                    <img :src="this.CardInfo.imgUrl" class="notice-img" />
                  </div>
                  <div class="carde-info-text-box">
                    <div class="carde-info-text">
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
                    <div class="carde-info-text">
                      <div style="float: left; font-size: 14px; color: #db522b">
                        级别
                      </div>
                      <div
                        style="float: right; font-size: 14px; color: #db522b"
                      >
                        {{ cardType }}
                      </div>
                    </div>
                    <div
                      class="carde-info-text"
                      style="border-bottom: 2px solid #59434f"
                    >
                      <div style="float: left; font-size: 14px; color: #db522b">
                        战力
                      </div>
                      <div
                        style="float: right; font-size: 14px; color: #584147"
                      >
                        {{ CardInfo.power }}
                      </div>
                    </div>
                    <div class="carde-info-text1">
                      {{ CardInfo.doc }}
                    </div>
                  </div>
                </div>
                <div class="upgrade">
                  <div class="upgrade-btn" @click="upgradeBtn">升级</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="upgrade-box" v-if="showUpgrade">
        <div class="upgrade-cont">
          <div class="deposit-num">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="notice-img" />
            </div>
            <div class="img-b10">
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
                <div class="bottom-col">
                  <div class="bottom-col1" @click="bottomColBtn">
                    <img src="../assets/img/2.png" class="notice-img" />
                    <div class="bottom-col2">
                      返回
                    </div>
                  </div>
                </div>
                <div class="home-cont-h-l">
                  <div class="home-cont-h-t">卡片升级</div>
                  <img src="../assets/img/3.png" class="notice-img" />
                </div>
                <!-- <div class="upgrade-h">时间</div>
                <div class="upgrade-b-c">
                  <div class="upgrade-left">下次升级倒计时</div>
                  <div class="upgrade-right" v-if="newNum == 3">
                    {{ newdata }}
                  </div>
                </div> -->
                <div class="upgrade-h" style="margin-top: 10px">升级次数</div>
                <div class="upgrade-b-c">
                  <div class="upgrade-left">已用次数/总次数</div>
                  <div class="upgrade-right">
                    <span>{{ newNum }}</span
                    >/ <span>{{ num }}</span>
                  </div>
                </div>
                <div class="upgrade-h" style="margin-top: 10px">战力</div>
                <div class="upgrade-b-c1">
                  <div class="upgrade-b-t">
                    <div class="upgrade-left1">目前战力</div>
                    <div class="upgrade-right1">{{ nowpower }}</div>
                  </div>
                  <div class="upgrade-b-t">
                    <div class="upgrade-left1">升级后战力</div>
                    <div class="upgrade-right1" style="color: #f94f1b">
                      {{ newnowpower }}
                    </div>
                  </div>
                </div>
                <div class="upgrade-h" style="margin-top: 10px">铜板</div>
                <div class="upgrade-b-c2">
                  <div class="upgrade-b-t">
                    <div class="upgrade-left1">我的数量</div>
                    <div class="upgrade-right1">{{ mNum }}</div>
                  </div>
                  <div class="upgrade-b-t">
                    <div class="upgrade-left1">需要消耗</div>
                    <div class="upgrade-right1">500</div>
                  </div>
                  <div class="upgrade-b-t">
                    <div class="upgrade-left1">剩余数量</div>
                    <div class="upgrade-right1">{{ mNum - 500 }}</div>
                  </div>
                </div>
                <div class="upgrade-b-btn">
                  <div class="upgrade-btn-left" v-if="updates1">
                    <div class="upgrade-left-t" @click="approveBtn">
                      <img src="../assets/img/2.png" class="notice-img" />
                      <div class="upgrade-left-text">授权</div>
                    </div>
                  </div>
                  <div class="upgrade-btn-left" v-if="updates2">
                    <div class="upgrade-left-t">
                      <img src="../assets/img/4.png" class="notice-img" />
                      <div class="upgrade-left-text">
                        <div class="info-text-c1">Pending</div>
                        <div class="info-text-c2">
                          <div></div>
                          <div></div>
                          <div></div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div
                    class="upgrade-btn-right"
                    @click="updateSdBtn"
                    v-if="updates"
                  >
                    <div class="upgrade-left-t">
                      <img src="../assets/img/2.png" class="notice-img" />
                      <div class="upgrade-left-text">升级</div>
                    </div>
                  </div>
                  <!-- <div
                    class="upgrade-btn-right"
                    @click="updateSdBtn1"
                    v-if="!updates"
                  >
                    <div class="upgrade-left-t">
                      <img src="../assets/img/4.png" class="notice-img" />
                      <div class="upgrade-left-text">升级</div>
                    </div>
                  </div> -->
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
// import Exchangec from "../../build/CardShopEx.json";
// import m3t from "../../build/MttToken.json";
// import CardToken from "../../build/CardToken.json";
// import { CardShopEx, MttToken, CardTokens } from "../common/config";
import { Toast } from "mint-ui";
import submitModel from "../model/submitModel";
export default {
  data() {
    return {
      showUpgrade: false,
      updates1: true,
      updates2: false,
      changec: null,
      M3Tfun: null,
      CardTokenfun: null,
      CardInfo: {},
      cardType: "",
      num: 0,
      newNum: 0,
      nowpower: 0,
      newnowpower: 0,
      mNum: 0,
      updates: false,
      getMinutes: null,
      getSeconds: null,
      getHours: null,
      newdata: null,
      account: "",
      Web3: "",
      Modelmsg: "提交升级成功，请到背包看看吧",
      showModel: false,
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
        let Mttcontract = this.$Fun.MttToken();

        this.$getWbe3.MttbalanceOf(Mttcontract, this.account, (res) => {
          this.mNum = this.Web3.utils.fromWei(res);
          this.mNum = parseFloat(this.mNum).toFixed(2);
        });
        // console.log(this.account);
      },
      (provider) => {
        this.Web3 = provider;
        // console.log(this.Web3);
      }
    );
    clearInterval(this.itmeData);
    this.GETitmeData();

    await this.$store.mutations.initWeb3Account();
    await this.getToken();
    this.CardInfo = JSON.parse(sessionStorage.getItem("cardInfo")); //获取需要升级卡片信息

    this.CardInfo.imgUrl = require("../assets/cardimg/" +
      this.CardInfo.imgUrlC +
      ".png");

    //判断卡片类型和升级次数
    this.num = parseInt(this.CardInfo.Alltimes);
    this.newNum = parseInt(this.CardInfo.times);
    if (this.CardInfo.genre == "0") {
      this.cardType = "特殊卡";
      this.num = "∞";
    }
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
      this.num = 0;
    }
    this.nowpower = this.CardInfo.power; //获取卡片当站战力
    this.newnowpower =
      parseInt(this.CardInfo.power) + parseInt(this.CardInfo.power) * 0.1; //计算卡片升级后的战力
    // if (this.$store.state.M3Tfun) {
    //   var account = sessionStorage.getItem("account");
    //   await this.$store.state.M3Tfun.methods //获取当前M3t代币
    //     .balanceOf(account)
    //     .call()
    //     .then((res) => {
    //       this.mNum = this.$store.state.web3.utils.fromWei(res);
    //     });
    // }
  },
  methods: {
    GETitmeData() {
      var _this = this;
      _this.itmeData = setInterval(function() {
        var Today = new Date();
        var one = new Date(
          new Date(new Date().toLocaleDateString()).getTime() +
            24 * 60 * 60 * 1000 -
            1
        );

        if (one.getSeconds() < Today.getSeconds()) {
          _this.getMinutes = one.getMinutes() - 1;
          _this.getSeconds = one.getSeconds() + 60 - Today.getSeconds();
        } else {
          _this.getMinutes = one.getMinutes();
          _this.getSeconds = one.getSeconds() - Today.getSeconds();
        }
        if (one.getMinutes() < Today.getMinutes()) {
          _this.getHours = one.getHours() - 1;
          _this.getMinutes = _this.getMinutes + 60 - Today.getMinutes();
        } else {
          _this.getHours = one.getHours();
          _this.getMinutes = one.getMinutes() - Today.getMinutes();
        }

        _this.getHours = _this.getHours - Today.getHours();
        if (_this.getHours < 10) {
          _this.getHours = "0" + _this.getHours;
        }
        if (_this.getMinutes < 10) {
          _this.getMinutes = "0" + _this.getMinutes;
        }
        if (_this.getSeconds < 10) {
          _this.getSeconds = "0" + _this.getSeconds;
        }
        _this.newdata =
          _this.getHours +
          "时" +
          _this.getMinutes +
          "分" +
          _this.getSeconds +
          "秒";
        // console.log(
        //   _this.getHours + ":" + _this.getMinutes + ":" + _this.getSeconds
        // );
      }, 1000);
    },
    async getToken() {
      if (this.$store.state.CardTokenFun) {
        // const web3 = this.$store.state.web3; //初始化web3
        this.CardTokenfun = this.$store.state.CardTokenFun;
      }
    },
    cardInfoBtn() {
      this.$router.push("/backpack");
    },
    upgradeBtn() {
      this.showUpgrade = true;
    },
    async approveBtn() {
      this.updates2 = true;
      this.updates1 = false;
      // console.log(this.CardInfo.tokenId);
      if (this.$Fun.MttToken() && this.$Fun.CardShop()) {
        let HeroCard = await this.$Fun.HeroCard().options.address;
        let contract = await this.$Fun.MttToken();
        const gasPrice = await this.Web3.eth.getGasPrice();
        await this.$getWbe3.Approve(
          HeroCard,
          this.CardInfo.tokenId,
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
              this.updates1 = false;
              this.updates = true;
              this.updates2 = false;
            }
          }
        );
      }

      // if (this.$store.state.M3Tfun && this.$store.state.CardShopExfun) {
      //   const account = sessionStorage["account"]; //取出缓存中的钱包地址;
      //   const web3 = this.$store.state.web3; //初始化web3
      //   this.changec = this.$store.state.CardShopExfun;
      //   this.M3Tfun = this.$store.state.M3Tfun;
      //   if (account != undefined && this.M3Tfun != undefined) {
      //     const gas = await this.M3Tfun.methods
      //       .approve(this.CardTokenfun.options.address, this.CardInfo.tokenId)
      //       .estimateGas({ from: account });
      //     const gasPrice = await web3.eth.getGasPrice();
      //     await this.M3Tfun.methods
      //       .approve(this.CardTokenfun.options.address, this.CardInfo.tokenId)
      //       .send({ from: account, gas: gas, gasPrice: gasPrice })
      //       .then((res) => {
      //         if (res) {
      //           // Toast("授权成功");
      //           Toast({
      //             message: "授权成功",
      //             position: "middle",
      //             duration: 1000,
      //           });
      //           this.updates = true;
      //         }
      //       })
      //       .catch((err) => {
      //         console.log(err);
      //         Toast({
      //           message: "授权失败",
      //           position: "middle",
      //           duration: 1000,
      //         });
      //         // Toast(err.message)
      //       });
      //   }
      // }
    },
    updateSdBtn1() {
      // Toast("请先授权");
      Toast({
        message: "请先授权",
        position: "middle",
        duration: 1000,
      });
    },
    submitModelMethod() {
      this.showModel = false;
    },
    async updateSdBtn() {
      let Mttcontract = this.$Fun.MttToken();
      // console.log(this.account);
      await this.$getWbe3.MttbalanceOf(Mttcontract, this.account, (res) => {
        // console.log(res);
        this.mNum = this.Web3.utils.fromWei(res);
        this.mNum = parseFloat(this.mNum).toFixed(2);
      });
      if (this.CardInfo.genre == "5") {
        // Toast("卡片不可升级");
        Toast({
          message: "卡片不可升级",
          position: "middle",
          duration: 1000,
        });
        return false;
      }
      // console.log(this.newNum,this.num)
      if (this.newNum == this.num) {
        Toast({
          message: "升级次数已用完",
          position: "middle",
          duration: 1000,
        });
        return false;
      }

      // this.showModel = true;
      this.updates1 = false;
      this.updates = false;
      this.updates2 = true;
      // console.log(this.CardInfo.tokenId);
      let cardInfos = JSON.parse(sessionStorage.getItem("cardInfo"));
      cardInfos.newnowpower = this.newnowpower;
      sessionStorage.setItem("cardInfo", JSON.stringify(cardInfos));
      let contract = await this.$Fun.HeroCard();
      const gasPrice = await this.Web3.eth.getGasPrice();
      this.$getWbe3.upgrate(
        contract,
        this.CardInfo.tokenId,
        this.account,
        gasPrice,
        (res) => {
          if (res) {
            this.$router.push("/updateSd");
            // Toast({
            //   message: "升级成功",
            //   position: "middle",
            //   duration: 1000,
            // });
            this.updates1 = true;
            this.updates = false;
            this.updates2 = false;
          }
        }
      );
    },
    bottomColBtn() {
      this.showUpgrade = false;
    },
  },
};
</script>
<style scoped>
.cardInfo {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.cardInfo-cont {
  max-width: 420px;
  height: 100%;
  background-color: #000;
  margin: auto;
  position: relative;
  background: url("../assets/img/52.png") no-repeat;
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
  z-index: 1;
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
.cardInfo-c {
  position: absolute;
  top: 57px;
  right: 0;
  left: 0;
  bottom: 0;
}
.cardInfo-c-box {
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
  position: relative;
  overflow: hidden;
}
.bottom-col {
  position: absolute;
  top: 0;
  right: 0;
  height: 35px;
  width: 55px;
  border-radius: 3px;
  overflow: hidden;
}
.bottom-col1 {
  width: 100%;
  height: 100%;
  position: relative;
}
.bottom-col2 {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 35px;
  color: #584147;
  font-size: 14px;
}
.carde-c-info {
  width: 100%;
  position: relative;
  overflow: hidden;
  margin-top: 35px;
}
.carde-info-img {
  float: left;
  width: 40%;
  border-radius: 3px;
}
.carde-info-text-box {
  float: left;
  width: 50%;
  margin-left: 5%;
}
.carde-info-text {
  width: 100%;
  height: 40px;
  overflow: hidden;
}
.carde-info-text1 {
  width: 100%;
  line-height: 24px;
  font-size: 12px;
  color: #584147;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
}
.upgrade {
  width: 100%;
  height: 40px;
  margin-top: 20px;
}
.upgrade-btn {
  width: 122px;
  height: 40px;
  background: url("../assets/img/2.png") no-repeat;
  background-size: cover;
  margin: auto;
  text-align: center;
  line-height: 40px;
  color: #5d4037;
  font-weight: 700;
}
.upgrade-b-btn {
  width: 100%;
  height: 40px;
  margin-top: 10px;
  overflow: hidden;
}
.upgrade-btn-left {
  /* float: left; */
  width: 60%;
  height: 40px;
  overflow: hidden;
  margin: auto;
}
.upgrade-btn-right {
  /* float: right; */
  width: 60%;
  height: 40px;
  overflow: hidden;
  margin: auto;
}
.upgrade-left-t {
  position: relative;
  width: 100%;
  height: 40px;
  background-color: #fff;
}
.upgrade-left-text {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 40px;
  color: #5d4037;
  font-size: 13px;
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
  right:25px;
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
.img-b9 {
  height: 370px;
  display: block;
  width: 93%;
  margin: auto;
}
.img-b10 {
  height: 525px;
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
.upgrade-box {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(24, 24, 30, 0.75);
  z-index: 1;
}
.upgrade-cont {
  width: 100%;
  position: relative;
}
.upgrade-h {
  width: 100%;
  height: 40px;
  line-height: 40px;
  font-size: 15px;
  font-weight: 700;
  color: #584147;
}
.upgrade-b-c1 {
  width: 100%;
  height: 80px;
  font-size: 12px;
  font-weight: 700;
  color: #584147;
  background: rgba(95, 66, 57, 0.15);
}
.upgrade-b-c2 {
  width: 100%;
  height: 100px;
  font-size: 12px;
  font-weight: 700;
  color: #584147;
  background: rgba(95, 66, 57, 0.15);
}
.upgrade-b-t {
  width: 100%;
  height: 30px;
}
.upgrade-b-c {
  width: 100%;
  height: 30px;
  background: rgba(95, 66, 57, 0.15);
  overflow: hidden;
}
.upgrade-right {
  float: right;
  line-height: 30px;
  font-size: 12px;
  margin-right: 5px;
  color: #5f4239;
  font-weight: 700;
}
.upgrade-left {
  float: left;
  line-height: 30px;
  font-size: 12px;
  margin-left: 5px;
  color: #5f4239;
  font-weight: 700;
}
.upgrade-right1 {
  float: right;
  line-height: 30px;
  font-size: 12px;
  margin-right: 5px;
  color: #5f4239;
  font-weight: 700;
}
.upgrade-left1 {
  float: left;
  line-height: 30px;
  font-size: 12px;
  margin-left: 5px;
  color: #5f4239;
  font-weight: 700;
}
</style>
