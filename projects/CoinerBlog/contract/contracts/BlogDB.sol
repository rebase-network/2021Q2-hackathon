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
        string content;
        uint256 repostBlogId;
        uint256[] commentBlogIds;
        uint256 typeNumber;
        uint256 createDate;
    }

    mapping(address => Group) groups;
    mapping(address => Person) persons;
    mapping(uint256 => Blog) blogs;

    address[] private _groupAddrs;
    address[] private _personAddrs;
    uint256 private _blogsLength=1;


    function groupAddrs() public view override returns (address[] memory) {
        return _groupAddrs;
    }

    function addGroupBlogId(
        address _person,
        address _group,
        uint256 _blogId
    ) public override onlyAcceptedCaller(msg.sender) returns (bool) {
        if (groups[_group].seted == false) {
            groups[_group].seted = true;
            _groupAddrs.push(_group);
        }
        if (groups[_group].havePerson[_person] == false) {
            groups[_group].havePerson[_person] = true;
            groups[_group].persons.push(_person);
        }
        groups[_group].blogIds.push(_blogId);
        emit AddGroupBlogId(_group, _blogId);
        return true;
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

    function addPersonBlogId(
        address _group,
        address _person,
        uint256 _blogId
    ) public override onlyAcceptedCaller(msg.sender) returns (bool) {
        if (persons[_person].seted == false) {
            persons[_person].seted = true;
            _personAddrs.push(_person);
        }
        if (persons[_person].haveGroup[_group] == false) {
            persons[_person].haveGroup[_group] = true;
            persons[_person].groups.push(_group);
        }
        persons[_person].blogIds.push(_blogId);
        emit AddPersonBlogId(_person, _blogId);
        return true;
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
        return _blogsLength;
    }

   function createBlog(
        address _person,
        address _group,
        uint256 _commentBlogId,
        string memory _content,
        uint256 _typeNumber
    ) public override onlyAcceptedCaller(msg.sender) returns (bool, uint256) {
        uint256 blogId = _blogsLength;
        _blogsLength = _blogsLength.add(1);
        blogs[blogId] = Blog(
            _person,
            _group,
            _content,
            _typeNumber,
            block.timestamp
        );

        emit BlogCreated(
            blogId,
            _person,
            _group,
            _content,
            _typeNumber,
            block.timestamp
        );
        return (true, blogId);
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
            uint256
        )
    {
        return (
            blogs[_blogId].person,
            blogs[_blogId].group,
            blogs[_blogId].content,
            blogs[_blogId].typeNumber,
            blogs[_blogId].createDate
        );
    }
}
