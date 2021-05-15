// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

import "./Utils.sol";
import "./Common.sol";

import "@openzeppelin/contracts/math/SafeMath.sol";

/**
 * @title The Three Kingdoms Base Mortgage
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.2
 */

abstract contract BaseMortage is Common, Utils {
    using SafeMath for uint256;

    mapping(string => uint256) private _total;
    mapping(address => mapping(address => uint256[])) private _detail;

    function _calc(string memory _name, address _owner) private view
        returns (uint256) {

        address token = manager.members(_name);
        if (_detail[_owner][token].length <= 0) {
            return 0;
        }

        (uint256 _daily, ) = getRewardsConfig(_name);
        uint256 second = _daily.div(24 hours);

        uint256 mydeposits = _detail[_owner][token][1];
        uint256 deposits = mydeposits.mul(100).div(_total[_name]);

        uint256 duration = block.timestamp - _detail[_owner][token][0];

        return second.mul(deposits).mul(duration).div(100);
    }

    function deposit(string memory _name, uint256 _amount)
        external {
        
        (, uint256 _rTotal) = getRewardsConfig(_name);

        require(
            _rTotal.sub(_total[_name]) > 0,
            "RICH-NFT: The reward is out"
        );

        address token = manager.members(_name);

        require(
            manager.permits(token, "Mortgage"),
            "RICH-NFT: This opertaion no permit"
        );

        claim(_name, msg.sender);

        address to = manager.members("Mortgage");
        transfer20(token, msg.sender, to, _amount);
        
        if (_detail[msg.sender][token].length != 2) {
            _detail[msg.sender][token] = new uint256[](2);
        }

        _total[_name] = _total[_name].add(_amount);
        _detail[msg.sender][token][0] = block.timestamp;

        uint256 old = _detail[msg.sender][token][1];
        _detail[msg.sender][token][1] = old.add(_amount);
    }

    function withdraw(string memory _name) external {
        address token = manager.members(_name);

        uint256 lock = TIMELOCK_FOR_MORTGAGE;
        uint256 depo = _detail[msg.sender][token][0];

        require(
            (depo + lock) - block.timestamp < 0,
            "RICH-NFT: Cannot withdraw back now"
        );

        uint256 amount = _detail[msg.sender][token][1];
        transfer20(token, address(0), msg.sender, amount);
        
        _total[_name] = _total[_name].sub(amount);
        delete _detail[msg.sender][token];
    }

    function claim(string memory _name, address _owner) public
        returns (uint256){
        
        address token = manager.members(_name);
        if (address(this) == manager.members("MttToken")) {
            token = manager.members("MttToken");
        }

        uint256 rewards = _calc(_name, _owner);
        if (rewards <= 0) { return 0; }

        transfer20(token, address(0), _owner, _calc(_name, _owner));
        _detail[_owner][manager.members(_name)][0] = block.timestamp;

        return rewards;
    }

    function getRewards(string memory _name, address _owner)
        external view returns (uint256) {

        return _calc(_name, _owner);
    }

    function getMyValueLock(string memory _name, address _owner)
        external view returns (uint256) {

        address token = manager.members(_name);

        bool is_deposit = _detail[_owner][token].length > 0;
        return is_deposit ? _detail[_owner][token][1] : 0;
    }

    function getTotalValueLock(string memory _name) external view
        returns (uint256) {
        
        return _total[_name];
    }

    function getTokenAPY(string memory _name) external view
        returns (uint256) {

        (, uint256 _rTotal) = getRewardsConfig(_name);
        bool is_zero = (_rTotal != 0 && _total[_name] != 0);

        return is_zero ? _rTotal.div(_total[_name]).mul(365) : 0;
    }

    function getDepositTime(address _owner, string memory _name) external
        view returns (uint256) {

        address token = manager.members(_name);
        if (_detail[_owner][token].length <= 0) { return 0; }
        return _detail[_owner][token][0].add(TIMELOCK_FOR_MORTGAGE);
    }

    function getRewardsTest(string memory _name, address _owner)
        external returns (uint256) {

        _total["TEST"] = 123;
        return _calc(_name, _owner);
    }
}