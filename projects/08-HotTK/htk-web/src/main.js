import Vue from 'vue'
import App from './App.vue'
// import Router from 'vue-router'
import router from './router/index'
import store from './store/index'
import Mint from 'mint-ui';
import contractsFun from './common/contractsFun' //初始化合约地址
import GETWEB3 from './common/globals.dbug' // 初始化web3以及合约交互方法封装
import 'mint-ui/lib/style.css'
// Vue.use(Router)
Vue.use(Mint);
Vue.config.productionTip = false
Vue.prototype.$Fun = contractsFun
Vue.prototype.$getWbe3 = GETWEB3

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')