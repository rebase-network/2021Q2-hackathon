
import React, { createContext } from 'react';
import './App.css';
import Board from './components/show.js'
import App1 from './components/App1.js'
// import useWeb3 from 'react-use-web3';
import Web3 from "web3"
export const Web3Context = createContext();
function App() {
  
//   const { web3, network } = useWeb3();
let web3 = null
// metamask
if (window.ethereum) {
    web3 = new Web3(window.ethereum)
    window.ethereum.enable().then(() => {
      web3.eth.getAccounts((_, accounts) => {
        window.defaultAccount = accounts[0];
        console.log(window.defaultAccount);
      })
    })
} 
//   } else if (typeof web3 !== 'undefined') {
//     web3 = new Web3(web3.currentProvider);
//     window.defaultAccount = web3.eth.accounts[0];
//     console.log(window.defaultAccount)
//   } else {
//     let provider = new Web3(new Web3.providers.HttpProvider("https://ropsten.infura.io/v3/0e68b6ad41f24d6f975fbcd16f82dfec"))
//     web3 = new Web3(provider)
//   }
  return (
    <div className="App">
    <div className='header'></div>
      <div style={{minHeight:'73px',width:'100%' ,zIndex:'-5'}}></div>
    <Web3Context.Provider value={{ 'web3':web3 }}>
      <Board rows={20} cols={36}/>
    </Web3Context.Provider>
    
    <App1/>
        

  
    </div>
  );
}

export default App;
