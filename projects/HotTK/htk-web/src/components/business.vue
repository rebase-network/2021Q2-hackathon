<template>
  <div class="business">
    <div class="business-conter">
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
          <div class="header-c">市场交易</div>
        </div>
        <div class="header-img-h">
          <img src="../assets/head/head.png" class="notice-img" />
        </div>
      </header>
      <div class="business-body">
        <div class="business-body-conter">
          <div class="business-body-nav">
            <div class="business-nav">
              <img src="../assets/img/49.png" class="notice-img" />
              <div class="business-nav-text">
                <div class="business-nav-c">
                  <div
                    class="business-nav-b"
                    v-for="itme in navList"
                    :key="itme.index"
                    @click="NavListBtn(itme)"
                  >
                    <img
                      src="../assets/img/3.png"
                      class="notice-img"
                      v-if="itme.state"
                    />
                    <div class="business-nav-t">{{ itme.navName }}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="backpack-nav" v-if="showNav">
              <div
                :class="item.style"
                v-for="item in NavLsit1"
                :key="item.index"
                @click="NavBtn(item)"
              >
                <div class="nav-bg-list" :class="{ active: item.sate }">
                  {{ item.navName }}
                </div>
              </div>
            </div>
          </div>
          <div class="list-box" v-if="showDiv == 1">
            <div class="list-box-cont">
              <cardListModel
                @func="getMsgFormSon"
                :cardList="cardList"
                :title="title"
              />
            </div>
          </div>
          <div class="list-box1" v-if="showDiv == 2">
            <div class="list-box-cont">
              <cardListModel
                @func="getMsgFormSon"
                :cardList="cardList"
                :title="title"
              />
            </div>
          </div>
          <div class="list-box2" v-if="showDiv == 3">
            <div class="list-box-cont">
              <cardListModel
                :title="title"
                @func="getMsgFormSon"
                :cardList="cardList"
              />
            </div>
          </div>
          <div class="business-btn-cont" v-if="showDiv == 1">
            <div class="business-btn-box">
              <img
                src="../assets/img/2.png"
                class="notice-img"
                v-if="cardInfo.length > 0"
              />
              <img src="../assets/img/4.png" class="notice-img" v-else />
              <div class="business-btn-text" @click="carDBuyBtn">点击购买</div>
            </div>
          </div>
          <div class="business-btn-cont" v-if="showDiv == 2">
            <div class="business-btn-box">
              <img
                src="../assets/img/2.png"
                class="notice-img"
                v-if="cardInfo.length > 0"
              />
              <img src="../assets/img/4.png" class="notice-img" v-else />
              <div class="business-btn-text" @click="carDsellBtn">点击挂单</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import cardListModel from "../model/cardListModel";
