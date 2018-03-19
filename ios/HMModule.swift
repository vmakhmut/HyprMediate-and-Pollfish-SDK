//
//  HMModule.swift
//  reactNativeCustom
//
//  Created by name surname on 19.03.2018.
//  Copyright © 2018 Facebook. All rights reserved.
//

import Foundation

@objc(HMModule)
class HMModule: RCTEventEmitter {
  
  @objc override func supportedEvents() -> [String]! {
    return ["EventReminder"];
  }
  
  @objc func addEvent(_ name: String, location: String, date: NSNumber, callback: RCTResponseSenderBlock ) -> Void {
  
    HYPRMediate.initialize("a283f955-6080-4682-a0e1-09bcc9578df5", userId: "e87bf486-712b-40ec-a6c0-d3ed6b5649a9")
    
    // Date is ready to use!
    
    /*NSLog("%@ %@ %@", name, location, date)
    let ret:[String:Any] =  ["name": name, "location": location, "date" : date]
    callback([ret])
    self.sendEvent(withName: "EventReminder", body: ret)*/
  }
  
  @objc func show() {
    HYPRMediate.showAd()
  }

  @objc override func constantsToExport() -> [AnyHashable : Any]! {
    return [
      "x": 1,
      "y": 2,
      "z": "Arbitrary string"
    ]
  }
}
