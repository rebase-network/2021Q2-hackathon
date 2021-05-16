// SPDX-License-Identifier: MIT
pragma solidity >=0.7.4;

import "./@openzeppelin/contracts/access/Ownable.sol";

interface IAcceptedCaller {
    function acceptCaller(address caller) external returns (bool);

    function refuseCaller(address caller) external returns (bool);

    function isAcceptedCaller(address caller) external view returns (bool);

    function callerAddrs() external view returns (address[] memory);

    function acceptedCaller() external view returns (address[] memory);
}

contract AcceptedCaller is IAcceptedCaller, Ownable {
    event CallerAccepted(address indexed caller);
    event CallerRefused(address indexed caller);

    struct Caller {
        bool accepted;
        bool seted;
    }

    mapping(address => Caller) callers;

    address[] private _callerAddrs;

    modifier onlyAcceptedCaller(address _caller) {
        require(
            callers[_caller].accepted == true,
            "AcceptedCaller: caller is not accepted caller"
        );
        _;
    }

    function callerAddrs() public view override returns (address[] memory) {
        return _callerAddrs;
    }

    function acceptedCaller() public view override returns (address[] memory) {
        uint256 len = 0;
        address[] memory arr = new address[](_callerAddrs.length);
        for (uint256 i = 0; i < _callerAddrs.length; i++) {
            if (callers[_callerAddrs[i]].accepted == true) {
                arr[len] = _callerAddrs[i];
                len++;
            }
        }
        address[] memory _arr = new address[](len);
        for (uint256 i = 0; i < len; i++) {
            _arr[i] = arr[i];
        }
        return _arr;
    }

    function isAcceptedCaller(address _caller)
        public
        view
        override
        returns (bool)
    {
        return callers[_caller].accepted;
    }

    function acceptCaller(address _caller)
        public
        override
        onlyOwner
        returns (bool)
    {
        require(
            callers[_caller].accepted == false,
            "AcceptedCaller: caller is not refused caller"
        );
        callers[_caller].accepted = true;
        if (callers[_caller].seted == false) {
            callers[_caller].seted = true;
            _callerAddrs.push(_caller);
        }
        emit CallerAccepted(_caller);
        return true;
    }

    function refuseCaller(address _caller)
        public
        override
        onlyOwner
        onlyAcceptedCaller(_caller)
        returns (bool)
    {
        callers[_caller].accepted = false;
        emit CallerRefused(_caller);
        return true;
    }
}
