/*
 * @Author: 33357
 * @Date: 2021-05-14 14:00:53
 * @LastEditTime: 2021-05-15 15:20:57
 * @LastEditors: 33357
 */
import Vue from "vue";
import Vuex from "vuex";
import { web3Provider } from "../web3/web3Provider";
Vue.use(Vuex);

//使用常量替代 Mutation 事件类型，多人协作的大型项目中，这会很有帮助。
const OPEN_PICTURE_VIEWER = "openPicViewer";
const CLOSE_PICTURE_VIEWER = "closePicViewer";

//在发布环境下关闭严格模式，以避免性能损失
//const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  //strict: debug,
  state: {
    switchPicViewer: false,
    viewTargetPicUrl: "",
    pagePos: 0,
    isBodyScrollDisabled: false,
    web3: new web3Provider(),
    walletAddress: "",
    name:'',
  },
  mutations: {
    [OPEN_PICTURE_VIEWER](state, payload) {
      state.switchPicViewer = true;
      state.viewTargetPicUrl = payload.targetPicUrl;
    },
    [CLOSE_PICTURE_VIEWER](state) {
      state.switchPicViewer = false;
    },
    storePagePos(state) {
      state.pagePos =
        document.documentElement.scrollTop ||
        window.pageYOffset ||
        document.body.scrollTop;
    },
    restorePagePos(state) {
      window.scrollTo(0, state.pagePos);
    },
    enableBodyScroll(state) {
      state.isBodyScrollDisabled = false;
      document.body.classList.remove("scroll-disabled");
    },
    disableBodyScroll(state) {
      state.isBodyScrollDisabled = true;
      document.body.classList.add("scroll-disabled");
    },
    setWeb3(state, web3) {
      state.web3 = web3;
    },
    setWalletAddress(state, walletAddress) {
      state.walletAddress = walletAddress;
    },
  },
  actions: {
    async login({state}) {
      state.walletAddress = await state.web3.getWeb3();
      state.name=state.walletAddress.substring(0,6)+'...'+state.walletAddress.substring(36);
      return true;
    },
  },
});
