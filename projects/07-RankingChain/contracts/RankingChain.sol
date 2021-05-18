// SPDX-License-Identifier: GPL-3.0
pragma solidity >=0.7.0 <0.9.0;

contract RankingChain {
    // 1. 链评发起期
    // 链评状态枚举
    enum RankingChainState{
        Initiation,
        Rating,
        Settlement
    }
    RankingChainState State;
    
    // 公共钱包
    address payable publicAccount;

    // 链评发起人信息
    address payable private sponsorAccount;
    uint private bouns = 0;
    
    // 构造函数需传入公共钱包地址, 用户钱包地址, 奖励金金额
    constructor(address payable _sponsorAccount, uint  _Bonus) payable{
        publicAccount = msg.sender;
        sponsorAccount = _sponsorAccount;
        bouns = _Bonus;
        State = RankingChainState.Initiation;
    }

    // 更新链评发起人信息
    function updateSponsorInfo(address payable userAccount, uint newBons) public payable{
        // 状态校验
        require(State == RankingChainState.Initiation, "state error");
        
        // 发起人退款
        if (bouns != 0) {
            sponsorAccount.transfer(bouns);
        }
        sponsorAccount = userAccount;
        bouns = newBons;
    }

    // 结束链评发起期，转移至评分期
    function InitiationEnd() public {
        // 状态转移
        require(State == RankingChainState.Initiation, "state error");
        State = RankingChainState.Rating;
    }

    // 2. 链评评分期
    // 链评参与人信息
    struct ParticipantInfo {
        address payable account;
        uint voting;
        uint score;
        uint boxOffice;
        uint rank;
    }
    ParticipantInfo[] public participantInfo;      // 参与者信息
    uint private totalVotes = 0;            // 总票数
    uint private averageScore;              // 平均分
    uint private averageBoxOffice;          // 平均票房

     // 更新投票信息
    function updateVotingInfo(address payable account, uint voting, uint score, uint boxOffice) public {
        // 状态校验
        require(State == RankingChainState.Rating, "state error");
        
        // 更新参与者信息
        participantInfo.push(ParticipantInfo(account, voting, score, boxOffice, 0));
        totalVotes += voting;
    }
    
    // 获取总奖金池
    function getTotalVotes() public view returns(uint) {
        require(State == RankingChainState.Rating, "state error");
        return totalVotes;
    }
    
    // 获取总参与人数
    function getParticipantNum() public view returns(uint) {
        require(State == RankingChainState.Rating, "state error");
        return participantInfo.length;
    }
    // 计算平均分和票房
    function computeAverage() private {
        uint totalScore;
        uint totalBoxOffice;
        for(uint i = 0; i < participantInfo.length; i++) {
            totalScore += participantInfo[i].score;
            totalBoxOffice += participantInfo[i].boxOffice;
        }
        averageScore = totalScore / participantInfo.length;
        averageBoxOffice = totalBoxOffice / participantInfo.length;
    }
    
    // 结束链评发起期，转移至评分期
    function votingEnd() public {
        // 状态转移
        require(State == RankingChainState.Rating, "state error");
        State = RankingChainState.Settlement;
        
        // 计算结果
        computeAverage();
    }
    
    // 3. 链评结算期
    //mapping(address=>uint) private rank;    // 参与者评分映射
    uint[1001] private rankNum;
    uint public rewardRank;
    uint public rewardNum;
    
    // 获取平均分
    function getAverageScore() public view returns(uint) {
        require(State == RankingChainState.Settlement, "state error");
        return averageScore;
    }
    
    // 获取平均票房
    function getAverageBoxOffice() public view returns(uint) {
        require(State == RankingChainState.Settlement, "state error");
        return averageBoxOffice;
    }
    
    // 参评者打分
    function getRank() private{
        require(State == RankingChainState.Settlement, "state error");
        uint scoreCoefficient;
        uint boxOfficeCoefficient;
        for (uint i = 0; i < participantInfo.length; i++) {
            if (participantInfo[i].score < averageScore) {
                scoreCoefficient = 500 * participantInfo[i].score / averageScore;
            } else {
                scoreCoefficient = 500 * averageScore / participantInfo[i].score;
            }
            if (participantInfo[i].boxOffice < averageBoxOffice) {
                boxOfficeCoefficient = 500 * participantInfo[i].boxOffice / averageBoxOffice;
            } else {
                boxOfficeCoefficient = 500 * averageBoxOffice / participantInfo[i].boxOffice;
            }
            participantInfo[i].rank = (scoreCoefficient + boxOfficeCoefficient);
            rankNum[participantInfo[i].rank]++;
        }
    }
    
    // 计算获奖信息
    struct BonusInfo {
        address payable account;
        uint bonusWeight;
        uint bonus;
    }
    uint totalBonusWeight;
    BonusInfo[] public bonusInfo;      // 获奖信息
    
    function getBonusInfo() private {    
        require(State == RankingChainState.Settlement, "state error");
        
        // 1. 计算排名前30%的RANK分
        rewardNum = participantInfo.length * 3 / 10;
        uint temp = 0;
        for (uint i = 1000; i > 0; i--) {
            temp += rankNum[i];
            if (temp >= rewardNum) {
                rewardRank = i;
                break;
            }
        }
        
        // 2. 将获奖人员信息记录并存放至bonusInfo
        uint tempBonusWeight;
        for (uint i = 0; i < participantInfo.length; i++) {
            if (participantInfo[i].rank < rewardRank) {
                continue;
            } 
            tempBonusWeight = participantInfo[i].rank * participantInfo[i].voting;
            bonusInfo.push(BonusInfo(participantInfo[i].account, tempBonusWeight, 0));
            totalBonusWeight += tempBonusWeight;
        }
        
    }
    
    // 获取获奖人数
    function getRewardNum() public view returns(uint) {    
        require(State == RankingChainState.Settlement, "state error");
        return rewardNum;
    }
    
    function assignBonus() public {
        require(State == RankingChainState.Settlement, "state error");
        getRank();
        getBonusInfo();
        // 奖金发放
        for (uint i = 0; i < bonusInfo.length; i++) {
            bonusInfo[i].bonus = totalVotes * bonusInfo[i].bonusWeight / totalBonusWeight;
            bonusInfo[i].account.transfer(bonusInfo[i].bonus);
        }
    }
}











