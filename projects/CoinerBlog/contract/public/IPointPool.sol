// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

interface IPointPool {
    event WithdrawToken(address person, address token ,uint256 amount);
    event StakeToken(address person, address token ,uint256 amount);

    function poolAddrs() external view returns (address[] memory);

    function getPerson(address _token,address _person)
        external
        view
        returns (
            uint256,
            uint256,
            uint256
        );

    function getPool(address _token)
        external
        view
        returns (
            uint256
        );

    function pause() external returns (bool);

    function start() external returns (bool);
    
    function usePersonPoint(address _token, address _person) external returns (uint256);

    function getPersonPoint(address _token,address _person) external view returns (uint256);

    function stakeToken(address _token,uint256 _amount) external returns (bool);

    function withdrawToken(address _token,uint256 _amount) external returns (bool);

    function transferAnyERC20Token(address _token, address _to, uint256 _amount) external returns (bool);
}