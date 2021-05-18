import Vue from 'vue'
import VueRouter from 'vue-router'

const home = () => import('../components/home.vue');
const notice = () => import('../components/notice.vue');
const store = () => import('../components/store.vue');
const deposit = () => import('../components/deposit.vue');
const exchange = () => import('../components/exchange.vue');
// const combination = () => import('../components/combination.vue')
const combinationEit = () => import('../components/combinationEit.vue')
const backpack = () => import('../components/backpack.vue')
const cardInfo = () => import('../components/cardInfo.vue')
const updateSd = () => import('../components/updateSuccessed.vue')
const fusing = () => import('../components/fusing.vue')
const fusionEnd = () => import('../components/fusionEnd.vue')
// const business = () => import('../components/business.vue')
// const buyInfo = () => import('../components/buyInfo.vue')
const buyEnd = () => import('../components/buyEnd.vue')
// const sellInfo = () => import('../components/sellInfo.vue')
// const sellEnd = () => import('../components/sellEnd.vue')
const giveUp = () => import('../components/giveUp.vue')
const giveUpInfo = () => import('../components/giveUpInfo.vue')
const editEnd = () => import('../components/editEnd.vue')
Vue.use(VueRouter)

export default new VueRouter({
  routes: [{
      path: '/home',
      component: home,
    },
    {
      path: '/notice',
      component: notice,
    },
    {
      path: '/store',
      component: store,
    },
    {
      path: '/deposit',
      component: deposit,
    },
    {
      path: '/exchange',
      component: exchange,
    },
    {
      path: '/combinationEit',
      component: combinationEit,
    },
    {
      path: '/backpack',
      component: backpack,
    },
    {
      path: '/cardInfo',
      component: cardInfo,
    },
    {
      path: '/updateSd',
      component: updateSd,
    }, {
      path: '/fusing',
      component: fusing,
    },
    {
      path: '/fusionEnd',
      component: fusionEnd,
    },
    {
      path: '/buyEnd',
      component: buyEnd,
    },
    // {
    //   path: '/sellInfo',
    //   component: sellInfo,
    // },
    // {
    //   path: '/sellEnd',
    //   component: sellEnd,
    // },
    {
      path: '/giveUp',
      component: giveUp,
    },
    {
      path: '/giveUpInfo',
      component: giveUpInfo,
    },
    {
      path: '/editEnd',
      component: editEnd,
    },
    {
      path: '/',
      redirect: '/home' //系统默认页
    }
  ]
})