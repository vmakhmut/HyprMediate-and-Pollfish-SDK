package com.reactnativecustom;

import android.support.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Base module with sendEvent()
 */

public class BaseModule extends ReactContextBaseJavaModule {

    private DeviceEventManagerModule.RCTDeviceEventEmitter eventEmitter = null;
    private String moduleName = null;

    BaseModule(String moduleName, ReactApplicationContext reactContext) {
        super(reactContext);
        this.moduleName = moduleName;
    }

    @Override
    public void initialize() {
        super.initialize();
        eventEmitter = getReactApplicationContext().getJSModule(
                DeviceEventManagerModule.RCTDeviceEventEmitter.class);
    }

    @Override
    public String getName() {
        return moduleName;
    }

    public void sendEvent(String eventName, @Nullable WritableMap params) {
        eventEmitter.emit(eventName, params);
    }
}