import heroList from "../common/heroIfon.json";
export default {
  data() {
    return {
      title: 1,
      web3: null,
      account: null,
      heroList: heroList.heroList,
      navList: [
        {
          navName: "购买",
          state: true,
          index: 1,
        },
        {
          navName: "我的卡片",
          state: false,
          index: 2,
        },
        {
          navName: "我的订单",
          state: false,
          index: 3,
        },
      ],
      NavLsit1: [
        {
          navName: "全部",
          sate: true,
          index: 1000,
          style: "backpack-nav-list4",
        },
        {
          navName: "白",
          sate: false,
          index: 5,
          style: "backpack-nav-list",
        },
        {
          navName: "蓝",
          sate: false,
          index: 4,
          style: "backpack-nav-list1",
        },
        {
          navName: "紫",
          sate: false,
          index: 3,
          style: "backpack-nav-list2",
        },
        {
          navName: "橙",
          sate: false,
          index: 2,
          style: "backpack-nav-list3",
        },
        {
          navName: "金",
          sate: false,
          index: 1,
          style: "backpack-nav-list4",
        },
      ],
      cardInfo: [],
      showNav: false,
      showDiv: 1,
      cardList: [],
    };
  },
  components: {
    cardListModel,
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
    await this.getinllcontracts();
    await this.getExchangeList();
  },
  methods: {
    backBtn() {
      this.$router.push("/home");
    },
    getMsgFormSon(e) {
      this.cardInfo = e;
      sessionStorage.setItem("carToken", JSON.stringify(e)); //存储钱包地址
    },
    async getinllcontracts() {
      // var web3 = this.$store.state.web3; //初始化web3
      this.account = sessionStorage["account"]; //取出缓存中的钱包地址;
      this.ExchangecFun = this.$store.state.ExchangecFun;
      this.CardTokenExFun = this.$store.state.CardTokenFun;
    },
    async NavListBtn(e) {
      this.cardInfo = [];
      for (let index = 0; index < this.navList.length; index++) {
        this.navList[index].state = false;
      }
      e.state = true;
      if (e.index == 1) {
        this.showDiv = 1;
        this.showNav = false;
        this.title = e.index;
        await this.getExchangeList();
      }
      if (e.index == 2) {
        this.showNav = true;
        this.showDiv = 2;
        this.title = e.index;
        var r = 1000;
        await this.getMyCard(r);
      }
      if (e.index == 3) {
        this.showDiv = 3;
        this.showNav = false;
        this.title = e.index;
        await this.getOderList();
      }
    },
    async NavBtn(e) {
      for (let i in this.NavLsit1) {
        this.NavLsit1[i].sate = false;
      }
      e.sate = true;
      await this.getMyCard(e.index);
    },
    async getMyCard(r) {
      this.cardList = [];
      let contract = await this.$Fun.CardDetail();
      await this.$getWbe3.getTokenIds(contract, this.account, r, (res) => {
        // console.log(res);
        if (res) {
          for (var i in res) {
            if (res[i].length > 0) {
              var obj = {};

              this.heroList.map((item) => {
                if (res[i][2] == item.no) {
                  obj.index = i;
                  obj.sate = false;
                  obj.tokenId = res[i][0];
                  obj.power = res[i][1];
                  obj.genre = res[i][3];
                  obj.genre = res[i][3];
                  obj.imgUrlC = res[i][2];
                  obj.imgUrl = require("../assets/cardimg/" +
                    res[i][2] +
                    ".png");
                  obj.doc = item.doc;
                  obj.name = item.name;
                  obj.name1 = item.name1;
                  obj.times = res[i][4];
                }
              });
              this.cardList.push(obj);
            }
          }
        }
      });
    },
    async getExchangeList() {
      this.cardList = [];
      let contract = await this.$Fun.Exchange();
      await this.$getWbe3.getExchangeList(contract, (res) => {
        // console.log(res);
        if (res) {
          // console.log(res);
          for (var i in res) {
            if (res[i].length > 0) {
              var obj = {};
              this.heroList.map((item) => {
                if (res[i][3] == item.no) {
                  obj.imgUrl = require("../assets/cardimg/" +
                    res[i][3] +
                    ".png");
                  obj.imgUrlC = res[i][3];
                  obj.doc = item.doc;
                  obj.name = item.name;
                  obj.name1 = item.name1;
                  // obj.times = res[i].times;
                  obj.price = this.$store.state.web3.utils.fromWei(res[i][4]);
                }
              });

              obj.index = i;
              obj.sate = false;
              obj.tokenId = res[i][0];
              obj.power = res[i][1];
              obj.genre = res[i][2];

              this.cardList.push(obj);
            }
          }
        }
      });
    },
    async getOderList() {
      this.cardList = [];
      let contract = await this.$Fun.Exchange();
      // console.log(this.account);
      await this.$getWbe3.getOderList(contract, this.account, (res) => {
        if (res) {
          console.log(res)
          var res1=this.uniq(res)
          for (var i in res1) {
            // alert(res[i][0]);
          
            if (res1[i][1] != '0') {
              console.log(res[i][0])
              if (res1[i].length > 0) {
                var obj = {};
                this.heroList.map((item) => {
                  if (res1[i][3] == item.no) {
                    obj.doc = item.doc;
                    obj.name = item.name;
                    obj.name1 = item.name1;
                    obj.times = res1[i].times;
                    obj.price = this.$store.state.web3.utils.fromWei(res1[i][4]);
                    obj.imgUrl = require("../assets/cardimg/" +
                      res1[i][3] +
                      ".png");
                  }
                });

                obj.index = i;
                obj.sate = false;
                obj.tokenId = res[i][0];
                obj.power = res1[i][1];
                obj.genre = res1[i][2];

                obj.imgUrlC = res1[i][3];

                this.cardList.push(obj);
              }
            }
          }
        }
      });
    },
    uniq(array) {
      var temp = [], tempt = []; //一个新的临时数组
      for (var i = 0; i < array.length; i++) {
        if (tempt.indexOf(array[i][0]) == -1) {
          temp.push(array[i]);
          tempt.push(array[i][0]);
        }
      }
      return temp;
    },
    carDBuyBtn() {
      if (this.cardInfo.length > 0) {
        this.$router.push("/buyInfo");
      } else {
        console.log("请选择卡片");
      }
    },
    carDsellBtn() {
      if (this.cardInfo.length > 0) {
        console.log("选择好了");
        this.$router.push("/sellInfo");
      } else {
        console.log("请选择卡片");
      }
    },
  },
};
</script>
<style scoped>
.business {
  width: 100%;
  height: 100%;
}
.business-conter {
  max-width: 420px;
  height: 100%;
  background: url("../assets/img/52.png") no-repeat;
  background-size: cover;
  margin: auto;
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
.business-body {
  position: absolute;
  top: 57px;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
}
.business-body-conter {
  position: relative;
  width: 100%;
  height: 100%;
}
.business-body-nav {
  width: 100%;
  overflow: hidden;
  background: #000;
}
.business-nav {
  width: 95%;
  height: 59px;
  margin: auto;
  margin-top: 10px;
  position: relative;
}
.business-nav-text {
  position: absolute;
  top: 15px;
  left: 0;
  right: 0;
  height: 30px;
}
.business-nav-c {
  width: 90%;
  height: 100%;
  overflow: hidden;
  margin: auto;
}
.business-nav-b {
  width: 30%;
  height: 100%;
  float: left;
  position: relative;
}
.business-nav-m {
  margin-left: 4.95%;
}
.business-nav-t {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 30px;
  color: #faf5e4;
  font-size: 14px;
}
.backpack-nav {
  width: 95%;
  height: 53px;
  margin: auto;
  overflow: hidden;
  background-size: cover;
  position: relative;
}
.backpack-nav-list {
  float: left;
  width: 16.5%;
  height: 37px;
  text-align: center;
  line-height: 37px;
  color: #ffffff;
  margin-top: 10px;
}
.backpack-nav-list1 {
  float: left;
  width: 16.5%;
  height: 37px;
  text-align: center;
  line-height: 37px;
  color: #3db8ff;
  margin-top: 10px;
}
.backpack-nav-list2 {
  float: left;
  width: 16.5%;
  height: 37px;
  text-align: center;
  line-height: 37px;
  color: #b46bff;
  margin-top: 10px;
}
.backpack-nav-list3 {
  float: left;
  width: 16.5%;
  height: 37px;
  text-align: center;
  line-height: 37px;
  color: #ff7826;
  margin-top: 10px;
}
.backpack-nav-list4 {
  float: left;
  width: 16.5%;
  height: 37px;
  text-align: center;
  line-height: 37px;
  color: #f9be61;
  margin-top: 10px;
}
.active {
  width: 100%;
  height: 100%;
  background: url("../assets/img/48.png") no-repeat;
  background-size: cover;
  position: relative;
}
.nav-bg-list {
  width: 100%;
  height: 100%;
}
.notice-img {
  width: 100%;
  height: 100%;
  display: block;
}
.list-box {
  position: absolute;
  top: 71px;
  left: 0;
  right: 0;
  bottom: 10px;
}
.list-box1 {
  position: absolute;
  top: 126px;
  left: 0;
  right: 0;
  bottom: 10px;
  overflow: auto;
}
.list-box2 {
  position: absolute;
  top: 70px;
  left: 0;
  right: 0;
  bottom: 10px;
}
.list-box-cont {
  width: 100%;
  height: 100%;
  position: relative;
}
.business-btn-cont {
  position: absolute;
  bottom: 10px;
  width: 100%;
  /* height: 79px; */
}
.business-btn-box {
  width: 120px;
  height: 40px;
  margin: auto;
  /* margin-top: 20px; */
  border-radius: 3px;
  overflow: hidden;
  position: relative;
}
.business-btn-text {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  line-height: 40px;
  text-align: center;
  color: #5d4037;
  font-size: 15px;
  font-weight: 700;
}
</style>
