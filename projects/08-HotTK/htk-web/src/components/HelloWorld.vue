<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      For a guide and recipes on how to configure / customize this project,<br />
      check out the
      <a href="https://cli.vuejs.org" target="_blank" rel="noopener"
        >vue-cli documentation</a
      >.
    </p>
    <h3>Installed CLI Plugins</h3>
    <ul>
      <li>
        <a
          href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-babel"
          target="_blank"
          rel="noopener"
          >babel</a
        >
      </li>
      <li>
        <a
          href="https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-eslint"
          target="_blank"
          rel="noopener"
          >eslint</a
        >
      </li>
    </ul>
    <h3>Essential Links</h3>
    <ul>
      <li>
        <a href="https://vuejs.org" target="_blank" rel="noopener">Core Docs</a>
      </li>
      <li>
        <a href="https://forum.vuejs.org" target="_blank" rel="noopener"
          >Forum</a
        >
      </li>
      <li>
        <a href="https://chat.vuejs.org" target="_blank" rel="noopener"
          >Community Chat</a
        >
      </li>
      <li>
        <a href="https://twitter.com/vuejs" target="_blank" rel="noopener"
          >Twitter</a
        >
      </li>
      <li>
        <a href="https://news.vuejs.org" target="_blank" rel="noopener">News</a>
      </li>
    </ul>
    <h3>Ecosystem</h3>
    <ul>
      <li>
        <a href="https://router.vuejs.org" target="_blank" rel="noopener"
          >vue-router</a
        >
      </li>
      <li>
        <a href="https://vuex.vuejs.org" target="_blank" rel="noopener">vuex</a>
      </li>
      <li>
        <a
          href="https://github.com/vuejs/vue-devtools#vue-devtools"
          target="_blank"
          rel="noopener"
          >vue-devtools</a
        >
      </li>
      <li>
        <a href="https://vue-loader.vuejs.org" target="_blank" rel="noopener"
          >vue-loader</a
        >
      </li>
      <li>
        <a
          href="https://github.com/vuejs/awesome-vue"
          target="_blank"
          rel="noopener"
          >awesome-vue</a
        >
      </li>
    </ul>
  </div>
</template>

<script>
import Web3 from "web3";
import Mortgage from "../../build/Mortgage.json";
import {Mortgages} from "../common/config";
export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
  data() {
    return {
      web3: {},
      account: "",
    };
  },
  async mounted() {
    await this.initWeb3Account();
  },
  methods: {
    async initWeb3Account() {
      //初始化web3并且初始化合约方法

      if (window.ethereum) {
        this.provider = window.ethereum;
        try {
          await window.ethereum.enable();
        } catch (error) {
          console.log("User denied account access");
        }
      } else if (window.web3) {
        this.provider = window.web3.currentProvider;
      } else {
        this.provider = new Web3.providers.HttpProvider(
          "http://127.0.0.1:7545"
        );
      }
      const web3 = new Web3(this.provider);
      this.web3 = web3; //全局中
      web3.eth.getAccounts().then((accs) => {
        console.log(accs[0]);
        this.getAA();
        this.account = accs[0];
      });
      // console.log(state,999)
    },
    async getAA() {
    
      var MortgageFun = new this.web3.eth.Contract(Mortgage.abi, Mortgages);
        console.log(this.account,Mortgages);
      console.log(MortgageFun.methods.getMyValueLock("RttToken"));
      var res = await MortgageFun.methods.getMyValueLock("RttToken").call()
      console.log(res);
      // await MortgageFun.methods
      //   .getMyValueLock("RttToken")
      //   .call()
      //   .then((res) => {
      //     console.log(res, "LLLKKK");
      //     // this.MyRTTdeposit = this.Web3.utils.fromWei(res);
      //   });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
