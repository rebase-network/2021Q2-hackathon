<template>
  <div class="fusing">
    <div class="fusing-conter">
      <header>
        <div class="header-img-box">
          <div class="header-left">
            <div class="header-left-cont" @click="fusingfoBtn">
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
      <div class="fusing-body-conter">
        <div class="fusing-body-c">
          <div class="deposit-num">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="head-img" />
            </div>
            <div class="img-b21">
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
                <div class="home-cont-h-t">合成信息</div>
                <img src="../assets/img/3.png" class="head-img" />
              </div>
              <div style="width: 100%; overflow: hidden; margin-top: 20px">
                <div class="deposit-left-t" style="margin-left: 5px">
                  已选择卡片
                </div>
                <div class="deposit-right-t" style="color: #584147">数量</div>
              </div>
              <div class="deposit-cont-box">
                <div class="itmeListBox">
                  <div
                    style="width: 100%; overflow: hidden; margin-top: 10px"
                    v-for="itme in CardList"
                    :key="itme.index"
                  >
                    <div class="deposit-left-t" style="margin-left: 5px">
                      {{ itme.name }}
                    </div>
                    <div class="deposit-right-t" style="color: #584147">1</div>
                  </div>
                  <div
                    style="
                    width: 100%;
                    overflow: hidden;
                    margin-top: 10px;
                    border-top: 1px solid #584147;
                  "
                  >
                    <div class="deposit-left-t" style="margin-left: 5px">
                      共计
                    </div>
                    <div class="deposit-right-t" style="color: #584147">
                      {{ CardList.length }}
                    </div>
                  </div>
                </div>
              </div>
              <div class="home-cont-h-l" style="top: 10px">
                <div class="home-cont-h-t">战力信息</div>
                <img src="../assets/img/3.png" class="head-img" />
              </div>
              <div class="deposit-num-cOne">
                <div style="width: 100%; overflow: hidden; margin-top: 10px">
                  <div class="deposit-left-t" style="margin-left: 5px">
                    目前战力
                  </div>
                  <div class="deposit-right-t" style="color: #584147">
                    {{ nowpower }}
                  </div>
                </div>
                <div style="width: 100%; overflow: hidden; margin-top: 10px">
                  <div class="deposit-left-t" style="margin-left: 5px">
                    合成后战力
                  </div>
                  <div class="deposit-right-t">{{ newpower }}</div>
                </div>
                <div style="width: 100%; overflow: hidden; margin-top: 10px">
                  <div class="deposit-left-t" style="margin-left: 5px">
                    需消耗铜板
                  </div>
                  <div class="deposit-right-t">{{ depleteNum }}</div>
                </div>
              </div>
              <div class="info-btn">
                <div class="info-btn-box" @click="warrantBtn" v-if="showBuy1">
                  <!-- 卡牌商店 -->
                  <div class="info-text-c">授权卡牌</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
                <div class="info-btn-box" @click="warrantBtn1" v-if="showBuy2">
                  <!-- 卡牌商店 -->
                  <div class="info-text-c">授权铜板</div>
                  <img src="../assets/img/2.png" class="head-img" />
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
                    <!-- <div class="info-text-c1">...</div> -->
                  </div>
                  <img src="../assets/img/4.png" class="head-img" />
                </div>
                <!-- <div class="info-btn-box" v-if="!showBuy1">
                 
                  <div class="info-text-c">授权</div>
                  <img src="../assets/img/4.png" class="head-img" />
                </div> -->
                <!-- <div class="info-btn-box1" v-if="!showBuy" @click="fusingBtn1">
                  
                  <div class="info-text-c">点击合成</div>
                  <img src="../assets/img/4.png" class="head-img" />
                </div> -->
                <div class="info-btn-box1" @click="fusingBtn" v-if="showBuy">
                  <!-- 我的背包 -->
                  <div class="info-text-c">点击合成</div>
                  <img src="../assets/img/2.png" class="head-img" />
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
// import Exchangec from "../../build/CardTokenEx.json";
// import m3t from "../../build/MttToken.json";
// import { CardTokens, MttToken } from "../common/config";
import submitModel from "../model/submitModel";
import heroList from "../common/heroIfon.json";
import { Toast } from "mint-ui";
export default {
  data() {
    return {
      CardList: [],
      tokenId: [],
      nowpower: 0,
      newpower: 0,
      showBuy: false,
      showBuy1: false,
      showBuy2: true,
      showSate: false,
      heroList: heroList.heroList,
      account: "",
      Web3: "",
      Modelmsg: "提交合成成功,请到背包看看吧",
      showModel: false,
      depleteNum: 0,
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
        // console.log(this.account);
        this.contracts = this.$Fun.MttToken();
        let addrs = this.$Fun.HeroCard().options.address;
        this.contracts.methods
          .allowance(this.account, addrs)
          .call()
          .then((res) => {
            this.allProvider = this.Web3.utils.fromWei(res);
            console.log(this.allProvider, 9912311199);
            if (parseInt(this.allProvider) > 0) {
              this.showBuy2 = false;
              this.showBuy1 = true;
            } else {
              this.showBuy1 = false;
              this.showBuy2 = true;
            }
            // if (this.allProvider >= this.editValue) {

            // } else {

            // }
          });
      },
      (provider) => {
        this.Web3 = provider;
      }
    );
    await this.$store.mutations.initWeb3Account();
    var CardList = JSON.parse(sessionStorage.getItem("cardList"));
    if (CardList) {
      this.CardList = CardList;
      this.tokenId = this.CardList.map((item) => {
        return item.tokenId;
      });
      this.CardList.map((item) => {
        if (item.genre == "5") {
          this.depleteNum = 0;
          this.showBuy2 = false;
          this.showBuy1 = true;
        }
        if (item.genre == "4") {
          this.depleteNum = 1000;
        }
        if (item.genre == "3") {
          this.depleteNum = 2000;
        }
        this.nowpower = this.nowpower + parseInt(item.power);
      });
      this.newpower = Math.round(this.nowpower * 1.2);
    }

    await this.getAinfo();
  },
  methods: {
    async getAinfo() {
      // const web3 = this.$store.state.web3; //初始化web3
      //const account = sessionStorage["account"]; //取出缓存中的钱包地址;
      this.CardTokenfun = this.$store.state.CardTokenFun;
      this.M3Tfun = this.$store.state.M3Tfun;
    },
    fusingfoBtn() {
      this.$router.push("/backpack");
    },
    async warrantBtn1() {
      this.showSate = true;
      this.showBuy2 = false;
      let HeroCard = await this.$Fun.HeroCard().options.address;
      let contract = await this.$Fun.MttToken();
      const gasPrice = await this.Web3.eth.getGasPrice();
      let money = 199999999;
      let value = this.Web3.utils.toWei(money.toString(), "ether");
      this.$getWbe3.Approve(
        HeroCard,
        value,
        contract,
        gasPrice,
        this.account,
        (res) => {
          if (res) {
            this.showBuy1 = true;
          }
        }
      );
    },
    async warrantBtn() {
      this.showSate = true;
      this.showBuy1 = false;
      let contract = await this.$Fun.CardToken();
      // let contract = await this.$Fun.MttToken();
      //let addr = await this.$Fun.CardToken().options.address;
      const gasPrice = await this.Web3.eth.getGasPrice();
      this.$getWbe3.approveMultiple(
        this.tokenId,
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
    },
    fusingBtn1() {
      // Toast("请先授权");
      Toast({
        message: "请先授权",
        position: "middle",
        duration: 1000,
      });
    },
    async fusingBtn() {
      this.showSate = true;
      this.showBuy = false;
      let contract = await this.$Fun.HeroCard();
      const gasPrice = await this.Web3.eth.getGasPrice();
      // this.showModel = true;
      this.$getWbe3.synthesis(
        contract,
        this.tokenId,
        this.account,
        gasPrice,
        (res) => {
          this.showSate = false;
          this.showBuy1 = true;
          this.showBuy = false;
          // console.log(res);
          if (res.events.Synthesis.returnValues) {
            let cardInfo = res.events.Synthesis.returnValues;
            console.log(cardInfo);
            let obj = {};
            obj.genre = cardInfo._quality;
            obj.imgUrlC =
              parseInt(cardInfo["1"]) * 100 + parseInt(cardInfo["2"]);
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
            var accountT = account.substr(0, 4) + "..." + account.substr(-4);
            this.$store.state.televise = "恭喜";
            this.$store.state.televise1 = accountT;
            this.$store.state.televise2 = this.caderP + " " + obj.name;
            sessionStorage.setItem("objList", JSON.stringify(obj));
          }
          this.$router.push("/fusionEnd");
          //this.$router.push("/buyEnd");
          // this.$store.state.cardType = 1;
          // this.showBuy1 = true;
          // this.showBuy =false;
          sessionStorage.setItem("cardType", 1);
        }
      );
    },
    submitModelMethod() {
      this.showModel = false;
    },
  },
};
</script>
<style scoped>
.fusing {
  width: 100%;
  height: 100%;
  position: relative;
}
.fusing-conter {
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
.fusing-body-conter {
  position: absolute;
  top: 58px;
  left: 0;
  right: 0;
  bottom: 0;
}
.fusing-body-c {
  width: 100%;
  height: 100%;
  position: relative;
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
  position: relative;
}
.itmeListBox {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
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
.img-b21 {
  height: 445px;
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
</style>
