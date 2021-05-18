# 项目描述
为中心化系统链下资产上链，提供提供规范和接口。
# 项目链接
https://github.com/ship-chain

# 团队成员
熊雁翎：负责开发 @Patract前端工程师 | 区块链新人，波卡生态
黄彦华：负责产品运营和市场开发 有三年区块链工作经验，曾任Bytom比原链产品，Mixin 公链商务总监，在此之前是传统金融行业从业者，曾在Houlihan Lokey、Pwc 工作

# 项目背景
5.12 决定参赛
5.14 决定项目

# 要解决的关键问题
1. 资产上链，需证明链下资产属于该链上用户 => SHIP规范、非对称加密、链上铸币交易验证，
2. 链上（状态变化）资产转移，链下需同步 =》SHIP接口
3. 资产承兑，需确认链上用户具有该链下资产 =》SHIP接口
4. 链下资产变化，链上需同步？
5. 灵活自由的数据定义？
6. 远期：跨链动态传递？

# 流程
1. X 网站: 卖书， UserA有书: [bookA, bookB]
2. X 网站: 书资产页面里, 有个按钮：(在链上 分享/转售)。转售：X网站页面JS代码会调用： SHIP 提供的JS函数FuncA。
  FuncA：
  1. 调用X网站后端接口，得到X网站对这个上链信息的签名
  2. 调用MetaMask插件，需要用户输入输入密码，对上链交易签名
3. market.ship.io: NFT市场，浏览上链资源
4. 购买：花费eth，得到NFT
5. X 网站：调用SHIP提供的函数Listen，监听链上交易，如果发现链上资产转移，X网站就需要对链下资产转移。
6. 链上用户:addresB 买了 UserA（addressA）NFT。去X网站兑现资源，X 网站前端会调用SHIP 提供的函数validate，确认addressB拥有该NFT，将资源释放给addressB对应的（新）账号UserB。UserB在X网站使用该资源。











1. Ship提供一个去中心化公共账本
2. X 网站前端调用Ship util提供的函数 CreateToken（resourceKey, origin）。resourceKey：代表该资源的key，根据业务自己实现；origin：Ship会向该域名的特定接口获取签名
    1. CreateToken向origin指向的X网站接口获取 签名 + 公钥 + 上链信息，（后端需要确保调用该接口的用户已登录，防止冒领签名）
    2. Ship util调用浏览器插件，获取链上用户交易签名，将X网站签名过的信息上链。上链信息 + 验证 + 链上用户地址

3. X 网站调用Ship Util提供的监听函数，对链上交易做出响应
3. 用户链上交易
4. 资产承兑时