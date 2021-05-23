// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

/**
 * @title Contract Owner Only - Modifier
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-5-14 | VERSION: v1.0.0
 */
abstract contract Owner {
    address public owner = msg.sender;
    
    modifier ContractOwnerOnly() {
        require(
            msg.sender == owner,
            "INFO: Contract owner only"
        );
        _;
    }
}