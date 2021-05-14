// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./interfaces/IRecommend.sol";
import "./libraries/@openzeppelin/contracts/math/SafeMath.sol";
import "./libraries/AcceptedCaller.sol";

contract Recommend is IRecommend, AcceptedCaller {
    using SafeMath for uint256;
    struct Blog {
        uint256 comments;
        uint256 createDate;
    }

    struct Channel {
        address token;
        mapping(uint256=>uint256) points;
        uint256[] blogIds;
        uint256[] recommendBlogIds;
    }

    mapping(address => Channel) channels;
    mapping(uint256 => Blog) blogs;
    uint256 [] blogIds;
    uint256 stepTime=3*24*60*60;

    function getHotBlogIds(uint256 _limit, uint256 _start)
        public
        view
        returns (uint256[] memory)
    {
        uint256 len = blogIds.length;
        require(_start <= len.sub(1), "startIndex over blogIds length");
        uint256[] memory arr;
        uint256 limit;
        if (_start.add(_limit) > len.sub(1)) {
            limit = len.sub(_startIndex);
        } else {
            limit = _limit;
        }
        arr = new uint256[](limit);
        for (uint256 i = 0; i < limit; i++) {
            arr[i] = (groups[_group].blogIds[i.add(_startIndex)]);
        }
        return arr;
    }

    function getRecommendBlogIds(
        address _token,
        uint256 _limit,
        uint256 _start
    ) public view returns (uint256[] memory) {
        uint256 len = groups[_group].blogIds.length;
        require(_startIndex <= len.sub(1), "startIndex over blogIds length");
        uint256[] memory arr;
        uint256 limit;
        if (_startIndex.add(_limit) > len.sub(1)) {
            limit = len.sub(_startIndex);
        } else {
            limit = _limit;
        }
        arr = new uint256[](limit);
        for (uint256 i = 0; i < limit; i++) {
            arr[i] = (groups[_group].blogIds[i.add(_startIndex)]);
        }
        return arr;
    }

    function getHotPoint(_blogId) public view returns (uint256) {
        return blogs[_blogId].comments;
    }

    function getRecommendPoint(_token,_blogId) public view returns (uint256) {
        return channels[_token].points[_blogId];
    }

    function setBlog(
        address _token,
        uint256 _blogId,
        uint256 _commentBlogId,
        uint256 _point,
        uint256 _createDate
    ) public returns (bool) {
        channels[_token].points[_blogId] = _point;
        blogs[_blogId].createDate = _createDate;
        if (_commentBlogId != 0) {
            blogs[_commentBlogId].comments = blogs[_commentBlogId].comments.add(
                1
            );
        }
        channels[_token].blogIds.push(_blogId);
        blogIds.push(_blogId);
        return true;
    }
}
