// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

interface IRecommend {
    function getHotBlogIds() external returns (uint256[] memory);

    function getRecommendBlogIds(
        address _channel,
        uint256 _limit,
        uint256 _start
    ) external returns (uint256[] memory, uint256[] memory);

    function setBlog(
        address _token,
        uint256 _blogId,
        uint256 _commentBlogId,
        uint256 _point,
        uint256 _createTime
    ) external returns (bool);
}