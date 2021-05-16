// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./interfaces/IBlogDB.sol";
import "./interfaces/IService.sol";
import "./interfaces/IRecommend.sol";
import "./interfaces/IPointPool.sol";
import "./libraries/AcceptedCaller.sol";

contract Service is IService, AcceptedCaller {
    address public blogDBAddr;
    address public pointPoolAddr;
    address public recommendAddr;

    constructor(
        address _blogDBAddr,
        address _pointPoolAddr,
        address _recommendAddr,
        address _routerAddr
    ) {
        blogDBAddr = _blogDBAddr;
        pointPoolAddr = _pointPoolAddr;
        recommendAddr = _recommendAddr;
        super.acceptCaller(_routerAddr);
    }

    function sendBlog(
        address _person,
        address _group,
        uint256 _commentBlogId,
        string memory _content,
        uint256 _typeNumber
    ) public override onlyAcceptedCaller(msg.sender) returns (bool, uint256) {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        (, uint256 blogId, uint256 createDate) =
            blogDB.createBlog(
                _person,
                _group,
                _commentBlogId,
                _content,
                _typeNumber
            );
        IPointPool pointPool = IPointPool(pointPoolAddr);
        (, uint256 point) = pointPool.usePersonPoint(_group, _person);
        IRecommend recommend = IRecommend(recommendAddr);
        recommend.setBlog(_group, blogId, _commentBlogId, point, createDate);
        return (true, blogId);
    }

    function isAccepted() public view returns (bool) {
        IAcceptedCaller blogDB = IAcceptedCaller(blogDBAddr);
        if (blogDB.isAcceptedCaller(address(this)) == false) {
            return false;
        }
        IAcceptedCaller pointPool = IAcceptedCaller(pointPoolAddr);
        if (pointPool.isAcceptedCaller(address(this)) == false) {
            return false;
        }
        IAcceptedCaller recommend = IAcceptedCaller(recommendAddr);
        if (recommend.isAcceptedCaller(address(this)) == false) {
            return false;
        }
        return true;
    }
}
