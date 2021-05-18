<template>
  <div class="combination">
    <div class="combination-cont">
      <div class="head">
        <div class="head-c">
          <div class="head-c-t">
            <span>组合挖矿</span>
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
      <div class="combination-body">
        <div class="combination-body-c">
          <div class="combination-body-list">
            <div class="img-left-b">
              <img src="../assets/img/45.png" class="head-img" />
            </div>
            <div class="img-mid">
              <img src="../assets/img/46.png" class="head-img" />
            </div>
            <div class="img-right-b">
              <img src="../assets/img/45.png" class="head-img" />
            </div>
            <!-- <div class="numBox">数量:</div> -->
            <div class="combination-list-c">
              <div class="combination-list-b">
                <div class="notice-cont-h-l">
                  <div class="home-cont-h-t">橙卡组合</div>
                  <img src="../assets/img/3.png" class="head-img" />
                </div>
                <div class="GroupPower">
                  <span>战力:</span><span>{{ GroupPowerOne }}</span>
                </div>
                <div class="hero-list-box">
                  <div class="hero-list">
                    <img
                      src="../assets/cardimg/102.png"
                      class="head-img"
                      :class="{ imgAction: two == '0' }"
                    />
                    <div class="hero-list-n">刘备</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/200.png"
                      class="head-img"
                      :class="{ imgAction: purple == '0' }"
                    />
                    <div class="hero-list-n">张飞</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/201.png"
                      class="head-img"
                      :class="{ imgAction: purple1 == '0' }"
                    />
                    <div class="hero-list-n">关羽</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/202.png"
                      class="head-img"
                      :class="{ imgAction: purple2 == '0' }"
                    />
                    <div class="hero-list-n">赵云</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/203.png"
                      class="head-img"
                      :class="{ imgAction: purple3 == '0' }"
                    />
                    <div class="hero-list-n">黄忠</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/204.png"
                      class="head-img"
                      :class="{ imgAction: purple4 == '0' }"
                    />
                    <div class="hero-list-n">马超</div>
                  </div>
                </div>
                <div class="hero-name-list-box">
                  <div class="hero-name-list">
                    <span>{{ two }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ purple }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ purple1 }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ purple2 }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ purple3 }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ purple4 }}张</span>
                  </div>
                </div>
                <!-- <div class="combination-list-h">
                  <div class="combination-list-h-left">已集卡牌数量</div>
                  <div class="combination-list-h-right">
                    <span>{{ Goldenmin }}</span>
                  </div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">张飞</div>
                  <div class="combination-list-v-right">{{ purple }}</div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">关羽</div>
                  <div class="combination-list-v-right">{{ purple1 }}</div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">赵云</div>
                  <div class="combination-list-v-right">{{ purple2 }}</div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">黄忠</div>
                  <div class="combination-list-v-right">{{ purple3 }}</div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">马超</div>
                  <div class="combination-list-v-right">{{ purple4 }}</div>
                </div>
                <div class="combination-list-v1">
                  <div class="combination-list-v-left">特殊卡牌(刘备)</div>
                  <div class="combination-list-v-right">{{ two }}</div>
                </div>
                <div class="combination-list-v2">
                  <div class="combination-list-v-left">当前组合战力</div>
                  <div class="combination-list-v-right">
                    {{ GroupPowerTwo }}
                  </div>
                </div> -->
              </div>
            </div>
          </div>
          <div class="combination-body-list">
            <div class="img-left-b">
              <img src="../assets/img/45.png" class="head-img" />
            </div>
            <div class="img-mid">
              <img src="../assets/img/46.png" class="head-img" />
            </div>
            <div class="img-right-b">
              <img src="../assets/img/45.png" class="head-img" />
            </div>
            <div class="combination-list-c">
              <div class="combination-list-b">
                <div class="notice-cont-h-l">
                  <div class="home-cont-h-t">紫卡组合</div>
                  <img src="../assets/img/3.png" class="head-img" />
                </div>
                <div class="GroupPower">
                  <span>战力:</span><span>{{ GroupPowerTwo }}</span>
                </div>
                <div class="hero-list-box">
                  <div class="hero-list">
                    <img
                      src="../assets/cardimg/100.png"
                      class="head-img"
                      :class="{ imgAction: one == '0' }"
                    />
                    <div class="hero-list-n">曹操</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/300.png"
                      class="head-img"
                      :class="{ imgAction: Golden == '0' }"
                    />
                    <div class="hero-list-n">张辽</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/301.png"
                      class="head-img"
                      :class="{ imgAction: Golden1 == '0' }"
                    />
                    <div class="hero-list-n">张郃</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/302.png"
                      class="head-img"
                      :class="{ imgAction: Golden2 == '0' }"
                    />
                    <div class="hero-list-n">乐进</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/303.png"
                      class="head-img"
                      :class="{ imgAction: Golden3 == '0' }"
                    />
                    <div class="hero-list-n">于禁</div>
                  </div>
                  <div class="hero-list1">
                    <img
                      src="../assets/cardimg/304.png"
                      class="head-img"
                      :class="{ imgAction: Golden4 == '0' }"
                    />
                    <div class="hero-list-n">徐晃</div>
                  </div>
                </div>
                <div class="hero-name-list-box">
                  <div class="hero-name-list">
                    <span>{{ one }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ Golden }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ Golden1 }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ Golden2 }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ Golden3 }}张</span>
                  </div>
                  <div class="hero-name-list1">
                    <span>{{ Golden4 }}张</span>
                  </div>
                </div>
                <!-- <div class="combination-list-h">
                  <div class="combination-list-h-left">已集卡牌数量</div>
                  <div class="combination-list-h-right">
                    <span>{{ purplemin }}</span>
                  </div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">张辽</div>
                  <div class="combination-list-v-right">{{ Golden }}</div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">张郃</div>
                  <div class="combination-list-v-right">{{ Golden1 }}</div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">乐进</div>
                  <div class="combination-list-v-right">{{ Golden2 }}</div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">于禁</div>
                  <div class="combination-list-v-right">{{ Golden3 }}</div>
                </div>
                <div class="combination-list-v">
                  <div class="combination-list-v-left">徐晃</div>
                  <div class="combination-list-v-right">{{ Golden4 }}</div>
                </div>
                <div class="combination-list-v1">
                  <div class="combination-list-v-left">特殊卡牌(曹操)</div>
                  <div class="combination-list-v-right">{{ one }}</div>
                </div>
                <div class="combination-list-v2">
                  <div class="combination-list-v-left">当前组合战力</div>
                  <div class="combination-list-v-right">
                    {{ GroupPowerOne }}
                  </div>
                </div> -->
              </div>
              <div class="test-num">
                更多卡牌组合正在奔来的路上...
              </div>
            </div>
          </div>
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
              <div class="footer-box-li" @click="depositBtn()">
                <img src="../assets/img/15.png" class="head-img" />
              </div>
              <div class="footer-box-li" @click="exchangeBtn()">
                <img src="../assets/img/16.png" class="head-img" />
              </div>
              <div class="footer-box-li">
                <img src="../assets/img/37.png" class="head-img" />
              </div>
            </div>
            <div style="width: 100%; overflow: hidden">
              <div class="footer-box-li-t">首页</div>
              <div class="footer-box-li-t">质押挖矿</div>
              <div class="footer-box-li-t">铜板兑换</div>
              <div class="footer-box-li-t" style="color: #f9be61">组合挖矿</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {
      account: "",
      Web3: "",
      purple: "",
      purple1: "",
      purple2: "",
      purple3: "",
      purple4: "",
      Golden: "",
      Golden1: "",
      Golden2: "",
      Golden3: "",
      Golden4: "",
      Golden5: "",
      one: "0",
      two: "0",
      purplemin: "",
      Goldenmin: " ",
      GroupPowerTwo: "",
      GroupPowerOne: "",
    };
  },
  async mounted() {
    await this.$getWbe3.Init(
      (addr) => {
        //得到相应的钱包地址
        this.account = addr;
        // console.log(this.account);
        this.getCardGroups();
        this.getGroupPower();
      },
      (provider) => {
        this.Web3 = provider;
        // console.log(this.Web3);
      }
    );
  },
  methods: {
    noticeBtn() {
      this.$router.push("/notice");
    },
    homeBtn() {
      this.$router.push("/home");
    },
    depositBtn() {
      this.$router.push("/deposit");
    },
    exchangeBtn() {
      this.$router.push("/exchange");
    },
    async getCardGroups() {
      var contract = this.$Fun.CardDetail();
      await this.$getWbe3.getCardGroups(contract, this.account, (res) => {
        // console.log(res, "231112");
        if (res) {
          this.purple = res[0][0][0];
          this.purple1 = res[0][0][1];
          this.purple2 = res[0][0][2];
          this.purple3 = res[0][0][3];
          this.purple4 = res[0][0][4];
          this.Golden = res[0][1][0];
          this.Golden1 = res[0][1][1];
          this.Golden2 = res[0][1][2];
          this.Golden3 = res[0][1][3];
          this.Golden4 = res[0][1][4];
          this.one = res[1];
          this.two = res[2];
          // for(var i in res[0]){
          //   console.log(res[0][i])
          // }
          // var arr = res[0][0];

          // arr.sort(function(a, b) {
          //   return a - b;
          // });

          // this.purplemin = arr[0];
          // var arr1 = res[0][1];

          // arr1.sort(function(a, b) {
          //   return a - b;
          // });

          // this.Goldenmin = arr1[0];
        }
      });
    },
    async getGroupPower() {
      var contract = this.$Fun.CardDetail();
      await this.$getWbe3.getGroupPower(contract, this.account, (res) => {
        //console.log(res);
        if (res) {
          (this.GroupPowerOne = res[0]), (this.GroupPowerTwo = res[1]);
        }
      });
    },
  },
};
</script>
<style scoped>
.combination {
  width: 100%;
  height: 100%;
}
.combination-cont {
  max-width: 420px;
  height: 100%;
  margin: auto;
  background-color: #000;
  position: relative;
  background: url("../assets/img/52.png") no-repeat;
  background-size: cover;
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
  background: linear-gradient(180deg, #13232e, #2d496f);
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
.combination-body {
  position: absolute;
  top: 58px;
  left: 2.5%;
  right: 2.5%;
  bottom: 82px;
  overflow: auto;
  opacity: 0.75;
}
.combination-body-c {
  width: 100%;
}
.combination-body-list {
  width: 100%;
  position: relative;
  height: 190px;
  margin-bottom: 20px;
}
.combination-list-c {
  position: absolute;
  top: 10px;
  left: 20px;
  right: 20px;
  bottom: 10px;
}
.combination-list-b {
  position: relative;
  width: 100%;
  height: 100%;
}
.notice-cont-h-l {
  width: 100px;
  height: 20px;
  position: relative;
}
.combination-list-h {
  width: 100%;
  height: 40px;
  color: #68433e;
  overflow: hidden;
  font-size: 14px;
  font-weight: 700;
  line-height: 40px;
}
.combination-list-h-left {
  float: left;
}
.combination-list-h-right {
  float: right;
  text-align: right;
}
.combination-list-v {
  width: 100%;
  height: 20px;
  background-color: rgba(95, 66, 57, 0.15);
  font-size: 12px;
  font-weight: 700;
  line-height: 20px;
  color: #68433e;
  margin-bottom: 5px;
}
.combination-list-v-left {
  float: left;
  margin-left: 10px;
}
.combination-list-v-right {
  float: right;
  text-align: right;
  margin-right: 10px;
}
.combination-list-v1 {
  width: 100%;
  height: 30px;
  background-color: rgba(95, 66, 57, 0.15);
  font-size: 12px;
  font-weight: 700;
  line-height: 30px;
  color: #68433e;
  margin-top: 15px;
}
.combination-list-v2 {
  width: 100%;
  height: 30px;
  background-color: rgba(249, 96, 3, 0.15);
  font-size: 12px;
  font-weight: 700;
  line-height: 30px;
  color: #68433e;
  margin-top: 15px;
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
.hero-list-box {
  width: 100%;
  height: 70px;
  /* background: #000; */
  overflow: hidden;
  margin-top: 20px;
}
.hero-list {
  float: left;
  width: 14%;
  height: 100%;
  margin-left: 10px;
  margin-bottom: 5px;
  position: relative;
}
.hero-list1 {
  float: left;
  width: 12%;
  margin-left: 10px;
  margin-bottom: 5px;
  margin-top: 10px;
  position: relative;
}
.hero-list-n {
  position: absolute;
  left: 0;
  right: 0;
  bottom: 0;
  height: 20px;
  color: #fff;
  text-align: center;
  font-size: 12px;
  line-height: 20px;
}
.hero-name-list-box {
  width: 100%;
  height: 30px;
  /* background-color: #000; */
  overflow: hidden;
  position: relative;
  line-height: 30px;
  top: 5px;
}
.hero-name-list {
  float: left;
  width: 14%;
  height: 100%;
  margin-left: 10px;
  font-size: 12px;
  text-align: center;
}
.hero-name-list1 {
  font-size: 12px;
  margin-left: 10px;
  float: left;
  width: 12%;
  height: 100%;
  text-align: center;
}
.numBox {
  position: absolute;
  font-size: 12px;
  left: 10px;
  top: 131px;
}
.GroupPower {
  position: absolute;
  top: 0;
  font-size: 16px;
  right: 15px;
  color: #72441c;
  line-height: 24px;
}
.imgAction {
  filter: grayscale(80%);
}
.test-num {
  width: 100%;
  line-height: 60px;
  text-align: center;
  font-size: 16px;
  color: #3c1c00;
  font-weight: 700;
  margin-top: 10px;
}
</style>
