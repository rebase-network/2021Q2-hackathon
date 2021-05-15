<template>
  <div class="home">
    <div class="home-cont">
      <div class="head">
        <div class="head-c">
          <div class="head-c-t">
            <span>质押挖矿</span>
          </div>
        </div>
        <img src="../assets/head/head.png" class="head-img" />
        <div class="head-left">
          <img src="../assets/img/8.png" class="head-img" />
        </div>

        <div class="head-right" @click="noticeBtn()">
          <img src="../assets/img/7.png" class="head-img" />
        </div>
      </div>
      <div class="deposit">
        <div class="deposit-content">
          <div class="home-cont-one">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="head-img" />
            </div>
            <div class="img-b3">
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
                <!-- 我的信息 -->
                <div class="home-cont-h-l">
                  <div class="home-cont-h-t" style="top: -9px">
                    金条质押
                  </div>
                  <img src="../assets/img/3.png" class="head-img" />
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/56.png" class="head-img" />
                </div>
                <div class="info-left">我的质押</div>
                <div class="info-right">
                  <span>{{ MyRTTdeposit }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/58.png" class="head-img" />
                </div>
                <div class="info-left">我可领取金条的数量</div>
                <div class="info-right">
                  <span>{{ RewardsOne }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/39.png" class="head-img" />
                </div>
                <div class="info-left">APY</div>
                <div class="info-right">
                  <span>{{ ListAPY }}</span
                  >%
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/21.png" class="head-img" />
                </div>
                <div class="info-left">总质押量</div>
                <div class="info-right">
                  <span>{{ RTTdeposit }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/22.png" class="head-img" />
                </div>
                <div class="info-left">总量</div>
                <div class="info-right"><span>48000</span></div>
              </div>
              <div class="info-btn">
                <div class="info-btn-box" @click="WithdrawBtn(0)">
                  <div class="info-text-c">质押/赎回</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
                <div class="info-btn-box1" @click="pullDown(0)">
                  <div class="info-text-c">领取金条</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
              </div>
            </div>
          </div>
          <div class="home-cont-one">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="head-img" />
            </div>
            <div class="img-b3">
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
                <!-- 我的信息 -->
                <div class="home-cont-h-l">
                  <div class="home-cont-h-t" style="top: -9px">
                    USDK质押
                  </div>
                  <img src="../assets/img/3.png" class="head-img" />
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/56.png" class="head-img" />
                </div>
                <div class="info-left">我的质押</div>
                <div class="info-right">
                  <span>{{ MyRTTdeposit1 }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/58.png" class="head-img" />
                </div>
                <div class="info-left">我可领取金条的数量</div>
                <div class="info-right">
                  <span>{{ RewardsTow }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/39.png" class="head-img" />
                </div>
                <div class="info-left">APY</div>
                <div class="info-right">
                  <span>{{ ListAPY1 }}</span
                  >%
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/21.png" class="head-img" />
                </div>
                <div class="info-left">总质押量</div>
                <div class="info-right">
                  <span>{{ RTTdeposit1 }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/22.png" class="head-img" />
                </div>
                <div class="info-left">总量</div>
                <div class="info-right"><span>16000</span></div>
              </div>
              <div class="info-btn">
                <div class="info-btn-box" @click="WithdrawBtn(1)">
                  <div class="info-text-c">质押/赎回</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
                <div class="info-btn-box1" @click="pullDown(1)">
                  <div class="info-text-c">领取金条</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
              </div>
            </div>
          </div>
          <div class="home-cont-one">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="head-img" />
            </div>
            <div class="img-b3">
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
                <!-- 我的信息 -->
                <div class="home-cont-h-l">
                  <div class="home-cont-h-t" style="top: -9px">
                    ETH质押
                  </div>
                  <img src="../assets/img/3.png" class="head-img" />
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/56.png" class="head-img" />
                </div>
                <div class="info-left">我的质押</div>
                <div class="info-right">
                  <span>{{ MyRTTdeposit2 }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/58.png" class="head-img" />
                </div>
                <div class="info-left">我可领取金条的数量</div>
                <div class="info-right">
                  <span>{{ RewardsThree }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/39.png" class="head-img" />
                </div>
                <div class="info-left">APY</div>
                <div class="info-right">
                  <span>{{ ListAPY2 }}</span
                  >%
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/21.png" class="head-img" />
                </div>
                <div class="info-left">总质押量</div>
                <div class="info-right">
                  <span>{{ RTTdeposit2 }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/22.png" class="head-img" />
                </div>
                <div class="info-left">总量</div>
                <div class="info-right"><span>16000</span></div>
              </div>
              <div class="info-btn">
                <div class="info-btn-box" @click="WithdrawBtn(2)">
                  <div class="info-text-c">质押/赎回</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
                <div class="info-btn-box1" @click="pullDown(2)">
                  <div class="info-text-c">领取金条</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
              </div>
            </div>
          </div>
          <!-- <div class="home-cont-one">
            <div class="img-h">
              <img src="../assets/img/44.png" class="head-img" />
            </div>
            <div class="img-b3">
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
          
                <div class="home-cont-h-l1">
                  <div class="home-cont-h-t" style="top: -9px">
                    LP质押挖矿
                  </div>
                  <img src="../assets/img/3.png" class="head-img" />
                </div>
                <div class="right-box">(敬请期待...)</div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/56.png" class="head-img" />
                </div>
                <div class="info-left">我的质押</div>
                <div class="info-right"><span></span></div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/58.png" class="head-img" />
                </div>
                <div class="info-left">我可领取金条的数量</div>
                <div class="info-right"><span></span></div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/39.png" class="head-img" />
                </div>
                <div class="info-left">APY</div>
                <div class="info-right"><span>0</span>%</div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/21.png" class="head-img" />
                </div>
                <div class="info-left">总质押量</div>
                <div class="info-right"><span>3322</span></div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/22.png" class="head-img" />
                </div>
                <div class="info-left">总量</div>
                <div class="info-right"><span>321</span></div>
              </div>
              <div class="info-btn">
                <div class="info-btn-box">
                  <div class="info-text-c">质押/赎回</div>
                  <img src="../assets/img/4.png" class="head-img" />
                </div>
                <div class="info-btn-box1">
                  <div class="info-text-c">领取金条</div>
                  <img src="../assets/img/4.png" class="head-img" />
                </div>
              </div>
            </div>
          </div> -->
        </div>
      </div>
      <div class="footer-box">
        <div class="footer-box-img">
          <img src="../assets/head/head.png" class="head-img" />
          <div class="footer-box-f">
            <div class="footer-box-c">
              <div class="footer-box-li" @click="homeBtn()">
                <img src="../assets/img/18.png" class="head-img" />
              </div>
              <div class="footer-box-li">
                <img src="../assets/img/19.png" class="head-img" />
              </div>
              <div class="footer-box-li" @click="exchangeBtn()">
                <img src="../assets/img/16.png" class="head-img" />
              </div>
              <div class="footer-box-li" @click="combinationBtn()">
                <img src="../assets/img/17.png" class="head-img" />
              </div>
            </div>
            <div style="width: 100%; overflow: hidden">
              <div class="footer-box-li-t">首页</div>
              <div class="footer-box-li-t" style="color: #f9be61">质押挖矿</div>
              <div class="footer-box-li-t">铜板兑换</div>
              <div class="footer-box-li-t">组合挖矿</div>
            </div>
          </div>
        </div>
      </div>
      <div class="deposit-pop-up" v-if="showPopUp">
        <div class="deposit-pop-up-c" @click="showPopUpBtny()">
          <div style="margin-top: 60%;" v-on:click.stop="doThis">
            <div class="deposit-num">
              <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
              <div class="img-h">
                <img src="../assets/img/44.png" class="head-img" />
              </div>
              <div class="img-b2" style="height: 290px;">
                <img src="../assets/img/42.png" class="head-img" />
              </div>
              <div class="img-f">
                <img src="../assets/img/43.png" class="head-img" />
              </div>
              <div class="img-min">
                <img src="../assets/img/41.png" class="head-img" />
              </div>
              <div class="deposit-num-cont">
                <div class="deposit-num-cont-h">
                  <div
                    class="deposit-num-cont-h-n"
                    :class="{ active: isShow == 1 }"
                    @click="activeBtn(1)"
                  >
                    质押
                  </div>
                  <div
                    class="deposit-num-cont-h-n"
                    :class="{ active: isShow == 0 }"
                    @click="activeBtns(0)"
                  >
                    赎回
                  </div>
                </div>
                <div class="deposit-num-cont-h-s" v-if="index == 0">
                  <!-- <div class="deposit-num-cont-h-t">选择LP来源:</div> -->
                  <div class="deposit-img-pop-box">
                    <div class="deposit-img-pop">
                      <img src="../assets/img/54.png" class="head-img" />
                    </div>
                    <div class="deposit-type">金条</div>
                    <div class="deposit-input">
                      <!-- <input type="text" /> -->
                    </div>
                  </div>
                </div>
                <div class="deposit-num-cont-h-s" v-if="index == 1">
                  <!-- <div class="deposit-num-cont-h-t">选择LP来源:</div> -->
                  <div class="deposit-img-pop-box">
                    <div class="deposit-img-pop">
                      <img src="../assets/img/USDT.png" class="head-img" />
                    </div>
                    <div class="deposit-type">USDK</div>
                    <div class="deposit-input">
                      <!-- <input type="text" /> -->
                    </div>
                  </div>
                </div>
                <div class="deposit-num-cont-h-s" v-if="index == 2">
                  <!-- <div class="deposit-num-cont-h-t">选择LP来源:</div> -->
                  <div class="deposit-img-pop-box">
                    <div class="deposit-img-pop">
                      <img src="../assets/img/Eth.png" class="head-img" />
                    </div>
                    <div class="deposit-type">ETH</div>
                    <div class="deposit-input">
                      <!-- <input type="text" /> -->
                    </div>
                  </div>
                </div>
                <div class="deposit-num-cont-h-l" v-if="isShow == 1">
                  <span class="label">余额</span>
                  <span class="value">{{ overage }}</span>
                </div>
                <div class="deposit-num-cont-h-l" v-if="isShow == 0">
                  <span class="label" v-if="ransomTime == 0"
                    >质押成功{{ 7 }}日后可赎回</span
                  >
                  <span class="label" v-else>{{ ransomTime }}后可赎回</span>
                  <span class="value" v-if="ransomTime != 0">{{ ransom }}</span>
                </div>
                <div class="deposit-two-input" v-if="isShow == 1">
                  <div class="deposit-input" style=" width: 70%;  float: left;">
                    <input type="Number" v-model="money" />
                  </div>
                  <div class="deposit-two-input-btn" @click="maxBtn">
                    <img src="../assets/img/2.png" class="head-img" />
                    <div class="deposit-two-input-t">
                      MAX
                    </div>
                  </div>
                </div>
                <div class="info-btn" v-if="isShow == 1">
                  <div
                    class="info-btn-box23"
                    @click="warrantBtn"
                    v-if="showBuy1 && index != 2"
                  >
                    <div class="info-text-c">授权</div>
                    <img src="../assets/img/2.png" class="head-img" />
                  </div>
                  <div class="info-btn-box23" v-if="showSate && index != 2">
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
                  <!-- <div class="info-btn-box" v-if="!showBuy1">
           
                    <div class="info-text-c">授权</div>
                    <img src="../assets/img/4.png" class="head-img" />
                  </div> -->
                  <div
                    class="info-btn-box23"
                    @click="depositBtn"
                    v-if="showBuy || index == 2"
                  >
                    <!-- 我的背包 -->
                    <div class="info-text-c">质押</div>
                    <img src="../assets/img/2.png" class="head-img" />
                  </div>

                  <!-- <div class="info-btn-box1" v-if="!showBuy">
        
                    <div class="info-text-c">质押</div>
                    <img src="../assets/img/4.png" class="head-img" />
                  </div> -->
                </div>
                <div
                  class="info-btn"
                  v-if="isShow == 0"
                  @click="withdrawBtns()"
                >
                  <div class="info-btn-box110">
                    <!-- 我的背包 -->
                    <div class="info-text-c">赎回</div>
                    <img src="../assets/img/2.png" class="head-img" />
                  </div>
                </div>
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
    </div>
  </div>
</template>

<script>
import popUpView from "../model/popUp";
import { Toast } from "mint-ui";
export default {
  name: "deposit",
  data() {
    return {
      money: null,
      rangeValue: 1,
      showPopUp: false,
      isShow: 1,
      index: 0,
      type: "",
      showBuy: false,
      RTTdeposit: 0,
      RTTdeposit1: 0,
      RTTdeposit2: 0,
      MyRTTdeposit: 0,
      MyRTTdeposit1: 0,
      MyRTTdeposit2: 0,
      ListAPY: 0,
      ListAPY1: 0,
      ListAPY2: 0,
      overage: 0,
      RTToverage: 0,
      RTToverage1: 0,
      RTToverage2: 0,
      datacur: "deposit",
      popUpShow: false,
      showBuy1: true,
      showSate: false,
      Rewards: "0",
      RewardsOne: "0",
      RewardsTow: "0",
      RewardsThree: "0",
      ransomTime: "0",
      ransom: "0",
    };
  },
  components: {
    popUpView,
    // homeListview
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
    await this.GETMortgage();
    await this.getRtt();
    await this.getUSDK();
    await this.getOkt();
    await this.getMortgageFun();
    await this.getRewards1("RttToken");
    await this.getRewards2("USDK");
    await this.getRewards3();
    // console.log(this.$store.state.USDKTokenFun, 88664);
  },
  methods: {
    doThis() {},
    WithdrawBtn(e) {
      // console.log(e);
      this.index = e;
      this.showPopUp = true;
      this.showSate = false;
      this.showBuy1 = true;
      this.money = null;
      if (e == 0) {
        this.type = "RttToken";
        this.overage = this.RTToverage;
        this.ransom = this.MyRTTdeposit;
      }
      if (e == 1) {
        this.type = "USDK";
        this.overage = this.RTToverage1;
        this.ransom = this.MyRTTdeposit1;
      }
      if (e == 2) {
        this.type = "OKT";
        this.overage = this.RTToverage2;
        this.ransom = this.MyRTTdeposit2;
      }
      this.getDepositTime(e);
    },

    noticeBtn() {
      this.$router.push("/notice");
    },
    homeBtn() {
      this.$router.push("/home");
    },
    exchangeBtn() {
      this.$router.push("/exchange");
    },
    combinationBtn() {
      this.$router.push("/combinationEit");
    },
    async GETMortgage() {
      var web3 = this.$store.state.web3; //初始化web3
      this.web3 = web3;
      this.account = sessionStorage["account"]; //取出缓存中的钱包地址;
      //this.m3tFun = this.$store.state.M3Tfun;
      this.MortgageFun = this.$store.state.MortgageFun;
    },
    maxBtn() {
      // console.log(this.index);
      if (this.index == 0) {
        this.money = this.RTToverage;
      }
      if (this.index == 1) {
        this.money = this.RTToverage1;
      }
      if (this.index == 2) {
        this.money = this.RTToverage2;
      }
    },
    async warrantBtn() {
      if (this.index == 0) {
        this.approveFun = this.$store.state.RttTokenfun;
      }
      if (this.index == 1) {
        this.approveFun = this.$store.state.USDKTokenFun;
      }
      // if (this.index == 2) {
      //   // this.approveFun = this.$store.state.OktTokenFun;
      // }
      if (this.money) {
        this.showBuy1 = false;
        this.showSate = true;
        const gas = await this.approveFun.methods
          .approve(
            this.MortgageFun.options.address,
            this.web3.utils.toWei(this.money.toString(), "ether")
          )
          .estimateGas({ from: this.account })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
        const gasPrice = await this.web3.eth.getGasPrice();
        await this.approveFun.methods
          .approve(
            this.MortgageFun.options.address,
            this.web3.utils.toWei(this.money.toString(), "ether")
          )
          .send({ from: this.account, gas: gas, gasPrice: gasPrice })
          .then((res) => {
            this.showBuy1 = false;
            this.showBuy = true;
            this.showSate = false;
            console.log(res);
            // Toast("授权成功");
            this;
            Toast({
              message: "授权成功",
              position: "middle",
              duration: 1000,
            });
            this.showBuy = true;
            this.showBuy = true;
          });
      } else {
        // Toast("请输入授权金额");
        Toast({
          message: "请输入授权金额",
          position: "middle",
          duration: 1000,
        });
      }
    },
    async depositBtn() {
      var money;
      // console.log(this.type);
      this.showBuy1 = false;
      this.showBuy = false;
      this.showSate = true;
      if (this.index != 2) {
        if (this.index == 0) {
          // console.log(this.money, this.RTToverage);
          if (parseFloat(this.RTToverage) < parseFloat(this.money)) {
            Toast({
              message: "质押失败,质押金额超过余额",
              position: "middle",
              duration: 1000,
            });
            return false;
          }
          money = this.web3.utils.toWei(this.money.toString(), "ether");
        }
        if (this.index == 1) {
          if (parseFloat(this.RTToverage1) < parseFloat(this.money)) {
            Toast({
              message: "质押失败,质押金额超过余额",
              position: "middle",
              duration: 1000,
            });
            return false;
          }
          money = this.money * 10000000000;
          money = money.toString();
        }

        const gas = await this.MortgageFun.methods
          .deposit(
            this.type,
            money
            // this.account
          )
          .estimateGas({ from: this.account })
          .catch((err) => {
            // var msg = err.message.toString(money);
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
        const gasPrice = await this.web3.eth.getGasPrice();
        await this.MortgageFun.methods
          .deposit(
            this.type,
            money
            // this.account
          )
          .send({ from: this.account, gas: gas, gasPrice: gasPrice })
          .then((res) => {
            if (res) {
              this.showPopUp = false;
              // this.showPopUp = false;
              this.showBuy1 = true;
              this.showBuy = false;
              this.showSate = false;
              this.isShow = 1;
              this.money = null;

              Toast({
                message: "质押成功",
                position: "middle",
                duration: 1000,
              });
              this.getMortgageFun();
              this.getRtt();
              this.getUSDK();
              this.getOkt();
            }
          })
          .catch((err) => {
            // this.showPopUp = false;
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
      } else {
        if (parseFloat(this.RTToverage2) < parseFloat(this.money)) {
          Toast({
            message: "质押失败,质押金额超过余额",
            position: "middle",
            duration: 1000,
          });
          return false;
        }

        // console.log(  this.$store.state.OktTokenFun.methods,'fun')
        const gas = await this.$store.state.OktTokenFun.methods
          .deposit()
          .estimateGas({ from: this.account })
          .catch((err) => {
            // var msg = err.message.toString(money);
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
        const gasPrice = await this.web3.eth.getGasPrice();
        await this.$store.state.OktTokenFun.methods
          .deposit()
          .send({
            from: this.account,
            gas: gas,
            gasPrice: gasPrice,
            value: this.web3.utils.toWei(this.money.toString(), "ether"),
          })
          .then((res) => {
            Toast({
              message: "质押成功",
              position: "middle",
              duration: 1000,
            });
            console.log(res);
            this.getMortgageFun();
            this.getRtt();
            this.getUSDK();
            this.getOkt();
            this.showBuy1 = true;
            this.showBuy = false;
            this.showSate = false;
            this.showPopUp = false;
          });
        // let num = this.money * 100000000;
        // console.log(num)
        // this.web3.eth
        //   .sendTransaction({
        //     from: this.account,
        //     to: "0x52290b23C18782859a973c1E8Af2C09c39198D19",
        //     value: num,
        //   })
        //   .then((res) => {
        //
        //     console.log(res);
        //     this.showPopUp = false;
        //     Toast({
        //       message: "质押成功",
        //       position: "middle",
        //       duration: 1000,
        //     });
        //   })
        //   .catch((err) => {
        //
        //     console.log(err);
        //     this.showPopUp = false;
        //     Toast({
        //       message: "质押异常",
        //       position: "middle",
        //       duration: 1000,
        //     });
        //   });
      }
    },
    getDepositTime(e) {
      // console.log(e,this.type,this.account);
      if (e != 2) {
        this.MortgageFun.methods
          .getDepositTime(this.account, this.type)
          .call()
          .then((res) => {
            this.ransomTime = 0;
            if (res != 0) {
              this.ransomTime = res;
              var date = new Date(this.ransomTime * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
              var Year = date.getFullYear();
              var Month =
                date.getMonth() + 1 < 10
                  ? 0 + (date.getMonth() + 1)
                  : date.getMonth() + 1;
              var Day = date.getDate();
              var Hours = date.getHours();
              var Minutes = date.getMinutes();
              var Seconds = date.getSeconds();
              this.ransomTime =
                Year +
                "-" +
                Month +
                "-" +
                Day +
                "  " +
                Hours +
                ":" +
                Minutes +
                ":" +
                Seconds;
            } else {
              this.ransomTime = res;
            }
          });
      }
      if (e == 2) {
        this.$store.state.OktTokenFun.methods
          .getDepositTime(this.account)
          .call()
          .then((res) => {
            this.ransomTime = 0;
            if (res != 0) {
              this.ransomTime = res;
              var date = new Date(this.ransomTime * 1000); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
              var Year = date.getFullYear();
              var Month =
                date.getMonth() + 1 < 10
                  ? 0 + (date.getMonth() + 1)
                  : date.getMonth() + 1;
              var Day = date.getDate();
              var Hours = date.getHours();
              var Minutes = date.getMinutes();
              var Seconds = date.getSeconds();
              this.ransomTime =
                Year +
                "-" +
                Month +
                "-" +
                Day +
                "  " +
                Hours +
                ":" +
                Minutes +
                ":" +
                Seconds;
            } else {
              this.ransomTime = res;
            }
          });
      }
    },
    showPopUpBtny() {
      this.showPopUp = false;
      this.isShow = 1;
    },
    activeBtn(e) {
      this.isShow = e;
    },
    activeBtns(e) {
      this.isShow = e;
    },
    async withdrawBtns() {
      //多币种质押赎回

      // console.log(this.type);
      if (this.type != "OKT") {
        var MortgageFun = this.$store.state.MortgageFun;
        var gas = await MortgageFun.methods
          .withdraw(this.type)
          .estimateGas({ from: this.account })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
        const gasPrice = await this.web3.eth.getGasPrice();
        await MortgageFun.methods
          .withdraw(this.type)
          .send({ from: this.account, gas: gas, gasPrice: gasPrice })
          .then((res) => {
            if (res) {
              this.showPopUp = false;
              this.showPopUp = false;
              this.isShow = 1;
              this.money = null;
            }
          })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
      } else {
        let MortgageFun = this.$store.state.OktTokenFun;

        var gas1 = await MortgageFun.methods
          .withdraw()
          .estimateGas({ from: this.account })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
        const gasPrice1 = await this.web3.eth.getGasPrice();
        await MortgageFun.methods
          .withdraw()
          .send({ from: this.account, gas: gas1, gasPrice: gasPrice1 })
          .then((res) => {
            if (res) {
              this.showPopUp = false;
              this.showPopUp = false;
              this.isShow = 1;
              this.money = null;
            }
          })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
      }
    },
    getRtt() {
      //获取RTT余额
      var account = sessionStorage.getItem("account");
      this.$store.state.RttTokenfun.methods
        .balanceOf(account)
        .call()
        .then((res) => {
          this.RTToverage = this.$store.state.web3.utils.fromWei(res);
          this.RTToverage = parseFloat(this.RTToverage).toFixed(2);
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
    },
    getUSDK() {
      //获取USDK余额
      var account = sessionStorage.getItem("account");
      this.$store.state.USDKTokenFun.methods
        .balanceOf(account)
        .call()
        .then((res) => {
          var num = res / 10000000000;
          this.RTToverage1 = parseFloat(num).toFixed(2);
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
    },
    async getOkt() {
      //获取Okt余额
      await this.Web3.eth.getBalance(this.account).then((res) => {
        //获取原生代币数量
        // console.log(res);
        var vuleC = this.Web3.utils.fromWei(res);
        var num = parseFloat(vuleC).toFixed(2);
        this.RTToverage2 = num;
      });
    },
    async getMortgageFun() {
      //获取总质押量
      let Morcontract = await this.$Fun.Mortgage();
      await this.$store.state.MortgageFun.methods
        .getTotalValueLock("RttToken")
        .call()
        .then((res) => {
          // console.log(res, "JJHH");
          this.RTTdeposit = this.Web3.utils.fromWei(res);
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
      await this.$store.state.MortgageFun.methods
        .getTotalValueLock("USDK")
        .call()
        .then((res) => {
          // console.log(res, 123333);
          this.RTTdeposit1 = res / 10000000000;
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
      // console.log(this.$store.state.MortgageFun);
      await this.$store.state.MortgageFun.methods
        .getMyValueLock("RttToken", this.account)
        .call()
        .then((res) => {
          // console.log(res, "LLLKKK");
          this.MyRTTdeposit = this.Web3.utils.fromWei(res);
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
      await this.$store.state.MortgageFun.methods
        .getMyValueLock("USDK", this.account)
        .call()
        .then((res) => {
          // console.log(res, "UUYH");
          this.MyRTTdeposit1 = res / 10000000000;
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
      await this.$store.state.OktTokenFun.methods
        .getTotalValueLock()
        .call()
        .then((res) => {
          // console.log(res, 123333);
          this.RTTdeposit2 = this.Web3.utils.fromWei(res);
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          // console.log(err);
          // throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
      await this.$store.state.OktTokenFun.methods
        .getMyValueLock(this.account)
        .call()
        .then((res) => {
          // console.log(res, 123333);
          this.MyRTTdeposit2 = this.Web3.utils.fromWei(res);
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
      //获取年华apy
      await this.$getWbe3
        .getTokenAPY(Morcontract, "RttToken", (res) => {
          // console.log(res, "RttToken");
          this.ListAPY = res;
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
      await this.$getWbe3
        .getTokenAPY(Morcontract, "USDK", (res) => {
          this.ListAPY1 = res;
          // console.log(this.ListAPY1, "USDK");
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
      await this.$store.state.OktTokenFun.methods
        .getTokenAPY()
        .call()
        .then((res) => {
          // console.log(res, "okt");
          this.ListAPY2 = res;
        })
        .catch((err) => {
          // var msg = err.message.toString();
          // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
          // console.log(msg);
          Toast({
            message: err,
            position: "middle",
            duration: 1000,
          });
          // throw 500;
          //alert('投资授权失败，稍后再试：', err)
        });
    },
    pullDown(e) {
      this.popUpShow = true;
      var num = e;
      if (num == 0) {
        this.index = num;
        this.getRewards(num);
      }
      if (num == 1) {
        //this.type = 0;
        this.index = num;
        this.getRewards(num);
      }
      if (num == 2) {
        //this.type = 0;
        this.index = num;
        this.getRewards(num);
      }
    },
    fatherMethod(e) {
      this.popUpShow = false;
      // console.log(e);
      if (e) {
        // console.log(this.index);
        if (this.index == 0) {
          //this.type = 0;
          this.claim(this.index);
        }
        if (this.index == 1) {
          //this.type = 0;
          this.claim(this.index);
        }
        if (this.index == 2) {
          //this.type = 0;
          this.claim(this.index);
        }
      }
    },
    getRewards(e) {
      var num;
      if (e != 2) {
        if (e == 0) {
          num = "RttToken";
        }
        if (e == 1) {
          num = "USDK";
        }
        // console.log(num);
        this.$store.state.MortgageFun.methods
          .getRewards(num, this.account)
          .call()
          .then((res) => {
            // console.log(res);
            if (e == 0) {
              this.Rewards = this.Web3.utils.fromWei(res);
              this.Rewards = parseFloat(this.Rewards).toFixed(2);
            }
            if (e == 1) {
              this.Rewards = parseInt(res) / 10000000000;
              this.Rewards = parseFloat(this.Rewards).toFixed(2);
              // this.Rewards = toString(this.Rewards);
            }
          });
      } else {
        this.$store.state.OktTokenFun.methods
          .getRewards(this.account)
          .call()
          .then((res) => {
            // console.log(res);
            this.Rewards = this.Web3.utils.fromWei(res);
            this.Rewards = parseFloat(this.Rewards).toFixed(2);
          });
      }
    },
    getRewards1(e) {
      var num = e;
      this.$store.state.MortgageFun.methods
        .getRewards(num, this.account)
        .call()
        .then((res) => {
          // console.log(res);
          this.RewardsOne = this.Web3.utils.fromWei(res);
          this.RewardsOne = parseFloat(this.RewardsOne).toFixed(2);
        });
    },
    getRewards2(e) {
      var num = e;

      this.$store.state.MortgageFun.methods
        .getRewards(num, this.account)
        .call()
        .then((res) => {
          // console.log(res, 999988854);
          this.RewardsTow = res / 10000000000;
          this.RewardsTow = parseFloat(this.RewardsTow).toFixed(2);
        });
    },
    getRewards3(e) {
      console.log(e);
      this.$store.state.OktTokenFun.methods
        .getRewards(this.account)
        .call()
        .then((res) => {
          // console.log(res, "OKT");
          this.RewardsThree = this.Web3.utils.fromWei(res);
          this.RewardsThree = parseFloat(this.RewardsThree).toFixed(2);
        });
      // this.$store.state.MortgageFun.methods
      //   .getRewards(num)
      //   .call()
      //   .then((res) => {
      //     console.log(res);
      //     this.RewardsThree = this.Web3.utils.fromWei(res);
      //   });
    },
    async claim(e) {
      if (this.Rewards == 0) {
        Toast({
          message: "当前无可领取奖励",
          position: "middle",
          duration: 1000,
        });
        this.popUpShow = false;
        return false;
      }
      var num;
      if (e != 2) {
        if (e == 0) {
          num = "RttToken";
        }
        if (e == 1) {
          num = "USDK";
        }

        var gas = await this.$store.state.MortgageFun.methods
          .claim(num, this.account)
          .estimateGas({ from: this.account })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
        const gasPrice = await this.web3.eth.getGasPrice();
        await this.$store.state.MortgageFun.methods
          .claim(num, this.account)
          .send({ from: this.account, gas: gas, gasPrice: gasPrice })
          .then((res) => {
            console.log(res);
            this.getRewards1("RttToken");
            this.getRewards2("USDK");
            Toast({
              message: "领取成功",
              position: "middle",
              duration: 1000,
            });
          })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
      } else {
        var gas1 = await this.$store.state.OktTokenFun.methods
          .claim(this.account)
          .estimateGas({ from: this.account })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
        const gasPrice = await this.web3.eth.getGasPrice();
        await this.$store.state.OktTokenFun.methods
          .claim(this.account)
          .send({ from: this.account, gas: gas1, gasPrice: gasPrice })
          .then((res) => {
            console.log(res);
            Toast({
              message: "领取成功",
              position: "middle",
              duration: 1000,
            });
            this.getRewards3();
          })
          .catch((err) => {
            // var msg = err.message.toString();
            // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
            // console.log(msg);
            Toast({
              message: err,
              position: "middle",
              duration: 1000,
            });
            throw 500;
            //alert('投资授权失败，稍后再试：', err)
          });
      }
      // console.log(num, 8888);
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
  top: 86px;
  bottom: 82px;
  width: 100%;
  overflow: auto;
}
.home-cont-c {
  width: 100%;
  position: relative;
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
}
.head-right {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 30px;
  height: 36px;
}
.deposit {
  position: absolute;
  top: 57px;
  left: 0;
  right: 0;
  bottom: 81px;
  overflow: auto;
  opacity: 0.75;
}
.deposit-content {
  width: 95%;
  height: 100%;
  position: relative;
  margin: auto;
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
.home-cont-h-l1 {
  width: 160px;
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
  height: 120px;
  background-color: rgba(95, 66, 57, 0.15);
  margin-top: 30px;
  overflow: hidden;
}
.home-cont-one {
  width: 100%;
  /* height: 235px; */
  margin: auto;
  margin-top: 20px;
  position: relative;
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
.right-box {
  position: absolute;
  top: -7px;
  left: 155px;
  color: #584147;
  font-weight: bold;
  font-size: 14px;
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
.info-btn-box23 {
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
  float: right;
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
  height: 185px;
  margin: auto;
  margin-top: 20px;
  position: relative;
}
.info-btn {
  width: 100%;
  height: 50px;
  overflow: hidden;
  float: left;
  margin-top: 10px;
}
.info-btn-box {
  float: left;
  width: 40%;
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
  float: right;
  width: 40%;
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
.img-b2 {
  height: 405px;
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
  height: 260px;
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
.range {
  width: 90%;
  margin: auto;
  margin-top: 40px;
}
.deposit-pop-up {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgb(0 0 0 / 48%);
}
.deposit-pop-up-c {
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
}
.deposit-num-cont-h {
  width: 100%;
  height: 40px;
  overflow: hidden;
}
.deposit-num-cont-h-n {
  float: left;
  width: 60px;
  line-height: 40px;
  text-align: center;
  font-size: 15px;
  font-weight: 700;
  color: #95786e;
}
.active {
  color: #5d4037;
}
.deposit-num-cont-h-s {
  width: 100%;
  height: 40px;
  margin-top: 7px;
  overflow: hidden;
}
.deposit-num-cont-h-t {
  float: left;
  line-height: 40px;
  font-size: 12px;
  padding-left: 14px;
  color: #5d4037;
}
.deposit-img-pop-box {
  float: left;
  height: 100%;
  width: 100%;
  /* overflow: hidden; */
  /* padding-left: 5px; */
  background-color: rgba(95, 66, 57, 0.15);
  border-radius: 5px;
}
.deposit-img-pop {
  float: left;
  width: 20px;
  height: 20px;
  margin-top: 10px;
  margin-left: 10px;
}
.deposit-type {
  float: left;
  line-height: 40px;
  font-size: 12px;
  padding-left: 4px;
  color: #5d4037;
}
.deposit-input {
  float: left;
  height: 100%;
  width: 40%;
}
.deposit-input input {
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
.deposit-num-cont-h-l {
  width: 100%;
  height: 30px;
  margin-top: 10px;
  line-height: 30px;
  background-color: rgba(95, 66, 57, 0.15);
  border-radius: 5px;
  font-size: 12px;
  overflow: hidden;
}
.label {
  float: left;
  display: inline-block;
  height: 100%;
  width: 63%;
  vertical-align: top;
  color: #5d4037;
  padding-left: 10px;
  text-align: left;
}
.label::after {
  display: inline-block;
  width: 100%;
  content: "";
  height: 0;
}
.value {
  float: right;
  color: #db522b;
  width: 25%;
  text-align: right;
  padding-right: 10px;
}
.deposit-two-input {
  width: 100%;
  height: 40px;
  margin-top: 10px;
  background-color: rgba(95, 66, 57, 0.15);
  overflow: hidden;
  border-radius: 5px;
}
.deposit-two-input-btn {
  width: 20%;
  height: 30px;
  float: right;
  border-radius: 2px;
  overflow: hidden;
  position: relative;
  margin-top: 5px;
  margin-right: 10px;
}
.deposit-two-input-t {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 30px;
  color: #5d4037;
}
.info-btn-box110 {
  width: 40%;
  height: 35px;
  margin-top: 8px;
  border-radius: 5px;
  text-align: center;
  line-height: 35px;
  color: #5d4037;
  font-size: 14px;
  font-weight: 700;
  position: relative;
  margin: auto;
}
.info-f-t {
  width: 100%;
  text-align: center;
  color: #5d4037;
  font-size: 13px;
}
</style>
