var exec = require('cordova/exec');
 

function WalletPlugin() {
	//utilService.hannafordLog().log("WalletPlugin.js: is created");
}

//method to check if Android Pay App or IOS Pay App are installed on the device
WalletPlugin.prototype.canAddToWallet = function(successCallback, errorCallback){
	
	exec(successCallback,errorCallback, "WalletPlugin", "canAddToWallet");
}

//method to launch the Android Pay App or IOS Pay App and install the card/pass on the app
WalletPlugin.prototype.launchPayApp = function(successCallback, errorCallback, cardData){
	
	exec(successCallback,errorCallback, "WalletPlugin", "launchPayApp",[cardData]);    
}

WalletPlugin.install = function () {
	  if (!window.plugins) {
	    window.plugins = {};
	  }

	  window.plugins.walletPlugin = new WalletPlugin();

	  return window.plugins.walletPlugin;
	};

cordova.addConstructor(WalletPlugin.install);	