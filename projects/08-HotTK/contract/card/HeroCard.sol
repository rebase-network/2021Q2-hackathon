// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;
pragma experimental ABIEncoderV2;

import "../common/Utils.sol";
import "../common/Common.sol";

import "@openzeppelin/contracts/math/SafeMath.sol";

/**
 * @title The Three Kingdoms Card Opertaion
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-5-14 | VERSION: v1.0.0
 */
contract HeroCard is Common, Utils {
    using SafeMath for uint256;

    uint256[] public TOTALUPGRATE_FOR_CARD = [
        10000, 10000, 30, 20, 5, 0
    ];
    
    uint256[] public SYNTHESIS_CARD_AMOUNT = [
        0, 0, 0, 2000, 1000, 0
    ];
    
    uint256[] public SYNTHESIS_CARD_NUMBER = [
        10000, 10000, 10000, 3, 5, 10
    ];

    uint256[] public DAILYUPGRATE_FOR_CARD = [
        0, 3, 3, 3, 3, 0
    ];

    // for card token upgrate M3T amount
    uint256 public constant PRICE_FOR_UPGRATE = 10**18 * 500;

    event Synthesis(uint256 _tokenId, uint256 _quality, uint256 _hero);

    function upgrate(uint256 _tokenId) external{
        require(
            msg.sender == _cardt.ownerOf(_tokenId),
            "INFO: Upgrate only owner"
        );

        CardEntity memory ce = _cardt.getCardDetail(_tokenId);
        uint256 total = TOTALUPGRATE_FOR_CARD[ce.quality];

        require(
            total > 0 && ce.times <= total,
            "INFO: Cannot be upgrate"
        );

        address token = manager.members("MttToken");
        address to = manager.members("CardToken");
        transfer20(token, msg.sender, to, PRICE_FOR_UPGRATE);

        _cardt.doUpgrate(_tokenId);
    }

    function synthesis(uint256[] memory _tokenIds) external {
        uint256 synthesisQuality = 0;
        address token = manager.members("MttToken");
        address to = manager.members("CardToken");

        for(uint256 i = 0; i < _tokenIds.length; i++) {
            require(
                msg.sender == _cardt.ownerOf(_tokenIds[i]),
                "INFO: Synthesis only owner"
            );

            CardEntity memory itm = _cardt.getCardDetail(
                _tokenIds[i]
            );

            CardEntity memory nxt = i == 0 ? itm : _cardt.getCardDetail(
                _tokenIds[i - 1]
            );
            
            require(
                itm.quality == nxt.quality,
                "INFO: These cards quality aren't equal"
            );

            uint256 quality = itm.quality;
            synthesisQuality = itm.quality;

            require(
                quality != 0 || quality != 1 || quality != 2,
                "INFO: These cards cannot synthesis"
            );

            if (i == 0) {
                require(
                    _tokenIds.length == SYNTHESIS_CARD_NUMBER[quality],
                    "INFO: Number of card not enough"
                );
            }
        }

        uint256 amount = SYNTHESIS_CARD_AMOUNT[synthesisQuality];
        if (amount > 0) {
            transfer20(token, msg.sender, to, 10**18 * amount);
        }
        
        uint256 tokenId = 0; uint256 _quality = 0; uint256 hero = 0;
        (tokenId, _quality, hero) = _cardt.doSynthesis(_tokenIds, msg.sender);
        Synthesis(tokenId, _quality, hero);
    }
}