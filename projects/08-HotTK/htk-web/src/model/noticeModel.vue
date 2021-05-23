<template>
  <div class="noticeModel" @click="cloneBtnALL()">
    <div class="noticeModel-s">
      <div class="home-cont-one">
        <div class="img-h">
          <img src="../assets/img/44.png" class="head-img" />
        </div>
        <div class="img-b" style="height: 410px;">
          <img src="../assets/img/42.png" class="head-img" />
        </div>
        <div class="img-f">
          <img src="../assets/img/43.png" class="head-img" />
        </div>
        <div class="img-min">
          <img src="../assets/img/41.png" class="head-img" />
        </div>
        <div class="home-cont-t">
          <div style="width: 100%;position: relative;height: 100%;">
            <!-- <div class="home-cont-h-l">
              <div class="home-cont-h-t">我的信息</div>
              <img src="../assets/img/3.png" class="head-img" />
            </div> -->
            <div class="cloneBtn" @click="cloneBtn()">
              <img
                src="../assets/img/24.png"
                class="head-img"
                style="transform:rotate(45deg);"
              />
            </div>
            <div class="noticeModel-c">
              <div
                class="noticeModel-c-box"
                v-for="(itmes, index) in broadcast"
                :key="index"
              >
                <div class="home-cont-h-l">
                  <div class="home-cont-h-t">活动公告</div>
                  <img src="../assets/img/3.png" class="head-img" />
                </div>
                <div class="noticeModel-c-test">{{ itmes.test }}</div>
                <div class="noticeModel-c-test1">{{ itmes.Time }}</div>
              </div>
            </div>
            <!-- <div class="home-cont-h-l">
              <div class="home-cont-h-t">我的信息</div>
              <img src="../assets/img/3.png" class="head-img" />
            </div> -->
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import broadcast from "../common/broadcast.json";
export default {
  data() {
    return {
      broadcast: broadcast,
    };
  },
  methods: {
    cloneBtn() {
      localStorage.setItem("cloneTime", "MTYyMDgwNjkyNA==");
      this.$emit("noticeModelBtn");
    },
    cloneBtnALL() {
      localStorage.setItem("cloneTime", "MTYyMDgwNjkyNA==");
      this.$emit("noticeModelBtn");
    },
  },
};
</script>
<style>
.noticeModel {
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  background: rgba(24, 24, 30, 0.75);
}
.noticeModel-s {
  width: 100%;
  height: 100%;
  overflow: hidden;
}
.home-cont-one {
  width: 95%;
  margin: auto;
  margin-top: 35%;
  position: relative;
}
.img-h {
  width: 100%;
  display: block;
}
.img-b {
  height: 260px;
  display: block;
  width: 93%;
  margin: auto;
}
.img-b1 {
  height: 155px;
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
.home-cont-t {
  position: absolute;
  left: 25px;
  right: 25px;
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
.head-img {
  width: 100%;
  height: 100%;
  display: block;
}
.home-cont-h-l {
  width: 100px;
  height: 20px;
  /* padding-top: 10px; */
  position: relative;
  top: 10px;
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
.noticeModel-c {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
}
.noticeModel-c-box {
  width: 100%;
  overflow: hidden;
  margin-bottom: 20px;
}
.noticeModel-c-test {
  margin-top: 15px;
  line-height: 20px;
  font-size: 12px;
  color: #5d4037;
}
.noticeModel-c-test1 {
  margin-top: 5px;
  line-height: 20px;
  font-size: 13px;
  color: #5d4037;
}
.cloneBtn {
  position: absolute;
  right: 10px;
  width: 25px;
  height: 25px;
}
</style>
