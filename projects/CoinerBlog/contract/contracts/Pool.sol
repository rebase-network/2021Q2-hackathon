// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./interfaces/IPool.sol";
import "./libraries/@openzeppelin/contracts/math/SafeMath.sol";
import "./libraries/@openzeppelin/contracts/token/erc20/SafeERC20.sol";
import "./libraries/@openzeppelin/contracts/token/erc20/IERC20.sol";
import "./libraries/AcceptedCaller.sol";

contract Pool is PPool, AcceptedCaller {
    using SafeMath for uint256;
    using SafeERC20 for IERC20;

    struct Person {
        uint256 balance;
        uint256 pointStored;
        uint256 lastUpdateBlock;
    }

    struct Pool {
        uint256 balance;
        uint256 allPoint;
        uint256 lastUpdateBlock;
        mapping(address => Person) persons;
        bool seted;
    }

    uint256 public startBlock;
    bool public paused;

    mapping(address => Pool) pools;

    address[] private _poolAddrs;

    modifier _checkStart() {
        require(block.number >= startBlock, "checkStart: not start!");
        require(paused == false, "checkStart: paused!");
        _;
    }

    constructor() {
        startBlock = block.number + (1 * 24 * 60 * 60) / 3;
    }

    function poolAddrs() public view override returns (address[] memory) {
        return _poolAddrs;
    }

    function getMiner(address _token, address _miner)
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
            pools[_token].miners[_miner].balance,
            pools[_token].miners[_miner].pointStored,
            pools[_token].miners[_miner].lastUpdateBlock
        );
    }

    function getPool(address _token)
        public
        view
        override
        returns (
            uint256,
            uint256,
            uint256,
            bool
        )
    {
        return (
            pools[_token].balance,
            pools[_token].allPoint,
            pools[_token].lastUpdateBlock,
            pools[_token].paused
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
        returns (uint256)
    {
        return
            pools[_token].persons[_person].pointStored.add(
                pools[_token].persons[_person].balance.mul(
                    pools[_token].persons[_person].lastUpdateBlock.sub(
                        block.number
                    )
                )
            );
    }

    modifier _updatePerson(address _token, address _person) {
        if (pools[_token].persons[_person].balance > 0) {
            pools[_token].miners[_person].pointStored = getPersonPoint(
                _token,
                _person
            );
            pools[_token].persons[_person].lastUpdateBlock=block.number;
        }
        _;
    }


    function stakeToken(address _token, uint256 _amount)
        public
        override
        _updatePerson(_token, msg.sender)
        _checkStart
        returns (bool)
    {
        require(_amount > 0);
        IERC20(_token).safeTransferFrom(
            msg.sender,
            address(this),
            _amount
        );
        pools[_token].balance = pools[_token].balance.add(_amount);
        pools[_token].miners[msg.sender].balance = pools[_token].miners[
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
        require(_amount <= pools[_token].miners[msg.sender].balance);
        pools[_token].balance = pools[_token].balance.sub(_amount);
        pools[_token].miners[msg.sender].balance = pools[_token].miners[
            msg.sender
        ]
            .balance
            .sub(_amount);
        IERC20(_token).safeTransfer(msg.sender, _amount);
        emit WithdrawLP(msg.sender, _token, _amount);
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
