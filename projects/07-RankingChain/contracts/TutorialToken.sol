pragma solidity >=0.4.24 <=0.9.0;
import "zeppelin-solidity/contracts/token/ERC20/StandardToken.sol";   //注意这个路径不需要node_modules,不然找不到
contract TutorialToken is StandardToken {
    string public name="TutorialToken";
    string public symbol="MC";
    uint8 public decimals=2;
    uint public INITIAL_SUPPLY=12000;

    constructor() public{
        totalSupply_=INITIAL_SUPPLY;
        balances[msg.sender]=INITIAL_SUPPLY;
    }


}