//
//  HYPRMediateResult.h
//  HyprMX Mediate iOS
//
//  Created by Jeremy Ellison on 9/4/14.
//

#ifndef HYPRMEDIATERESULT_H
#define HYPRMEDIATERESULT_H

#import <Foundation/Foundation.h>

@interface HYPRMediateReward : NSObject

/** These properties provide information about the result's virtual currency transaction */

@property (nonatomic, readonly) SInt64 virtualCurrencyAmount;
@property (nonatomic, readonly) NSString *virtualCurrencyName;

@end

#endif //HYPRMEDIATERESULT_H
