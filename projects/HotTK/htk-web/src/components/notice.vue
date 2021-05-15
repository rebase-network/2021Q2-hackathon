<template>
  <div class="notice">
    <div class="notice-cont">
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
          <div class="header-c">活动公告</div>
        </div>
        <div class="header-img-h">
          <img src="../assets/head/head.png" class="notice-img" />
        </div>
      </header>
      <div class="body-box">
        <div class="body-ul">
          <div class="body-li" v-for="itme in broadcast" :key="itme.id">
            <!-- <img src="../assets/home/home.png" class="notice-img" /> -->
            <div class="img-left-b">
              <img src="../assets/img/45.png" class="notice-img" />
            </div>
            <div class="img-mid">
              <img src="../assets/img/46.png" class="notice-img" />
            </div>
            <div class="img-right-b">
              <img src="../assets/img/45.png" class="notice-img" />
            </div>
            <div class="body-li-text-box">
              <!-- <div class="body-li-text-h">活动公告</div> -->
              <div class="notice-cont-h-l">
                <div class="home-cont-h-t">活动公告</div>
                <img src="../assets/img/3.png" class="notice-img" />
              </div>
              <div class="body-li-text-d">
                {{itme.test}}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import broadcast from "../common/broadcast.json";
export default {
  name: "notice",
  data(){
    return{
      broadcast:broadcast
    }
  },
  methods: {
    backBtn() {
      this.$router.push("/home");
    },
  },
};
</script>

<style scoped>
.notice {
  width: 100%;
  height: 100%;
}
.notice-cont {
  max-width: 420px;
  height: 100%;
  background-color: #000;
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
.body-box {
  position: absolute;
  top: 57px;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
}
.body-ul {
  width: 95%;
  margin: auto;
}
.body-li {
  width: 100%;
  height: 185px;
  margin-top: 20px;
  position: relative;
}
.notice-img {
  width: 100%;
  height: 100%;
  display: block;
}
.body-li-text-box {
  position: absolute;
  top: 15px;
  left: 20px;
  right: 20px;
  bottom: 30px;
}
.body-li-text-h {
  font-size: 14px;
}
.body-li-text-d {
  margin-top: 10px;
  line-height: 20px;
  font-size: 12px;
  color: #5d4037;
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
.notice-cont-h-l {
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
</style>