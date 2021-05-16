// SPDX-License-Identifier: MIT
pragma solidity ^0.6.12;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract AnkiToken is Ownable, ERC20 {
    constructor() public ERC20("Anki", "ANKI") {
    }

    function mint(address owner, uint256 v) external onlyOwner {
        _mint(owner, v);
    }

    function burn(address owner, uint256 v) external onlyOwner {
        _burn(owner, v);
    }
}