// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./interfaces/IBlogDB.sol";
import "./libraries/@openzeppelin/contracts/math/SafeMath.sol";
import "./libraries/AcceptedCaller.sol";

contract BlogDB is IBlogDB, AcceptedCaller {
    using SafeMath for uint256;

    struct Group {
        uint256[] blogIds;
        address[] persons;
        mapping(address => bool) havePerson;
        bool seted;
    }

    struct Person {
        uint256[] blogIds;
        address[] groups;
        mapping(address => bool) haveGroup;
        bool seted;
    }

    struct Blog {
        address person;
        address group;
        uint256 repostBlogId;
        uint256[] commentBlogIds;
        string content;
        uint256 typeNumber;
        uint256 createDate;
    }

    mapping(address => Group) groups;
    mapping(address => Person) persons;
    mapping(uint256 => Blog) blogs;

    address[] private _groupAddrs;
    address[] private _personAddrs;
    uint256 private _blogsLength = 1;

    function groupAddrs() public view override returns (address[] memory) {
        return _groupAddrs;
    }

    function getGroupBlogIds(address _group)
        public
        view
        override
        returns (uint256[] memory)
    {
        return groups[_group].blogIds;
    }

    function getGroupBlogIdsLength(address _group)
        public
        view
        override
        returns (uint256)
    {
        return groups[_group].blogIds.length;
    }

    function getGroupBlogIdsByLimit(
        address _group,
        uint256 _limit,
        uint256 _startIndex
    ) public view override returns (uint256[] memory) {
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

    function getGroupPersons(address _group)
        public
        view
        override
        returns (address[] memory)
    {
        return groups[_group].persons;
    }

    function personAddrs() public view override returns (address[] memory) {
        return _personAddrs;
    }

    function getPersonBlogIds(address _person)
        public
        view
        override
        returns (uint256[] memory)
    {
        return (persons[_person].blogIds);
    }

    function getPersonBlogIdsLength(address _person)
        public
        view
        override
        returns (uint256)
    {
        return persons[_person].blogIds.length;
    }

    function getPersonBlogIdsByLimit(
        address _person,
        uint256 _limit,
        uint256 _startIndex
    ) public view override returns (uint256[] memory) {
        uint256 len = persons[_person].blogIds.length;
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
            arr[i] = (persons[_person].blogIds[i.add(_startIndex)]);
        }
        return arr;
    }

    function getPersonGroups(address _person)
        public
        view
        override
        returns (address[] memory)
    {
        return persons[_person].groups;
    }

    function blogsLength() public view override returns (uint256) {
        return _blogsLength - 1;
    }

    function createBlog(
        address _person,
        address _group,
        uint256 _commentBlogId,
        string memory _content,
        uint256 _typeNumber
    ) public override onlyAcceptedCaller(msg.sender) returns (bool, uint256,uint256) {
        if (groups[_group].seted == false) {
            groups[_group].seted = true;
            _groupAddrs.push(_group);
        }
        if (groups[_group].havePerson[_person] == false) {
            groups[_group].havePerson[_person] = true;
            groups[_group].persons.push(_person);
        }
        if (groups[_group].seted == false) {
            groups[_group].seted = true;
            _groupAddrs.push(_group);
        }
        if (groups[_group].havePerson[_person] == false) {
            groups[_group].havePerson[_person] = true;
            groups[_group].persons.push(_person);
        }
        uint256 blogId = _blogsLength;
        _blogsLength = _blogsLength.add(1);
        uint256 [] memory commentBlogIds;
        uint256 createDate=block.timestamp;
        blogs[blogId] = Blog(
            _person,
            _group,
            _commentBlogId,
            commentBlogIds,
            _content,
            _typeNumber,
            createDate
        );
        if (_commentBlogId != 0) {
            blogs[blogId].commentBlogIds.push(blogId);
        }
        groups[_group].blogIds.push(blogId);
        persons[_person].blogIds.push(blogId);
        emit BlogCreated(
            blogId,
            _person,
            _group,
            _commentBlogId,
            createDate
        );
        return (true, blogId,createDate);
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
        return (
            blogs[_blogId].person,
            blogs[_blogId].group,
            blogs[_blogId].content,
            blogs[_blogId].repostBlogId,
            blogs[_blogId].commentBlogIds,
            blogs[_blogId].typeNumber,
            blogs[_blogId].createDate
        );
    }
}
