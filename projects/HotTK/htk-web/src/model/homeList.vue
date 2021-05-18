<template>
  <div class="homeList">
    <div class="homeList-h">
      <div class="home-nav">
        <img
          src="../assets/img/49.png"
          class="notice-img"
          style="opacity: 0;"
        />
        <div class="home-nav-text">
          <div class="home-nav-c">
            <div
              class="home-nav-b"
              v-for="itme in navList"
              :key="itme.index"
              @click="NavListBtn(itme)"
            >
              <img
                src="../assets/img/3.png"
                class="notice-img"
                v-if="itme.state"
              />
              <div class="home-nav-t" :class="{ active: itme.state }">
                {{ itme.navName }}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="th-box">
      <div class="th">排名</div>
      <div class="th1">地址</div>
      <div class="th1">战力</div>
    </div>
    <div class="tbody-box">
      <div class="td">1</div>
      <div class="td1">0x3f...4dc6</div>
      <div class="td1">6013729</div>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    datacur: String,
  },
  data() {
    return {
      navList: [
        {
          navName: "战力排行",
          state: true,
          index: 1,
        },
        {
          navName: "集卡排行",
          state: false,
          index: 2,
        },
        {
          navName: "交易金额排行",
          state: false,
          index: 3,
        },
      ],
    };
  },
  mounted() {},
  methods: {
    NavListBtn(e) {
      for (var i in this.navList) {
        this.navList[i].state = false;
      }
      e.state = true;
    },
  },
};
</script>
<style scoped>
.homeList {
  width: 88%;
  min-height: 295px;
  background-color: #fff;
  margin: auto;
  width: 88%;
  height: 450px;
  margin: auto;
  margin-top: 20px;
  border-radius: 20px;
  overflow: hidden;
}
.notice-img {
  width: 100%;
  height: 100%;
  display: block;
}
.homeList-h {
  width: 100%;
  position: relative;
  color: #5d4037;
  overflow: hidden;
  margin-top: 18px;
}
.home-nav {
  width: 95%;
  margin: auto;
  position: relative;
}
.home-nav-text {
  position: absolute;
  top: 15px;
  left: 0;
  right: 0;
  height: 30px;
}
.home-nav-c {
  width: 90%;
  height: 100%;
  overflow: hidden;
  margin: auto;
}
.home-nav-b {
  width: 30%;
  height: 100%;
  float: left;
  position: relative;
}
.home-nav-t {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  text-align: center;
  line-height: 30px;
  font-size: 13px;
  font-weight: 700;
}
.active {
  color: #faf5e4;
}
.th-box {
  width: 95%;
  height: 30px;
  background: rgba(95, 66, 57, 0.15);
  margin: auto;
  margin-top: 10px;
  overflow: hidden;
  font-weight: 700;
  font-size: 12px;
}
.th {
  float: left;
  width: 20%;
  line-height: 30px;
  text-align: center;
  color: #584147;
}
.th1 {
  float: left;
  width: 40%;
  line-height: 30px;
  text-align: center;
  color: #584147;
}
.tbody-box {
  width: 95%;
  height: 30px;
  margin: auto;
  margin-top: 10px;
  overflow: hidden;
  font-weight: 700;
  font-size: 12px;
}
.td {
  float: left;
  width: 20%;
  line-height: 30px;
  text-align: center;
  color: #584147;
}
.td1 {
  float: left;
  width: 40%;
  line-height: 30px;
  text-align: center;
  color: #584147;
}
</style>
