<template>
  <div class="updateSuccessed">
    <div class="updateSuccessed-conter">
      <header>
        <div class="header-img-box">
          <div class="header-c">升级成功</div>
        </div>
        <div class="header-img-h">
          <img src="../assets/head/head.png" class="notice-img" />
        </div>
      </header>
      <div class="updateSd">
        <div class="updateSd-conter">
          <div class="deposit-num">
            <!-- <img src="../assets/home/home1.png" class="head-img" /> -->
            <div class="img-h">
              <img src="../assets/img/44.png" class="notice-img" />
            </div>
            <div class="img-b12">
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
                  <div class="home-cont-h-t">升级成功</div>
                  <img src="../assets/img/3.png" class="notice-img" />
                </div>
                <div class="updateSd-c-info">
                  <div class="updateSd-info-img">
                    <img :src="cardInfo.imgUrl" class="notice-img" />
                  </div>
                  <div class="updateSd-info-text-box">
                    <div class="updateSd-info-text">
                      <div
                        style="
                          float: left;
                          font-size: 15px;
                          font-weight: 700;
                          color: #db522b;
                        "
                      >
                        {{ cardInfo.name }}
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #584147"
                      >
                        {{ cardInfo.name1 }}
                      </div>
                    </div>
                    <div class="updateSd-info-text">
                      <div
                        style="
                          float: left;
                          font-size: 13px;
                          color: #db522b;
                        "
                      >
                        级别
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #db522b"
                        v-if="cardInfo.genre == '1'"
                      >
                        金卡
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #db522b"
                        v-if="cardInfo.genre == '2'"
                      >
                        橙卡
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #db522b"
                        v-if="cardInfo.genre == '3'"
                      >
                        紫卡
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #db522b"
                        v-if="cardInfo.genre == '4'"
                      >
                        蓝卡
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #db522b"
                        v-if="cardInfo.genre == '5'"
                      >
                        白卡
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #db522b"
                        v-if="cardInfo.imgUrlC == '99'"
                      >
                        特殊卡
                      </div>
                    </div>
                    <div class="updateSd-info-text">
                      <div
                        style="
                          float: left;
                          font-size: 13px;
                          color: #db522b;
                        "
                      >
                        战力(原)<br />
                        <span style="font-size:12px;color:#584147;">{{
                          cardInfo.power
                        }}</span>
                      </div>
                      <div
                        style="float: right; font-size: 13px; color: #db522b"
                      >
                        战力<br />
                        <span style="font-size:12px;color:#584147;">{{
                          cardInfo.newnowpower
                        }}</span>
                      </div>
                    </div>
                    <div class="updateSd-btn-box">
                      <div class="updateSd-btn" @click="updateSdBtn">
                        <img src="../assets/img/2.png" class="notice-img" />
                        <div class="updateSd-btn-text">确定</div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
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
      cardInfo: {},
    };
  },
  mounted() {
    let cardInfos = JSON.parse(sessionStorage.getItem("cardInfo"));
    if (cardInfos) {
      cardInfos.imgUrl = require("../assets/cardimg/" +
        cardInfos.imgUrlC +
        ".png");
      this.cardInfo = cardInfos;
    }
  },
  methods: {
    updateSdBtn() {
      this.$router.push("/backpack");
    },
  },
};
</script>

<style scoped>
.updateSuccessed {
  width: 100%;
  height: 100%;
  position: relative;
}
.updateSuccessed-conter {
  max-width: 420px;
  height: 100%;
  margin: auto;
  background-color: #000;
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
.notice-img {
  width: 100%;
  height: 100%;
  display: block;
}
.updateSd {
  position: absolute;
  top: 57px;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
}
.updateSd-conter {
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
  overflow: hidden;
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
.img-b12 {
  height: 320px;
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
.updateSd-c-info {
  width: 100%;
  position: relative;
  overflow: hidden;
  margin-top: 35px;
}
.updateSd-info-img {
  float: left;
  width: 40%;
  border-radius: 3px;
}
.updateSd-info-text-box {
  float: left;
  width: 50%;
  margin-left: 5%;
}
.updateSd-info-text {
  width: 100%;
  height: 40px;
  overflow: hidden;
}
.updateSd-btn-box {
  width: 100%;
  height: 40px;
  margin-top: 30px;
}
.updateSd-btn {
  position: relative;
  width: 80%;
  height: 40px;
  margin: auto;
}
.updateSd-btn-text {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  text-align: center;
  font-size: 14px;
  line-height: 40px;
  color: #5d4037;
  font-weight: 700;
}
</style>
