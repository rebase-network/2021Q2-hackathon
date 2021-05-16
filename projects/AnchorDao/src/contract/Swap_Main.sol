import "@openzeppelin/contracts/token/ERC20/utils/SafeERC20.sol";
import "@openzeppelin/contracts/utils/math/SafeMath.sol";
import "@openzeppelin/contracts/token/ERC20/ERC20.sol";
import "@openzeppelin/contracts/access/Ownable.sol";


interface Swap_Mains {
    function transferOwnership(address newOwner) external virtual;
    function WhetherToPay() external view returns(bool);
}

contract Swap_Factory {

    mapping(address => address[200]) userOrders;
    address[] public orders;

    function NewSwapMain(ERC20 _AToken, uint256 _ATokenCount, uint256 _ATokenPrice, ERC20 _BToken, uint256 _MatchUpNumberOfRewards) public returns (address) {
        address ContractAddr = address(new Swap_Main(msg.sender, _AToken, _ATokenCount, _ATokenPrice, _BToken, _MatchUpNumberOfRewards, address(this)));
        for (uint8 i = 0; i < userOrders[msg.sender].length; i++) {
            if (userOrders[msg.sender].length == 200) {
                address[200] memory NewArray;
                userOrders[msg.sender] = NewArray;
            }
            if (userOrders[msg.sender][i] == address(0)) {
                userOrders[msg.sender][i] = ContractAddr;
                Swap_Mains(userOrders[msg.sender][i]).transferOwnership(msg.sender);
                break;
            }

        }
        return ContractAddr;
    }

    function GetOrders() external view returns (address[] memory){
        address[] memory NewArray = new address[](10);
        uint8 count = 0;
        for (uint8 i = 0; i < orders.length; i++) {
            if(Swap_Mains(orders[i]).WhetherToPay()){
                if (count > 10) {
                    break;
                }
                NewArray[i] = orders[i];
                count++;
            }

        }
        return NewArray;
    }

    function GetUserOrders(address userAddress) external view returns (address[] memory){
        address[] memory userOriders = new address[](userOrders[userAddress].lenght);
        for(uint8 i = 0;i<userOrders[userAddress].lenght;i++){
            userOriders[i] = userOrders[userAddress][i];
        }
        return userOriders;
    }
}


