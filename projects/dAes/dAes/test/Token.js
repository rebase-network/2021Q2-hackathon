const { expect } = require("chai");

describe("Token contract", function() {
  it("Deployment should assign the total supply of tokens to the owner", async function() {

    const Canvas = await ethers.getContractFactory("Canvas");
    const canvas = await Canvas.deploy('pixel' , 'PXL' , 128 , 128);
    var symbol = await canvas.symbol();
    console.log(symbol);
    await canvas.deployed();


    var init_color = await canvas.getColor( 7 , 7);
    console.log(init_color);

    await canvas.setColor(7,7 , 1,2,3,4);
    var next_color = await canvas.getColor( 7 , 7);
    console.log(next_color);

  });
});