// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./interfaces/IBlogDB.sol";
import "./interfaces/IService.sol";
import "./libraries/AcceptedCaller.sol";

contract Service is IService, AcceptedCaller {
    address public blogDBAddr;

    constructor (address _blogDBAddr,address _RouterAddr){
        blogDBAddr=_blogDBAddr;
        super.acceptCaller(_RouterAddr);
    }

    function sendBlog(
        address _person,
        address _group,
        string memory _content,
        uint256 _typeNumber
    ) public override onlyAcceptedCaller(msg.sender) returns (bool,uint256) {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        (, uint256 blogId) =
            blogDB.createBlog(
                _person,
                _group,
                _content,
                _typeNumber
            );
        blogDB.addGroupBlogId(_person,_group, blogId);
        blogDB.addPersonBlogId(_group,_person, blogId);
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