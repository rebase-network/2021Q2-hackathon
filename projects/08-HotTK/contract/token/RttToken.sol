// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

import "../authority/Member.sol";

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

/**
 * @title R3T Token - ERC20
 *
 * It's mainly used for pledge to get M3T Token
 * QUANTITIES: 2 MILLION | DECIMALS: 18
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-5-14 | VERSION: v1.0.0
 */
contract RttToken is ERC20, Member {
    constructor(string memory _name, string memory _symbol)
        ERC20(_name, _symbol) {

        uint256 supply  = 2 * 1000000;
        uint256 decimal = (10 ** uint256(decimals()));

        _mint(msg.sender, supply * decimal);
    }
}