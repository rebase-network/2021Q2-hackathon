// SPDX-License-Identifier: MIT
pragma solidity ^0.6.12;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "@openzeppelin/contracts/utils/Counters.sol";

contract AnkiAwardItem is Ownable, ERC721 {
    using Counters for Counters.Counter;
    Counters.Counter private _tokenIds;

    struct ItemInfo {
        uint256 level;
    }

    mapping (uint256 => ItemInfo) _itemInfos;

    constructor() public ERC721("AnkiAwardItem", "AKI") {}

    function awardItem(address player, string memory tokenURI, uint256 level)
        public onlyOwner
        returns (uint256)
    {
        _tokenIds.increment();

        uint256 newItemId = _tokenIds.current();
        _mint(player, newItemId);
        _setTokenURI(newItemId, tokenURI);
        _itemInfos[newItemId] = ItemInfo(level);

        return newItemId;
    }


    function getLevel(uint256 tokenId) public view returns (uint256) {
        ItemInfo memory info = _itemInfos[tokenId];
        return info.level;
    }

    function transferFrom(address, address, uint256) override public {
        revert("Not allow transfer");
    }
}