pragma solidity ^0.8.0

import "./ERC721Ex.sol";
import "./extensions/ICanvas.sol";

contract GameItem is ERC721Ex, ICanvas{

    function claimPixel(address claimer, uin16 xAxis, uint16 yAxis)
        public
        returns (uint256)
    {
        uint256 tokenId = _mappingPositionToTokenId(xAxis, yAxis);
        if (tokenId != address(0)) {
            _mint(claimer, tokenId);
            return tokenId;
        } else {
            return 0;
        }
    }

    function _overflowXAxis(uint16 xAxis_) private returns (bool) {
        if (xAxis_ >= _width) {
            return true;
        } else {
            return false;
        }
    }

    function _overflowYAxis(uint16 yAxis_) private returns (bool) {
        if (yAxis_ >= _height) {
            return true;
        } else {
            return false;
        }
    }

    function _validRGBA(RGBA pixel_) private returns (bool) {
        return pixel_.A <= 10;
    }

    function _mappingPositionToTokenId(uint16 xAxis_, uint16 yAxis_) private returns (uint256) {
        require(!_overflowXAxis(xAxis_), "X axis overflow");
        require(!_overflowYAxis(yAxis), "Y axis overflow");
        return xAxis_ + yAxis_ * _width;
    }

    function setColor(uint16 xAxis_, uint16 yAxis_, uint8 r_, uint8 g_, uint8 b_, uint8 a_) public virtual override {
        uint256 tokenId = _mappingPositionToTokenId(xAxis_, yAxis_);
        _color(tokenId, r_, g_, b_, a_);
        emit Color(_msgSender(), xAxis_, yAxis_, r_, g_, b_, a_);
    }

    function _color(uint256 tokenId_, uint8 r_, uint8 g_, uint8 b_, uint8 a_) internal virtual {
        RGBA pixel = RGBA(r_, g_, b_, a_);
        require(_validRGBA(pixel), "invalid rgba string");
        _canvas[tokenId_] = pixel;
    }

     function getColor(uint16 xAxis_, uint16 yAxis_) external view override returns (uint8, uint8, uint8, uint8) {
        RGBA color = RGBA(255, 255, 255, 10);
        uint256 tokenId = _mappingPositionToTokenId(xAxis_, yAxis_);
        if (_canvas[tokenId] != address(0)){
            color = _canvas[tokenId];
        }
        return (color.R, color.G, color.B, color.A);
    }
}