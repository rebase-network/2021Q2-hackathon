<template>
  <div class="home">
    <div class="home-cont">
      <div class="head">
        <div class="head-c">
          <div class="head-c-t">
            <span>铜板兑换</span>
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
          <div class="deposit-num">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="head-img" />
            </div>
            <div class="img-b4">
              <img src="../assets/img/42.png" class="head-img" />
            </div>
            <div class="img-f">
              <img src="../assets/img/43.png" class="head-img" />
            </div>
            <div class="img-min">
              <img src="../assets/img/41.png" class="head-img" />
            </div>
            <div class="deposit-num-cont">
              <div class="home-cont-h-l" style="top: 20px">
                <div class="home-cont-h-t">铜板兑换</div>
                <img src="../assets/img/3.png" class="head-img" />
              </div>
              <div class="deposit-test-cont" @click="depositBtns">
                选择支付方式:
              </div>
              <div class="deposit-cont-left" @click="depositBtnh">
                <div style="width:100%;height:100%;overflow: hidden;">
                  <div class="currency-box1">
                    <img :src="deimg" class="head-img" />
                  </div>
                  <div class="currency-box2">{{ btNma }}</div>
                </div>
              </div>
              <div class="deposit-cont-box">
                <div class="exchange-text">铜板是升级卡片必须的消耗品</div>
                <div class="exchange-text1">总量1亿枚，永不增发</div>
                <div class="exchange-text1">使用即销毁，且用且珍惜</div>
                <div class="exchange-text1">购买比率：3USDK:500铜板</div>
              </div>
              <div class="deposit-num-c">
                <div class="deposit-left-t" style="margin-left: 5px">总量</div>
                <div class="deposit-right-t">200000000</div>
              </div>
              <div class="deposit-num-c1">
                <div class="deposit-left-t" style="margin-left: 5px">剩余</div>
                <div class="deposit-right-t">{{ allMtt }}</div>
              </div>
              <div class="exchange-num">
                <div class="exchange-num-left">
                  <div class="exchange-left" @click="JBTN()">
                    <img src="../assets/img/23.png" class="head-img" />
                  </div>
                  <div class="exchange-left1">
                    <input
                      type="Number"
                      v-model="numer"
                      placeholder="输入购买数量"
                      @input="exchangeN"
                    />
                  </div>
                  <div class="exchange-left" @click="ADDBTN()">
                    <img src="../assets/img/24.png" class="head-img" />
                  </div>
                </div>
                <div class="exchange-num-right" @click="maxnumBtn">
                  <div class="info-text-c12">MAX</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
              </div>
              <div class="deposit-num-c1">
                <div class="deposit-left-t" style="margin-left: 5px">
                  需要支付(USDK)
                </div>
                <div class="deposit-right-t">{{ this.numer }}</div>
              </div>
              <div class="deposit-num-c1">
                <div class="deposit-left-t" style="margin-left: 5px">
                  可获获得(铜板)
                </div>
                <div class="deposit-right-t">{{ allM3tNum }}</div>
              </div>
              <div class="deposit-num-c1">
                <div class="deposit-left-t" style="margin-left: 5px">
                  我的余额(铜板)
                </div>
                <div class="deposit-right-t">{{ mNum }}</div>
              </div>
              <!-- <div class="deposit-num-c1">
                <div class="deposit-left-t" style="margin-left: 5px">
                  剩余(USDK)
                </div>
                <div class="deposit-right-t">{{ mNum - this.numer }}</div>
              </div> -->
              <div class="info-btn">
                <!-- <div class="info-btn-box" v-if="!showBuy1">

                  <div class="info-text-c">授权</div>
                  <img src="../assets/img/4.png" class="head-img" />
                </div> -->
                <div class="info-btn-box" @click="warrantBtn()" v-if="showBuy1">
                  <div class="info-text-c">授权</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
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
                <div class="info-btn-box1" @click="BuyBtn()" v-if="showBuy">
                  <!-- 我的背包 -->
                  <div class="info-text-c">兑换</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
                <!-- <div class="info-btn-box1" v-if="!showBuy">
              
                  <div class="info-text-c">兑换</div>
                  <img src="../assets/img/4.png" class="head-img" />
                </div> -->
              </div>
            </div>
          </div>
          <!-- 暂时待定质押R3T获取M3T -->
          <!-- <div class="deposit-num">
            <div class="img-h">
              <img src="../assets/img/44.png" class="head-img" />
            </div>
            <div class="img-New">
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
                <div class="home-cont-h-t">R3T质押</div>
                <img src="../assets/img/3.png" class="head-img" />
              </div>
              <div class="home-cont-info" style="margin-top: 10px;">
                <div class="info-log">
                  <img src="../assets/img/21.png" class="head-img" />
                </div>
                <div class="info-left">总质押量</div>
                <div class="info-right">
                  <span>{{ ValueLock }}</span>
                </div>
              </div>
              <div class="home-cont-info">
                <div class="info-log">
                  <img src="../assets/img/22.png" class="head-img" />
                </div>
                <div class="info-left">总矿量</div>
                <div class="info-right"><span>200000000</span></div>
              </div>
              <div class="lockup">
                <span>质押后将锁定7日后方可解锁</span>
                <div class="lockuB" @click="getRBtn">
                  <div
                    class="info-text-c"
                    style="line-height: 30px;color: #5d4037;"
                  >
                    领取M3T
                  </div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
              </div>
              <div class="changeBtn-box">
                <div
                  class="changeBtn"
                  :class="{ changeA: showc == 1 }"
                  @click="changeABtn(1)"
                >
                  质押
                </div>
                <div
                  class="changeBtn"
                  :class="{ changeA: showc == 2 }"
                  @click="changeABtn(2)"
                >
                  赎回
                </div>
              </div>
              <div class="deposit-num-c1" v-if="showc == 1">
                <div class="deposit-left-t" style="margin-left: 5px">
                  需要支付
                </div>
                <div class="deposit-right-t">{{ numer1 }}</div>
              </div>
              <div class="deposit-two-input" v-if="showc == 1">
                <div class="deposit-input" style=" width: 70%;  float: left;">
                  <input type="text" v-model="numer1" />
                </div>
                <div class="deposit-two-input-btn">
                  <img src="../assets/img/2.png" class="head-img" />
                  <div class="deposit-two-input-t" @click="MAXBtn">
                    MAX
                  </div>
                </div>
              </div>
              <div class="info-btn" v-if="showc == 1">
                <div class="info-btn-box" @click="sqBtn()" v-if="showBuy2">
                  <div class="info-text-c">授权</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
                <div class="info-btn-box" v-if="showSate1">
                  <div class="info-text-c">Pending...</div>
                  <img src="../assets/img/4.png" class="head-img" />
                </div>
                <div class="info-btn-box1" @click="zhiyBtn()" v-if="showBuy3">
                  <div class="info-text-c">质押</div>
                  <img src="../assets/img/2.png" class="head-img" />
                </div>
              </div>
              <div class="info-btn" v-if="showc == 2">
                <div class="info-btn-box110">
                  <div class="info-text-c" @click="withdrawBtns()">赎回</div>
                  <img src="../assets/img/2.png" class="head-img" />
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
              <div class="footer-box-li" @click="depositBtn()">
                <img src="../assets/img/15.png" class="head-img" />
              </div>
              <div class="footer-box-li">
                <img src="../assets/img/20.png" class="head-img" />
              </div>
              <div class="footer-box-li" @click="combinationBtn()">
                <img src="../assets/img/17.png" class="head-img" />
              </div>
            </div>
            <div style="width: 100%; overflow: hidden">
              <div class="footer-box-li-t">首页</div>
              <div class="footer-box-li-t">质押挖矿</div>
              <div class="footer-box-li-t" style="color: #f9be61">铜板兑换</div>
              <div class="footer-box-li-t">组合挖矿</div>
            </div>
          </div>
        </div>
      </div>
      <div class="popUp-test" v-if="showpopUp" @click="popUpTest()">
        <div class="popUp-test1">
          <div class="popUp-test2">
            <div class="popUp-test3">
              <div
                style="width:100%;height:100%;overflow: hidden;"
                v-for="itme in btlist"
                :key="itme.index"
                @click="popGup(itme)"
              >
                <div class="currency-box1" style="margin-top: 5px;">
                  <img :src="itme.img" class="head-img" />
                </div>
                <div class="currency-box2" style="line-height: 30px;">
                  {{ itme.name }}
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
import imgUr from "../assets/img/USDT.png";
// import imgUr1 from "../assets/img/OKT.png";
// import imgUr2 from "../assets/img/54.png";
import { Toast } from "mint-ui";
import popUpView from "../model/popUp";
export default {
  name: "exchange",
  data() {
    return {
      showpopUp: false,
      datacur: "exchange",
      numer: 0,
      showc: 1,
      allMtt: 0,
      mNum: 0,
      disabled: "disabled",
      mttAll: 0,
      allM3tNum: 0,
      showBuy: false,
      showBuy1: true,
      showSate: false,
      showBuy2: true,
      showBuy3: false,
      showSate1: false,
      addrsType: {},
      typeindex: {},
      numer1: null,
      popUpShow: false,
      Rewards: "0",
      ValueLock: 0,
      CURaddress: "",
      deimg: imgUr,
      btNma: "USDK",
      allBanaceOf: null,
      RTToverage: null,
      btlist: [
        {
          name: "USDK",
          img: imgUr,
          index: 1,
        },
      ],
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
        //this.CURaddress = addr;
      },
      (provider) => {
        this.Web3 = provider;
        // console.log(this.Web3);
      }
    );
    await this.$store.mutations.initWeb3Account();
    await this.GETM3t();
    await this.getAllMTT();
    await this.GetMYMTT();
  },
  methods: {
    depositBtns() {
      this.showpopUp = true;
    },
    depositBtnh() {
      this.showpopUp = true;
    },
    popUpTest() {
      this.showpopUp = false;
    },
    popGup(e) {
      // console.log(e);
      this.deimg = e.img;
      this.btNma = e.name;
    },
    async GETM3t() {
      var web3 = this.$store.state.web3; //初始化web3
      this.web3 = web3;
      this.account = sessionStorage["account"]; //取出缓存中的钱包地址;
      this.m3tFun = this.$store.state.M3Tfun;
      this.USDKTokenFun = this.$store.state.USDKTokenFun;
      this.typeindex = "USDK";
      // let addrsType = localStorage.getItem("addrsType");
      // if (addrsType) {
      //   this.addrsType = JSON.parse(addrsType);
      //   console.log(this.addrsType);
      // } else {
      //   this.addrsType.index = 2;
      // }
      // if (this.addrsType.index == 1) {
      //   this.RttTokenfun = this.$store.state.USDKTokenFun;
      //   this.typeindex = "USDK";
      // }
      // if (this.addrsType.index == 2) {
      //   this.RttTokenfun = this.$store.state.OktTokenFun;
      //   console.log();
      //   this.typeindex = "OkChain";
      // }
    },
    async warrantBtn() {
      if (this.numer == 0 || this.numer == "") {
        Toast({
          message: "请输入授权金额",
          position: "middle",
          duration: 1000,
        });
      } else {
        await this.$getWbe3.MttbalanceOf(
          this.$Fun.USDKTokens(),
          this.account,
          (res) => {
            // console.log(res, 989);
            var RTToverage = this.Web3.utils.fromWei(res);
            this.RTToverage = parseFloat(RTToverage).toFixed(2);
            // this.currencyList[0].num = this.RTToverage;
          }
        );
        if (parseFloat(this.RTToverage) < parseFloat(this.numer)) {
          Toast({
            message: "操作失败,授权金额超过余额",
            position: "middle",
            duration: 1000,
          });
          return false;
        }
        this.showBuy1 = false;
        this.showSate = true;
        let value = this.Web3.utils.toWei(this.numer.toString(), "ether");
        let addr = await this.$Fun.MttToken().options.address;
        const gasPrice = await this.Web3.eth.getGasPrice();
        let contract = this.$Fun.USDKTokens();
        await this.$getWbe3.Approve(
          addr,
          value,
          contract,
          gasPrice,
          this.account,
          (res) => {
            this.showBuy1 = false;
            this.showBuy = true;
            this.showSate = false;
            if (res) {
              Toast({
                message: "授权成功",
                position: "middle",
                duration: 1000,
              });

              // this.showBuy = true;
              // this.allProvider = arr;
            }
          }
        );
      }
    },
    async BuyBtn() {
      this.showSate = true;
      this.showBuy = false;
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
      if (parseFloat(this.RTToverage) < parseFloat(this.numer)) {
        Toast({
          message: "操作失败,兑换金额超过余额",
          position: "middle",
          duration: 1000,
        });
        return false;
      }
      this.showBuy1 = false;
      this.showBuy = false;
      this.showSate = true;
      let contract = this.$Fun.MttToken();
      var vule = this.numer * 10000000000;
      // console.log(this.typeindex, vule);
      const gasPrice = await this.Web3.eth.getGasPrice(); //获取gas价格
      this.$getWbe3.buyM3T(
        this.typeindex,
        contract,
        vule,
        gasPrice,
        this.account,
        (res) => {
          console.log(res);
          this.showBuy1 = true;
          this.showBuy = false;
          this.showSate = false;
          this.GetMYMTT();
          this.getAllMTT();
          this.numer = 0;
          Toast({
            message: "兑换成功",
            position: "middle",
            duration: 1000,
          });
        }
      );
    },
    noticeBtn() {
      // console.log(this.$router);
      this.$router.push("/notice");
    },
    homeBtn() {
      this.$router.push("/home");
    },

    depositBtn() {
      this.$router.push("/deposit");
    },
    combinationBtn() {
      this.$router.push("/combinationEit");
    },
    changeABtn(e) {
      this.showc = e;
    },
    async getAllMTT() {
      // console.log(this.m3tFun,'3D111');
      await this.m3tFun.methods
        .balanceOf(this.m3tFun.options.address)
        .call()
        .then((res) => {
          this.allMtt = this.web3.utils.fromWei(res);
          this.allMtt = parseFloat(this.allMtt).toFixed(2);
        });
      // let Mttcontract = await this.$Fun.MttToken();
      // await this.$getWbe3.getTotalValueLockE(Mttcontract, "RttToken", (res) => {
      //   //console.log(res,8888);
      //   this.ValueLock = this.web3.utils.fromWei(res);
      // });
      // await this.m3tFun.methods
      //   .totalSupply()
      //   .call()
      //   .then((res) => {
      //     this.allMtt = this.web3.utils.fromWei(res);
      //   });
    },
    async GetMYMTT() {
      await this.m3tFun.methods //获取当前M3t代币
        .balanceOf(this.account)
        .call()
        .then((res) => {
          this.mNum = this.$store.state.web3.utils.fromWei(res);
          this.mNum = parseFloat(this.mNum).toFixed(2);
        });
    },

    async maxnumBtn() {
      let numer;
      let USDKTokens = this.$Fun.USDKTokens();
      await this.$getWbe3.MttbalanceOf(USDKTokens, this.account, (res) => {
        // console.log(res);
        var num = res / 10000000000;
        numer = parseFloat(num).toFixed(2);
      });
      this.numer = numer;
      var allNumer = numer / 3;
      this.allM3tNum = parseFloat(allNumer) * 500;
      this.allM3tNum = parseFloat(this.allM3tNum).toFixed(2);
    },
    JBTN() {
      if (this.numer == 0 || this.numer == null) {
        this.numer = 0;
        this.allM3tNum = 0;
      } else {
        this.numer = this.numer - 3;
        var allNumer = this.numer / 3;
        this.allM3tNum = allNumer * 500;
      }
    },
    ADDBTN() {
      this.numer = parseInt(this.numer) + parseInt(3);
      var allNumer = this.numer / 3;
      this.allM3tNum = allNumer * 500;
    },
    exchangeN() {
      if (this.numer == "") {
        this.numer = null;
      } else {
        // console.log(this.numer);
        // this.numer = this.mNum;
        var allNumer = this.numer / 3;
        this.allM3tNum = parseFloat(allNumer) * 500;
        this.allM3tNum = parseFloat(this.allM3tNum).toFixed(2);
      }
    },
    async sqBtn() {
      if (this.numer1) {
        this.showSate1 = true;
        this.showBuy2 = false;
        let value = this.Web3.utils.toWei(this.numer1.toString(), "ether");

        let addr = await this.$Fun.MttToken().options.address;
        const gasPrice = await this.Web3.eth.getGasPrice();
        let contract = this.$Fun.RttToken();
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
              this.showBuy3 = true;
              this.showBuy2 = false;
              this.showSate1 = false;
              // this.showBuy = true;
              // this.allProvider = arr;
            }
          }
        );
      } else {
        Toast({
          message: "授权代币不能为空",
          position: "middle",
          duration: 1000,
        });
      }
    },
    async zhiyBtn() {
      let Rttcontract = this.$Fun.RttToken();
      let num;
      await this.$getWbe3.RttbalanceOf(Rttcontract, this.account, (res) => {
        // console.log(res);
        num = this.Web3.utils.fromWei(res);
      });
      // console.log(num,this.numer1)
      if (parseFloat(num) < parseFloat(this.numer1)) {
        Toast({
          message: "操作失败,质押金额超过余额",
          position: "middle",
          duration: 1000,
        });
        return false;
      }
      this.showSate1 = true;
      this.showBuy2 = false;
      let contract = this.$Fun.MttToken();
      let vule = this.web3.utils.toWei(this.numer1.toString(), "ether");
      // console.log(this.account, "1112");
      const gasPrice = await this.web3.eth.getGasPrice();
      this.$getWbe3.deposit(
        contract,
        "RttToken",
        vule,
        gasPrice,
        this.account,
        (res) => {
          Toast({
            message: "质押成功",
            position: "middle",
            duration: 1000,
          });
          this.numer1 = 0;
          console.log(res);
          this.showSate1 = false;
          this.showBuy2 = true;
          this.showBuy3 = true;
          this.getAllMTT();
        }
      );
    },
    getRBtn() {
      this.popUpShow = true;
      this.m3tFun.methods
        .getRewards("RttToken", this.account)
        .call()
        .then((res) => {
          // console.log(res);
          this.Rewards = this.Web3.utils.fromWei(res);
          this.Rewards = parseFloat(this.Rewards).toFixed(2);
        });
    },
    async fatherMethod(e) {
      if (e) {
        this.popUpShow = false;
        var contract = this.m3tFun;
        const gasPrice = await this.Web3.eth.getGasPrice();
        var gas = await contract.methods
          .claim("RttToken", this.account)
          .estimateGas({
            from: this.account,
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
          });
        await contract.methods
          .claim("RttToken", this.account)
          .send({
            from: this.account,
            gas: gas,
            gasPrice: gasPrice,
          })
          .then((res) => {
            console.log(res);
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
          });
      }
    },

    async withdrawBtns() {
      var MortgageFun = this.m3tFun;
      var gas = await MortgageFun.methods
        .withdraw("RttToken")
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
        });
      const gasPrice = await this.web3.eth.getGasPrice();
      await MortgageFun.methods
        .withdraw("RttToken")
        .send({ from: this.account, gas: gas, gasPrice: gasPrice })
        .then((res) => {
          if (res) {
            console.log(res);
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
        });
    },
    async MAXBtn() {
      let Rttcontract = this.$Fun.RttToken();
      await this.$getWbe3.RttbalanceOf(Rttcontract, this.account, (res) => {
        // console.log(res);
        this.numer1 = this.Web3.utils.fromWei(res);
        this.numer1 = parseFloat(this.numer1).toFixed(2);
      });
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
.popUp-test {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  /* background-color: #000; */
}
.popUp-test1 {
  width: 100%;
  height: 100%;
  /* background-color: #000; */
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
.deposit-cont-left {
  position: absolute;
  top: 20px;
  right: 0;
  width: 100px;
  line-height: 20px;
  background-color: rgba(95, 66, 57, 0.15);
}
.deposit-test-cont {
  position: absolute;
  top: 20px;
  right: 100px;
  /* width: 100px; */
  line-height: 20px;
  font-size: 0.13rem;
  /* background-color: rgba(95, 66, 57, 0.15); */
  color: #584147;
  font-weight: 700;
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
.deposit-num-c1 {
  width: 100%;
  height: 20px;
  background-color: rgba(95, 66, 57, 0.15);
  margin-top: 5px;
  overflow: hidden;
}
.exchange-text {
  width: 100%;
  height: 20px;
  line-height: 20px;
  font-size: 12px;
  margin-top: 10px;
  margin-left: 5px;
  color: #584147;
}
.exchange-text1 {
  width: 100%;
  height: 20px;
  line-height: 20px;
  font-size: 12px;
  margin-top: 5px;
  margin-left: 5px;
  color: #584147;
}
.exchange-num {
  width: 100%;
  height: 50px;
  margin-top: 5px;
  overflow: hidden;
}
.exchange-num-left {
  float: left;
  width: 70%;
  height: 40px;
  overflow: hidden;
}
.exchange-left {
  float: left;
  width: 15%;
}
.exchange-left1 {
  float: left;
  width: 63%;
  height: 30px;
  background-color: rgba(95, 66, 57, 0.15);
  margin-left: 5px;
  margin-right: 5px;
}
.exchange-left1 input {
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
.exchange-num-right {
  float: right;
  width: 30%;
  height: 30px;
  position: relative;
}
.info-text-c12 {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 30px;
  color: #5d4037;
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
.img-b4 {
  height: 475px;
  display: block;
  width: 93%;
  margin: auto;
}
.img-New {
  height: 350px;
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
.lockup {
  width: 100%;
  height: 40px;
  font-size: 12px;
  line-height: 40px;
  overflow: hidden;
}
.lockuB {
  position: relative;
  float: right;
  width: 60px;
  height: 30px;
  margin-top: 5px;
}
.changeBtn-box {
  width: 100%;
  height: 50px;
  overflow: hidden;
}
.changeBtn {
  float: left;
  width: 80px;
  height: 40px;
  text-align: center;
  line-height: 40px;
  font-weight: 700;
  color: #95786e;
}
.changeA {
  color: #5d4037;
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
.popUp-test2 {
  position: absolute;
  top: 137px;
  width: 100px;
  right: 39px;
  /* background-color: rgba(95, 66, 57, 0.15); */
}
.popUp-test3 {
  width: 100%;
  /* height: 30px; */
  line-height: 30px;
  background-color: rgba(95, 66, 57, 0.15);
}
</style>
