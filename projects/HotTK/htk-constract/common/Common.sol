// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

import "../token/CardToken.sol";
import "../authority/Member.sol";

import "@openzeppelin/contracts/token/ERC20/IERC20.sol";

/**
 * @title The Three Kingdoms Common
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.2
 */
abstract contract Common is Member {
    CardToken public _cardt;

    mapping(address => uint256[]) private _rewards;

    function setCardToken(string memory _name) external
        CheckPermit("Config") {

        _cardt = CardToken(manager.members(_name));
    }

    function setRewardsConfig(string memory _token, uint256 _daily, uint256 _total)
        external CheckPermit("Config") {

        address token = manager.members(_token);
        _rewards[token] = new uint256[](2);
        _rewards[token][0] = _daily;
        _rewards[token][1] = _total;
    }

    function getRewardsConfig(string memory _token) internal view
        returns (uint256, uint256) {

        address token = manager.members(_token);
        uint256[] memory config = _rewards[token];

        return config.length > 0 ? (config[0], config[1]) : (0, 0);
    }

    function withdrawal(string memory _token, string memory _contract)
        public CheckPermit("Admin") {

        address token = manager.members(_token);
        address admin = manager.members("Admin");
        address contract_ = manager.members(_contract);
        uint256 amount = IERC20(token).balanceOf(contract_);

        IERC20(token).transfer(admin, amount);
    }
}