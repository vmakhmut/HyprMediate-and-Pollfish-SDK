package com.reactnativecustom;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Base module with sendEvent()
 */

public class BaseModule extends ReactContextBaseJavaModule {

    private String moduleName = null;

    public BaseModule(String moduleName, ReactApplicationContext reactContext) {
        super(reactContext);
        this.moduleName = moduleName;
    }

    @Override
    public String getName() {
        return moduleName;
    }

    void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }
}
