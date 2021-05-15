<template>
  <div class="home" @click="addrBtnShow">
    <div class="home-cont">
      <div class="head">
        <div class="head-c">
          <div class="head-c-t">
            <span>首页</span>
          </div>
        </div>
        <img src="../assets/head/head.png" class="head-img" />
        <div class="head-left">
          <!-- <img src="../assets/img/8.png" class="head-img" /> -->
          麻辣三国
        </div>

        <div class="head-right" @click="noticeBtn()">
          <img src="../assets/img/7.png" class="head-img" />
        </div>
      </div>
      <div class="home-cont-box">
        <div
          style="width: 100%;height: 100%; overflow: hidden;position: relative;"
        >
          <div
            class="bulletin"
            v-if="
              $store.state.televise != '' &&
                $store.state.televise1 != '' &&
                $store.state.televise2 != ''
            "
          >
            <div class="bulletin-img">
              <img src="../assets/img/6.png" class="head-img" />
              <div class="bulletin-img1">
                <img src="../assets/img/5.png" class="head-img" />
              </div>
              <div class="bulletin-text">
                <span style="color: #ffffff">{{ $store.state.televise }}</span
                ><span style="color: #ffebac">{{ $store.state.televise1 }}</span
                ><span style="color: #ffffff">{{
                  $store.state.televise2
                }}</span>
              </div>
            </div>
          </div>
          <div
            style="position: absolute;top:30px;left: 0;right: 0;bottom: 1px;overflow: auto;"
          >
            <div class="home-cont-c">
              <div class="home-cont-one">
                <div class="img-h">
                  <img src="../assets/img/44.png" class="head-img" />
                </div>
                <div class="img-b" style="height: 235px;">
                  <img src="../assets/img/42.png" class="head-img" />
                </div>
                <div class="img-f">
                  <img src="../assets/img/43.png" class="head-img" />
                </div>
                <div class="img-min">
                  <img src="../assets/img/41.png" class="head-img" />
                </div>
                <div class="home-cont-t">
                  <div style="width: 100%;position: relative;height: 100%;">
                    <!-- <div class="currency-ul" v-if="showK">
                      <div
                        class="currency-li"
                        v-for="itme in currencyList"
                        :key="itme.index"
                        @click="currencyListBtn(itme)"
                      >
                        <div style="width:100%;height:100%;overflow: hidden;">
                          <div class="currency-box1" style="margin-top: 10px;">
                            <img :src="itme.curImg" class="head-img" />
                          </div>
                          <div class="currency-box2" style="margin-top: 10px;">
                            {{ itme.address }}
                          </div>
                        </div>
                      </div>
                    </div> -->
                    <div class="home-cont-h">
                      <!-- 我的信息 -->
                      <div class="home-cont-h-l">
                        <div class="home-cont-h-t">我的信息</div>
                        <img src="../assets/img/3.png" class="head-img" />
                      </div>
                      <div class="currency-box" @click="currency()">
                        <div style="width:100%;height:100%;overflow: hidden;">
                          <div class="currency-box1">
                            <img src="../assets/img/53.png" class="head-img" />
                          </div>
                          <div class="currency-box2">{{ CURaddress }}</div>
                        </div>
                      </div>
                    </div>
                    <div class="home-cont-info">
                      <div class="info-log">
                        <img src="../assets/img/11.png" class="head-img" />
                      </div>
                      <div class="info-left">我的战力</div>
                      <div class="info-right">
                        <span>{{ power }}</span>
                      </div>
                    </div>
                    <div class="home-cont-info">
                      <div class="info-log">
                        <img src="../assets/img/12.png" class="head-img" />
                      </div>
                      <div class="info-left">我的卡牌</div>
                      <div class="info-right">
                        <span>{{ CardNum }}</span>
                      </div>
                    </div>
                    <div class="home-cont-info">
                      <div class="info-log">
                        <img src="../assets/img/13.png" class="head-img" />
                      </div>
                      <div class="info-left">我的铜板数量</div>
                      <div class="info-right">
                        <span>{{ mNum }}</span>
                      </div>
                    </div>
                    <div class="home-cont-info">
                      <div class="info-log">
                        <img src="../assets/img/54.png" class="head-img" />
                      </div>
                      <div class="info-left">我的金条数量</div>
                      <div class="info-right">
                        <span>{{ RTToverage }}</span>
                      </div>
                    </div>
                    <div class="info-btn">
                      <div class="info-btn-box">
                        <!-- 卡牌商店 -->
                        <div class="info-text-c" @click="storeBtn">
                          卡牌商店
                        </div>
                        <img src="../assets/img/2.png" class="head-img" />
                      </div>
                      <div class="info-btn-box1" @click="backpackBtn()">
                        <!-- 我的背包 -->
                        <div class="info-text-c">我的背包</div>
                        <img src="../assets/img/2.png" class="head-img" />
                      </div>
                      <!-- <div class="info-btn-box11" @click="pullDown">
                   
                        <div class="info-text-c">领取金条</div>
                        <img src="../assets/img/2.png" class="head-img" />
                      </div> -->
                      <div class="info-btn-box11" @click="pullDown1">
                        <!-- 我的背包 -->
                        <div class="info-text-c">领取金条</div>
                        <img src="../assets/img/4.png" class="head-img" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="home-banner">
                <img src="../assets/home/home.png" class="head-img" />
                <div class="home-banner-box">
                  <div class="home-banner-box1">
                    <img src="../assets/img/59.png" class="head-img" />
                    <div class="info-btn">
                      <!-- <div class="info-btn-box2" @click="ShopBtn">
                        <div class="info-text-c">进入市场</div>
                        <img src="../assets/img/2.png" class="head-img" />
                      </div> -->
                      <div class="info-btn-box2" @click="ShopBtn1">
                        <div class="info-text-c">进入市场</div>
                        <img src="../assets/img/4.png" class="head-img" />
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- <div class="home-cont-two">
                <div class="img-h">
                  <img src="../assets/img/44.png" class="head-img" />
                </div>
                <div class="img-b1">
                  <img src="../assets/img/42.png" class="head-img" />
                </div>
                <div class="img-f">
                  <img src="../assets/img/43.png" class="head-img" />
                </div>
                <div class="img-min">
                  <img src="../assets/img/41.png" class="head-img" />
                </div>
                <div class="home-cont-t">
                  <div class="home-cont-h">
                    <div class="home-cont-h-l">
                      <div class="home-cont-h-t">我的交易</div>
                      <img src="../assets/img/3.png" class="head-img" />
                    </div>
                  </div>
                  <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/9.png" class="head-img" />
                    </div>
                    <div class="info-left">全服累计交易额</div>
                    <div class="info-right">
                      <span>{{ transaction }}</span>
                    </div>
                  </div>
                  <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/28.png" class="head-img" />
                    </div>
                    <div class="info-left">全服24小时交易额(OKT)</div>
                    <div class="info-right">
                      <span>{{ transactionOne }}</span>
                    </div>
                  </div>
                  <div class="info-btn">
                    <div class="info-btn-box2" @click="ShopBtn">
                      <div class="info-text-c">进入商店</div>
                      <img src="../assets/img/2.png" class="head-img" />
                    </div>
                  </div>
                </div>
              </div> -->
              <!-- <div class="home-cont-two"></div> -->
              <div class="home-cont-three">
                <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
                <div class="img-h">
                  <img src="../assets/img/44.png" class="head-img" />
                </div>
                <div class="img-b">
                  <img src="../assets/img/42.png" class="head-img" />
                </div>
                <div class="img-f">
                  <img src="../assets/img/43.png" class="head-img" />
                </div>
                <div class="img-min">
                  <img src="../assets/img/41.png" class="head-img" />
                </div>
                <div class="home-cont-t">
                  <div class="home-cont-h">
                    <!-- 我的交易 -->
                    <div class="home-cont-h-l">
                      <div class="home-cont-h-t">世界数据</div>
                      <img src="../assets/img/3.png" class="head-img" />
                    </div>
                  </div>
                  <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/11.png" class="head-img" />
                    </div>
                    <div class="info-left">全服总战力</div>
                    <div class="info-right">
                      <span>{{ allPower }}</span>
                    </div>
                  </div>
                  <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/12.png" class="head-img" />
                    </div>
                    <div class="info-left">全服卡牌数量</div>
                    <div class="info-right">
                      <span>{{ allCardNum }}</span>
                    </div>
                  </div>
                  <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/9.png" class="head-img" />
                    </div>
                    <div class="info-left">全服累计交易额</div>
                    <div class="info-right">
                      <span>{{ transaction }}</span>
                    </div>
                  </div>
                  <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/28.png" class="head-img" />
                    </div>
                    <div class="info-left">全服24小时交易额</div>
                    <div class="info-right">
                      <span>{{ transactionOne }}</span>
                    </div>
                  </div>
                  <!-- <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/13.png" class="head-img" />
                    </div>
                    <div class="info-left">TVL(OKT)</div>
                    <div class="info-right">
                      <span>{{ tvl }}</span>
                    </div>
                  </div>
                  <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/14.png" class="head-img" />
                    </div>
                    <div class="info-left">集卡平均APY</div>
                    <div class="info-right">
                      <span>{{ apy3 }}</span>
                    </div>
                  </div>
                  <div class="home-cont-info">
                    <div class="info-log">
                      <img src="../assets/img/55.png" class="head-img" />
                    </div>
                    <div class="info-left">权益分红池</div>
                    <div class="info-right">
                      <span>{{ nover2 }}</span>
                    </div>
                  </div> -->
                </div>
              </div>
              <!-- <homeListview/> -->
            </div>
          </div>
        </div>
      </div>
      <div class="footer-box">
        <div class="footer-box-img">
          <img src="../assets/head/head.png" class="head-img" />
          <div class="footer-box-f">
            <div class="footer-box-c">
              <div class="footer-box-li">
                <img src="../assets/img/10.png" class="head-img" />
              </div>
              <div class="footer-box-li" @click="depositBtn()">
                <img src="../assets/img/15.png" class="head-img" />
              </div>
              <div class="footer-box-li" @click="exchangeBtn()">
                <img src="../assets/img/16.png" class="head-img" />
              </div>
              <div class="footer-box-li" @click="combinationBtn()">
                <img src="../assets/img/17.png" class="head-img" />
              </div>
            </div>
            <div style="width: 100%; overflow: hidden">
              <div class="footer-box-li-t" style="color: #f9be61">首页</div>
              <div class="footer-box-li-t">质押挖矿</div>
              <div class="footer-box-li-t">铜板兑换</div>
              <div class="footer-box-li-t">组合挖矿</div>
            </div>
          </div>
        </div>
      </div>
      <div class="home-popBox" @click="popBoxBtn()" v-if="showK">
        <div class="home-popBox1">
          <div class="poplist-ul">
            <div
              style="width:100%;height:30px;overflow: hidden;"
              v-for="itme in currencyList"
              :key="itme.index"
            >
              <div class="currency-box1" style="padding-top: 5px;">
                <img :src="itme.curImg" class="head-img" />
              </div>
              <div
                class="currency-box2"
                style="padding-top: 5px;  color: #9a7f73;"
              >
                {{ itme.name }}: <span>{{ itme.num }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <popUpView
        :datacur="datacur"
        v-if="popUpShow"
        @fatherMethod="fatherMethod"
        :Rewards="Rewards"
      />
      <noticeModelView v-if="noticeshow" @noticeModelBtn="noticeModelBtn" />
    </div>
  </div>
</template>

<script>
import crimg from "../assets/img/54.png";
import crsimg from "../assets/img/USDT.png";
import crsimg1 from "../assets/img/Eth.png";
import { OktToken, USDKToken, RttTokenS } from "../common/config";
import { Toast } from "mint-ui";
import popUpView from "../model/popUp";
import noticeModelView from "../model/noticeModel";
// import homeListview from "../model/homeList";
var Okt = OktToken;
var USDK = USDKToken;
var Rtt = RttTokenS;
var Okt1 = Okt.substr(0, 4) + "..." + Okt.substr(-4);
var USDK1 = USDK.substr(0, 4) + "..." + USDK.substr(-4);
var Rtt1 = Rtt.substr(0, 4) + "..." + Rtt.substr(-4);
export default {
  name: "home",
  data() {
    return {
      power: 0,
      datacur: "home",
      CardNum: 0,
      mNum: 0,
      allPower: 0,
      allCardNum: 0,
      showK: false,
      popUpShow: false,
      RTToverage: 0,
      noticeshow: false,
      currencyList: [
        {
          curImg: crimg,
          address: Rtt1,
          index: 0,
          name: "金条",
          num: "",
        },
        {
          curImg: crsimg,
          address: USDK1,
          index: 1,
          name: "USDK",
          num: "",
        },
        {
          curImg: crsimg1,
          address: Okt1,
          index: 2,
          name: "ETH",
          num: "",
        },
      ],
      CURaddress: "",
      account: "",
      Web3: null,
      transaction: 0,
      transactionOne: 0,
      tvl: 0,
      apy: 0,
      apy1: 0,
      apy2: 0,
      apy3: 0,
      Rewards: "0",
      CardUSDK: 0,
      MttTUSDK: 0,
      hVlue: 0,
      popBoxBtnShow: false,
      USDKVule: 0,
      RttVule1: 0,
      VulaMun: 0,
      nover: 0,
      nover1: 0,
      nover2: 0,
    };
  },
  components: {
    popUpView,
    noticeModelView,
    // homeListview
  },
  async mounted() {
    // var cloneTime = localStorage.getItem("cloneTime");
    // if (!cloneTime) {

    //   this.noticeshow = true;
    // }
    await this.$getWbe3.Init(
      (addr) => {
        //得到相应的钱包地址

        this.account = addr;
        // console.log(this.account);
        this.getInfoHome();
        this.CURaddress = addr.substr(0, 4) + "..." + addr.substr(-4);
        //console.log(addr, "111");
      },
      (provider) => {
        // console.log(provider);
        this.Web3 = provider;
      }
    );
  },
  methods: {
    async getInfoHome() {
      let Mttcontract = this.$Fun.MttToken();
      let Shopcontract = this.$Fun.CardShop();
      // let Morcontract = this.$Fun.Mortgage();
      //  let contract = await this.$Fun.HeroCard();
      let Detailcontract = await this.$Fun.CardDetail();
      let Cardcontract = this.$Fun.CardToken();
      let Rttcontract = this.$Fun.RttToken();
      let USDKTokens = this.$Fun.USDKTokens();
      // let OktMortgage = this.$Fun.OktTokens();
      // let r = 1000;
      // await this.$getWbe3.getTokenIds(
      //   Detailcontract,
      //   this.account,
      //   r,
      //   (res) => {
      //     console.log(res);
      //     if (res) {
      //       for (var i in res) {
      //         // console.log(res[i],'pppp');
      //         this.power=this.power+parseInt(res[i][1])
      //       }
      //     }
      //   }
      // );
      await this.$getWbe3.MttbalanceOf(USDKTokens, this.account, (res) => {
        // console.log(res);
        var num = this.Web3.utils.fromWei(res);
        this.currencyList[1].num = parseFloat(num).toFixed(2);
      });
      await this.Web3.eth.getBalance(this.account).then((res) => {
        //获取原生代币数量
        // console.log(res);
        var vuleC = this.Web3.utils.fromWei(res);
        this.currencyList[2].num = parseFloat(vuleC).toFixed(2);
      });
      await this.$getWbe3.RttbalanceOf(Rttcontract, this.account, (res) => {
        // console.log(res, 989);
        var RTToverage = this.Web3.utils.fromWei(res);
        this.RTToverage = parseFloat(RTToverage).toFixed(2);
        this.currencyList[0].num = parseFloat(RTToverage).toFixed(2);
      });
      await this.$getWbe3.getUsrCombatPowers(
        //获取用户战力
        Detailcontract,
        this.account,
        (res) => {
          // Toast({
          //   message: res+'-'+this.account,
          //   position: "middle",
          //   duration: 5000,
          // });
          // console.log(res, 44456);
          this.power = res;
        }
      );
      await this.$getWbe3.balanceOf(Cardcontract, this.account, (res) => {
        //console.log(res)
        this.CardNum = res;
      });
      await this.$getWbe3.MttbalanceOf(Mttcontract, this.account, (res) => {
        // console.log(res, "0992540");
        this.mNum = this.Web3.utils.fromWei(res);
        this.mNum = parseFloat(this.mNum).toFixed(2);
      });

      await this.$getWbe3.getTotalCombatPowers(Detailcontract, (res) => {
        // console.log(res, 877874);
        this.allPower = res;
      });

      await this.$getWbe3.totalSupply(Cardcontract, (res) => {
        // console.log(res);
        this.allCardNum = res;
      });

      // console.log(this.Web3, 999);

      // await this.$getWbe3.getCardTurnover(Shopcontract, "USDK", (res) => {
      //   console.log(res, 9999);
      //   this.transaction = this.Web3.utils.fromWei(res[0]);
      //   this.transactionOne = this.Web3.utils.fromWei(res[1]);
      // });
      await this.$getWbe3.getTotalTrade(Shopcontract, "USDK", (res) => {
        // console.log(res, 10000);
        this.transaction = this.Web3.utils.fromWei(res[0]);
        this.transactionOne = this.Web3.utils.fromWei(res[1]);
      });
      // await this.$getWbe3.getTotalValueLock(OktMortgage, (res) => {
      //   //获取OKT TVL
      //   // console.log(res, 999952223);
      //   this.tvl = this.Web3.utils.fromWei(res);
      // });

      // await this.$getWbe3.getTokenAPY(Morcontract, "RttToken", (res) => {
      //   // console.log(res, "RttToken");
      //   this.apy = res;
      // });
      // await this.$getWbe3.getTokenAPY(Morcontract, "USDK", (res) => {
      //   // console.log(res, "USDK");
      //   this.apy1 = res;
      // });
      // await this.$getWbe3.getTokenAPY2(OktMortgage, (res) => {
      //   // console.log(res, "OKT");
      //   // console.log(res, "llll");
      //   this.apy2 = res;
      // });
      // var u =
      //   parseFloat(this.apy) + parseFloat(this.apy1) + parseFloat(this.apy2);
      // this.apy3 = u / 3;
      // this.apy3 = parseFloat(this.apy3).toFixed(2);
      // this.$getWbe3.getCardTurnover(Shopcontract, (res) => {
      //   console.log(res);
      //   this.nover = res;
      // });
      // this.$getWbe3.getMttTurnover(Mttcontract, (res) => {
      //   // console.log(res);
      //   this.nover1 = res;
      // });

      this.$getWbe3.getTotalTrade(Mttcontract, "USDK", (res) => {
        // console.log(res, "qwe222");
        this.nover = this.Web3.utils.fromWei(res);
      });
      var vueq = parseFloat(this.nover) + parseFloat(this.transaction);
      this.nover2 = vueq * 0.2;
    },
    currency() {
      this.showK = !this.showK;
    },
    popBoxBtn() {
      this.showK = !this.showK;
    },
    addrBtnShow() {
      // console.log(this.showK)
      //   this.showK = !this.showK;
    },
    currencyListBtn(e) {
      this.showK = 0;
      // this.CURaddress = e.address;
      console.log(e);
      // localStorage.setItem("addrsType", JSON.stringify(e));
    },
    async pullDown() {
      this.popUpShow = true;
      let contract = this.$Fun.CardDetail();
      // console.log(this.account);
      await this.$getWbe3.calcUsrRewards(contract, this.account, (res) => {
        // console.log(res, 1236666);
        this.Rewards = this.Web3.utils.fromWei(res);
        this.Rewards = parseFloat(this.Rewards).toFixed(2);
      });
      // await this.getRewards();
    },
    pullDown1() {
      Toast({
        message: "敬请期待···",
        position: "middle",
        duration: 1000,
      });
    },
    async fatherMethod(e) {
      if (e) {
        if (this.Rewards == 0) {
          Toast({
            message: "当前无可领取奖励",
            position: "middle",
            duration: 1000,
          });
          this.popUpShow = false;
          return false;
        }
        await this.claim();
      }
      this.popUpShow = false;
    },
    async claim() {
      let contract = this.$Fun.CardDetail();
      const gasPrice = await this.Web3.eth.getGasPrice();
      this.$getWbe3.claim(contract, gasPrice, this.account, (res) => {
        console.log(res);
        Toast({
          message: "领取成功",
          position: "middle",
          duration: 1000,
        });
        this.getInfoHome();
      });
    },
    noticeBtn() {
      this.$router.push("/notice");
    },
    storeBtn() {
      this.$router.push("/store");
    },
    depositBtn() {
      this.$router.push("/deposit");
    },
    exchangeBtn() {
      this.$router.push("/exchange");
    },
    combinationBtn() {
      this.$router.push("/combinationEit");
    },
    backpackBtn() {
      this.$router.push("/backpack");
    },
    // ShopBtn() {
    //   this.$router.push("/business");
    // },
    ShopBtn1() {
      Toast({
        message: "敬请期待···",
        position: "middle",
        duration: 1000,
      });
    },
    noticeModelBtn() {
      this.noticeshow = false;
    },
  },
};
</script>

<style scoped>
.home {
  width: 100%;
  height: 100%;
}
.home-cont {
  max-width: 420px;
  height: 100%;
  margin: auto;
  background-color: #000;
  background: url("../assets/img/52.png") no-repeat;
  background-size: cover;
  position: relative;
}
.home-cont-box {
  position: absolute;
  top: 57px;
  width: 100%;
  bottom: 82px;
  /* overflow: auto; */
}
.home-cont-c {
  width: 100%;
  position: relative;
  opacity: 0.75;
  overflow: hidden;
}
.head {
  width: 100%;
  height: 56px;
  position: relative;
  background: linear-gradient(180deg, #13232e, #2d496f);
}
.head-c {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.head-c-t {
  width: 100%;
  height: 100%;
  text-align: center;
  line-height: 56px;
  color: #faf5e4;
}
.head-img {
  width: 100%;
  height: 100%;
  display: block;
}
.head-left {
  position: absolute;
  top: 0;
  left: 10px;
  width: 75px;
  height: 90%;
  line-height: 60px;
  color: #d8be97;
  font-weight: 700;
}
.head-right {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 30px;
  height: 36px;
}
.bulletin {
  width: 100%;
  height: 30px;
}
.bulletin-img {
  width: 90%;
  height: 20px;
  margin: auto;
  padding-top: 10px;
  position: relative;
}
.bulletin-img1 {
  position: absolute;
  top: 15px;
  left: 20px;
  width: 8px;
  height: 12px;
}
.bulletin-text {
  position: absolute;
  top: 12px;
  left: 35px;
  right: 0;
  bottom: 0;
  font-size: 12px;
}
.home-cont-one {
  width: 95%;
  margin: auto;
  margin-top: 20px;
  position: relative;
}
.img-h {
  width: 100%;
  display: block;
}
.img-b {
  height: 180px;
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
.home-cont-t {
  position: absolute;
  left: 40px;
  right: 40px;
  top: 12px;
  bottom: 7px;
}
.home-cont-h {
  width: 100%;
  height: 40px;
  line-height: 40px;
  font-size: 14px;
  position: relative;
}
.currency-box {
  position: absolute;
  top: 10px;
  right: 0;
  width: 100px;
  line-height: 20px;
  background-color: rgba(95, 66, 57, 0.15);
}
.currency-box1 {
  width: 20px;
  height: 20px;
  float: left;
  margin-left: 5px;
}
.currency-box2 {
  float: left;
  margin-left: 2px;
  line-height: 20px;
  font-size: 12px;
  color: #5e4138;
}
.currency-ul {
  position: absolute;
  top: 30px;
  right: 0;
  width: 100px;
  max-height: 150px;
  z-index: 999;
  overflow: auto;
  background-color: #fff;
}
.currency-li {
  height: 40px;
}
.home-cont-h-l {
  width: 100px;
  height: 20px;
  /* padding-top: 10px; */
  position: relative;
}
.home-cont-h-t {
  position: absolute;
  top: -9px;
  left: 8px;
  right: 0;
  bottom: 0;
  color: #faf5e4;
  font-size: 13px;
}
.home-cont-info {
  width: 100%;
  height: 20px;
  background-color: #0e0d0d2e;
  overflow: hidden;
  margin-bottom: 10px;
}
.info-left {
  float: left;
  line-height: 20px;
  font-size: 12px;
  margin-left: 5px;
  font-weight: 700;
  color: #72441c;
}
.info-right {
  float: right;
  line-height: 20px;
  font-size: 12px;
  margin-right: 5px;
  font-weight: 500;
  color: #d8731b;
}
.info-log {
  float: left;
  width: 15px;
  height: 15px;
  margin-top: 2.5px;
  margin-left: 5px;
  border-radius: 100%;
  background-color: #fff;
}
.info-btn {
  width: 100%;
  height: 50px;
  overflow: hidden;
  float: left;
}
.info-btn-box {
  float: left;
  width: 30%;
  height: 35px;
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
  float: left;
  width: 30%;
  height: 35px;
  margin-top: 8px;
  margin-left: 10px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  position: relative;
}
.info-btn-box11 {
  float: left;
  width: 30%;
  height: 35px;
  margin-top: 8px;
  margin-left: 10px;
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
  /* margin: auto; */
  /* position: relative; */
  position: absolute;
  right: -5px;
  top: 40px;
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
.home-banner {
  width: 90%;
  height: 120px;
  margin: auto;
  margin-top: 20px;
  position: relative;
}
.home-banner-box {
  position: absolute;
  top: 5px;
  left: 10px;
  right: 10px;
  bottom: 15px;
  background-color: #5d4037;
}
.home-banner-box1 {
  width: 100%;
  height: 100%;
}
.home-cont-two {
  width: 95%;
  height: 50px;
  margin: auto;
  margin-top: 20px;
  position: relative;
}
.footer-box {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  height: 80px;
  background: linear-gradient(180deg, #13232e, #2d496f);
}
.footer-box-img {
  position: relative;
  width: 100%;
  height: 100%;
}
.footer-box-f {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.footer-box-c {
  width: 100%;
  overflow: hidden;
}
.footer-box-li {
  float: left;
  width: 12.5%;
  margin-left: 10%;
  margin-top: 10px;
}
.footer-box-li-t {
  float: left;
  width: 16%;
  margin-left: 7%;
  font-size: 12px;
  text-align: center;
  color: #e3dacb;
  margin-top: 5px;
}
.home-cont-three {
  width: 95%;
  margin: auto;
  margin-top: 20px;
  position: relative;
}
.home-popBox {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
.home-popBox1 {
  width: 100%;
  height: 100%;
  position: relative;
}
.poplist-ul {
  position: absolute;
  top: 149px;
  right: 10px;
  width: 140px;
  /* height: 30px; */
  background: rgba(66, 57, 43, 0.9);
}
</style>
