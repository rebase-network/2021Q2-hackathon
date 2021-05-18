import Web3 from 'web3'
import {
  Toast
} from "mint-ui";
export default {
  async Init(callback, provider) {
    if (window.ethereum) {
      this.provider = window.ethereum;
      try {
        await window.ethereum.enable();
      } catch (error) {
        console.log("User denied account access");
      }
    } else if (window.web3) {
      this.provider = window.web3.currentProvider;
    } else {
      this.provider = new Web3.providers.HttpProvider(
        "http://127.0.0.1:7545"
      );
    }
    const web3 = new Web3(this.provider);
    provider(web3);
    web3.eth.getAccounts().then((accs) => {
      //const account = accs[0];
      callback(accs[0]);
      //sessionStorage.setItem("account", account);
    });
  },

  // 投资授权
  async Approve(addr, value, contract, gasPrice, account, callback) {
    // console.log(addr, value, contract.methods)
    const gas = await contract.methods
      .approve(
        addr,
        value
      )
      .estimateGas({
        from: account
      }).then(res => {
        console.log(res)
      }).catch((err) => {
        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });

    await contract.methods
      //智能合约方法(所需的参数)
      .approve(addr, value).send({ //授权方法
        from: account,
        gas: gas,
        gasPrice: gasPrice
      })
      .then((res) => {
        // console.log('投资授权成功', res)
        callback(res);
      })
      .catch((err) => {
        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });
  },
  async getGenrePercent(contract, callback) { //获取抽卡概率
    await contract.methods.getGenrePercent().call().then(res => {
      callback(res);
    }).catch(err => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
    })
  },
  async buy(Token, contract, gasPrice, account, callback) { //购买卡包方法
    console.log(contract.methods);
    const gas = await contract.methods
      .buy(Token)
      .estimateGas({
        from: account
      }).then(res => {

        console.log(res)
      }).catch(error => {

        // var msg = error.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: error,
          position: "middle",
          duration: 1000,
        });
        throw 500;
      }); //计算gas用量
    await contract.methods.buy(Token).send({
      from: account,
      gas: gas,
      gasPrice: gasPrice
    }).then(res => {

      callback(res);
    }).catch(error => {

      // var msg = error.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: error,
        position: "middle",
        duration: 1000,
      })
      throw 500;
    });
  },
  async getUsrCombatPowers(contract, account, callback) { //获取用户当前战力
    await contract.methods.getUsrCombatPowers(account).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async balanceOf(contract, account, callback) { //获取用户卡片
    await contract.methods.balanceOf(account).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async MttbalanceOf(contract, account, callback) { //获取用户M3T代币
    await contract.methods.balanceOf(account).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getTotalCombatPowers(contract, callback) { //获取全服总战力
    await contract.methods.getTotalCombatPowers().call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async totalSupply(contract, callback) { //获取全服卡牌数量
    await contract.methods.totalSupply().call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async RttbalanceOf(contract, account, callback) { //获取用户RTT数量
    await contract.methods.balanceOf(account).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async calcUsrRewards(contract, account, callback) { //查看奖励方法
    console.log(account)
    await contract.methods.calcUsrRewards(account).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async claim(contract, gasPrice, account, callback) { //首页领取奖励
    var gas = await contract.methods.claim(account).estimateGas({
      from: account
    }).catch((err) => {

      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
    await contract.methods.claim(account).send({
      from: account,
      gas: gas,
      gasPrice: gasPrice
    }).then(res => {

      callback(res)
    }).catch((err) => {

      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message:err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getTokenIds(contract, account, key, callback) { //获取当前用户所以卡片
    await contract.methods.getTokenIds(account, key).call().then(res => {
      callback(res)
    }).catch((err) => {
      console.log(err)
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async upgrate(contract, tokenId, account, gasPrice, callback) { //卡片升级方法
    const gas = await contract.methods
      .upgrate(tokenId)
      .estimateGas({
        from: account
      }).catch((err) => {
        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      }); //计算gas用量
    await contract.methods
      .upgrate(tokenId)
      .send({
        from: account,
        gas: gas,
        gasPrice: gasPrice
      })
      .then((res) => {
        console.log(res);
        callback(res)
      })
      .catch((err) => {
        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });
  },
  async approveMultiple(value, contract, gasPrice, account, callback) { //批量授权
    const gas = await contract.methods
      .approveMultiple(
        value
      )
      .estimateGas({
        from: account
      }).catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });
    await contract.methods
      //智能合约方法(所需的参数)
      .approveMultiple(value).send({ //授权方法
        from: account,
        gas: gas,
        gasPrice: gasPrice
      })
      .then((res) => {
        // console.log('投资授权成功', res)
        callback(res);

      })
      .catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });
  },
  async synthesis(contract, tokenId, account, gasPrice, callback) { //合成卡牌

    const gas = await contract.methods
      .synthesis(tokenId)
      .estimateGas({
        from: account
      }).catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      }); //计算gas用量
    await contract.methods
      .synthesis(tokenId)
      .send({
        from: account,
        gas: gas,
        gasPrice: gasPrice
      })
      .then((res) => {

        console.log(res);
        callback(res)
      })
      .catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message:err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });
  },
  async put(contract, tokenId, account, gasPrice, addr, value, callback) { //交易挂单

    const gas = await contract.methods
      .put(tokenId, addr, value)
      .estimateGas({
        from: account
      }).catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message:err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      }); //计算gas用量
    await contract.methods
      .put(tokenId, addr, value)
      .send({
        from: account,
        gas: gas,
        gasPrice: gasPrice
      })
      .then((res) => {

        console.log(res);
        callback(res)
      })
      .catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message:err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });
  },
  async buyT(Token, contract, gasPrice, account, callback) { //交易购卡购买卡包方法

    const gas = await contract.methods
      .buy(Token)
      .estimateGas({
        from: account
      }).catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      }); //计算gas用量
    // const gas = 2000000
    await contract.methods.buy(Token).send({
      from: account,
      gas: gas,
      gasPrice: gasPrice
    }).then(res => {

      callback(res);
    }).catch((err) => {

      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async pull(contract, tokenId, account, gasPrice, callback) { //取消挂单

    const gas = await contract.methods
      .pull(tokenId)
      .estimateGas({
        from: account
      }).catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      }); //计算gas用量
    await contract.methods
      .pull(tokenId)
      .send({
        from: account,
        gas: gas,
        gasPrice: gasPrice
      })
      .then((res) => {

        console.log(res);
        callback(res)
      })
      .catch((err) => {

        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });
  },
  async getExchangeList(contract, callback) { //获取当前用户所以卡片
    await contract.methods.getExchangeList().call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getOderList(contract, account, callback) { //获取当前用户的挂单卡片
    await contract.methods.getOderList(account).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getTotalTrade(contract, token, callback) { //获取首页交易数据
    await contract.methods.getTotalTrade(token).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getTotalValueLock(contract, callback) { //获取首页tvl
    console.log(123)
    await contract.methods.getTotalValueLock().call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getTotalValueLockE(contract, Token, callback) { //获取首页tvl
    console.log(123)
    await contract.methods.getTotalValueLock(Token).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getValueAPY(contract, Tonken, callback) { //获取首页apy
    await contract.methods.getValueAPY(Tonken).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getValueAPY2(contract, callback) { //获取首页OKTapy
    await contract.methods.getValueAPY().call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getCardGroups(contract, account, callback) { //获取用户组合卡牌
    await contract.methods.getCardGroups(account).call().then(res => {
      callback(res)
      console.log(res)
    }).catch((err) => {
      console.log(err)
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message:err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getGroupPower(contract, account, callback) {
    await contract.methods.getGroupPower(account).call().then(res => {
      callback(res)
      console.log(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getCardTurnoverOfUSDK(contract, callback) { //getCardTurnoverOfUSDK
    await contract.methods.getCardTurnoverOfUSDK().call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getMttTurnoverOfUSDK(contract, callback) { //getCardTurnoverOfUSDK
    await contract.methods.getMttTurnoverOfUSDK().call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getListAPY(contract, Token, callback) { //getCardTurnoverOfUSDK
    await contract.methods.getListAPY(Token).call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getCountDwon(contract, callback) {
    await contract.methods.getCountDwon().call().then(res => {
      callback(res)
    }).catch((err) => {
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async buyM3T(Token, contract, vule, gasPrice, account, callback) { //M3T兑换
    const gas = await contract.methods
      .buy(Token, vule)
      .estimateGas({
        from: account
      }).then(res => {
        console.log(res)
      }).catch(error => {

        //var data = {}
        // data.code = "500"
        // callback(data);
        // var msg = error.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: error,
          position: "middle",
          duration: 1000,
        });
        throw 500;
      }); //计算gas用量
    await contract.methods.buy(Token, vule).send({
      from: account,
      gas: gas,
      gasPrice: gasPrice
    }).then(res => {

      callback(res);
    }).catch(error => {

      // callback(error);
      // var msg = error.message.toString();
      // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
      // console.log(msg)
      Toast({
        message: error,
        position: "middle",
        duration: 1000,
      })
      throw 500;
    });
  },
  async deposit(contract, Token, vule, gasPrice, account, callback) {
    console.log(Token, vule);
    const gas = await contract.methods
      .deposit(Token, vule)
      .estimateGas({
        from: account
      }).then(res => {
        console.log(res)
      }).catch(error => {

        // var data = {}
        // data.code = "500"
        // callback(data);
        // var msg = error.message.toString();
        // msg = JSON.parse(msg.replace('Internal JSON-RPC error.', ''))
        // console.log(msg)
        Toast({
          message: error,
          position: "middle",
          duration: 1000,
        });
        throw 500;
      }); //计算gas用量
    // await M3Tfun.methods
    await contract.methods
      .deposit(Token, vule)
      .send({
        from: account,
        gas: gas,
        gasPrice: gasPrice
      })
      .then((res) => {

        callback(res)
      })
      .catch((err) => {

        // console.log(err)
        // var msg = err.message.toString();
        // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
        // console.log(msg);
        Toast({
          message: err,
          position: "middle",
          duration: 1000,
        });
        throw 500;
        //alert('投资授权失败，稍后再试：', err)
      });
  },
  async getTokenAPY(contract, tonken, callback) {
    contract.methods.getTokenAPY(tonken).call().then(res => {
      callback(res)
    }).catch((err) => {
      // console.log(err)
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
      // console.log(msg);
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getTokenAPY2(contract, callback) {
    contract.methods.getTokenAPY().call().then(res => {
      callback(res)
    }).catch((err) => {
      // console.log(err)
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
      // console.log(msg);
      Toast({
        message:err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getCardTurnover(contract, callback) {
    console.log(contract)
    contract.methods.getCardTurnover().call().then(res => {
      callback(res)
    }).catch((err) => {
      // console.log(err)
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
      // console.log(msg);
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  },
  async getMttTurnover(contract, callback) {
    contract.methods.getMttTurnover().call().then(res => {
      callback(res)
    }).catch((err) => {
      // console.log(err)
      // var msg = err.message.toString();
      // msg = JSON.parse(msg.replace("Internal JSON-RPC error.", ""));
      // console.log(msg);
      Toast({
        message: err,
        position: "middle",
        duration: 1000,
      });
      throw 500;
      //alert('投资授权失败，稍后再试：', err)
    });
  }
}