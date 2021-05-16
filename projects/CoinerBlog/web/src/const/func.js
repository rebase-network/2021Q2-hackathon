/*
 * @Author: 33357
 * @Date: 2021-05-15 17:53:31
 * @LastEditTime: 2021-05-16 11:05:24
 * @LastEditors: 33357
 */

import moment from "moment";
import web3 from "web3";

export function show(msg) {
  window.alert(msg);
}

export function formatTime(time) {
  // 大于昨天
  if (moment().add(-1, "days").startOf("day") > time) {
    return moment(time).format("M/D HH:mm");
  }
  // 昨天
  if (moment().startOf("day") > time) {
    return "昨天 " + moment(time).format("HH:mm");
  }
  // 大于五分钟不显示秒
  if (new Date().valueOf() > time + 300000) {
    return moment(time).format("HH:mm");
  }
  return moment(time).format("HH:mm:ss");
}

export function formatName(str) {
  return str.substring(0, 6) + "..." + str.substring(36);
}

export const web3Utils = {
  toChecksumAddress(address) {
    return web3.utils.toChecksumAddress(address);
  },
  ethAddress: "0x0000000000000000000000000000000000000000",
  isEthAddress(str) {
    return web3.utils.isAddress(str);
  },
};

export function formatBalance(balance, decimals, symbol, effNum) {
  return getEffectiveNumber(balance / Math.pow(10, decimals), effNum) + ' ' + symbol;
};

export function getEffectiveNumber(number, effNum) {
  if (number > Math.pow(10, effNum)) {
    return Math.floor(number);
  } else {
    if (number.toString().length > effNum) {
      return number.toFixed(effNum);
    } else {
      return number;
    }
  }
}
