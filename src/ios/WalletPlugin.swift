import UIKit
import PassKit
import IBMMobileFirstPlatformFoundation

@objc(WalletPlugin) class WalletPlugin : CDVPlugin {
    
    override func pluginInitialize() {

    }
    
    @objc(launchPayApp:)
    func launchPayApp(_ command: CDVInvokedUrlCommand) {
        let canAddPass = PKAddPassesViewController.canAddPasses()
        if canAddPass { 
            print("Add to Apple Wallet clicked")
            let pluginResult: CDVPluginResult
            if let passPackage = command.arguments[0] as? String {
                pluginResult = CDVPluginResult(status: CDVCommandStatus_OK, messageAs: "Arg was good")

                let decodedData: Data =  Data(base64Encoded: passPackage, options: Data.Base64DecodingOptions(rawValue: UInt(0)))!
                if let pass = try? PKPass(data: decodedData as Data) as PKPass {
                    let pkPassLibrary = PKPassLibrary.init()
                    if (!pkPassLibrary.containsPass(pass)) {
                        if let addPassVC = PKAddPassesViewController.init(pass: pass) {
                            self.viewController.present(addPassVC, animated:true, completion:nil)
                        }
                    } else {
                        UIApplication.shared.openURL(pass.passURL!)
                    }
                } else {
                    print("trouble producing pass")
                    let decodedString = String(data: decodedData, encoding: .utf8)!
                    let alert = UIAlertController(title: "Error Creating Pass", message: decodedString, preferredStyle: UIAlertController.Style.alert)
                    alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
                    self.viewController.present(alert, animated: true, completion: nil)
                }
            }else {
                print("Data is empty")
                let alert = UIAlertController(title: "Server Error", message: "Sorry but we had an error with our server", preferredStyle: UIAlertController.Style.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
                self.viewController.present(alert, animated: true, completion: nil)
                pluginResult = CDVPluginResult(status: CDVCommandStatus_ERROR, messageAs: "Arg was nil")
            }
            self.commandDelegate.send(pluginResult, callbackId: command.callbackId)
        }else {
            let alert = UIAlertController(title: "Error Adding Pass ", message: "Your phone does not support adding passes to your wallet.", preferredStyle: UIAlertController.Style.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertAction.Style.default, handler: nil))
            self.viewController.present(alert, animated: true, completion: nil) 
        }
    }
}
