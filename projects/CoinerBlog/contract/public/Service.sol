// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./IBlogDB.sol";
import "./IService.sol";
import "./AcceptedCaller.sol";

contract Service is IService, AcceptedCaller {
    address public blogDBAddr;

    constructor (address _blogDBAddr,address _routerAddr){
        blogDBAddr=_blogDBAddr;
        super.acceptCaller(_routerAddr);
    }

    function sendBlog(
        address _person,
        address _group,
        uint256 _commentBlogId,
        string memory _content,
        uint256 _typeNumber
    ) public override onlyAcceptedCaller(msg.sender) returns (bool,uint256) {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        (, uint256 blogId) =
            blogDB.createBlog(
                _person,
                _group,
                _commentBlogId,
                _content,
                _typeNumber
            );
        return (true,blogId);
    }

    function isAccepted() public view returns (bool) {
        IAcceptedCaller blogDB = IAcceptedCaller(blogDBAddr);
        if(blogDB.isAcceptedCaller(address(this))==false){
            return false;
        }
        return true;
    }
}