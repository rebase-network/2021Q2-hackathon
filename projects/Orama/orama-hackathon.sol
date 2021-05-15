// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract OramaHackathon { 
  
    
    mapping(string => uint) private q0;
    mapping(string => uint) private q1;
    mapping(string => uint) private q2;
    mapping(string => uint) private q3;
    
    
    function InitMap() private {
        q0["To Da Earth"] = 0;
        q0["To Da Moon"] = 1;
        q0["To Da Mars"] = 2;
        q0["To Da Mercuy"] = 3;
        q0["To Da Jupiter"] = 4;
        q0["To Da Saturn"] = 5;
        q0["To Da Neptune"] = 6;
        q0["To Da Uranus"] = 7;
        q0["To Da Venus"] = 8;
        q0["To Da Sun"] = 9;
        q0["To Da Pluto"] = 10;
        
        q1["Red"] = 0;
        q1["Orange"] = 1;
        q1["Yellow"] = 2;
        q1["Green"] = 3;
        q1["Blue"] = 4;
        q1["Indigo"] = 5;
        q1["Violet"] = 6;
        q1["Purple"] = 7;
        q1["Pink"] = 8;
        q1["Silver"] = 9;
        q1["Gold"] = 10;
        
        q2["Happiness"] = 0;
        q2["Sadness"] = 1;
        q2["Anger"] = 2;
        q2["Anticipation"] = 3;
        q2["Fear"] = 4;
        q2["Loneliness"] = 5;
        q2["Jealousy"] = 6;
        q2["Disgust"] = 7;
        q2["Surprise"] = 8;
        q2["Trust"] = 9;
        q2["Cool"] = 10;
        
        q3["Bitcoin"] = 0;
        q3["Ethereum"] = 1;
        q3["Ripple"] = 2;
        q3["Cardano"] = 3;
        q3["Litecoin"] = 4;
        q3["EOS"] = 5;
        q3["Stellar"] = 6;
        q3["IOTA"] = 7;
        q3["NEO"] = 8;
        q3["DogeCoin"] = 9;
        q3["SHIB"] = 10;
    }
    
    function DecisionTree(string memory EthPrice, string memory color, string memory emotion, string memory token) public view returns(uint category) {
        
        uint f0 = q0[EthPrice];
        uint f1 = q1[color];
        uint f2 = q2[emotion];
        uint f3 = q3[token];
        
        if (f0 <= 9) {
            if (f2 <= 6) {
                if (f3 <= 6) {
                    if (f1 <= 6) {
                        if (f0 <= 6) {
                            return 0;
                        }
                        else {
                            return 1;
                        }
                    } else {
                        if (f0 <= 7) {
                            return 5;
                        } else {
                            return 2;
                        }
                    } 
                } else {
                    return 5;
                }
            } else {
                if (f0 <= 6) {
                    return 5;
                } else {
                    if (f3 <= 6) {
                        return 5;
                    } else {
                        if (f1 <= 6) {
                            return 5;
                        } else {
                            return 4;
                        }
                    }
                }
            }
        } else {
            return 3;
        }
    }

    constructor() public {
        InitMap();
    }
}
