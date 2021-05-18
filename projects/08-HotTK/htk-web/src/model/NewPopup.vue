<template>
  <div class="popUp" @click="popUpBtn()">
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
          <div class="popUp-cont-h-l">
            <div class="popUp-cont-h-t">合成规则</div>
            <img src="../assets/img/3.png" class="head-img" />
          </div>
          <div class="popUp-cont-c">
            <div
              style="line-height: 30px;
    font-size: 14px;
    color: #584147;margin-top: 10px;"
            >
              10张白卡可合成1张蓝卡，<br />
              5张蓝卡可合成1张紫卡，<br />
              3张紫卡可合成1张橙卡，<br />
              橙卡不可合成金卡。
            </div>
          </div>
          <div class="popUp-cont-h-l" style="margin-top: 10px;">
            <div class="popUp-cont-h-t">合成卡片流程</div>
            <img src="../assets/img/3.png" class="head-img" />
          </div>
          <div class="popUp-cont-c">
            <div
              style="line-height: 30px;
    font-size: 14px;
    color: #584147;margin-top: 5px;"
            >
              第一步：点击“选择合成”按钮<br />第二步：再选择需要合成的卡片<br />
              第三步：点击“去合成”按钮
            </div>
          </div>
          <div class="popup-p-f">
            <div class="popup-p-f-btn">
              <img src="../assets/img/2.png" class="head-img" />
              <div class="popUp-p-f-t" @click="pulldownBtn()">知道了</div>
            </div>
          </div>
          <!-- <div class="popup-p-t">
            <div class="popup-left">可领取数量</div>
            <div class="popup-right"><span>{{Rewards}}</span>R3T</div>
          </div>
          <div class="popup-p-f">
            <div class="popup-p-f-btn">
              <img src="../assets/img/2.png" class="head-img" />
              <div class="popUp-p-f-t" @click="pulldownBtn(1)">领取</div>
            </div>
          </div> -->
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
  data() {
    return {
      // Popup:"领取R3T"
    };
  },
  mounted() {},
  methods: {
    popUpBtn() {
      this.$emit("popUpBtnMethod");
    },
    pulldownBtn() {
      this.$emit("popUpBtnMethod");
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
  background: rgba(24, 24, 30, 0.75);
}
.popUp-box {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.popUp-num {
  width: 100%;
  margin-top: 35%;
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
  height: 345px;
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
  margin-top: 10px;
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
.popUp-cont-c {
  width: 100%;
  overflow: hidden;
}
</style>
