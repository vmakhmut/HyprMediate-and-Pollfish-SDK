//
//  HYPRMediate.h
//  HyprMX Mediate iOS
//
//  Created by Jeremy Ellison on 8/5/14.
//

#ifndef HYPRMEDIATE_H
#define HYPRMEDIATE_H

#import <AdSupport/AdSupport.h>
#import <AVFoundation/AVFoundation.h>
#import <CoreGraphics/CoreGraphics.h>
#import <Foundation/Foundation.h>
#import <MessageUI/MessageUI.h>
#import <MobileCoreServices/MobileCoreServices.h>
#import <QuartzCore/QuartzCore.h>
#import <SystemConfiguration/SystemConfiguration.h>
#import <UIKit/UIKit.h>
#import <StoreKit/StoreKit.h>
#import <WebKit/WebKit.h>
#import <SafariServices/SafariServices.h>
#import <EventKit/EventKit.h>
#import <EventKitUI/EventKitUI.h>
#import <Photos/Photos.h>
#import <CoreTelephony/CTTelephonyNetworkInfo.h>
#import "HYPRMediateReward.h"
#import "HYPRMediateError.h"
#import "HYPRMediateAdapter.h"
#import "HyprMediateDelegate.h"

typedef enum {
    HyprMediateLogLevelError = 0, // Messages at this level get logged all the time.
    HyprMediateLogLevelVerbose,   //                               ... only when verbose logging is turned on.
    HyprMediateLogLevelDebug      //                               ... in debug mode.
} HyprMediateLogLevel;

/* These methods are used by the adapters. */
/* Logs message at the HyprMediateLogLevelError level */
extern void HyprMediateLogE(NSString* log, ...);
/* Logs message at the HyprMediateLogLevelVerbose level */
extern void HyprMediateLogV(NSString* log, ...);
/* Logs message at the HyprMediateLogLevelDebug level */
extern void HyprMediateLogD(NSString* log, ...);

//! Project version number for HyprMediate iOS.
extern NSUInteger const kHYPRMediateSDKVersionNumber;

@interface HYPRMediate : NSObject <HYPRMediateAdapterDelegate>

/** Set the Mediate delegate
 *
 * @param delegate the delegate to receive callbacks from Mediate
 */
+ (void)setDelegate:(NSObject<HyprMediateDelegate>*)delegate;

/** Initialize the SDK with a given API key.
 *
 * @param mediateAPIKey the API key provided from the HyprMX Mediate Platform.
 * @param userId a unique userId identifying the user across devices.
 *
 * Should be called when your application finishes launching.
 */
+ (void)initialize:(NSString *)mediateAPIKey userId:(NSString *)userId;

/** Check whether an ad is ready to show. */
+ (void)checkInventory;

/** Show an advertisement */
+ (void)showAd;

/** Set the User ID
 *
 * @param userId a unique userId identifying the user across devices.
 * Use this method to change the user id after you've initialized the SDK
 */
+ (void)setUserId:(NSString *)userId;

/** Set the Mediate log level (Default is HyprMediateLogLevelError)
 *
 * @param logLevel how verbose the logging should be.
 */
+ (void)setLogLevel:(HyprMediateLogLevel)logLevel;

/** Fetch the Mediate log level */
+ (HyprMediateLogLevel)logLevel;

@end

#endif //HYPRMEDIATE_H
