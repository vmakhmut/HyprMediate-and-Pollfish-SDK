package com.reactnativecustom;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.hyprmx.mediate.HyprMediate;
import com.hyprmx.mediate.HyprMediateError;
import com.hyprmx.mediate.HyprMediateListener;
import com.hyprmx.mediate.HyprMediateReward;

/**
 * Created by name on 06.03.2018.
 */

public class HyprMediateModule extends BaseModule {

    private static final String REACT_CLASS = "HyprMediateModule";
    private static final String EVENT = "onHyperMediateEvent";

    private HyprMediateListener listener = null;

    public HyprMediateModule(ReactApplicationContext reactContext) {
        super(REACT_CLASS, reactContext);
        onReceiveNativeEvent();
    }

    //initialize with API key and user ID
    @ReactMethod
    public void initialize(String apiKey, String uid) {
        HyprMediate.getInstance().initialize(getCurrentActivity(), apiKey, uid, listener);
    }

    //show ad
    @ReactMethod
    public void showAd() {
        HyprMediate.getInstance().showAd();
    }

    //ads available сheck
    @ReactMethod
    public void checkInventory() {
        HyprMediate.getInstance().checkInventory();
    }

    private void onReceiveNativeEvent() {

        listener = new HyprMediateListener() {

            //HyprMediate will call back hyprMediateCanShowAd method when ad is available
            @Override
            public void hyprMediateCanShowAd(boolean b) {
                WritableMap params = Arguments.createMap();
                params.putBoolean("hyprMediateCanShowAd", b);
                sendEvent(EVENT, params);
            }

            @Override
            public void hyprMediateRewardDelivered(HyprMediateReward hyprMediateReward) {
                WritableMap params = Arguments.createMap();
                params.putString("virtualCurrencyName", hyprMediateReward.virtualCurrencyName());
                params.putDouble("virtualCurrencyAmount", hyprMediateReward.virtualCurrencyAmount());
                sendEvent(EVENT, params);
            }

            @Override
            public void hyprMediateErrorOccurred(HyprMediateError hyprMediateError) {
                WritableMap params = Arguments.createMap();
                params.putString("hyprMediateError", hyprMediateError.toString());
                sendEvent(EVENT, params);
            }

            //HyprMediate started displaying an ad
            @Override
            public void hyprMediateStartedDisplaying() {
                WritableMap params = Arguments.createMap();
                params.putString("displaying_state", "HyprMediateStartAd");
                sendEvent(EVENT, params);
            }

            //HyprMediate finished displaying an ad
            @Override
            public void hyprMediateFinishedDisplaying() {
                WritableMap params = Arguments.createMap();
                params.putString("displaying_state", "HyprMediateStopAd");
                sendEvent(EVENT, params);
            }
        };
    }
}
