/*
 * @Author: 33357
 * @Date: 2021-05-14 14:00:53
 * @LastEditTime: 2021-05-15 20:22:10
 * @LastEditors: 33357
 */
import Vue from "vue";
import Vuex from "vuex";
import { web3Provider } from "../web3/web3Provider";
import { tokenList } from "../const/tokenList";
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
    name: "",
    tokenList: {},
    follows: [],
    blogs: {},
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
  },
  actions: {
    async login({ state }) {
      state.walletAddress = await state.web3.getWeb3();
      state.name =
        state.walletAddress.substring(0, 6) +
        "..." +
        state.walletAddress.substring(36);
      return true;
    },
    async getBlog({ state }, blogId) {
      Vue.set(state.blogs, blogId, await state.web3.routerFunc.getBlog(blogId));
    },
    async getTokenList({ state }) {
      tokenList.forEach(async (token) => {
        const balance = await state.web3.erc20Func.getBalance(
          state.walletAddress,
          token.address
        );
        if (balance > 0) {
          const res = await Promise.all([
            state.web3.erc20Func.getName(token.address),
            state.web3.erc20Func.getSymbol(token.address),
            state.web3.erc20Func.getDecimals(token.address),
          ]);
          console.log({
            address: token.address,
            name: res[0],
            symbol: res[1],
            decimals: res[2],
            balance: balance,
          });
          Vue.set(state.tokenList, token.address, {
            address: token.address,
            name: res[0],
            symbol: res[1],
            decimals: res[2],
            balance: balance,
          });
        }
      });
    },
  },
});
