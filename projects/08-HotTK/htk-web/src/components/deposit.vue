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
          <!-- <img src="../assets/img/8.png" class="head-img" /> -->
           麻辣三国
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
                  <img src="../assets/img/4.png" class="head-img" />
                </div>
                <div class="info-btn-box1" @click="pullDown(0)">
                  <div class="info-text-c">领取金条</div>
                  <img src="../assets/img/4.png" class="head-img" />
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
                  <img src="../assets/img/4.png" class="head-img" />
                </div>
                <div class="info-btn-box1" @click="pullDown(1)">
                  <div class="info-text-c">领取金条</div>
                  <img src="../assets/img/4.png" class="head-img" />
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
                  <img src="../assets/img/4.png" class="head-img" />
                </div>
                <div class="info-btn-box1" @click="pullDown(2)">
                  <div class="info-text-c">领取金条</div>
                  <img src="../assets/img/4.png" class="head-img" />
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

  methods: {
    WithdrawBtn() {
      Toast({
        message: "敬请期待···",
        position: "middle",
        duration: 1000,
      });
    },
    pullDown() {
      Toast({
        message: "敬请期待···",
        position: "middle",
        duration: 1000,
      });
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
