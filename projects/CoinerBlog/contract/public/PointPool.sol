// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./IPointPool.sol";
import "./SafeMath.sol";
import "./SafeERC20.sol";
import "./IERC20.sol";
import "./AcceptedCaller.sol";

contract PointPool is IPointPool, AcceptedCaller {
    using SafeMath for uint256;
    using SafeERC20 for IERC20;

    struct Person {
        uint256 balance;
        uint256 pointStored;
        uint256 lastUpdateBlock;
    }

    struct Pool {
        uint256 balance;
        mapping(address => Person) persons;
        bool seted;
    }

    bool public paused;

    mapping(address => Pool) pools;

    address[] private _poolAddrs;

    modifier _checkStart() {
        require(paused == false, "checkStart: paused!");
        _;
    }

    constructor() {}

    function poolAddrs() public view override returns (address[] memory) {
        return _poolAddrs;
    }

    function getPerson(address _token, address _person)
        public
        view
        override
        returns (
            uint256,
            uint256,
            uint256
        )
    {
        return (
            pools[_token].persons[_person].balance,
            pools[_token].persons[_person].pointStored,
            pools[_token].persons[_person].lastUpdateBlock
        );
    }

    function getPool(address _token)
        public
        view
        override
        returns (
            uint256
        )
    {
        return (
            pools[_token].balance
        );
    }

    function pause() public override onlyOwner returns (bool) {
        paused = true;
        return true;
    }

    function start() public override onlyOwner returns (bool) {
        paused = false;
        return true;
    }

    function getPersonPoint(address _token, address _person)
        public
        view
        override
        returns (uint256)
    {
        return
            pools[_token].persons[_person].pointStored.add(
                pools[_token].persons[_person].balance.mul(
                    block.number.sub(pools[_token].persons[_person].lastUpdateBlock)
                )
            );
    }

    modifier _updatePerson(address _token, address _person) {
        if (pools[_token].persons[_person].balance > 0) {
            pools[_token].persons[_person].pointStored = getPersonPoint(
                _token,
                _person
            );
            pools[_token].persons[_person].lastUpdateBlock = block.number;
        }
        _;
    }

    function usePersonPoint(address _token, address _person)
        public
        override
        _updatePerson(_token, msg.sender)
        _checkStart
        returns (uint256)
    {
        uint256 point = pools[_token].persons[_person].pointStored;
        pools[_token].persons[_person].pointStored = 0;
        return point;
    }

    function stakeToken(address _token, uint256 _amount)
        public
        override
        _updatePerson(_token, msg.sender)
        _checkStart
        returns (bool)
    {
        require(_amount > 0);
        IERC20(_token).safeTransferFrom(msg.sender, address(this), _amount);
        pools[_token].balance = pools[_token].balance.add(_amount);
        pools[_token].persons[msg.sender].balance = pools[_token].persons[
            msg.sender
        ]
            .balance
            .add(_amount);
        emit StakeToken(msg.sender, _token, _amount);
        return true;
    }

    function withdrawToken(address _token, uint256 _amount)
        public
        override
        _updatePerson(_token, msg.sender)
        returns (bool)
    {
        require(_amount <= pools[_token].persons[msg.sender].balance);
        pools[_token].balance = pools[_token].balance.sub(_amount);
        pools[_token].persons[msg.sender].balance = pools[_token].persons[
            msg.sender
        ]
            .balance
            .sub(_amount);
        IERC20(_token).safeTransfer(msg.sender, _amount);
        emit WithdrawToken(msg.sender, _token, _amount);
        return true;
    }

    function transferAnyERC20Token(
        address _token,
        address _to,
        uint256 _amount
    ) public override onlyOwner returns (bool) {
        IERC20(_token).safeTransfer(_to, _amount);
        return true;
    }
}