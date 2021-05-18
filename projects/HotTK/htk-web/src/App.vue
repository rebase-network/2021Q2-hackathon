<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: "app",
  data() {
    return {};
  },
  async mounted() {
    await this.$getWbe3.Init(
      (addr) => {
        //得到相应的钱包地址
        this.account = addr;
        //console.log(addr, "111");
      },
      (provider) => {
        // console.log(provider);
        this.Web3 = provider;
      }
    );
    await this.$store.mutations.initWeb3Account();
  },
};
</script>

<style>
html,
body {
  padding: 0;
  margin: 0;
  width: 100%;
  height: 100%;
}
#app {
  /* font-family: "Avenir", Helvetica, Arial, sans-serif; */
  font-family: Source Han Sans CN;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  width: 100%;
  height: 100%;
  background: url("../src/assets/img/52.png") no-repeat;
  background-size: cover;
  overflow: hidden;
}
</style>
