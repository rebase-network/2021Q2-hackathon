var ProviderEngine = require("web3-provider-engine");
var FiltersSubprovider = require('web3-provider-engine/subproviders/filters.js');
var WalletSubprovider = require('web3-provider-engine/subproviders/wallet.js');
var Web3Subprovider = require("web3-provider-engine/subproviders/web3.js");
var Web3 = require("web3");

function WalletProvider(wallet, provider_url) {
  this.wallet = wallet;
  this.address = "0x" + this.wallet.getAddress().toString("hex");

  this.engine = new ProviderEngine();
  this.engine.addProvider(new WalletSubprovider(this.wallet, {}));
  this.engine.addProvider(new FiltersSubprovider());
  this.engine.addProvider(new Web3Subprovider(new Web3.providers.HttpProvider(provider_url)));
  this.engine.start(); // Required by the provider engine.
};

WalletProvider.prototype.sendAsync = function() {
  this.engine.sendAsync.apply(this.engine, arguments);
};

WalletProvider.prototype.send = function() {
  return this.engine.send.apply(this.engine, arguments);
};

WalletProvider.prototype.getAddress = function() {
  return this.address;
};

module.exports = WalletProvider;
