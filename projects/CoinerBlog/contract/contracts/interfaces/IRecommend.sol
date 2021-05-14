// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

interface IRecommend {
    function getHotBlogIds() external returns (uint256[] memory);

    function getRecommendBlogIds() external returns (uint256[] memory);

    function setBlog(
        address _token,
        uint256 _blogId,
        uint256 _commentBlogId,
        uint256 _point,
        uint256 _createTime
    ) external returns (bool);
    
}