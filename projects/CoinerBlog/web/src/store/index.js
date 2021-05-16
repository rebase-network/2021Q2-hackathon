/*
 * @Author: 33357
 * @Date: 2021-05-14 14:00:53
 * @LastEditTime: 2021-05-16 13:47:30
 * @LastEditors: 33357
 */
import Vue from "vue";
import Vuex from "vuex";
import { web3Provider } from "../web3/web3Provider";
import { tokenList } from "../const/tokenList";
import { web3Utils } from "../const/func";
import Identicon from "identicon.js";
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
    tokenList: {},
    follows: ['0xCc460046e02Ec807BbbbE5B2f5e202C68e871855','0x1f2479ee1b4aFE789e19D257D2D50810ac90fa59','0x3FEB75B9d843dcbA53F7eD5a68fc0a2D25cc3B00'],
    blogs: {},
    favorites: [],
    tokens:[],
    avatars: {},
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
    async login({ state, dispatch }) {
      state.walletAddress = await state.web3.getWeb3();
      dispatch("getAvatar", state.walletAddress);
      return true;
    },
    async getBlog({ state, dispatch }, blogId) {
      if (state.blogs[blogId] === undefined) {
        const blog = await state.web3.routerFunc.getBlog(blogId);
        Vue.set(state.blogs, blogId, blog);
        dispatch("getAvatar", blog.person);
        if (blog.repostBlogId != 0) {
          dispatch("getBlog", blog.repostBlogId);
        }
      }
    },
    async getAvatar({ state }, address) {
      if (state.avatars[address] === undefined) {
        Vue.set(
          state.avatars,
          address,
          "data:image/png;base64," + new Identicon(address, 120).toString()
        );
      }
    },
    async getTokenList({ state }) {
      tokenList.forEach(async (token) => {
        const balance = await state.web3.erc20Func.getBalance(
          state.walletAddress,
          token.address
        );
        if (balance > 0) {
          state.tokens.push(web3Utils.toChecksumAddress(token.address))
          const res = await Promise.all([
            state.web3.erc20Func.getName(token.address),
            state.web3.erc20Func.getSymbol(token.address),
            state.web3.erc20Func.getDecimals(token.address),
          ]);
          Vue.set(state.tokenList, web3Utils.toChecksumAddress(token.address), {
            address: web3Utils.toChecksumAddress(token.address),
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
