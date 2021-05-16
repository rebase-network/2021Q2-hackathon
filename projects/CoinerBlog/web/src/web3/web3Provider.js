/*
 * @Author: 33357
 * @Date: 2021-05-15 11:02:36
 * @LastEditTime: 2021-05-16 11:10:35
 * @LastEditors: 33357
 */
import detectEthereumProvider from "@metamask/detect-provider";
import Web3 from "web3";
import * as ERC20 from "./contracts/erc20";
import * as Router from "./contracts/router";
import * as PointPool from "./contracts/pointPool";
import * as Recommend from "./contracts/recommend";
import * as Setting from "../const/setting";

export class web3Provider {
  async getWeb3() {
    const provider = await detectEthereumProvider();
    if (provider) {
      if (provider !== window.ethereum) {
        throw new Error("请使用支持web3的浏览器打开!");
      } else {
        const ethereum = window.ethereum;
        if (ethereum) {
          ethereum.on("accountsChanged", (accounts) => {
            window.location.reload();
          });
          ethereum.on("chainChanged", (chainId) => {
            window.location.reload();
          });
          try {
            await ethereum.request({ method: "eth_requestAccounts" });
          } catch (error) {
            throw new Error("拒绝连接！");
          }
        }
        this.web3 = new Web3(provider);
        this.router = new this.web3.eth.Contract(Router.abi, Router.address);
        this.pointPool = new this.web3.eth.Contract(
          PointPool.abi,
          PointPool.address
        );
        this.recommend = new this.web3.eth.Contract(
          Recommend.abi,
          Recommend.address
        );
        const res = await this.web3.eth.net.getId();
        if (res !== Setting.NETWORK_ID) {
          throw new Error("不是" + Setting.NETWORK_NAME);
        }
        const accounts = await this.web3.eth.getAccounts();
        this.walletAddress = accounts[0];
        return this.walletAddress;
      }
    } else {
      throw new Error("请使用支持web3的浏览器打开");
    }
  }

  pointPoolFunc = {
    stakeToken: async (_pool, _amount, change) => {
      await this.transaction(
        this.pointPool.methods.stakeToken,
        [_pool, _amount],
        change
      );
    },
    withdrawToken: async (_pool, _amount, change) => {
      await this.transaction(
        this.pointPool.methods.withdrawToken,
        [_pool, _amount],
        change
      );
    },
    getPersonPoint: async (_pool, _person) => {
      const res = await this.autoTry(
        this.pointPool.methods.getPersonPoint,
        [_pool, _person],
        true
      );
      return Number(res);
    },
    getPerson: async (_pool, _person) => {
      const res = await this.autoTry(
        this.pointPool.methods.getPerson,
        [_pool, _person],
        true
      );
      return {
        balance: Number(res[0]),
        pointStored: Number(res[1]),
        lastUpdateBlock: Number(res[2]),
      };
    },
  };

  routerFunc = {
    sendBlog: async (_group, _commentBlogId, _content, _typeNumber, change) => {
      await this.transaction(
        this.router.methods.sendBlog,
        [_group, _commentBlogId, _content, _typeNumber],
        change
      );
    },
    getPersonBlogIds: async (_person) => {
      const res = await this.autoTry(
        this.router.methods.getPersonBlogIds,
        [_person],
        true
      );
      return res;
    },
    getBlog: async (_blogId) => {
      const res = await this.autoTry(
        this.router.methods.getBlog,
        [_blogId],
        true
      );
      return {
        person: res[0],
        group: res[1],
        content: res[2],
        repostBlogId: Number(res[3]),
        commentBlogIds: res[4],
        typeNumber: Number(res[5]),
        createDate: new Date(res[6] * 1000),
      };
    },
    getPersonBlogIdsLength: async (_person) => {
      const res = await this.autoTry(
        this.router.methods.getPersonBlogIdsLength,
        [_person],
        true
      );
      return Number(res);
    },
  };

  recommendFunc = {
    getHotBlogIds: async () => {
      const res = await this.autoTry(
        this.recommend.methods.getHotBlogIds,
        [],
        true
      );
      return res;
    },
    getRecommendBlogIds: async (_channel, _limit, _start) => {
      const res = await this.autoTry(
        this.recommend.methods.getRecommendBlogIds,
        [_channel, _limit, _start],
        true
      );
      return res;
    },
  };

  erc20Func = {
    approve: async (contractAddress, spender, change) => {
      const Erc20Contract = new this.web3.eth.Contract(
        ERC20.abi,
        contractAddress
      );
      await this.transaction(
        Erc20Contract.methods.approve,
        [
          spender,
          "0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
        ],
        change
      );
    },
    getBalance: async (walletAddress, contractAddress) => {
      const Erc20Contract = new this.web3.eth.Contract(
        ERC20.abi,
        contractAddress
      );
      const res = await this.autoTry(
        Erc20Contract.methods.balanceOf,
        [walletAddress],
        true
      );
      return Number(res);
    },

    getName: async (contractAddress) => {
      const Erc20Contract = new this.web3.eth.Contract(
        ERC20.abi,
        contractAddress
      );
      const res = await this.autoTry(Erc20Contract.methods.name, [], true);
      return res;
    },

    getSymbol: async (contractAddress) => {
      const Erc20Contract = new this.web3.eth.Contract(
        ERC20.abi,
        contractAddress
      );
      const res = await this.autoTry(Erc20Contract.methods.symbol, [], true);
      return res;
    },

    getDecimals: async (contractAddress) => {
      const Erc20Contract = new this.web3.eth.Contract(
        ERC20.abi,
        contractAddress
      );
      const res = await this.autoTry(Erc20Contract.methods.decimals, [], true);
      return Number(res);
    },
  };

  async transaction(func, args, change) {
    return new Promise((resolve, reject) => {
      func(...args)
        .send({ from: this.walletAddress })
        .on("transactionHash", function (hash) {
          change({ message: "等待确认", status: "loading2", hash: hash });
        })
        .on("receipt", function (receipt) {
          change({ message: "发送成功", status: "success" });
          resolve({ receipt });
        })
        .on("error", function (error, receipt) {
          change({ message: "发送失败", status: "error" });
          reject(error);
        });
    });
  }

  async autoTry(fun, args, isCall = false, notErr = null) {
    return new Promise(async (resolve, reject) => {
      let err;
      let time = 30;
      let _time = new Date().getTime();
      for (let i = 0; i < time; i++) {
        try {
          let res;
          if (isCall) {
            res = await fun(...args).call();
          } else {
            res = await fun(...args);
          }
          resolve(res);
          break;
        } catch (error) {
          err = error;
          if (notErr) {
            if (JSON.parse(err.message.substring(25)).message == notErr) {
              break;
            }
          }
          if (i === time - 1) {
            console.log("autoTry", args, i, err, new Date().getTime() - _time);
            reject(err);
            break;
          }
          await this.sleep(100);
          console.log("autoTry", args, i, err, new Date().getTime() - _time);
          _time = new Date().getTime();
          continue;
        }
      }
    });
  }

  sleep(time) {
    return new Promise((resolve) => setTimeout(resolve, time));
  }
}
