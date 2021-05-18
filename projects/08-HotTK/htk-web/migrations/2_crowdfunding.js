const crowd = artifacts.require("Crowdfunding");

module.exports = function(deployer) {
  deployer.deploy(crowd);
};
