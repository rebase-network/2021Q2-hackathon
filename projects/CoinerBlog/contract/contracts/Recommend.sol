// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./interfaces/IRecommend.sol";
import "./libraries/@openzeppelin/contracts/math/SafeMath.sol";
import "./libraries/AcceptedCaller.sol";

contract Recommend is IRecommend, AcceptedCaller {
    using SafeMath for uint256;
    struct Blog {
        uint256 point;
        uint256 comments;
        uint256 createDate;
    }

    struct Channel {
        address token;
        uint256[] blogIds;
        uint256[] recommendBlogIds;
    }

    mapping(address => Channel) channels;
    mapping(uint256 => Blog) blogs;
    uint256[] hotBlogIds;

    function getHotBlogIds(uint256 _limit, uint256 _start)
        public
        view
        returns (uint256[] memory)
    {}

    function getRecommendBlogIds(
        address _token,
        uint256 _limit,
        uint256 _start
    ) public view returns (uint256[] memory) {}

    function getHotPoint() public view returns (uint256[] memory) {}

    function getRecommendPoint() public view returns (uint256[] memory) {}

    function setBlog(
        address _token,
        uint256 _blogId,
        uint256 _commentBlogId,
        uint256 _point,
        uint256 _createDate
    ) public returns (bool) {
        blogs[_blogId].point = _point;
        blogs[_blogId].createDate = _createDate;
        if (_commentBlogId != 0) {
            blogs[_commentBlogId].comments = blogs[_commentBlogId].comments.add(
                1
            );
        }
        channels[_token].blogIds.push(_blogId);
        return true;
    }
}
