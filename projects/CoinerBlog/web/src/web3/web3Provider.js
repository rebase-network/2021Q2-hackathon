/*
 * @Author: 33357
 * @Date: 2021-05-15 11:02:36
 * @LastEditTime: 2021-05-15 15:12:25
 * @LastEditors: 33357
 */
import detectEthereumProvider from "@metamask/detect-provider";
import Web3 from "web3";
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
        this.pointPool = new this.web3.eth.Contract(
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
}
