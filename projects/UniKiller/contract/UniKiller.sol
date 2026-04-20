// SPDX-License-Identifier: GPL-2.0-or-later
pragma solidity 0.6.6;
pragma experimental ABIEncoderV2;
// written by 少平

interface ERC20 {
    function name() external pure returns (string memory);
    function symbol() external pure returns (string memory);
    function decimals() external pure returns (uint8);
    function totalSupply() external view returns (uint);
    function balanceOf(address owner) external view returns (uint);
    function allowance(address owner, address spender) external view returns (uint);
}

// helper methods for interacting with ERC20 tokens and sending ETH that do not consistently return true/false
library TransferHelper {
    function safeApprove(address token, address to, uint value) internal {
        // bytes4(keccak256(bytes('approve(address,uint256)')));
        (bool success, bytes memory data) = token.call(abi.encodeWithSelector(0x095ea7b3, to, value));
        require(success && (data.length == 0 || abi.decode(data, (bool))), 'OB TransferHelper: APPROVE_FAILED');
    }

    function safeTransfer(address token, address to, uint value) internal {
        // bytes4(keccak256(bytes('transfer(address,uint256)')));
        (bool success, bytes memory data) = token.call(abi.encodeWithSelector(0xa9059cbb, to, value));
        require(success && (data.length == 0 || abi.decode(data, (bool))), 'OB TransferHelper: TRANSFER_FAILED');
    }

    function safeTransferFrom(address token, address from, address to, uint value) internal {
        // bytes4(keccak256(bytes('transferFrom(address,address,uint256)')));
        (bool success, bytes memory data) = token.call(abi.encodeWithSelector(0x23b872dd, from, to, value));
        require(success && (data.length == 0 || abi.decode(data, (bool))), 'OB TransferHelper: TRANSFER_FROM_FAILED');
    }

    function safeTransferETH(address to, uint value) internal {
        (bool success,) = to.call{value:value}(new bytes(0));
        require(success, 'OB TransferHelper: ETH_TRANSFER_FAILED');
    }
}

// a library for performing overflow-safe math, courtesy of DappHub (https://github.com/dapphub/ds-math)
library SafeMath {
    function add(uint x, uint y) internal pure returns (uint z) {
        require((z = x + y) >= x, 'ds-math-add-overflow');
    }

    function sub(uint x, uint y) internal pure returns (uint z) {
        require((z = x - y) <= x, 'ds-math-sub-underflow');
    }

    function mul(uint x, uint y) internal pure returns (uint z) {
        require(y == 0 || (z = x * y) / y == x, 'ds-math-mul-overflow');
    }
}

interface IUniKillerCallee {
    function uniKillerCall(address tokenIn, address tokenOut, uint amountIn, uint amountOut, bytes calldata data) external;
    function loanCall(uint amount, bytes calldata data) external;
}

contract LPToken {

    string public name     = "UniKiller Liquidity Token";
    string public symbol   = "UKT";
    uint8  public decimals = 6;
    uint  public totalSupply;
    mapping(address => uint) public balanceOf;

}

