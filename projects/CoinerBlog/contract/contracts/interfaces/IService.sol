// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

interface IService {
    function sendBlog(
        address _person,
        address _group,
        string memory _content,
        uint256 _typeNumber
    ) external returns (bool,uint256);
}