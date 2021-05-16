// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

interface IPointPool {
    event WithdrawToken(address person, address pool ,uint256 amount);
    event StakeToken(address person, address pool ,uint256 amount);

    function poolAddrs() external view returns (address[] memory);

    function getPerson(address _pool,address _person)
        external
        view
        returns (
            uint256,
            uint256,
            uint256
        );

    function getPool(address _pool)
        external
        view
        returns (
            uint256
        );

    function pause() external returns (bool);

    function start() external returns (bool);
    
    function usePersonPoint(address _pool, address _person) external returns (bool,uint256);

    function getPersonPoint(address _pool,address _person) external view returns (uint256);

    function stakeToken(address _pool,uint256 _amount) external returns (bool);

    function withdrawToken(address _pool,uint256 _amount) external returns (bool);

    function transferAnyERC20Token(address _pool, address _to, uint256 _amount) external returns (bool);
}