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
        mapping(uint256 => uint256) points;
        uint256[] blogIds;
        uint256[] recommendBlogIds;
    }

    mapping(address => Channel) channels;
    mapping(uint256 => Blog) blogs;

    uint256 public day = 24 * 60 * 60;
    uint256 public stepTime = 3 * day;
    uint256 public hotLength;
    uint256[] public hotBlogIds;

    function getHotBlogIds() public view override returns (uint256[] memory) {
        return hotBlogIds;
    }

    function getRecommendBlogIds(
        address _channel,
        uint256 _limit,
        uint256 _start
    ) public view override returns (uint256[] memory, uint256[] memory) {
        uint256 len = channels[_channel].blogIds.length;
        if (len == 0) {
            return (new uint256[](0), new uint256[](0));
        } else {
            require(_start <= len.sub(1), "startIndex over blogIds length");
            uint256[] memory blogIds = new uint256[](_limit);
            uint256[] memory blogIdsRecommendPoint = new uint256[](_limit);
            uint256 start = _start;
            uint256 blogIdsLength = 0;
            uint256 lastTime = block.timestamp - stepTime;
            for (uint256 i = len; i >= 0 && blogIdsLength <= _limit; i--) {
                uint256 blogId = channels[_channel].blogIds[i];
                if (blogs[blogId].createDate < lastTime) {
                    break;
                }
                if (start != 0) {
                    start.sub(1);
                } else {
                    blogIds[blogIdsLength] = blogId;
                    blogIdsRecommendPoint[blogIdsLength] = getRecommendPoint(
                        _channel,
                        blogId
                    );
                    blogIdsLength.add(1);
                }
            }
            return (blogIds, blogIdsRecommendPoint);
        }
    }

    function getHotPoint(uint256 _blogId) public view returns (uint256) {
        uint256 time = block.timestamp.sub(blogs[_blogId].createDate);
        if (time < day) {
            return blogs[_blogId].comments;
        } else if (time < 2 * day) {
            return blogs[_blogId].comments.div(2);
        } else if (time < 3 * day) {
            return blogs[_blogId].comments.div(4);
        } else {
            return blogs[_blogId].comments.div(8);
        }
    }

    function getRecommendPoint(address _channel, uint256 _blogId)
        public
        view
        returns (uint256)
    {
        uint256 time = block.timestamp.sub(blogs[_blogId].createDate);
        if (time < day) {
            return channels[_channel].points[_blogId];
        } else if (time < 2 * day) {
            return channels[_channel].points[_blogId].div(2);
        } else if (time < 3 * day) {
            return channels[_channel].points[_blogId].div(4);
        } else {
            return blogs[_blogId].comments.div(8);
        }
    }

    function updateHot(uint256 blogId) internal returns (bool) {
        // for(uint256 i=hotLength-1;i>=0;i++){
        //     if(getHotPoint(_blogId))
        // }
    }

    function setBlog(
        address _channel,
        uint256 _blogId,
        uint256 _commentBlogId,
        uint256 _point,
        uint256 _createDate
    ) public override returns (bool) {
        channels[_channel].points[_blogId] = _point;
        blogs[_blogId].createDate = _createDate;
        if (_commentBlogId != 0) {
            blogs[_commentBlogId].comments = blogs[_commentBlogId].comments.add(
                1
            );
            updateHot(_commentBlogId);
        }
        channels[_channel].blogIds.push(_blogId);
        return true;
    }
}
