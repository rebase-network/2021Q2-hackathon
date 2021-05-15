// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

import "./common/Utils.sol";
import "./common/Common.sol";

import "@openzeppelin/contracts/math/SafeMath.sol";

/**
 * @title The Three Kingdoms Card Shop
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.2
 */
contract CardShop is Common, Utils {
    using SafeMath for uint256;

    bool private _open = true;
    uint256 private _timer = block.timestamp;

    mapping(uint256 => uint256) private _total;
    mapping(address => uint256) private _tokens;
    mapping(uint256 => uint256[]) private _heros;
    
    event BuyDetail(uint256 _tokenId, uint256 _quality, uint256 _hero);

    function calculation(uint256[] memory _arrays) private
        view returns (uint256) {

        uint256 quantities = 0; uint256 total = 0;
        for (uint256 i = 0; i < _arrays.length; i++) {
            quantities = quantities.add(_arrays[i]);
        }

        uint256 _random = random(10000); uint256 result = 0;
        for (uint256 i = 0; i < _arrays.length; i++) {
            if (_arrays[i] <= 0) continue;

            uint256 quality = _arrays[i];
            uint256 percent = quality.mul(10000);
            percent = percent.div(quantities);

            bool start = _random > total;
            bool end = _random <= total + percent;
            if (start && end) result = i;

            total = total + percent;
        }

        return result;
    }

    function setHeors(uint256 _quality, uint256[] memory _value)
        external CheckPermit("Config") {

        _heros[_quality] = _value;

        if (_quality != 100) {
            for (uint256 i = 0; i < _heros[_quality].length; i++) {
                uint256 value = _heros[_quality][i];
                _heros[100][_quality] = _heros[100][_quality].add(value);
            }
        }
    }

    function setOpen(bool _enable) external
        CheckPermit("Config") {
        
        _open = _enable;
    }

    function setCountDown(uint256 _oper, uint256 _second)
        external CheckPermit("Config") {
        
        if (_oper == 0) {
            _timer = _timer.add(_second);
        } else {
            _timer = _timer.sub(_second);
        }
    }

    function setSellAmount(string memory _name, uint256 _amount)
        external CheckPermit("Config") {
        
        _tokens[manager.members(_name)] = _amount;
    }

    function buy(string memory _name) external {
        require(_open, "RICH-NFT: Shop is not open.");

        address token = manager.members(_name);
        uint256 amount = _tokens[token];
        address to = manager.members("CardShop");
        transfer20(token, msg.sender, to, amount);

        uint256[] memory detail = new uint256[](3);
        detail[0] = calculation(_heros[100]);
        detail[1] = calculation(_heros[detail[0]]);
        detail[2] =  generate(detail[0], detail[1], amount);
        emit BuyDetail(detail[2], detail[0], detail[1]);

        _cardt.doMint(msg.sender, detail);
        _total[detail[2]] = block.timestamp;
    }

    function buyTest(string memory _name, uint256 hero) external {
        address token = manager.members(_name);
        uint256 amount = _tokens[token];
        uint256[] memory detail = new uint256[](3);
        detail[0] = hero;
        detail[1] = calculation(_heros[detail[0]]);
        detail[2] =  generate(detail[0], detail[1], amount);
        emit BuyDetail(detail[2], detail[0], detail[1]);

        _cardt.doMint(msg.sender, detail);
        _total[detail[2]] = block.timestamp;
    }

    function getGenrePercent() external view returns
        (uint256[] memory) {

        uint256 quantities = 0;
        uint256[] memory arrays = _heros[100];
        uint256[] memory percents = new uint256[](
            arrays.length
        );

        for (uint256 i = 0; i < arrays.length; i++) {
            quantities = quantities.add(arrays[i]);
        }

        for (uint256 i = 0; i < arrays.length; i++) {
            if (arrays[i] <= 0) continue;

            uint256 item = arrays[i].mul(10000);
            percents[i] = item.div(quantities);
        }

        return percents;
    }

    function getTotalTrade(string memory _name) external view
        returns (uint256, uint256) {

        uint256 supply = _cardt.totalSupply();
        if (supply <= 0) { return (0, 0);}

        address token = manager.members(_name);

        uint256 totalDay = 0;
        uint256 totalAll = _tokens[token].mul(supply);

        for (uint256 i = 0; i < supply; i++) {
            uint256 tokenId = _cardt.tokenByIndex(i);
            uint256 time = _total[tokenId].add(24 hours);
            if (time < block.timestamp) { continue; }
            totalDay = totalDay.add(_tokens[token]);
        }

        return (totalAll, totalDay);
    }

    function getCountDwon() external view returns (uint256) {
        return _timer;
    }
}