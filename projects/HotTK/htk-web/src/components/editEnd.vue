<template>
  <div class="fusionEnd">
    <div class="fusionEnd-cont">
      <header>
        <div class="header-img-box">
          <div class="header-c">修改成功</div>
        </div>
        <div class="header-img-h">
          <img src="../assets/head/head.png" class="notice-img" />
        </div>
      </header>
      <div class="fusionEnd-conter">
        <div class="fusionEnd-conter-box">
            <cardModel :vname='vname'/>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import cardModel from '../model/cardModel'
export default {
  data() {
    return {
        vname:'editEnd'
    };
  },
  components:{
      cardModel
  }
};
</script>
<style scoped>
.fusionEnd {
  width: 100%;
  height: 100%;
  position: relative;
}
.fusionEnd-cont {
  max-width: 420px;
  height: 100%;
  margin: auto;
  background-color: #000;
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
.fusionEnd-conter {
  position: absolute;
  top: 57px;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: #000;
}
.fusionEnd-conter-box{
    width: 100%;
    height: 100%;
    position: relative;
}
</style>