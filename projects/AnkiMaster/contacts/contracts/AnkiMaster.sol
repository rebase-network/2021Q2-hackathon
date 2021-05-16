// SPDX-License-Identifier: MIT
pragma solidity ^0.6.12;

import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/token/ERC20/IERC20.sol";
import "@openzeppelin/contracts/token/ERC20/SafeERC20.sol";
import "@openzeppelin/contracts/math/SafeMath.sol";

import "./AnkiAwardItem.sol";
import "./AnkiToken.sol";

contract AnkiMaster is Ownable {
    using SafeERC20 for IERC20;
    using SafeERC20 for AnkiToken;
    using SafeMath for uint256;

    event Deposit(address indexed _user, uint _level, uint _value);

    event Withdraw(address indexed _user, uint _amount);

    event InputWordResult(address indexed _user, uint _level, uint _progress, bool success);

    event LevelClear(address indexed _user, uint _level);
    event LevelFail(address indexed _user, uint _level);

    struct WordDictInfo {
        bytes32[] hashs;
    }

    struct UserInfo {
        uint256 curLevel;
        uint256 curProgress;
        uint256 stepShares;
    }

    string public constant baseUri = "https://github.com/nomadalex/2021Q2-hackathon/tree/main/projects/AnkiMaster/nftmetadata/awardlv";

    AnkiAwardItem public awardItemMaster;
    AnkiToken public cToken;
    IERC20 public poolToken;

    uint256 private _awardPoolBalance;

    mapping (address => UserInfo) private _userInfos;

    WordDictInfo[] private _wordDictInfos;

    constructor(address _poolToken) public {
        poolToken = IERC20(_poolToken);
        awardItemMaster = new AnkiAwardItem();
        cToken = new AnkiToken();
    }

    function poolOfNormal() public view returns (uint256) {
        return poolToken.balanceOf(address(this)).sub(_awardPoolBalance);
    }

    function poolOfAward() public view returns (uint256) {
        return _awardPoolBalance;
    }

    function addWordDict(bytes32[] calldata hashs) external onlyOwner {
        _wordDictInfos.push(WordDictInfo(hashs));
    }

    function deposit(uint256 _amount) external {
        UserInfo storage user = _userInfos[msg.sender];
        require(user.curLevel == 0, "Already playing");

        uint256 lastLevel = getLastLevel(msg.sender);
        require(lastLevel < _wordDictInfos.length, "Already max level");

        uint256 shares = 0;
        uint256 supply = cToken.totalSupply();
        if (supply == 0) {
            shares = _amount;
        } else {
            shares = _amount.mul(supply).div(poolOfNormal());
        }
        cToken.mint(address(this), shares);
        poolToken.safeTransferFrom(msg.sender, address(this), _amount);

        user.curLevel = lastLevel + 1;
        user.curProgress = 1;
        user.stepShares = shares.div(_wordDictInfos[user.curLevel-1].hashs.length-1);

        emit Deposit(msg.sender, user.curLevel, _amount);
    }

    function getLastLevel(address user) public view returns (uint256) {
        uint256 len = awardItemMaster.balanceOf(user);
        uint256 max = 0;
        for (uint256 i=0; i<len; i++) {
            uint256 id = awardItemMaster.tokenOfOwnerByIndex(user, i);
            uint256 level = awardItemMaster.getLevel(id);
            if (level > max) {
                max = level;
            }
        }
        return max;
    }

    function withdraw(uint256 _shares) external {
        uint256 supply = cToken.totalSupply();
        cToken.burn(msg.sender, _shares);
        uint256 _amount = _shares.mul(poolOfNormal()).div(supply);
        uint256 _awardShares = _shares.mul(2);
        if (_awardShares > supply) {
            _awardShares = supply;
        }
        uint256 _awardAmount = _awardShares.mul(_awardPoolBalance).div(supply);
        _amount = _amount.add(_awardAmount);
        poolToken.safeTransfer(msg.sender, _amount);
        _awardPoolBalance = _awardPoolBalance - _awardAmount;

        emit Withdraw(msg.sender, _amount);
    }

    function inputWord(string memory word) external returns (bool) {
        UserInfo storage user = _userInfos[msg.sender];
        require(user.curLevel != 0, "Not playing");

        WordDictInfo storage dict = _wordDictInfos[user.curLevel-1];
        bytes32 r = keccak256(abi.encodePacked(dict.hashs[user.curProgress-1], word));
        if (r == dict.hashs[user.curProgress]) {
            emit InputWordResult(msg.sender, user.curLevel, user.curProgress, true);

            user.curProgress = user.curProgress + 1;
            cToken.safeTransfer(msg.sender, user.stepShares);

            if (user.curProgress >= dict.hashs.length) {
                emit LevelClear(msg.sender, user.curLevel);

                awardItemMaster.awardItem(msg.sender, string(abi.encodePacked(baseUri, user.curLevel, ".json")), user.curLevel);
                user.curLevel = 0;
            }
            return true;
        } else {
            emit InputWordResult(msg.sender, user.curLevel, user.curProgress, false);
            emit LevelFail(msg.sender, user.curLevel);

            uint256 rest = dict.hashs.length - user.curProgress;
            uint256 shares = user.stepShares.mul(rest);
            _awardPoolBalance = _awardPoolBalance + shares.mul(poolOfNormal()).div(cToken.totalSupply().mul(2));
            cToken.burn(address(this), shares);
            user.curLevel = 0;
            return false;
        }
    }
}