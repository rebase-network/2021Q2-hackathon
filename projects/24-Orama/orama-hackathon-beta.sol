// SPDX-License-Identifier: GPL-3.0

pragma solidity >=0.7.0 <0.9.0;

contract OramaHackathon { 
  
    
    mapping(string => uint) private q0;
    mapping(string => uint) private q1;
    mapping(string => uint) private q2;
    mapping(string => uint) private q3;
    int32[16] private HIDDEN0 = [0];
    int32[16] private HIDDEN1 = [0];
    int32[16] private init_pic = [int32(25), int32(93), int32(24), int32(-89), int32(-119), int32(-46), int32(9), int32(-64), int32(21), int32(100), int32(-9), int32(-205), int32(143), int32(118), int32(92), int32(1)];
    int32[256] private weights0 = [int32(60), int32(142), int32(35), int32(162), int32(-159), int32(-136), int32(89), int32(205), int32(105), int32(113), int32(-157), int32(7), int32(-212), int32(-76), int32(-94), int32(66), int32(245), int32(60), int32(209), int32(135), int32(27), int32(137), int32(23), int32(-148), int32(-121), int32(-141), int32(-128), int32(132), int32(-44), int32(-223), int32(-229), int32(-62), int32(113), int32(87), int32(-88), int32(-84), int32(22), int32(14), int32(-21), int32(2), int32(-179), int32(-182), int32(-58), int32(-32), int32(-42), int32(37), int32(-145), int32(28), int32(156), int32(25), int32(-90), int32(-221), int32(-40), int32(-60), int32(86), int32(205), int32(25), int32(-8), int32(45), int32(-127), int32(-41), int32(-236), int32(-177), int32(115), int32(108), int32(81), int32(60), int32(-23), int32(161), int32(-101), int32(-18), int32(-25), int32(135), int32(102), int32(10), int32(42), int32(42), int32(28), int32(10), int32(92), int32(48), int32(-119), int32(91), int32(212), int32(-2), int32(-206), int32(13), int32(-162), int32(219), int32(-3), int32(7), int32(-45), int32(316), int32(-88), int32(-53), int32(-96), int32(9), int32(-61), int32(-114), int32(75), int32(-89), int32(26), int32(-96), int32(-37), int32(-105), int32(15), int32(66), int32(243), int32(99), int32(-128), int32(-12), int32(-116), int32(-58), int32(93), int32(-53), int32(37), int32(-31), int32(106), int32(51), int32(60), int32(110), int32(214), int32(57), int32(102), int32(-57), int32(82), int32(-71), int32(76), int32(148), int32(253), int32(73), int32(84), int32(14), int32(-127), int32(100), int32(-67), int32(-53), int32(56), int32(224), int32(-104), int32(19), int32(65), int32(-175), int32(102), int32(196), int32(108), int32(-58), int32(-91), int32(47), int32(-25), int32(111), int32(32), int32(0), int32(6), int32(-66), int32(-246), int32(-116), int32(90), int32(-15), int32(-8), int32(60), int32(121), int32(96), int32(-242), int32(62), int32(-86), int32(71), int32(81), int32(5), int32(-19), int32(71), int32(12), int32(-89), int32(-51), int32(-92), int32(-60), int32(103), int32(-117), int32(8), int32(-83), int32(-125), int32(113), int32(54), int32(-149), int32(27), int32(-46), int32(71), int32(74), int32(67), int32(-28), int32(8), int32(-146), int32(207), int32(-78), int32(-89), int32(23), int32(-11), int32(33), int32(79), int32(25), int32(-186), int32(-127), int32(-78), int32(-134), int32(27), int32(-61), int32(-167), int32(-244), int32(-116), int32(-48), int32(39), int32(5), int32(109), int32(3), int32(63), int32(-35), int32(-120), int32(-51), int32(-52), int32(-117), int32(-4), int32(39), int32(76), int32(77), int32(-93), int32(-56), int32(-56), int32(66), int32(82), int32(-24), int32(96), int32(-65), int32(-53), int32(-103), int32(213), int32(-154), int32(16), int32(75), int32(-104), int32(160), int32(-12), int32(0), int32(-98), int32(295), int32(5), int32(40), int32(56), int32(61), int32(134), int32(-100), int32(97), int32(115), int32(51), int32(-46), int32(-6), int32(57)];
    
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
    
    function mlp (uint category) public    returns(int32[16]  memory image) {
        for (uint nid = 0; nid < 16; nid++) {
            for (uint ins = 0; ins < 16; ins++) {
                HIDDEN0[nid] = init_pic[ins] * weights0[nid * 16 + ins];
                if (HIDDEN0[nid] < 0)
                    HIDDEN0[nid] = 0;
            }
        }
        
        return HIDDEN0;
    }
    
    function main(string memory EthPrice, string memory color, string memory emotion, string memory token)  public   returns(int32[16] memory image)  {
     uint r;
     r = DecisionTree(EthPrice, color, emotion, token);
     
     return mlp(r);
    }
    
    constructor() public {
        InitMap();
    }
}
