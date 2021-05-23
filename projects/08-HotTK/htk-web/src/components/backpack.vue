<template>
  <div class="NewPopup">
    <div class="backpack-cont">
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
          <div class="header-c">我的背包</div>
          <div class="header-right" @click="headerBtn()">
            <div style="width:100%;height:100%;position:relative;">
              合成规则
            </div>
            <div class="img-icon">
              <img src="../assets/img/57.png" class="notice-img" />
            </div>
          </div>
        </div>
        <div class="header-img-h">
          <img src="../assets/head/head.png" class="notice-img" />
        </div>
      </header>
      <div class="body-box">
        <div class="body-box-conter">
          <div style="width:100%;background-color:#000; overflow: hidden;">
            <div class="backpack-nav">
              <div
                :class="item.style"
                v-for="item in NavLsit"
                :key="item.index"
                @click="NavBtn(item)"
              >
                <div class="nav-bg-list" :class="{ active: item.sate }">
                  {{ item.navName }}
                </div>
              </div>
            </div>
          </div>
          <div class="card-body-box">
            <div class="card-body-ul">
              <div
                class="card-body-li"
                v-for="item in cardList"
                :key="item.index"
                @click="cardBtn(item)"
              >
                <!-- <img :src="phat+item.imgUrl + '.png'" class="notice-img" /> -->
                <img :src="item.imgUrl" class="notice-img" />
                <div class="card-body-text">
                  <div>
                    <span>名字:</span><span>{{ item.name }}</span>
                  </div>
                  <div>
                    <span>战力:</span><span>{{ item.power }}</span>
                  </div>
                </div>
                <div class="card-img-c" v-if="item.sate">
                  <div class="card-img-log">
                    <img src="../assets/img/50.png" class="notice-img" />
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="card-btn-box">
            <div
              class="card-btn-cont"
              @click="cardBtnInfo"
              v-if="showModle == 1"
            >
              选择合成
            </div>
            <div
              class="card-btn-cont1"
              :class="{ cardBg: Oisf }"
              @click="cardBtnInfo"
              v-else
            >
              去合成
            </div>
          </div>
        </div>
      </div>
      <NewPopup v-if="NewPopup" @popUpBtnMethod="popUpBtnMethod" />
    </div>
  </div>
</template>

<script>
import { Toast } from "mint-ui";
// import CardToken from "../../build/CardTokenEx.json";
// import { CardTokens } from "../common/config";
import heroList from "../common/heroIfon.json";
import NewPopup from "../model/NewPopup";
export default {
  name: "backpack",
  data() {
    return {
      NewPopup: false,
      showModle: 1,
      showText: "",
      Oisf: false,
      pickList: [],
      phat: "../assets/cardimg/",
      cardList: [],
      heroList: heroList.heroList,
      NavLsit: [
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
      account: "",
      provider: "",
      inex: 0,
      mixNum: 3,
      Web3: "",
    };
  },
  components: {
    NewPopup,
    // homeListview
  },
  async mounted() {
    await this.$getWbe3.Init(
      (addr) => {
        //得到相应的钱包地址
        this.account = addr;
        // console.log(this.account);
        var r = 1000;
        this.getCardToken(r);
      },
      (provider) => {
        this.Web3 = provider;
        // console.log(this.Web3);
      }
    );
    await this.$store.mutations.initWeb3Account();
    this.cardList = [];

    if (this.showModle == 1) {
      this.showText = "选择合成";
    } else {
      this.showText = "去合成";
    }
  },
  methods: {
    headerBtn() {
      this.NewPopup = true;
    },
    popUpBtnMethod() {
      this.NewPopup = false;
    },
    async NavBtn(e) {
      for (let i in this.NavLsit) {
        this.NavLsit[i].sate = false;
      }
      e.sate = true;
      var r = e.index;
      await this.getCardToken(r);
    },
    async getCardToken(r) {
      this.cardList = [];
      // console.log(this.account);
      let contract = await this.$Fun.CardDetail();
      await this.$getWbe3.getTokenIds(contract, this.account, r, (res) => {
        // console.log(res, 999);
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
                  obj.Alltimes = res[i][6];
                }
              });
              this.cardList.push(obj);
            }
          }
        }
      });
    },
    backBtn() {
      this.$router.push("/home");
    },
    cardBtnInfo() {
      if (this.showModle == 1) {
        this.showModle = 2;
      } else {
        this.showModle = 1;
        if (this.pickList.length > 0 && this.pickList.length == this.mixNum) {
          this.$router.push("/fusing");
        } else {
          this.showModle = 2;
          // Toast("五张蓝卡合一张紫卡,三张紫卡合一张橙卡，橙卡金卡不可合成");
          Toast({
            message: "五张蓝卡合一张紫卡,三张紫卡合一张橙卡，橙卡金卡不可合成",
            position: "middle",
            duration: 1000,
          });
          Toast.message = " ";
        }
      }
    },
    cardBtn(e) {
      if (this.showModle == 2) {
        e.sate = !e.sate;

        if (e.genre == "1" || e.genre == "2" || e.genre == "0") {
          // Toast("该卡片不可合成");
          Toast({
            message: "该卡片不可合成",
            position: "middle",
            duration: 1000,
          });
          Toast.message = " ";
          e.sate = false;
          return true;
        }

        if (this.pickList.length > 0) {
          if (this.pickList[0].genre == e.genre) {
            if (e.genre == "5") {
              this.mixNum = 10;
            }
            if (e.genre == "4") {
              this.mixNum = 5;
            }
            if (e.genre == "3") {
              this.mixNum = 3;
            }
            for (var i in this.pickList) {
              if (this.pickList[i].sate == false) {
                this.pickList.splice(i, 1);
              }
            }
          } else {
            e.sate = false;
            // Toast("请选择相同品质的卡牌进行合成");
            Toast({
              message: "请选择相同品质的卡牌进行合成",
              position: "middle",
              duration: 1000,
            });
            Toast.message = " ";
          }
        }

        if (this.pickList.length >= this.mixNum) {
          e.sate = false;

          // Toast("该卡片最多可选择" + this.mixNum + "张");
          Toast({
            message: "该卡片最多可选择" + this.mixNum + "张",
            position: "middle",
            duration: 1000,
          });
          Toast.message = " ";
          return false;
        } else {
          if (e.sate == true) {
            this.pickList.push(e);
          }
        }

        if (this.pickList.length > 0 && this.pickList.length < this.mixNum) {
          this.Oisf = false;
        } else {
          this.Oisf = true;
        }
        sessionStorage.setItem("cardList", JSON.stringify(this.pickList)); //存储钱包地址
      } else {
        this.$router.push("/cardInfo");
        sessionStorage.setItem("cardInfo", JSON.stringify(e)); //存储钱包地址
      }
    },
  },
};
</script>

