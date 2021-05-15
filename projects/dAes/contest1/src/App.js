
import React, { createContext } from 'react';
import './App.css';
import Board from './components/show.js'
import useWeb3 from 'react-use-web3';
export const Web3Context = createContext();
function App() {
  
  const { web3, network } = useWeb3();
  console.log(web3,network)
  console.log(window.ethereum,'metemask')
  return (
    <div className="App">
    <div className='header'></div>
    <Web3Context.Provider value={{ 'web3':web3, 'network':network }}>
      <Board rows={20} cols={36}/>
    </Web3Context.Provider>
        

  
    </div>
  );
}

export default App;
