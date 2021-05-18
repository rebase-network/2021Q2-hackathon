// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;
pragma experimental ABIEncoderV2;

import "../common/Utils.sol";
import "../common/Common.sol";

import "@openzeppelin/contracts/math/SafeMath.sol";

/**
 * @title The Three Kingdoms Card Detail
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-5-14 | VERSION: v1.0.0
 */
contract CardDetail is Common, Utils {
    using SafeMath for uint256;

    uint256[] public TOTALUPGRATE_FOR_CARD = [
        0, 200, 160, 120, 80, 0
    ];

    uint256 public constant DAILY = 10 ** 17 * 1500;

    function _calcpower(
        uint256 _total, uint256 _group, bool _person
    ) private pure returns (uint256) {

        if (_group <= 0) return 0;
        uint256 percent = _total.mul(20).div(100);
        percent = percent.add(percent);

        if (_person == false) return percent;

        percent = _total.mul(30).div(100);
        percent = percent.add(percent);

        return percent;
    }

    function _getGroups(address _owner, uint256 _supply) private view
        returns (uint256[][] memory, uint256, uint256, uint256) {

        uint256 totalCombatPowers = 0;
        uint256 caocao = 0; uint256 liubei = 0;
        uint256[][] memory result = new uint256[][](2);

        result[0] = new uint256[](5);
        result[1] = new uint256[](5);

        for (uint256 i = 0; i < _supply; i++) {
            uint256 tokenId = _cardt.tokenOfOwnerByIndex(
                _owner, i
            );

            CardEntity memory ce = _cardt.getCardDetail(
                tokenId   
            );

            totalCombatPowers = totalCombatPowers.add(
                ce.power
            );

            if (ce.quality == 2 && ce.heroId <= 4) {
                result[0][ce.heroId] = result[0][ce.heroId].add(1);
            }

            if (ce.quality == 3 && ce.heroId <= 4) {
                result[1][ce.heroId] = result[1][ce.heroId].add(1);
            }

            if (ce.quality != 1) continue;

            if (ce.heroId == 0) caocao = caocao.add(1);
            if (ce.heroId == 1) liubei = liubei.add(1);
        }

        return (result, totalCombatPowers, caocao, liubei);
    }

    function calcUsrRewards(address _owner) public view
        returns (uint256) {
        
        uint256 total = getTotalCombatPowers();
        uint256 usr = getUsrCombatPowers(_owner);

        usr = usr == 0 ? 1 : usr;
        total = total == 0 ? 1 : total;
        
        uint256 combatPowers = usr.mul(100).div(total);
        uint256 daily = DAILY.div(24 hours);
        uint256 duration = _cardt.getDuration(_owner);
        if (duration == 0) duration = 0;
        else duration = block.timestamp - duration;

        uint256 rewards = daily.mul(combatPowers).mul(duration);

        if (_cardt.getSpecial(_owner) == false)
            return rewards;
        
        uint256 sdaily = DAILY.div(24 hours);
        sdaily = sdaily.mul(duration);
        uint256 srewards = sdaily.mul(1).div(100);
        srewards = srewards.add(rewards);

        return srewards;
    }

    function claim(address _owner) external returns (uint256) {
        uint256 rewards = calcUsrRewards(_owner);
        if (rewards <= 0) return rewards;

        address token = manager.members("RttToken");
        transfer20(token, address(0), msg.sender, rewards);

        _cardt.setDuration(msg.sender);

        return rewards;
    }

    function getCardGroups(address _owner) public view
        returns (uint256[][] memory, uint256, uint256) {

        uint256 supply = _cardt.balanceOf(_owner);
        uint256 caocao = 0; uint256 liubei = 0;

        uint256[][] memory result = new uint256[][](2);
        (result,,caocao,liubei) = _getGroups(_owner, supply);

        return (result, caocao, liubei);
    }

    function getGroupPower(address _owner) public view
        returns (uint256, uint256) {

        uint256 supply = _cardt.balanceOf(_owner);
        uint256 caocao = 0; uint256 liubei = 0;

        for (uint256 i = 0; i < supply; i++) {
            uint256 tokenId = _cardt.tokenOfOwnerByIndex(
                _owner, i
            );

            CardEntity memory ce = _cardt.getCardDetail(
                tokenId   
            );

            if (ce.quality == 2 && ce.heroId <= 2) {
                caocao = caocao.add(ce.power);
            }

            if (ce.quality == 3 && ce.heroId <= 4) {
                liubei = liubei.add(ce.power);
            }
        }

        return (caocao, liubei);
    }

    function getUsrCombatPowers(address _owner) public view
        returns (uint256) {

        uint256 supply = _cardt.balanceOf(_owner);
        if (supply <= 0) { return 0; }

        uint256 total = 0;
        uint256 caocao = 0; uint256 liubei = 0;
        uint256[][] memory result = new uint256[][](2);

        (result, total, caocao, liubei) = _getGroups(_owner, supply);

        uint256 purpleTotal = 100; uint256 orangeTotal = 100;

        for (uint256 i = 0; i < 5; i++) {
            if (result[0][i] < purpleTotal)
                purpleTotal = result[0][i];

            if (result[1][i] < orangeTotal)
                orangeTotal = result[1][i];
        }
        
        purpleTotal = _calcpower(total, purpleTotal, caocao > 0);
        orangeTotal = _calcpower(total, orangeTotal, liubei > 0);

        total = total.add(purpleTotal);
        total = total.add(orangeTotal);

        return total;
    }

    function getTotalCombatPowers() public view
        returns (uint256) {

        uint256 totalCombatPowers = 0;
        uint256 supply = _cardt.totalSupply();

        for (uint256 i = 0; i < supply; i++) {
            uint256 tokenId = _cardt.tokenByIndex(i);

            CardEntity memory d = _cardt.getCardDetail(
                tokenId
            );

            totalCombatPowers = totalCombatPowers.add(
                d.power
            );
        }

        return totalCombatPowers;
    }
    
    function getTokenIds(address _owner, uint256 _quality) public
        view returns (uint256[][] memory) {

        uint[][] memory cards = new uint256[][](
            _cardt.balanceOf(_owner)
        );

        for (uint256 i = 0; i < cards.length; i++) {
            uint256 tokenId = _cardt.tokenOfOwnerByIndex(
                _owner, i
            );
            
            CardEntity memory detail = _cardt.getCardDetail(
                tokenId
            );

            bool all = uint(_quality) != 1000;
            if (all && uint(detail.quality) != _quality)
                continue;

            cards[i] = new uint256[](7);

            cards[i][0] = tokenId;
            cards[i][1] = detail.power;
            cards[i][3] = detail.quality;
            cards[i][4] = detail.times;
            cards[i][5] = detail.lock;
            cards[i][2] = detail.heroId + (detail.quality * 100);
            cards[i][6] = TOTALUPGRATE_FOR_CARD[detail.quality];
        }

        return cards;
    }

    function getValueAPY() public pure returns (uint256) {
        return 10;
    }
}