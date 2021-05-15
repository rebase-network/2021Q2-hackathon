// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

import "./Owner.sol";
import "./Manager.sol";

/**
 * @title Administrator & Contract 's Members
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.2
 */
abstract contract Member is Owner {
    // authority's manager
    Manager public manager;

    /**
     * @notice Check Operator Permit - modifier
     * @param _permit name of permit
     */
    modifier CheckPermit(string memory _permit) {
        require(
            manager.permits(msg.sender, _permit),
            "RICH-NFT: This opertaion no permit"
        );
        _;
    }
    
    /**
     * @notice Set new manager
     * @param _manager address of manager
     *
     * This function is onwer only
     */
    function setManager(address _manager) external
        ContractOwnerOnly {
        
        manager = Manager(_manager);
    }
}