contract Utils {

    uint  constant internal PRECISION = (10**18);
    uint  constant internal MAX_QTY   = (10**28); // 10B tokens
    uint  constant internal MAX_RATE  = (PRECISION * 10**6); // up to 1M tokens per ETH
    uint  constant internal MAX_DECIMALS = 18;
    uint  constant internal ETH_DECIMALS = 18;

    function getDecimals(address token) internal pure returns (uint) {
        return ERC20(token).decimals();
    }

    function calcDstQty(uint srcQty, uint srcDecimals, uint dstDecimals, uint rate) internal pure returns(uint) {
        require(srcQty <= MAX_QTY);
        require(rate <= MAX_RATE);

        if (dstDecimals >= srcDecimals) {
            require((dstDecimals - srcDecimals) <= MAX_DECIMALS);
            return (srcQty * rate * (10**(dstDecimals - srcDecimals))) / PRECISION;
        } else {
            require((srcDecimals - dstDecimals) <= MAX_DECIMALS);
            return (srcQty * rate) / (PRECISION * (10**(srcDecimals - dstDecimals)));
        }
    }
    
    function calcSrcQty(
        uint256 dstQty,
        uint256 srcDecimals,
        uint256 dstDecimals,
        uint256 rate
    ) internal pure returns (uint256) {
        require(dstQty <= MAX_QTY, "dstQty > MAX_QTY");
        require(rate <= MAX_RATE, "rate > MAX_RATE");

        //source quantity is rounded up. to avoid dest quantity being too low.
        uint256 numerator;
        uint256 denominator;
        if (srcDecimals >= dstDecimals) {
            require((srcDecimals - dstDecimals) <= MAX_DECIMALS, "src - dst > MAX_DECIMALS");
            numerator = (PRECISION * dstQty * (10**(srcDecimals - dstDecimals)));
            denominator = rate;
        } else {
            require((dstDecimals - srcDecimals) <= MAX_DECIMALS, "dst - src > MAX_DECIMALS");
            numerator = (PRECISION * dstQty);
            denominator = (rate * (10**(dstDecimals - srcDecimals)));
        }
        return (numerator + denominator - 1) / denominator; //avoid rounding down errors
    }
    
    function calcRateFromQty(
        uint256 srcAmount,
        uint256 destAmount,
        uint256 srcDecimals,
        uint256 dstDecimals
    ) internal pure returns (uint256) {
        require(srcAmount <= MAX_QTY, "srcAmount > MAX_QTY");
        require(destAmount <= MAX_QTY, "destAmount > MAX_QTY");

        if (dstDecimals >= srcDecimals) {
            require((dstDecimals - srcDecimals) <= MAX_DECIMALS, "dst - src > MAX_DECIMALS");
            return ((destAmount * PRECISION) / ((10**(dstDecimals - srcDecimals)) * srcAmount));
        } else {
            require((srcDecimals - dstDecimals) <= MAX_DECIMALS, "src - dst > MAX_DECIMALS");
            return ((destAmount * PRECISION * (10**(srcDecimals - dstDecimals))) / srcAmount);
        }
    }

    function minOf(uint256 x, uint256 y) internal pure returns (uint256) {
        return x > y ? y : x;
    }

}

contract PermissionGroups{
    
    address public admin;
    address public pendingAdmin;

    constructor() public {
        admin = msg.sender;
    }

    modifier onlyAdmin() {
        require(msg.sender == admin,"onlyAdmin");
        _;
    }

    function transferAdmin(address newAdmin) external onlyAdmin {
        pendingAdmin = newAdmin;
    }

    function claimAdmin() external {
        require(pendingAdmin == msg.sender,"pendingAdmin != msg.sender");
        admin = pendingAdmin;
        pendingAdmin = address(0);
    }

}

contract TokenManagement is PermissionGroups, Utils {
    
    //  baseToken weight is fixed as 100, use USDT as baseToken 
    address public baseToken;
    // address public assetPoolAddress = address(this);

    struct TokenData {
        bool listed;  // was added 
        bool enabled; // whether trade is enabled
        uint index;  // index in tokenList
        uint weight;  // the token weight
        uint baseRate;
    }
    
    address[] internal listedTokens;
    mapping(address=>TokenData) public tokenData;

    function addTokens(address[] calldata _addingTokens) external onlyAdmin {
        for(uint i=0; i<_addingTokens.length; i++) {
            addToken(_addingTokens[i]);
        }
    }

    function addToken(address token) internal {
        require(!tokenData[token].listed,"token listed");
        tokenData[token].listed = true;
        tokenData[token].enabled = true;
        tokenData[token].index = listedTokens.length;
        tokenData[token].weight = 100;
        tokenData[token].baseRate = 1;
        listedTokens.push(token);
    }
        
    function removeTokens(address[] calldata _removingTokens) external onlyAdmin {
        for(uint i=0; i<_removingTokens.length; i++) {
            removeToken(_removingTokens[i]);
        }
    }
    
    function removeToken(address token) internal {
        require(listedTokens.length > 0,"listedTokens.length <= 0");
        require(tokenData[token].listed,"token not listed");
        tokenData[token].listed = false;
        tokenData[token].enabled = false;
        tokenData[token].baseRate = 0;
        
        address lastToken = listedTokens[listedTokens.length -1];
        if(token == lastToken) {
            listedTokens.pop();
        }
        else {
            tokenData[lastToken].index = tokenData[token].index;            
            listedTokens[tokenData[token].index] = lastToken;
            listedTokens.pop();
        }
    }
    
    function getListedTokens() public view returns(address[] memory) {
        return listedTokens;
    }
    
    function updateTokensWeight(address[] calldata tokens, uint[] calldata weightList) external onlyAdmin
    {
        require(tokens.length == weightList.length,"tokens.length != weightList.length");
        for (uint ind = 0; ind < tokens.length; ind++) {
            require(tokenData[tokens[ind]].listed,"token not listed");
            tokenData[tokens[ind]].weight = weightList[ind];
        }
    }
    
    function feedPrice(address[] calldata tokens, uint[] calldata _baseRate) external onlyAdmin
    {
        require(tokens.length == _baseRate.length,"tokens.length != _baseRate.length");
        for (uint ind = 0; ind < tokens.length; ind++) {
            require(tokenData[tokens[ind]].listed,"token not listed");
            tokenData[tokens[ind]].baseRate = _baseRate[ind];
        }
    }
    
    function getTokenQty(address token, uint srcQty, uint rate) internal view returns(uint) {
        uint dstDecimals = getDecimals(token);
        uint srcDecimals = getDecimals(baseToken);
        return calcDstQty(srcQty, srcDecimals, dstDecimals, rate);
    }

}

