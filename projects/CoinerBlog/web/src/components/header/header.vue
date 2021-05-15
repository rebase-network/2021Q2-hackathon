<!--
 * @Author: 33357
 * @Date: 2021-05-14 14:00:53
 * @LastEditTime: 2021-05-15 15:21:15
 * @LastEditors: 33357
-->
<template>
  <div class="header">
    <div class="user-and-group">
      <p class="user-title txt-cut">{{$store.state.name}}</p>
      <!-- <i class="iconfont icon-down-arrow"></i> -->
    </div>
    <send-window
      v-if="showSendWindow"
      @closeSendWindow="closeSendWindow"
    ></send-window>
    <!-- <div v-show="pageName!=='Home'" class="common-header">
      <h1>{{processPageName}}</h1>
    </div> -->
    <div class="icon-group">
      <a class="iconfont icon-compose" @click="showSendWindow = true"></a>
      <!-- <a class="iconfont icon-search"></a> -->
      <a
        v-show="pageName === 'Home'"
        class="iconfont icon-refresh"
        @click.prevent="updateContent()"
      ></a>
      <!-- <a v-show="pageName==='Message'" class="iconfont icon-msg"></a> -->
      <a v-show="pageName === 'Me'" class="iconfont icon-gear"></a>
    </div>
  </div>
</template>

<script>
import sendWindow from "../../components/sendWindow/sendWindow.vue";
export default {
  // name: "header",
  data() {
    return {
      showSendWindow: false
    };
  },
  components: {
    "send-window": sendWindow
  },
  methods: {
    updateContent() {
      this.$emit("toUpdateWeibo");
    },
    closeSendWindow() {
      // this.allowBgScroll();
      this.showSendWindow = false;
    }
  },
  computed: {
    pageName() {
      return this.$route.name;
    }
  }
};
</script>

<style lang="stylus" scoped>
.header {
  width: 100%;
  height: 44px;
  line-height: 44px;
  color: #333;
  background-color: #fff;
  overflow: hidden;

  .common-header {
    display: inline-block;
    color: #5d5d5d;
    background-image: url('../../../static/img/favicon.png');
    background-repeat: no-repeat;
    background-size: 29px auto;
    background-position: 12px 9px;
    padding-left: 50px;
  }

  .user-and-group {
    max-width: 40%;
    position: relative;
    float: left;
    padding: 0 12px;

    .user-title {
      font-size: 1.125rem;
    }

    .icon-down-arrow {
      font-size: 0.8215rem;
      color: #f07c10;
      position: absolute;
      top: 0;
      right: -8px;
    }
  }

  .icon-group {
    float: right;

    a.iconfont {
      vertical-align: top;
      display: inline-block;
      width: 46px;
      font-size: 22px;
      height: 44px;
      line-height: 44px;
      color: #444;
      text-align: center;

      &:active {
        background-color: #ebebeb;
      }
    }
  }
}
</style>
