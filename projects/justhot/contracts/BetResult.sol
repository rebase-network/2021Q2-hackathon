pragma solidity ^0.6.0;

//import "https://github.com/smartcontractkit/chainlink/evm-contracts/src/v0.6/ChainlinkClient.sol";
import "../chainlink-develop/evm-contracts/src/v0.6/ChainlinkClient.sol";
import "../chainlink-develop/evm-contracts/src/v0.6/vendor/Ownable.sol";

contract BetResult is ChainlinkClient, Ownable {
    address private constant ORACLE =
        0x83dA1beEb89Ffaf56d0B7C50aFB0A66Fb4DF8cB1;
    string private constant JOB_ID = "93547cb3c6784ec08a366be6211caa24";
    uint256 private constant ORACLE_PAYMENT = (1 * LINK) / 10;

    uint256 public currentPrice;

    event RequestEthereumPriceFulfilled(
        bytes32 indexed requestId,
        uint256 indexed price
    );

    constructor() public Ownable() {
        //setPublicChainlinkToken();
    }

    function requestEthereumPrice() public onlyOwner {
        setPublicChainlinkToken();
        Chainlink.Request memory req =
            buildChainlinkRequest(
                stringToBytes32(JOB_ID),
                address(this),
                this.fulfillEthereumPrice.selector
            );
        sendChainlinkRequestTo(ORACLE, req, ORACLE_PAYMENT);
    }

    function fulfillEthereumPrice(bytes32 _requestId, uint256 _price)
        public
        recordChainlinkFulfillment(_requestId)
    {
        setPublicChainlinkToken();
        emit RequestEthereumPriceFulfilled(_requestId, _price);
        currentPrice = _price;
    }

    function withdrawLink() public onlyOwner {
        LinkTokenInterface link = LinkTokenInterface(chainlinkTokenAddress());
        require(
            link.transfer(msg.sender, link.balanceOf(address(this))),
            "Unable to transfer"
        );
    }

    function stringToBytes32(string memory source)
        private
        pure
        returns (bytes32 result)
    {
        bytes memory tempEmptyStringTest = bytes(source);
        if (tempEmptyStringTest.length == 0) {
            return 0x0;
        }

        assembly {
            // solhint-disable-line no-inline-assembly
            result := mload(add(source, 32))
        }
    }

    function AmountOne() public view returns (uint256) {
        return 123;
    }

    // function rand(uint256 _length) public view returns (uint256) {
    //    return uint256(uint256(keccak256(block.timestamp, block.difficulty))%_length);
     
    // }
function rand(uint256 _length) public view returns(uint256) {
    uint256 random = uint256(keccak256(abi.encodePacked(block.difficulty, block.timestamp)));
    return random%_length;
}
    function GetChainLinkResulet() public view returns (uint256) {
        return rand(256);
    }
}
