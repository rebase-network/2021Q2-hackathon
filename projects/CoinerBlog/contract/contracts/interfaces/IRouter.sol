// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

interface IRouter {
    event SendBlog(
        address indexed person,
        address indexed group,
        uint256 blogId
    );

    function sendBlog(
        address _group,
        uint256 _commentBlogId,
        string memory _content,
        uint256 _typeNumber
    ) external returns (bool);
    
    function groupAddrs() external view returns (address[] memory);

    function getGroupBlogIds(address _group)
        external
        view
        returns (uint256[] memory);

    function getGroupBlogIdsByLimit(
        address _group,
        uint256 _limit,
        uint256 _startIndex
    ) external view returns (uint256[] memory);
    
    function getGroupBlogIdsLength(address _group)
        external
        view
        returns (uint256);
        
    function getGroupPersons(address _group)
        external
        view
        returns (address[] memory);

    function personAddrs() external view returns (address[] memory);

    function getPersonBlogIds(address _person)
        external
        view
        returns (uint256[] memory);

    function getPersonBlogIdsByLimit(
        address _person,
        uint256 _limit,
        uint256 _startIndex
    ) external view returns (uint256[] memory);

    function getPersonBlogIdsLength(address _person)
        external
        view
        returns (uint256);

    function getPersonGroups(address _person)
        external
        view
        returns (address[] memory);

    function blogsLength()external view returns (uint256);

    function getBlog(uint256 blogId)
        external
        view
        returns (
           address,
            address,
            string memory,
            uint256,
            uint256[] memory,
            uint256,
            uint256
        );
}
