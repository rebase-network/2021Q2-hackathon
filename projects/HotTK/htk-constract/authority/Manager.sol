// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

import "./Owner.sol";

/**
 * @title Authority Of Administrator & Contract
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.2
 */
contract Manager is Owner {
    // member of administrator or contract
    mapping(string => address) public members;
    
    // permits of members
    mapping(address => mapping(string => bool)) public permits;

    /**
     * @notice Set member of administrator or contract
     *
     * @param _name     name of member
     * @param _member   member's address
     *
     * This function is onwer only
     */
    function setMember(string memory _name, address _member)
        external ContractOwnerOnly {
        
        members[_name] = _member;
    }

    /**
     * @notice Set member's permit
     *
     * @param _member   member's address
     * @param _permit   name of permit
     * @param _enable   enable this member's permit
     *
     * This function is onwer only
     */
    function setPermit(address _member, string memory _permit, bool _enable)
        external ContractOwnerOnly {
        
        permits[_member][_permit] = _enable;
    }

    function test(address[] memory members_)public {
        members["Config"] = members_[0];
        members["CardDetail"] = members_[1];
        members["CardShop"] = members_[2];
        members["CardToken"] = members_[3];
        members["Exchange"] = members_[4];
        members["HeroCard"] = members_[5];
        members["Mortgage"] = members_[6];
        members["MttToken"] = members_[7];
        members["OktMortgage"] = members_[8];
        members["RttToken"] = members_[9];
        members["USDK"] = address(0x533367b864D9b9AA59D0DCB6554DF0C89feEF1fF);
        
        permits[members_[0]]["Config"] = true;
        permits[members_[2]]["CardShop"] = true;
        permits[members_[1]]["CardDetail"] = true;
        permits[members_[5]]["HeroCard"] = true;
        permits[members_[6]]["Mortgage"] = true;
        permits[members_[7]]["MttMortgage"] = true;
        permits[address(0x533367b864D9b9AA59D0DCB6554DF0C89feEF1fF)]["BuyCard"] = true;
    }
}