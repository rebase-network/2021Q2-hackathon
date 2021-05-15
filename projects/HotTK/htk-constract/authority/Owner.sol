// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

/**
 * @title Contract Owner Only - Modifier
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.2
 */
abstract contract Owner {
    address public owner = msg.sender;
    
    modifier ContractOwnerOnly() {
        require(
            msg.sender == owner,
            "RICH-NFT: Contract owner only"
        );
        _;
    }
}