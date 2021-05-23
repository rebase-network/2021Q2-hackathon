// SPDX-License-Identifier: MIT

pragma solidity ^0.8.0;

interface ICanvas {

    event Color(address indexed operator, uint16 indexed xAxis, uint16 indexed yAxis, uint8 r, uint8 g, uint8 b, uint8 a);

    /**
     * set color into a position
     */
    function setColor(uint16 xAxis, uint16 yAxis, uint8 r, uint8 g, uint8 b, uint8 a) external;

    /**
     * get color from a position 
     */
    function getColor(uint16 xAxis, uint16 yAxis) external returns(uint8, uint8, uint8, uint8);

    /**
     * claim a token by position 
     */
    function claim(address claimer, uint16 xAxis, uint16 yAxis) external returns (uint256);
}