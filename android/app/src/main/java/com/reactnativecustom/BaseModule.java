package com.reactnativecustom;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Base module with sendEvent()
 */

public class BaseModule extends ReactContextBaseJavaModule implements LifecycleEventListener {

    private static final String RESUME = "onResume";
    private static final String PAUSE = "onPause";
    private static final String DESTROY = "onDestroy";
    private static final String EVENT = "onActivityStateChange";

    private String moduleName = null;

    BaseModule(String moduleName, ReactApplicationContext reactContext) {
        super(reactContext);
        reactContext.addLifecycleEventListener(this);
        this.moduleName = moduleName;
    }

    @Override
    public String getName() {
        return moduleName;
    }

    @Override
    public void onHostResume() {
        sendActivityState(RESUME);
    }

    @Override
    public void onHostPause() {
        sendActivityState(PAUSE);
    }

    @Override
    public void onHostDestroy() {
        sendActivityState(DESTROY);
    }

    private void sendActivityState(String change) {
        WritableMap params = Arguments.createMap();
        params.putString("onChange", change);
        sendEvent(getReactApplicationContext(), EVENT, params);
    }

    void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
