// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;
pragma experimental ABIEncoderV2;

import "./common/Utils.sol";
import "./common/Common.sol";

import "@openzeppelin/contracts/math/SafeMath.sol";
import "@openzeppelin/contracts/token/ERC721/IERC721.sol";

/**
 * @title The Three Kingdoms Exchange
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.2
 */
contract Exchange is Common, Utils {
    using SafeMath for uint256;

    mapping(uint256 => bool) public _status;
    mapping(address => uint256[]) public _orders;
    mapping(uint256 => TradeEntity) private _trades;

    function _deleteo(uint256[] memory _array, uint256 _value) private
        pure returns (uint256[] memory) {

        if (_array.length <= 0) return new uint256[](1);
        
        bool jump = false;
        uint256[] memory result = new uint256[](_array.length);

        for (uint256 i = 0; i < _array.length; i++) {
            if (_array[i] == _value) {
                jump = true;
                continue;
            }

            if (jump) result[i - 1] = _array[i];
            else result[i] = _array[i];
        }

        return result;
    }

    function _allorders(uint256 _total, uint256 _genre, address _owner) private view returns
        (uint256[][] memory) {

        uint256[][] memory result = new uint256[][](_total);
        
        for (uint256 i = 0; i < _total; i++) {
            result[i] = new uint256[](5);

            if (_genre == 0) {
                result[i][0] = _cardt.tokenOfOwnerByIndex(
                    manager.members("Exchange"), i
                );
            } else {
                result[i][0] = _orders[_owner][i];
            }

            if (_status[result[i][0]] == false) {
                continue;
            }

            if (_trades[result[i][0]].owner != _owner && _genre != 0) {
                continue;
            }

            CardEntity memory ce = _cardt.getCardDetail(
                result[i][0]
            );

            result[i][1] = ce.power;
            result[i][2] = ce.quality;
            result[i][3] = ce.heroId + (ce.quality * 100);
            result[i][4] = _trades[result[i][0]].amount;
        }

        return result;
    }

    function put(uint256 _tokenId, string memory _name, uint256 _amount)
        external {
        
        bool check = msg.sender == _cardt.ownerOf(_tokenId);
        require(check, "RICH-NFT: Token only owner");

        check = manager.permits(manager.members(_name), "Trade");
        require(check, "RICH-NFT: This opertaion no permit");

        IERC721(manager.members("CardToken")).transferFrom(
            msg.sender, manager.members("Exchange"), _tokenId
        );

        _status[_tokenId] = true;
        _orders[msg.sender].push(_tokenId);

        _trades[_tokenId] = TradeEntity({
            owner: msg.sender, token: _name, amount: _amount
        });
    }

    function pull(uint256 _tokenId) external {
        bool check = msg.sender == _trades[_tokenId].owner;
        require(check, "RICH-NFT: Token only owner");

        IERC721(manager.members("CardToken")).transferFrom(
            manager.members("Exchange"), msg.sender, _tokenId
        );

        delete _trades[_tokenId];
        _status[_tokenId] = false;
    }

    function buy(uint256 _tokenId) external {
        address owner = _trades[_tokenId].owner;
        address token = manager.members(_trades[_tokenId].token);
        transfer20(token, msg.sender, owner, _trades[_tokenId].amount);
        
        IERC721(manager.members("CardToken")).transferFrom(
            manager.members("Exchange"), msg.sender, _tokenId
        );

        delete _trades[_tokenId];
        _status[_tokenId] = false;
    }

    function getDetail(uint256 _tokenId) public view
        returns (TradeEntity memory) {

        return _trades[_tokenId];
    }

    function getExchangeList() public view returns
        (uint256[][] memory) {
        
        address exchange = manager.members("Exchange");
        return _allorders(_cardt.balanceOf(exchange), 0, address(0));
    }

    function getOderList(address _owner) public view returns
        (uint256[][] memory) {

        return _allorders(_orders[_owner].length, 1, _owner);
    }

    function getOderListTest(address _owner) public returns
        (uint256[][] memory) {

        _status[0] = false;
        return _allorders(_orders[_owner].length, 1, _owner);
    }
}