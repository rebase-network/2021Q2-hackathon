// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.8.0;
import "remix_tests.sol"; // this import is automatically injected by Remix.
import "../contracts/0_Initiate.sol";

contract ChainRatingTest {
    ChainRating chainRatingTest;
    function initTest () public {
        chainRatingTest = new ChainRating(0xdD870fA1b7C4700F2BD7f44238821C26f7392148, 100);
        chainRatingTest.InitiationEnd();
    }
    
    uint public totalVotes;
    uint public averageScore;
    uint public boxOfficeCoefficient;
    uint public participantNum;
    
    function votingTest () public {
        chainRatingTest.updateVotingInfo(0x5B38Da6a701c568545dCfcB03FcB875f56beddC4, 10, 60,  80000);
        chainRatingTest.updateVotingInfo(0xAb8483F64d9C6d1EcF9b849Ae677dD3315835cb2, 10, 65,  85000);
        chainRatingTest.updateVotingInfo(0x4B20993Bc481177ec7E8f571ceCaE8A9e22C02db, 10, 70,  90000);
        chainRatingTest.updateVotingInfo(0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB, 10, 75,  95000);
        chainRatingTest.updateVotingInfo(0x617F2E2fD72FD9D5503197092aC168c91465E7f2, 10, 80,  100000);
        chainRatingTest.updateVotingInfo(0x5B38Da6a701c568545dCfcB03FcB875f56beddC4, 10, 80,  100000);
        chainRatingTest.updateVotingInfo(0xAb8483F64d9C6d1EcF9b849Ae677dD3315835cb2, 10, 86,  105000);
        chainRatingTest.updateVotingInfo(0x4B20993Bc481177ec7E8f571ceCaE8A9e22C02db, 10, 90,  110000);
        chainRatingTest.updateVotingInfo(0x78731D3Ca6b7E34aC0F824c42a7cC18A495cabaB, 10, 96,  115000);
        chainRatingTest.updateVotingInfo(0x617F2E2fD72FD9D5503197092aC168c91465E7f2, 10, 100, 120000);
        totalVotes = chainRatingTest.getTotalVotes();
        participantNum = chainRatingTest.getParticipantNum();
        
        chainRatingTest.votingEnd();
        averageScore = chainRatingTest.getAverageScore();
        boxOfficeCoefficient = chainRatingTest.getAverageBoxOffice();
        
    }
    
    uint public rewardRank;
    uint public rewardNum;
    function rankingTest () public {
        chainRatingTest.assignBonus();
    }
    

}


