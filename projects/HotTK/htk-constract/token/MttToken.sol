// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

import "../common/BaseMortage.sol";

import "@openzeppelin/contracts/math/SafeMath.sol";
import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

/**
 * @title M3T Token - ERC20
 *
 * It's mainly used for upgrate card's combat power
 * QUANTITIES: 200 MILLION | DECIMALS: 18
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.2
 */
contract MttToken is ERC20, BaseMortage {
    using SafeMath for uint256;

    mapping(address => uint256) private _tokens;
    mapping(address => uint256[]) private _total;

    constructor(string memory _name, string memory _symbol)
        ERC20(_name, _symbol) {

        uint256 _supply = 2 * 100 * 1000000;
        _mint(msg.sender, _supply * (10 ** uint256(decimals())));
    }

    function setSellAmount(string memory _name, uint256 _amount)
        external CheckPermit("Config") {
        
        _tokens[manager.members(_name)] = _amount;
    }

    function buy(string memory _name, uint256 _amount)
        external {

        address token = manager.members(_name);

        // require(
        //     manager.permits(token, "MttBuy"),
        //     "RICH-NFT: This opertaion no permit"
        // );

        address to = manager.members("MttToken");
        transfer20(token, msg.sender, to, _amount);

        // uint256 mtt = _amount.mul(_tokens[token]);
        uint256 mtt = _amount.mul(500 * 10 ** 8).div(3);
        // uint256 mtt = _amount.mul(500 * 10 ** 8);
        transfer20(to, address(0), msg.sender, mtt);

        _total[token] = new uint256[](2);
        _total[token][1] = block.timestamp;
        _total[token][0] = _total[token][0].add(_amount);
    }

    function getTotalTrade(string memory _name) external view
        returns (uint256) {

        address token = manager.members(_name);
        return _total[token].length > 0 ? _total[token][0] : 0;
    }
}