<!--
 * @Author: 33357
 * @Date: 2021-05-14 14:00:53
 * @LastEditTime: 2021-05-16 12:36:12
 * @LastEditors: 33357
-->

<template>
  <div class="comment-window-container">
    <header>
      <div class="header-part left-header">
        <span @click.prevent="closeSendWindow()">×</span>
      </div>
      <div class="middle-header">
        <span>{{ title }}</span>
      </div>
      <div class="header-part right-header">
        <button
          class="btn btn-sm btn-orange"
          @click="send"
        >
          发布
        </button>
      </div>
    </header>
    <div class="comment-main-body input-wrapper">
      <textarea
        name="main-textarea"
        id="main-input-textarea"
        cols="30"
        rows="10"
        :placeholder="'say hello'+'('+formatBalance(points[group],18,'point',12)+')'"
        v-model="content"
      ></textarea>
    </div>
    <footer>
      <ul class="box">
        <li
          v-for="(item, index) in $store.state.tokens"
          :class="{ checked: item == group }"
          :key="index"
          @click="changeList(item)"
        >
          {{ $store.state.tokenList[item].symbol}}
        </li>
      </ul>
    </footer>
  </div>
</template>

<script>
import { show,formatBalance} from "../../const/func";

export default {
  props: {
    commentBlogId: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      title: "微博",
      content: "",
      group: this.$store.state.tokens[0],
      loading: true,
      points:{}
    };
  },
   mounted: async function () {
    try {
      this.$store.state.tokens.forEach(async (address) => {
        const res=await this.$store.state.web3.pointPoolFunc.getPersonPoint(address,this.$store.state.walletAddress)
        this.$set(this.points,address,res)
      });
    } catch (error) {
      console.log(error)
      show(error);
    }
  },
  methods: {
    formatBalance(...args) {
      return formatBalance(...args);
    },
    changeList(address) {
      this.group = address; //this指向app
    },
    closeSendWindow() {
      this.$emit("closeSendWindow");
    },
    async send() {
      try {
        this.loading = true;
        await this.$store.state.web3.routerFunc.sendBlog(
          this.group,
          this.commentBlogId,
          this.content,
          0,
          (args) => {
            if (args.status == "success" || args.status == "error") {
              this.loading = false;
              show(args.message);
              this.$emit("closeSendWindow");
            }
          }
        );
      } catch (error) {
        show(error);
      }
    },
  },
};
</script>

<style scoped lang="stylus">
.comment-window-container {
  background-color: #fff;
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  z-index: 999;
}

header {
  box-sizing: border-box;
  width: 100%;
  height: 48px;
  line-height: 16px;
  padding: 0.65625rem 0.6rem;
  text-align: center;
  display: flex;
  justify-content: space-between;
  align-items: center;

  .header-part {
    flex: 1;
  }

  .left-header {
    font-size: 26px;
    text-align: left;
  }

  .right-header {
    text-align: right;
  }
}

#main-input-textarea {
  border: none;
  outline: none;
  resize: none;
  width: 100%;
  font-size: 1rem;
  margin-top: 0.65625rem;
}

.comment-main-body {
  /* overflow-y: auto; */
  padding: 0 0.6rem;
  -webkit-overflow-scrolling: touch;
}

footer {
  text-align: center;
}
</style>

<style type="text/css">
body {
  margin: 0;
}
ul {
  padding: 0;
  list-style: none;
  margin: 80px 80px;
}
li {
  width: 80px;
  height: 50px;
  display: inline-block;
  border-radius: 8px;
  border: 1px #000 solid;
  text-align: center;
  line-height: 50px;
  cursor: pointer;
  transition: all 0.3s linear;
  margin-left: 5px;
}
li:hover {
  background-color: #0cf;
  color: #fff;
  border: 1px #fff solid;
}
li.checked {
  background-color: #0cf;
  color: #fff;
  border: 1px #fff solid;
}
</style>