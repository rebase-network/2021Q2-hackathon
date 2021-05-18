<template>
  <div class="popUp">
    <div class="popUp-box">
      <div class="popUp-num" v-on:click.stop="doThis">
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
        <div class="popUp-num-cont">
          <div class="popUp-num-cont-test-box">
            <!-- <div>Transaction submitted</div> -->
            <div class="popUp-num-cont-test">{{Modelmsg}}</div>
            <div class="popUp-num-cont-btn">
              <div class="popUp-num-cont-right">
                <img src="../assets/img/2.png" class="head-img" />
                <div class="popUp-num-cont-lef-test" @click="pulldownBtn()">
                  我知道了
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- <div class="popUp-num">
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
        <div class="popUp-num-cont"></div>
      </div> -->
    </div>
  </div>
</template>
<script>
export default {
  props: {
    Modelmsg: String,
  },
  data() {
    return {
      // Popup:"领取R3T"
    };
  },
  mounted() {},
  methods: {
    // pulldownBtn(e) {
    //   this.$emit("fatherMethod", e);
    // },
    pulldownBtn() {
      this.$emit("submitModelMethod");
    },
    doThis() {},
  },
};
</script>
<style scoped>
.popUp {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  z-index: 999;
}
.popUp-box {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.popUp-num {
  width: 100%;
  margin-top: 70%;
  position: relative;
}
.head-img {
  width: 100%;
  height: 100%;
  display: block;
}
.img-h {
  width: 100%;
  display: block;
}
.img-b {
  height: 165px;
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
.popUp-num-cont {
  position: absolute;
  top: 20px;
  left: 25px;
  right: 25px;
  bottom: 15px;
}
.popUp-cont-h-l {
  width: 145px;
  height: 20px;
  position: relative;
}
.popUp-cont-h-t {
  position: absolute;
  top: 0;
  left: 8px;
  right: 0;
  bottom: 0;
  color: #faf5e4;
  font-size: 13px;
}
.popup-p-t {
  width: 100%;
  height: 20px;
  background: rgba(95, 66, 57, 0.15);
  margin-top: 35px;
  overflow: hidden;
  color: #5e4138;
  font-size: 12px;
  line-height: 20px;
}
.popup-p-f-btn {
  width: 120px;
  height: 40px;
  margin: auto;
  border-radius: 3px;
  position: relative;
}
.popUp-p-f-t {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 40px;
  font-size: 15px;
  font-weight: 700;
  color: #5e4138;
}
.popup-p-f {
  width: 100%;
  height: 40px;
  margin-top: 40px;
}
.popup-left {
  float: left;
  padding-left: 10px;
}
.popup-right {
  float: right;
  padding-right: 10px;
  color: #db522b;
}
.popUp-num-cont-test-box {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.popUp-num-cont-test {
  color: #584147;
  font-weight: 700;
  text-align: center;
  line-height: 50px;
  margin-top: 8%;
}
.popUp-num-cont-btn {
  width: 80%;
  height: 40px;
  margin: auto;
  margin-top: 20px;
  overflow: hidden;
}
.popUp-num-cont-left {
  float: left;
  width: 100px;
  height: 40px;
  position: relative;
}
.popUp-num-cont-right {
  width: 100px;
  height: 40px;
  position: relative;
  margin: auto;
}
.popUp-num-cont-lef-test {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 40px;
  color: #5d4037;
  font-weight: 600;
  font-size: 15px;
}
</style>
