import Web3 from 'web3'

export const Music_ADDRESS = '0xC338c0FBE7aAeF0fc11dF80aa2888852af2ca502'

export const Music_ABI = [
    {
        "constant": false,
        "inputs": [
            {
                "internalType": "string",
                "name": "_musicNa",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "_musicIP",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "_musType",
                "type": "string"
            },
            {
                "internalType": "address",
                "name": "_musicOwner",
                "type": "address"
            },
            {
                "internalType": "uint32",
                "name": "_cost",
                "type": "uint32"
            },
            {
                "internalType": "string",
                "name": "_musicHash",
                "type": "string"
            }
        ],
        "name": "addMusic",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            },
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "payable": true,
        "stateMutability": "payable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "internalType": "string",
                "name": "peo_name",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "peo_passw",
                "type": "string"
            },
            {
                "internalType": "address",
                "name": "peo_addr",
                "type": "address"
            },
            {
                "internalType": "string",
                "name": "peo_type",
                "type": "string"
            }
        ],
        "name": "addPeople",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            },
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "internalType": "address",
                "name": "_user2",
                "type": "address"
            },
            {
                "internalType": "string",
                "name": "_musicNa",
                "type": "string"
            },
            {
                "internalType": "uint32",
                "name": "_cost",
                "type": "uint32"
            }
        ],
        "name": "byMusicCopyright",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": false,
        "inputs": [
            {
                "internalType": "string",
                "name": "_musicNa",
                "type": "string"
            },
            {
                "internalType": "uint32",
                "name": "_cost",
                "type": "uint32"
            }
        ],
        "name": "changeCost",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "payable": false,
        "stateMutability": "nonpayable",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "string",
                "name": "_musicNa",
                "type": "string"
            }
        ],
        "name": "getMusic",
        "outputs": [
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            },
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            },
            {
                "internalType": "uint32",
                "name": "",
                "type": "uint32"
            },
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            },
            {
                "internalType": "address",
                "name": "",
                "type": "address"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "getMusicList",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "string",
                "name": "_musicNa",
                "type": "string"
            }
        ],
        "name": "getOwnerShip",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            },
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            },
            {
                "internalType": "address",
                "name": "",
                "type": "address"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "address",
                "name": "peo_addr",
                "type": "address"
            }
        ],
        "name": "getPeople",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            },
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            },
            {
                "internalType": "address",
                "name": "",
                "type": "address"
            },
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            },
            {
                "internalType": "string[]",
                "name": "",
                "type": "string[]"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "musicCopy_id",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "name": "musiclist",
        "outputs": [
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            }
        ],
        "name": "musics",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "music_id",
                "type": "uint256"
            },
            {
                "internalType": "string",
                "name": "musName",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "musIP",
                "type": "string"
            },
            {
                "internalType": "address",
                "name": "musOwner",
                "type": "address"
            },
            {
                "internalType": "address",
                "name": "writer",
                "type": "address"
            },
            {
                "internalType": "uint32",
                "name": "cost",
                "type": "uint32"
            },
            {
                "internalType": "string",
                "name": "musType",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "musicHash",
                "type": "string"
            },
            {
                "internalType": "uint256",
                "name": "timeStamp",
                "type": "uint256"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "string",
                "name": "",
                "type": "string"
            }
        ],
        "name": "ownerShips",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "ownerShipId",
                "type": "uint256"
            },
            {
                "internalType": "string",
                "name": "music_name_O",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "owener_name",
                "type": "string"
            },
            {
                "internalType": "address",
                "name": "owener_addr",
                "type": "address"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "owner_id",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [],
        "name": "people_id",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "",
                "type": "uint256"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "address",
                "name": "",
                "type": "address"
            }
        ],
        "name": "peoples",
        "outputs": [
            {
                "internalType": "uint256",
                "name": "people_id",
                "type": "uint256"
            },
            {
                "internalType": "string",
                "name": "peoName",
                "type": "string"
            },
            {
                "internalType": "string",
                "name": "peoPassw",
                "type": "string"
            },
            {
                "internalType": "address",
                "name": "peoAddr",
                "type": "address"
            },
            {
                "internalType": "string",
                "name": "peoType",
                "type": "string"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    },
    {
        "constant": true,
        "inputs": [
            {
                "internalType": "address",
                "name": "_pAddr",
                "type": "address"
            },
            {
                "internalType": "string",
                "name": "_pass",
                "type": "string"
            }
        ],
        "name": "vertifyAccounts",
        "outputs": [
            {
                "internalType": "bool",
                "name": "",
                "type": "bool"
            }
        ],
        "payable": false,
        "stateMutability": "view",
        "type": "function"
    }
]
export default {

    async loadweb3() {
        /* 新版的方式 */
        var web3Provider;
        if (window.ethereum) {
            web3Provider = window.ethereum;
            try {
                // 请求用户授权
                await window.ethereum.enable();
            } catch (error) {
                // 用户不授权时
                console.error("User denied account access")
            }
        } else if (window.web3) {   // 老版 MetaMask Legacy dapp browsers...
            web3Provider = window.web3.currentProvider;
        } else {
            web3Provider = new Web3.providers.WebsocketProvider("ws://localhost:65500");
        }

        const web3js = new Web3(web3Provider);//web3js就是你需要的web3实例

        const accounts = await web3js.eth.getAccounts()

        const music = new web3js.eth.Contract(Music_ABI, Music_ADDRESS)

        return {accounts, music};
    }
}