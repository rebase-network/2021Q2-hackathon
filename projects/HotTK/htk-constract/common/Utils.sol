// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;

import "@openzeppelin/contracts/math/SafeMath.sol";
import "@openzeppelin/contracts/token/ERC20/IERC20.sol";

/**
 * @title The Three Kingdoms Utils
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-5-14 | VERSION: v1.0.2
 */
abstract contract Utils {
    using SafeMath for uint256;

    uint256 public constant TIMELOCK_FOR_MORTGAGE = 7 days;

    struct CardEntity {
        uint256 quality; uint256 heroId;
        uint256 power; uint256 times; uint256 lock;
    }
    
    struct TradeEntity {
        address owner; string token; uint256 amount;
    }

    /**
     * @notice To do bit operation for create card token id
     *
     * @param _quality quality of card
     * @param _hero    hero id for card
     * @param _amount  sell amount for card
     *
     * @return a 256 token id
     */
    function generate(uint256 _quality, uint256 _hero, uint256 _amount)
        internal view returns (uint256) {

        uint256 heroTokenId = 1 << 255;

        heroTokenId = heroTokenId | uint256(uint16(_quality)) << 239;
        heroTokenId = heroTokenId | uint256(uint64(_hero)) << 175;
        heroTokenId = heroTokenId | uint256(uint64(_amount)) << 111;
        heroTokenId = heroTokenId | uint256(block.timestamp) << 40;

        return heroTokenId;
    }

    /**
     * @notice Get a 256 random number
     *
     * @param _value - range of random
     * @return - random number
     */
    function random(uint256 _value) internal view returns (uint256) {
        bytes memory seed = abi.encodePacked(block.timestamp);
        uint256 result = uint256(keccak256(seed)) % _value;
        return result == 0 ? _value : result;
    }

    /**
     * @notice Common ERC20 transfer method
     *
     * @param _token  ERC20 token contract address
     * @param _from   transfer from address
     * @param _to     transfer to address
     * @param _amount transfer amount value
     */
    function transfer20(
        address _token, address _from, address _to, uint256 _amount
    ) internal returns (bool) {
        
        require(_amount > 0, "RICH-NFT: Wrong transaction amount");

        bool is_transfer = false;

        if(_from == address(0)) {
            is_transfer = IERC20(_token).transfer(_to, _amount);
        } else {
            is_transfer = IERC20(_token).transferFrom(_from, _to, _amount);
        }

        require(is_transfer, "RICH-NFT: Transfer is faild");
    }

    /**
     * @notice Check string
     *
     * @param a string a
     * @param b string b

     * @return - is equal
     */
    function hashCompareInternal(string memory a, string memory b)
        internal pure returns (bool) {
        
        return keccak256(abi.encodePacked(a)) == keccak256(abi.encodePacked(b));
    }
}
