# 作品编号24 Orama
## 队伍信息
### 队伍名称
The Hermitian Team

### 成员
Nate
Luke
Ares
Sicong
E. Huang

## 接受奖励地址 (用于接收ETH)
0x78890969408f622B0f432c892B20fDE3aaC392C8

## 作品信息

**Orama 是一个结合 AI 和 NFT 的应用场景，支持用户一键生成 NFT 资产。NFT 资产的属性会由内置的人工智能决定。**

**具体操作**  
运行开始时，用户会输入四个属性（从已有的预设属性里选择），用户选择的属性会被输入到决策树（decision tree）里，决策树将生成一个对应的类（class）
将类作为结果输入到一个的神经网络中（MLP，多层感知器）将会得到一个输出，此输出将在线下生成一个位图，作为图形资产。每运行一次根据不同的属性可以生成不同的图像资产，并且可将资产将记录在 NFT 链上。

**结构剖析**  
智能合约有几层，表层是面向用户层，让用户提供四个问题的答案（四个多选题），以此作为决策树 classifier 的输入。  
决策树 classifier 在线下通过了生成的原始数据来训练。原始数据包含四个特征（feature）对应四个多选题的答案，以及一个标记（label），其中标记有6个类（class）。训练逻辑可参见 decision tree 文件夹里示意图。

把训练好的 classifier 的逻辑写入智能合约。最终这个智能合约里面的 decisionTree 函数包含了所有的逻辑，输入为四个 string 作为用户的四个多选题的答案，输出为一个 class
这个 class 会进一步作为神经网络的输入。

里层神经网络为 MLP（多层感知器）通过链下训练 (FP32：32位单精度浮点数）然后在链上进行 inference (quantize 到 int8）输出结果为一个数字串，通过线下 python 脚本生成位图，作为图像资产。

生成的图像资产可写入到 NFT 链上。此处未展示。


作品代码仓库：https://github.com/orama-io/2021Q2-hackathon/tree/main/projects/Orama
