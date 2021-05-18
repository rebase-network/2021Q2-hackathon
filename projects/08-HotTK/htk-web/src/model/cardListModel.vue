<template>
  <div class="cardListModel">
    <div class="cardListModel-list">
      <div
        class="card-body-li"
        v-for="item in cardList"
        :key="item.index"
        @click="cardBtn(item)"
      >
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
</template>
<script>
// import imgarry from "../assets/img/40.png";
export default {
  props: {
    title: Number,
    cardList: Array,
  },
  data() {
    return {};
  },
  mounted() {
   
  },
  methods: {
    cardBtn(e) {
      // console.log(e)
      if (this.title == 3) {
        this.$router.push("/giveUp");
      }
      var arry = [];
      if (e.sate == false) {
        for (let index = 0; index < this.cardList.length; index++) {
          this.cardList[index].sate = false;
        }
        arry = [];
      }
      e.sate = !e.sate;
      if (e.sate == true) {
        arry.push(e);
      } else {
        arry = [];
      }

      this.$emit("func", arry);
    },
  },
};
</script>
<style scoped>
.cardListModel {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  overflow: auto;
}
.cardListModel-list {
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
.notice-img {
  width: 100%;
  height: 100%;
  display: block;
}
</style>