contract Pool is TokenManagement {
    
    using SafeMath for uint;
    
    string public constant name = 'UniKiller Token';
    string public constant symbol = 'UKT';
    uint8 public constant decimals = 6;
    uint  public totalSupply;
    mapping(address => uint) public balanceOf;
    
    event Approval(address indexed owner, address indexed spender, uint value);
    event Transfer(address indexed from, address indexed to, uint value);
    
    receive() external payable{}
    fallback() external payable{}
    
    struct TokenInfo {
        string symbol;
        uint amount;
        uint value;
        uint weight;
    }
    
    function getAllTokensInfo() external view returns (TokenInfo[] memory) {
        TokenInfo[] memory tokensInfo = new TokenInfo[](listedTokens.length);
        for (uint i = 0; i < listedTokens.length; i++) {
            address token = listedTokens[i];
            tokensInfo[i].symbol = ERC20(token).symbol();
            uint tokenAmount = ERC20(token).balanceOf(address(this));
            tokensInfo[i].amount = tokenAmount;
            tokensInfo[i].value = getTokenValue(token,tokenAmount);
            tokensInfo[i].weight = tokenData[token].weight;
        }
    } 
    
    
    function getTotalTokensValue() public view returns (uint)  {
        uint totalTokensValue = ERC20(baseToken).balanceOf(address(this));
        for (uint i = 0; i < listedTokens.length; i++) {
            address token = listedTokens[i];
            uint baseRate = tokenData[token].baseRate;
            uint tokenBalance = ERC20(token).balanceOf(address(this));
            uint tokenValue = getTokenQty(token,tokenBalance,baseRate);
            totalTokensValue = tokenValue;
        }
        return totalTokensValue;
    }
    
    function getTokenValue(address token, uint amount) public view returns (uint) {
        uint tokenValue;
        if (token == baseToken) {
            tokenValue = amount;
        }
        else {
            uint baseRate = tokenData[token].baseRate;
       
            uint srcDecimals = getDecimals(token);
            uint dstDecimals = getDecimals(baseToken);
            tokenValue = calcDstQty(amount,srcDecimals,dstDecimals,baseRate);
        }
        return tokenValue;
    }
    
    function getLPTokenAmountByToken(address token, uint amount) public view returns (uint) {
        uint tokenValue = getTokenValue(token,amount);
        uint totalTokensValue = getTotalTokensValue();
        uint lpTokenAmount = tokenValue * totalSupply / totalTokensValue;
        return lpTokenAmount;
    }
    
    function getTokenAmountByLP(address token, uint lpTokenAmount) public view returns (uint) {
        uint totalTokensValue = getTotalTokensValue();
        uint tokenValue = lpTokenAmount * totalTokensValue / totalSupply;
        uint tokenAmount;
        if (token == baseToken) {
            tokenAmount = tokenValue;
        }
        else {
            uint baseRate = tokenData[token].baseRate;
        
            uint srcDecimals = getDecimals(token);
            uint dstDecimals = getDecimals(baseToken);
            tokenAmount = calcSrcQty(tokenValue,srcDecimals,dstDecimals,baseRate);
        }
    } 
    
    function deposit(address token, uint tokenAmount) external {
        TransferHelper.safeTransferFrom(token, msg.sender, address(this), tokenAmount);
        uint lpTokenAmount = getLPTokenAmountByToken(token,tokenAmount);
        _mint(msg.sender,lpTokenAmount);
    }
    
    function withdraw(address token, uint tokenAmount) external {
        uint lpTokenAmount = getLPTokenAmountByToken(token,tokenAmount);
        _burn(msg.sender,lpTokenAmount);
        TransferHelper.safeTransfer(token, msg.sender, tokenAmount);
    }
    
    function withdrawByLpToken(address token, uint lpTokenAmount) external {
        uint tokenAmount = getTokenAmountByLP(token, lpTokenAmount);
        TransferHelper.safeTransfer(token, msg.sender, tokenAmount);
        _burn(msg.sender,lpTokenAmount);
    }
    
    function _mint(address to, uint value) internal {
        totalSupply = totalSupply.add(value);
        balanceOf[to] = balanceOf[to].add(value);
        emit Transfer(address(0), to, value);
    }

    function _burn(address from, uint value) internal {
        require(balanceOf[from] >= value);
        balanceOf[from] = balanceOf[from].sub(value);
        totalSupply = totalSupply.sub(value);
        emit Transfer(from, address(0), value);
    }
    
}

