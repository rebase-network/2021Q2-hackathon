// SPDX-License-Identifier: SimPL-2.0

pragma solidity ^0.7.0;
pragma experimental ABIEncoderV2;

import "../common/Utils.sol";
import "../authority/Member.sol";

import "@openzeppelin/contracts/math/SafeMath.sol";
import "@openzeppelin/contracts/token/ERC721/ERC721.sol";

/**
 * @title The Three Kingdoms Hero Card Token (721)
 *
 * @author Bit Lighthouse. Ace
 * AT: 2021-3-28 | VERSION: v1.0.3
 */
contract CardToken is ERC721, Member, Utils {
    using SafeMath for uint256;

    uint256[] public COMBATPOWER_FOR_CARD = [
        0, 20000, 5000, 3000, 1000, 200
    ];

    uint256[] public QUANTITES_FOR_CARD = [
        1, 3, 11, 10, 10, 0
    ];

    uint256 public constant SYNTHESIS_PERCENT = 120;

    bool private _is_send = false;
    mapping(uint256 => CardEntity) private _cards;

    mapping(address => bool) private _is_special;
    mapping(address => uint256) private _duration;

    constructor(string memory _name, string memory _symbol)
        ERC721(_name, _symbol) {

    }

    function setDuration(address _onwer) external
        CheckPermit("CardDetail") {

        _duration[_onwer] = block.timestamp;
    }

    function doMint(address _to, uint256[] memory _detail)
        external CheckPermit("CardShop") {

        _cards[_detail[2]] = CardEntity({
            quality: _detail[0], heroId: _detail[1],
            power: COMBATPOWER_FOR_CARD[_detail[0]],
            times: 0, lock: 0
        });

        _safeMint(_to, _detail[2]);

        if (_duration[_to] == 0) {
            _duration[_to] = block.timestamp;
        }
    }

    function doUpgrate(uint256 _tokenId) external
        CheckPermit("HeroCard") {

        uint256 power = _cards[_tokenId].power;
        power = power.mul(10).div(100);

        _cards[_tokenId].lock = block.timestamp;
        _cards[_tokenId].times = _cards[_tokenId].times.add(1);
        _cards[_tokenId].power = _cards[_tokenId].power.add(power);
    }

    function doSynthesis(uint256[] memory _tokenIds, address _to)
        external CheckPermit("HeroCard")
        returns (uint256, uint256, uint256) {
        
        uint256 total = 0; uint256 tokenId = 0;
        uint256 quality = _cards[_tokenIds[0]].quality;

        for (uint256 i = 0; i < _tokenIds.length; i++) {
            CardEntity memory item = _cards[_tokenIds[i]];

            _burn(_tokenIds[i]);
            delete _cards[_tokenIds[i]];

            total = total.add(item.power);
        }

        quality = quality.sub(1);
        total = total.mul(SYNTHESIS_PERCENT).div(100);
        uint256 hero = random(QUANTITES_FOR_CARD[quality].sub(1));
        tokenId = generate(quality, hero, _tokenIds.length);

        _safeMint(_to, tokenId);

        _cards[tokenId] = CardEntity({
            quality: quality, heroId: hero,
            power: total, times: 0, lock: 0
        });

        return (tokenId, quality, hero);
    }

    function sendWhite(address[] memory _recipients) external
        CheckPermit ("Config") {

        for (uint256 i = 0; i < _recipients.length; i++) {
            uint256 tokenId = generate(6, 0, 0);
            _safeMint(_recipients[i], tokenId);

            _cards[tokenId] = CardEntity({
                quality: 6, heroId: 0,
                power: 200, times: 0, lock: 0
            });
        }
    }

    function sendSpecial(address _recipient) external
        CheckPermit ("Config") {

        require(
            _is_send == false,
            "RICH-NFT: Already send"
        );

        uint256 tokenId = generate(6, 0, 0);
        _safeMint(_recipient, tokenId);

        _cards[tokenId] = CardEntity({
            quality: 0, heroId: 0,
            power: 0, times: 0, lock: 0
        });

        _is_send = true;
        _is_special[_recipient] = true;
    }

    function getSpecial(address _owner) public view
        returns (bool) {

        return _is_special[_owner];
    }

    function approveMultiple(uint256[] memory _tokenIds) public {
        for (uint256 i = 0; i < _tokenIds.length; i++) {
            approve(manager.members("HeroCard"), _tokenIds[i]);
        }
    }

    function getCardDetail(uint256 _tokenId) public view
        returns (CardEntity memory) {

        return _cards[_tokenId];
    }

    function getDuration(address _onwer) public view
        returns (uint256) {

        return _duration[_onwer];
    }
}