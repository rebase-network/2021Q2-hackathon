# Anchor

## 介绍

Anchor是Anchor团队开发的链上定制化金融合同开放平台。我们旨在开拓链上的定制化的非标准交易市场，让区块链的使用者无需懂得代码就可以根据需要去定制自己的链上金融合约。

传统金融市场中，定制化金融产品需要强力的机构背书和高成本的运营才可运行。而这样强信用背书的背后是平民百姓所无法触及和参与的万亿金融市场。但通过智能合约，我们可以以极低成本和极低门槛的方式去定制我们所需要的金融产品。

相较于传统的金融产品的资金规模与维护成本，通过智能合约生成的金融合同其成立与运作的成本几乎为零。而且这样的金融产品在运行过程中除了区块链系统底层架构的风险外没有任何其他风险。在您设置好相关参数，存入相关资产，就可以等待着合约内置或者预言机提供的触发条件来完成金融合同，并通过与其他DeFi合约的可组合性让这些金融产品快速推向市场。区块链与智能合约带来的透明、高效、低成本与定制化将会满足更多金融领域的更多特定需求。

## 架构

Anchor的架构本质上是一个买卖双方约定参数和条件的交割合同，其核心技术壁垒是通过合约工厂，为交易双方生成一个开放的智能合约地址，不断生成新的智能合约来实现更复杂的金融逻辑。例如， A 用户在未来某一时间用 X 数量的 aToken 换 B 用户 Y 数量的 bToken 。用户的交易都是在链上完成的，所有数据链上可查。同时在质押标的物等待交割的时间段内，我们也将辅助发行Token来帮助其缓解资金流动性问题。我们可以使用智能合约作为买卖对手方，从而达到更复杂的合约逻辑。

## 项目功能

### 查看订单

用户创建订单，该订单将被保存到工厂合约中，如果用户愿意支付相应的代价，将为其优先展示其订单信息。

### 买家购买

卖家发布过订单后，以订单是否优质，是否合理，是否满足市场需求，是否支付广告费来为其展示订单，此时买家即可看到其订单信息，来决定是否购买卖家的发布内容。

### 成为撮合人

该项目将有赏金猎人的概念，卖家在发布订单时，将选择是否愿意支付相应数量的赏金奖励，如果第三方用户能为卖家撮合这笔订单，使其加速完成，当该订单的剩余量小于百分之五时，该赏金猎人将获得卖家为其支付的奖励，当然，成为赏金猎人时需支付卖家出售物品相应的保证金，订单完成时，赏金以及保证金将为其直接返还。

### 用户创建订单

任何用户都可未自己手中的出售物标定数量，标定价格，并选择自己想的得到的买家出售物，此时卖家仅先将出售物，打入已经生成的子合约中，然后选择是否愿意支付赏金未其订单增加流动性，最后只需付一点创建订单的手续费，将可获得其子合约的拥有权，可操作其订单
查询当前用户所有已经发布的订单：

订单创建者，赏金猎人，买家，将可以在自己的订单列表中查询到自己交互过的订单。

关闭当前订单：

卖家反悔功能，如果卖家觉得这笔订单不太合理时，将有权力关闭这笔订单，但其出售物将只会返回其剩余的百分之五十，创建订单费用不予退还，赏金猎人返还其全部保证金，并拿到卖家设置的赏金数量的百分之五十，其余将全部返回给工厂合约，并销毁该合约。