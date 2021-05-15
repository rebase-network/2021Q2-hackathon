pragma solidity ^0.8.0

import "./ERC721Ex.sol";

contract GameItem is ERC721Ex{

    constructor() public ERC721Ex("dAesthetic", "dAes") {}

    function claimPixel(address claimer, uin16 xAxis, uint16 yAxis)
        public
        returns (uint256)
    {
        uint256 tokenId = _mappingPositionToTokenId(xAxis, yAxis);
        _mint(claimer, tokenId);
        return tokenId;
    }
}