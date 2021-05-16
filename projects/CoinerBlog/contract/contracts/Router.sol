// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./libraries/AcceptedCaller.sol";
import "./interfaces/IRouter.sol";
import "./interfaces/IBlogDB.sol";
import "./interfaces/IService.sol";

contract Router is IRouter, AcceptedCaller {
    address public serviceAddr;
    address public blogDBAddr;

    constructor(address _blogDBAddr) {
        blogDBAddr = _blogDBAddr;
    }

    function setBlogServiceAddr(address _serviceAddr)
        public
        onlyOwner
        returns (bool)
    {
        serviceAddr = _serviceAddr;
        return true;
    }

    function sendBlog(
        address _group,
        uint256 _commentBlogId,
        string memory _content,
        uint256 _typeNumber
    ) public override returns (bool) {
        IService service = IService(serviceAddr);
        (, uint256 blogId) =
            service.sendBlog(
                msg.sender,
                _group,
                _commentBlogId,
                _content,
                _typeNumber
            );
        emit SendBlog(msg.sender, _group, blogId);
        return true;
    }

    function groupAddrs() public view override returns (address[] memory) {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.groupAddrs();
    }

    function getGroupBlogIds(address _group)
        public
        view
        override
        returns (uint256[] memory)
    {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getGroupBlogIds(_group);
    }

    function getGroupBlogIdsByLimit(
        address _group,
        uint256 _limit,
        uint256 _startIndex
    ) public view override returns (uint256[] memory) {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getGroupBlogIdsByLimit(_group, _limit, _startIndex);
    }

    function getGroupBlogIdsLength(address _group)
        public
        view
        override
        returns (uint256)
    {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getGroupBlogIdsLength(_group);
    }

    function getGroupPersons(address _group)
        public
        view
        override
        returns (address[] memory)
    {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getGroupPersons(_group);
    }

    function personAddrs() public view override returns (address[] memory) {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.personAddrs();
    }

    function getPersonBlogIds(address _person)
        public
        view
        override
        returns (uint256[] memory)
    {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getPersonBlogIds(_person);
    }

    function getPersonBlogIdsByLimit(
        address _person,
        uint256 _limit,
        uint256 _startIndex
    ) public view override returns (uint256[] memory) {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getPersonBlogIdsByLimit(_person, _limit, _startIndex);
    }

    function getPersonBlogIdsLength(address _person)
        external
        view
        override
        returns (uint256)
    {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getPersonBlogIdsLength(_person);
    }

    function getPersonGroups(address _person)
        public
        view
        override
        returns (address[] memory)
    {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getPersonGroups(_person);
    }

    function blogsLength() public view override returns (uint256) {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.blogsLength();
    }

    function getBlog(uint256 _blogId)
        public
        view
        override
        returns (
            address,
            address,
            string memory,
            uint256,
            uint256[] memory,
            uint256,
            uint256
        )
    {
        IBlogDB blogDB = IBlogDB(blogDBAddr);
        return blogDB.getBlog(_blogId);
    }

    function getHotBlogIds(uint256 _limit, uint256 _startIndex)
        public
        view
        returns (uint256[] memory)
    {}

    function getRecommendBlogIds(
        address _person,
        uint256 _limit,
        uint256 _startIndex
    ) public view returns (uint256[] memory) {}

    function isAccepted() public view returns (bool) {
        IAcceptedCaller service = IAcceptedCaller(serviceAddr);
        if (service.isAcceptedCaller(address(this)) == false) {
            return false;
        }
        IAcceptedCaller blogDB = IAcceptedCaller(blogDBAddr);
        if (blogDB.isAcceptedCaller(address(this)) == false) {
            return false;
        }
        return true;
    }
}
