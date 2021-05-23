const express = require('express')
const app = express()

app.get('/', (req, res) => res.send('Hello World!'))
app.get('/joins', (req, res) => {
  getJoins( rows=> {
    //  设置允许跨域访问
    res.set({'Access-Control-Allow-Origin': '*'})
    .send(rows)
  });
})

app.listen(3000, () => console.log('Start Server, listening on port 3000!'))


// 引入web库
var Web3 = require('web3');
// 使用WebSocket协议 连接节点
let web3 = new Web3(new Web3.providers.WebsocketProvider('ws://localhost:7545'));


var mysql  = require('mysql');


function insertJoins(address, price, tx, blockNo) {
  // 连接数据库
  var connection = getConn();

  connection.connect();

   // 构建插入语句
  const query = `INSERT into joins (
        address,
        price,
        tx,
        block_no,
        created_at
    ) Values (?,?,?,?,NOW())`;
  const params = [address, price, tx, blockNo];  

  // 执行插入操作
  connection.query(query, params, function (error, results) {
    if (error) throw error;
    // console.log('results=> ' + results);
  });

  connection.end();
}

function getConn() {
  return mysql.createConnection({
    host     : 'localhost',
    user     : 'root',
    password : 'root',
    port     : '8889',
    database : 'crowdfund'
  });
}

// 通过一个回调函数把结果返回出去
function getJoins(callback) {
  // 获取数据库链接
  var connection = getConn();
  connection.connect();

  // 查询 SQL
  const query = `SELECT address, price from joins`;
  const params = [];

  // 查询数据库
  connection.query(query, params, (err, rows)=>{
      if(err){
          return callback(err);
      }
      console.log(`result=>`, rows);        
      callback(rows);
  });

  connection.end();
}

// 获取合约实例
var Crowdfunding = require('../build/contracts/Crowdfunding.json');
const crowdFund = new web3.eth.Contract(
  Crowdfunding.abi,
  Crowdfunding.networks[5777].address
);



//  监听Join 加速事件
crowdFund.events.Join(function(error, event) {
  if (error) {
    console.log(error);
  }

  // 打印出交易hash 及区块号
  console.log("交易hash:" + event.transactionHash);
  console.log("区块高度:" + event.blockNumber);

  //   获得监听到的数据：
  console.log("参与地址:" + event.returnValues.user);
  console.log("参与金额:" + event.returnValues.price);

  insertJoins(event.returnValues.user,
      // 把以wei为单位的价格转为ether单位
    web3.utils.fromWei(event.returnValues.price),
    event.transactionHash,
    event.blockNumber )

});


