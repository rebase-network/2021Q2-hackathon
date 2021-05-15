<template>
  <div class="cardModel">
    <div class="cardModel-conter">
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
            <div class="home-cont-h-t" v-if="vname == 'buyEnd'">购买成功</div>
            <div class="home-cont-h-t" v-if="vname == 'sellEnd'">挂单成功</div>
            <div class="home-cont-h-t" v-if="vname == 'giveUpInfo'">
              取消挂单
            </div>
            <div class="home-cont-h-t" v-if="vname == 'editEnd'">修改挂单</div>
            <div class="home-cont-h-t" v-if="vname == 'fusionEnd'">
              合成成功
            </div>
            <img src="../assets/img/3.png" class="head-img" />
          </div>
          <div class="cardModel-text" v-if="vname == 'sellEnd'">
            恭喜您已经挂单一张
          </div>
          <div class="cardModel-text" v-if="vname == 'giveUpInfo'">
            您已取消挂单
          </div>
          <div class="cardModel-text" v-if="vname == 'fusionEnd'">
            恭喜您已经获得一张
          </div>
          <div class="cardModel-text" v-if="vname == 'editEnd'">
            恭喜您已成功修改挂单
          </div>
          <div class="cardModel-text1" v-if="CardInfo"><span>{{CardInfo.cardType}}</span>:<span>{{CardInfo.name}}</span></div>
          <div class="cardModel-img">
            <div class="cardModel-img-info" v-if="CardInfo">
              <img :src="CardInfo.imgUrl" class="head-img" />
            </div>
          </div>
          <div
            class="info-btn1"
            v-if="
              vname == 'sellEnd' || vname == 'giveUpInfo' || vname == 'editEnd'
            "
          >
            <div class="info-btn-box21" @click="cardMBtnS()">
              <!-- 卡牌商店 -->
              <div class="info-text-c">确定</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div>
          </div>
          <div class="info-btn" v-else>
            <div
              class="info-btn-box"
              @click="cardMBtnB()"
              v-if="vname == 'buyEnd'"
            >
              <!-- 卡牌商店 -->
              <div class="info-text-c">返回</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div>
            <div class="info-btn-box" @click="cardMBtn()" v-else>
              <!-- 卡牌商店 -->
              <div class="info-text-c">返回</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div>
            <div class="info-btn-box1" @click="cardMBtn1()">
              <!-- 我的背包 -->
              <div class="info-text-c">放入背包</div>
              <img src="../assets/img/2.png" class="head-img" />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  props: ["vname",'CardInfo'],
  data() {
    return {};
  },
  methods: {
    cardMBtn() {
       sessionStorage.removeItem("objList");
      this.$router.push("/backpack");
    },
    cardMBtn1() {
      sessionStorage.removeItem("objList");
      this.$router.push("/backpack");
    },
    cardMBtnS() {
       sessionStorage.removeItem("objList");
      this.$router.push("/business");
    },
    cardMBtnB() {
       sessionStorage.removeItem("objList");
        const cardType = sessionStorage["cardType"];
      if (cardType == 1) {
         this.$router.push("/store");
      } else {
        this.$router.push("/business");
      }
    },
  },
};
</script>

<style scoped>
.cardModel {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
}
.cardModel-conter {
  width: 100%;
  height: 100%;
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
}
.info-btn {
  width: 100%;
  height: 50px;
  overflow: hidden;
  float: left;
  margin-top: 60px;
}
.info-btn1 {
  width: 100%;
  height: 50px;
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
.info-btn-box21 {
  width: 100px;
  height: 35px;
  margin: auto;
  margin-top: 20px;
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
.cardModel-text {
  width: 100%;
  height: 40px;
  margin-top: 20px;
  text-align: center;
  line-height: 40px;
  color: #584147;
}
.cardModel-text1 {
  width: 100%;
  height: 40px;
  line-height: 40px;
  text-align: center;
  color: #db522b;
  font-weight: 700;
}
.cardModel-img {
  width: 100%;
  overflow: hidden;
}
.cardModel-img-info {
  width: 40%;
  margin: auto;
}
</style>
