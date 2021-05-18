async function main() {
    // We get the contract to deploy
    const Canvas = await ethers.getContractFactory("Canvas");
    const canvas = await Canvas.deploy('pixel' , 'PXL' , 36 , 20);
  
    console.log("Canvas deployed to:", canvas.address);
  }
  
  main()
    .then(() => process.exit(0))
    .catch(error => {
      console.error(error);
      process.exit(1);
    });