contract Unikiller is Pool {
    
    uint public flashLoadFee = 5;  // bps 
    uint public swapFee = 30; // bps

    constructor(address _baseToken) public {
        baseToken = _baseToken;
    }
    
    function getAmountOut(address tokenIn, address tokenOut, uint amountIn) view public returns (uint amountOut) {
        uint refRate;
        if (tokenIn == baseToken) {
            refRate = PRECISION * PRECISION / tokenData[tokenIn].baseRate;
        }
        else if (tokenOut == baseToken) {
            refRate = tokenData[tokenIn].baseRate;
        }
        else {
            refRate = tokenData[tokenIn].baseRate * PRECISION / tokenData[tokenOut].baseRate;
        }
        
        uint maxSlippage = 500;
        uint startRate;
        
        uint configWeightRate = PRECISION * tokenData[tokenIn].weight / tokenData[tokenOut].weight;
        uint currentWeightRate = calcRateFromQty(ERC20(tokenOut).balanceOf(address(this)), ERC20(tokenIn).balanceOf(address(this)), 
            getDecimals(tokenOut), getDecimals(tokenIn));
           
        // TODO priceSlippage logic
        if (currentWeightRate <= configWeightRate) {
            startRate = refRate * (PRECISION *10000 + maxSlippage*currentWeightRate) / configWeightRate;
        }
        else {
            startRate = refRate * (PRECISION*10000 + PRECISION*maxSlippage*configWeightRate/currentWeightRate - PRECISION*maxSlippage) / PRECISION;
        }
        // priceSliappage = (startPriceSlippage + endPriceSliappage) / 2 ; 
        
        amountOut = startRate * amountIn;
        amountOut = amountOut * (10000-swapFee)/10000;
    }
    
    function swap(address tokenIn, address tokenOut, uint amountIn, uint amountOutMin, address to, bytes calldata data) external returns (uint) {
        uint amountOut = getAmountOut(tokenIn,tokenOut,amountIn);
        require(amountOut >= amountOutMin,'Unikiller swap: INSUFFICIENT_OUTPUT_AMOUNT');
            
        TransferHelper.safeTransfer(tokenOut, to, amountOut);
        if (data.length > 0) {
            IUniKillerCallee(to).uniKillerCall(tokenIn, tokenOut, amountIn, amountOut, data);
        }
        TransferHelper.safeTransferFrom(tokenIn, msg.sender, address(this), amountIn);
    }
    
    function flashLoan(address token, uint256 amount, bytes calldata data)  external returns (uint) {
        TransferHelper.safeTransfer(token, msg.sender, amount);
        require(data.length > 0, "no data in flashloan");
        IUniKillerCallee(msg.sender).loanCall(amount, data);
        uint repayAmount = amount * (10000+flashLoadFee)/10000;
        TransferHelper.safeTransferFrom(token,msg.sender,address(this),repayAmount);
        return repayAmount;
    }

}



