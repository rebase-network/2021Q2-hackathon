
import './show.css';
import InputColor from 'react-input-color';
import  {CountdownCircleTimer } from 'react-countdown-circle-timer'
import React, { useState ,useContext } from 'react';
import {Web3Context} from "../App.js";

//import Claim from './artifacts/contracts/Greeter.sol/Greeter.json'
const v =['#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#1bb86b', '#50514d', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#a2a49f', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#a2a49f', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#a2a49f', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#ffffff', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#facDC7', '#facDC7', '#facDC7', '#ffffff', '#ffffff', '#50514d', '#74eefd', '#74eefd', '#74eefd', '#facDC7', '#facDC7', '#facDC7', '#ffffff', '#ffffff', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#a2a49f', '#ffffff', '#ffffff', '#facDC7', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#74eefd', '#74eefd', '#74eefd', '#ffffff', '#facDC7', '#ffffff', '#ffffff', '#a2a49f', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#50514d', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#74eefd', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#ffffff', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b', '#1bb86b']    
const v1=[]
const shuffleIndex = [...Array(720).keys()].sort(function() {
    return .5 - Math.random();
})

/* 用户染色 setcolor 函数然后就染色    getcolor */

/*claim 获取方框所有权*/

/* 应该是是像充值购买一样的东西吧，我充值购买代币然后可以花钱控制一个色块多久不变色  话题停留多久吧  这样才会好写一点*/ 
/*最后要截图，要重新开始，页面要刷新,色块颜色变化,*/
/* 把之前方块之前的颜色去掉 不按确定就不正式修改*/
function Board(props){

    const lockTime=10000
    const rows = props.rows
    const cols = props.cols
    const [visible,setVisible] = useState(false)
    const [colors, setColors] = useState(Array(rows*cols).fill('#FFFFFF'))
    //const [colors, setColors] = useState(v)
    const [choosei,setChoosei]=useState(0)
    const [chooser,setChooser]=useState(0)
    const [choosec,setChoosec]=useState(0)
    const [org,setOrg]=useState('#FFFFFF')  
    const [lock,setLock]=useState(Array(rows*cols).fill(false))
    //var show_board=[]
   /* function painting(){
        [...Array(720).keys()].forEach((i)=>{setTimeout(()=>{const colors1=colors.slice();
            //console.log('第几个',chooser*cols+choosec,chooser,choosec)
            colors1[shuffleIndex[i]] = v[shuffleIndex[i]];
            setColors(colors1);setColors()},1000*i)})

    }*/

    function lockUp(index){
        const lock1=lock.slice();
        
        lock1[index] = !lock[index]
        setLock(lock1)
        setTimeout(()=>{
            const lock1=lock.slice()
            lock1[index] = !lock[index]
        },lockTime)

    }
    function changeColor(color){
        var c=false
        if (!lock[chooser*cols+choosec]){
        const colors1=colors.slice();
        //console.log('第几个',chooser*cols+choosec,chooser,choosec)
        colors1[chooser*cols+choosec] = color;
        setColors(colors1);
        //console.log('changecolor',colors)
        }else{
            alert('The square is locked and cannot be changed in color')
            return 'close'
        }

    }

    function restart(){
        /*保存现在数据发送*/
        setColors(Array(rows*cols).fill('#FFFFFF'))
        setLock(Array(rows*cols).fill(false))
    }

    function handleClick(r,j){

        console.log('点击',r,j,visible)
        
        if (!visible){
        console.log('点击',r,j,visible)
        setOrg(colors[chooser*cols+choosec]) 
        setVisible(!visible)
        setChooser(r)
        setChoosec(j)
        setChoosei(r*cols+j)
        }

        
    }
    function close(){
        console.log(11);
        if (visible){
        setVisible(!visible)}
    }
    function BoardRow(props){
        const r=props.r
        const cols=props.cols
        const colors=props.colors
        const handleClick = props.handleClick
        const choosei =props.choosei
        //console.log(handleClick)

        const listItem= [...Array(cols).keys()].map((index,j)=>{return <Box key={r*cols+index}  id={r*cols+index} color={colors[r*cols+j]}  choosei={choosei} onClick={()=>{handleClick(r,j)}}></Box>})
      
        return (
            <div className="board-row">
                {listItem}
            </div>
        )
    }
    /*for (var r=0 ;r<rows;r++){
        console.log(r)
    }*/
    //for ( var r= 0 ;r<rows;r++){
    //   console.log(r,cols,rows)
        
     //   show_board.push(
           // [...Array(cols).keys()].map((j)=>{<Box color={colors[j*cols+r]} onClick={handleClick(r,j)}></Box>})
         const show_board= [...Array(rows).keys()].map((index,r)=>{ return <BoardRow key={index} colors={colors} r={r} cols={cols}  choosei={choosei}  handleClick={handleClick} ></BoardRow >})
        // console.log(show_board)
       // )

        
  //  }
  const renderTime = ({ remainingTime }) => {
    if (remainingTime === 0) {
      return <div className="timer">A new round will start</div>;
    }
  
    return (
      <div className="timer">
        <div className="text">Remaining</div>
        <div className="value">{remainingTime}</div>
        <div className="text">seconds</div>
      </div>
    );
  };
  
    return (
        <div className ='Board'>
            
            <div className='center'>
                <div>
                    {show_board}
                </div>
            </div>
             <div className='right'>
                 
                <CountdownCircleTimer
                isPlaying
                duration={20}
                colors={[["#8b9df3", 0.6], ["#e49366", 0.2], ["#83d8bb"]]}
               // onComplete={() =>{ restart();return [true, 1000]}}
                onComplete={() =>{setColors(v);return [true, 1000]}}
                >
                    {renderTime}
                </CountdownCircleTimer> 
            </div>
            
          
                <ChoosePanel visible={visible} changeColor={changeColor}  index ={{'r':chooser,'c':choosec,'cols':cols,'close':close,'org':org,'lockUp':lockUp,'colors':colors}} />

            
            
        </div>
        

    )
}




function Box(props){
    //console.log('--------------',props.color)
    //console.log(props.id ,'box key')
    return (
        <div  className='box' style={{backgroundColor:props.color , borderColor:(props.choosei===props.id) ? "#677cdd" : props.color}} onClick={()=>{props.onClick()}}></div>
    )
}

function ChoosePanel(props){
    
    const visible= props.visible
    const changeColor=props.changeColor
    const { web3 } =  useContext(Web3Context);
    const {r,c,cols,close,org,lockUp,colors}= props.index
    window.test = web3
    const contractAddress = '0xe7f1725E7734CE288F8367e1Bb143E90bb3F0512'
    //实例化合约
    const abi = require("../abi").default
    window.myContract = new web3.eth.Contract(abi, contractAddress)
    console.log(web3)
    // web3.eth.getAccounts((_, accounts) => {
    //     window.defaultAccount = accounts[0];
    //     console.log(window.defaultAccount);
    //   })
    
    // window.defaultAccount = web3.eth.accounts[0].toLowerCase()
    console.log(window.defaultAccount )
    const purchase = async () => {
        const account=await web3.eth.getAccounts()
        console.log(account)
        window.myContract.methods.claim(account[0],c,r ).send({from:account[0]})
        .on('transactionHash',(transactionHash)=>{
          console.log('transactionHash',transactionHash)
        })
        .on('confirmation',(confirmationNumber,receipt)=>{
          console.log({ confirmationNumber: confirmationNumber, receipt: receipt });
          lockUp(r*cols+c); 
          close()

        })
        .on('receipt',(receipt)=>{
          console.log({ receipt: receipt })
        })
        .on('error',(error,receipt)=>{
          console.log({ error: error, receipt: receipt });
          changeColor(org);
          close()
        })
      }
    
    //console.log('index',props.index,r)
    //const originalColor 
    const cc=colors.slice()
    const originalColor = cc[r*cols+c] 

    function handelChange(color){
       // console.log(111111,color)
       // console.log('zuoseqi',color)
        var signal=changeColor(color.hex)
        if (signal){
        close()}
    }
    //console.log(org,'颜色')

    return (
        visible && <div className='dialog'>
            <div className='flex-row'>
                <div style={{marginRight:'10px'}}>Choose color:</div>
            <InputColor
            initialValue={originalColor}
            onChange={handelChange}
            placement="right"
            />
            </div>
            <div className='flex-row12'>
            <div className='btn' onClick={()=>{ close()}}>Confirm</div>
            <div className='btn' onClick={()=>{console.log('gggg',org);changeColor(org);close()}}>Cancle</div>
            </div>
            
            <div className='btn1'  title = {`purchase and you will get the token of the box in [${c},${r}]`} onClick={()=>{purchase()}}>Purchase</div>
        </div>
    )
}

export default Board