contract Swap_Main is Ownable {
    using SafeERC20 for ERC20;
    using SafeMath for uint;

    //订单拥有人
    address public m_OrderOwner;
    //AToken
    ERC20 public m_AToken;
    //BToken
    ERC20 public m_BToken;
    //出售AToken数量
    uint256 public m_ATokenCount = 1;
    //当前订单AToken数量
    uint256 public m_ThisATokenCount = 1;
    //出售AToken单价
    uint256 public m_ATokenPrice = 1;
    //是否支付费用
    bool public m_WhetherToPay;
    //是否支付token
    bool public m_WhetherToPayToken;
    //是否被撮合
    bool public  m_MatchUp;
    //撮合人
    address public m_MatchUpAddr;
    //撮合人奖励数量
    uint256 public m_MatchUpNumberOfRewards = 1;
    //撮合人保证金
    uint256 public m_MatchUpMargin = 1;

    //工厂地址
    address public m_FactoryContract;
    ///----------MODIFIER----------
    modifier MatchUp(){
        require(m_MatchUp == false && m_MatchUpAddr == address(0), "Has been  Match up:(");
        _;
    }
    modifier UnMatchUp(){
        require(m_MatchUp  && m_MatchUpAddr == msg.sender, "You're not a Match up");
        _;
    }

    modifier isAToken(){
        require(m_WhetherToPayToken, "unpaid Token:(");
        _;
    }
    modifier isFee(){
        require(m_WhetherToPay, "unpaid Fee:(");
        _;
    }

    constructor(address _OrderOwner, ERC20 _AToken, uint256 _ATokenCount, uint256 _ATokenPrice, ERC20 _BToken, uint256 _MatchUpNumberOfRewards, address _FactoryContract) public {
        m_OrderOwner = _OrderOwner;
        m_AToken = _AToken;
        m_ATokenCount = _ATokenCount;
        m_ThisATokenCount = _ATokenCount;
        m_ATokenPrice = _ATokenPrice;
        m_BToken = _BToken;
        m_MatchUpNumberOfRewards = _MatchUpNumberOfRewards;
        m_FactoryContract = _FactoryContract;
    }

    /*
    支付AToken
    */
    function PayAToken() external onlyOwner returns (bool){
        m_AToken.safeTransferFrom(msg.sender, address(this), m_ATokenCount);
        PayTheFee();
        m_WhetherToPayToken = true;
        return true;
    }

    /*
    是否支付AToken
    */
    function WhetherToPayToken() external view returns (bool){
        return m_WhetherToPayToken;
    }

    /*
    支付费用
    */
    function PayTheFee() private onlyOwner  returns (bool){
        uint256 count =jisuan();
        m_AToken.safeTransfer(m_FactoryContract, count);
        m_ATokenCount = (m_ATokenCount.sub(count)).sub(m_MatchUpNumberOfRewards);
        m_ThisATokenCount = (m_ThisATokenCount.sub(count)).sub(m_MatchUpNumberOfRewards);
        m_WhetherToPay = true;
        return true;
    }

    function jisuan()private returns(uint256){
        (,uint256 count) = (m_ATokenCount.mul(15)).tryDiv(100);
        return count;
    }

    /*
    是否支付费用
    */
    function WhetherToPay() external view returns (bool) {
        return m_WhetherToPay;
    }

    /*
    成为撮合人
    传入 当前合约调用者
    */
    function BecomeMatchUpAddr(address _MatchUpAddr) external isAToken isFee MatchUp returns (bool){
        CalculateTheMargin();
        m_MatchUpAddr = _MatchUpAddr;
        m_AToken.safeTransferFrom(msg.sender, address(this), m_MatchUpMargin);
        m_MatchUp = true;
        return true;
    }
    /*
    撮合人领取奖励
    */
    function ReceiveAward() external UnMatchUp returns(bool){
        require(m_ThisATokenCount<=(m_ATokenCount.mul(5))/100,"Working hard:(");
        m_AToken.safeTransfer(msg.sender,m_MatchUpNumberOfRewards);
        return true;
    }

    /*
    是否有撮合人
    */
    function WhetherToMatchUpAddr() external view returns (bool){
        return m_MatchUp;
    }

    /*
    交易
    传入 需要交易数量
    */
    function Merchandise(uint256 count) external isAToken isFee returns (uint256){
        (uint256 counts1,uint256 counts2) = CalculateTheRemaining(count);
        require(count > 1000, "The quantity is too small:(");
        require(counts1 <= counts2, "Insufficient swappable balance:(");
        m_BToken.safeTransferFrom(msg.sender, m_OrderOwner, count);
        m_AToken.safeTransfer(msg.sender, counts1);
        (, m_ThisATokenCount) = m_ThisATokenCount.trySub(counts1);
        return counts1;
    }

    /*
    计算剩余AToken
    */
    function CalculateTheRemaining(uint256 count) public view returns (uint256 quantity, uint256 endQuantity){
        (, uint256 a) = count.tryMul(m_ATokenPrice / 1e15);
        (, quantity) = a.tryDiv(1000);
        (, uint256 b) = m_ThisATokenCount.tryDiv(m_ATokenPrice);
        (, endQuantity) = b.tryMul(1e18);
        return (quantity, endQuantity);
    }


    /*
    计算保证金
    */
    function CalculateTheMargin() private {
        (, m_MatchUpMargin) = (m_ThisATokenCount.mul(2)).tryDiv(10);
    }

    /*
    删除订单
    返回 用户现存数量的百分之50
    */
    function DeleteOrder(address ContractAddress) external onlyOwner isAToken isFee  returns (bool){
        uint256 count = m_ATokenCount.div(2);
        m_AToken.safeTransfer(m_OrderOwner, count);
        m_AToken.safeTransfer(m_FactoryContract, count);
        m_AToken.safeTransfer(m_MatchUpAddr, m_MatchUpMargin);
        uint256 counts = m_MatchUpNumberOfRewards.div(2);
        m_AToken.safeTransfer(m_MatchUpAddr, counts);
        m_AToken.safeTransfer(m_FactoryContract, counts);
        selfdestruct(payable(m_OrderOwner));
        return true;
    }
}