<style scoped>
.NewPopup {
  width: 100%;
  height: 100%;
}
.backpack-cont {
  max-width: 420px;
  height: 100%;
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
.header-right {
  position: absolute;
  top: 0;
  right: 0;
  width: 80px;
  height: 56px;
  text-align: center;
  line-height: 56px;
  color: #faf5e4;
  font-size: 0.14rem;
}
.img-icon {
  position: absolute;
  top: 18px;
  left: -10px;
  width: 20px;
  height: 20px;
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
  top: 57px;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
}
.body-box-conter {
  width: 100%;
  height: 100%;
  overflow: hidden;
  position: relative;
}
.backpack-nav {
  width: 95%;
  height: 53px;
  margin: auto;
  margin-top: 10px;
  overflow: hidden;
  background: url("../assets/img/49.png") no-repeat;
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
.card-body-box {
  position: absolute;
  top: 65px;
  left: 0;
  right: 0;
  bottom: 15px;
  overflow: auto;
}
.card-body-ul {
  width: 100%;
  overflow: hidden;
  display: flex;
  flex-wrap: wrap;
}
.card-body-li {
  width: 30%;
  border-radius: 3px;
  overflow: hidden;
  margin-left: 10px;
  margin-top: 10px;
  position: relative;
}
.card-body-text {
  position: absolute;
  bottom: 0;
  width: 100%;
  height: 40px;
  background-color: rgb(0 0 0 / 58%);
  line-height: 20px;
  padding-left: 5px;
  color: #fff;
  font-size: 12px;
}
.card-img-c {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgb(0 0 0 / 42%);
}
.card-img-log {
  position: absolute;
  width: 50px;
  height: 50px;
  left: 32px;
  top: 64px;
}
.card-btn-box {
  position: absolute;
  bottom: 15px;
  width: 100%;
  height: 50px;
}
.card-btn-cont {
  width: 150px;
  height: 50px;
  margin: auto;
  border-radius: 5px;
  background: url("../assets/img/2.png") no-repeat;
  background-size: cover;
  position: relative;
  text-align: center;
  line-height: 50px;
  color: #5d4037;
  font-weight: 700;
}
.card-btn-cont1 {
  width: 150px;
  height: 50px;
  margin: auto;
  border-radius: 5px;
  background: url("../assets/img/4.png") no-repeat;
  background-size: cover;
  position: relative;
  text-align: center;
  line-height: 50px;
  color: #5d4037;
  font-weight: 700;
}
.cardBg {
  background: url("../assets/img/2.png") no-repeat;
  background-size: cover;
  position: relative;
}
</style>
