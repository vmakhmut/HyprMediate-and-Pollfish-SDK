package com.reactnativecustom;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.pollfish.constants.Position;
import com.pollfish.interfaces.PollfishClosedListener;
import com.pollfish.interfaces.PollfishOpenedListener;
import com.pollfish.interfaces.PollfishSurveyCompletedListener;
import com.pollfish.interfaces.PollfishSurveyNotAvailableListener;
import com.pollfish.interfaces.PollfishSurveyReceivedListener;
import com.pollfish.interfaces.PollfishUserNotEligibleListener;
import com.pollfish.main.PollFish;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by name on 06.03.2018.
 */

public class PollfishModule extends BaseModule implements PollfishSurveyNotAvailableListener,
        PollfishSurveyCompletedListener, PollfishOpenedListener, PollfishClosedListener,
        PollfishUserNotEligibleListener, PollfishSurveyReceivedListener {

    private static final String REACT_CLASS = PollfishModule.class.getName();
    private static final String EVENT = "onPollfishEvent";

    private static final String POSITION_TOP_LEFT = "top_left";
    private static final String POSITION_BOTTOM_LEFT = "bottom_left";
    private static final String POSITION_TOP_RIGHT = "top_right";
    private static final String POSITION_BOTTOM_RIGHT = "bottom_right";
    private static final String POSITION_MIDDLE_LEFT = "middle_left";
    private static final String POSITION_MIDDLE_RIGHT = "middle_right";

    public PollfishModule(ReactApplicationContext reactContext) {
        super(REACT_CLASS, reactContext);
    }

    //reinitialize on configuration change
    @ReactMethod
    public void initialize(String apiKey, String uid) {
        PollFish.initWith(getCurrentActivity(), new PollFish.ParamsBuilder(apiKey)
                .requestUUID(uid)
                .build());
    }

    //first initialize
    @ReactMethod
    public void initialize(String apiKey, String uid, Position position) {
        PollFish.initWith(getCurrentActivity(), new PollFish.ParamsBuilder(apiKey)
                .requestUUID(uid)
                .pollfishSurveyNotAvailableListener(this)
                .pollfishClosedListener(this)
                .pollfishSurveyReceivedListener(this)
                .pollfishOpenedListener(this)
                .pollfishSurveyNotAvailableListener(this)
                .pollfishSurveyCompletedListener(this)
                .pollfishUserNotEligibleListener(this)
                .indicatorPosition(position)
                .build());
    }

    //show Pollfish button on current Activity
    @ReactMethod
    public void show() {
        PollFish.show();
    }

    //hide Pollfish button on current Activity
    @ReactMethod
    public void hide() {
        PollFish.hide();
    }

    //
    @ReactMethod
    public void isPollfishPresent() {
        WritableMap params = Arguments.createMap();
        params.putBoolean("isPollFishPresent", PollFish.isPollfishPresent());
        sendEvent(getReactApplicationContext(), EVENT, params);
    }

    @ReactMethod
    public void isPollfishPanelOpen() {
        WritableMap params = Arguments.createMap();
        params.putBoolean("isPollfishPanelOpen", PollFish.isPollfishPanelOpen());
        sendEvent(getReactApplicationContext(), EVENT, params);
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(POSITION_TOP_LEFT, Position.TOP_LEFT);
        constants.put(POSITION_BOTTOM_LEFT, Position.BOTTOM_LEFT);
        constants.put(POSITION_TOP_RIGHT, Position.TOP_RIGHT);
        constants.put(POSITION_BOTTOM_RIGHT, Position.BOTTOM_RIGHT);
        constants.put(POSITION_MIDDLE_LEFT, Position.MIDDLE_LEFT);
        constants.put(POSITION_MIDDLE_RIGHT, Position.MIDDLE_RIGHT);
        return constants;
    }

    //sets a notification listener when Pollfish Survey is received
    @Override
    public void onPollfishSurveyCompleted(boolean b, int i) {
        WritableMap params = Arguments.createMap();
        params.putBoolean("playfulSurveys", b);
        params.putInt("surveyPrice", i);
        sendEvent(getReactApplicationContext(), "onPollfishSurveyCompleted", params);
    }

    //sets a notification listener when Pollfish Survey is not available
    @Override
    public void onPollfishSurveyNotAvailable() {
        WritableMap params = Arguments.createMap();
        params.putString("pollfishState", "PollfishSurveyNotAvailable");
        sendEvent(getReactApplicationContext(), "onPollfishSurveyNotAvailable", params);
    }

    //Sets a notification listener when Pollfish Survey panel is closed
    @Override
    public void onPollfishClosed() {
        WritableMap params = Arguments.createMap();
        params.putString("pollfishState", "Closed");
        sendEvent(getReactApplicationContext(), EVENT, params);
    }

    //sets a notification listener when Pollfish Survey panel is opened
    @Override
    public void onPollfishOpened() {
        WritableMap params = Arguments.createMap();
        params.putString("pollfishState", "Opened");
        sendEvent(getReactApplicationContext(), EVENT, params);
    }

    //sets a notification listener when a user is not eligible for a Pollfish survey
    @Override
    public void onUserNotEligible() {
        WritableMap params = Arguments.createMap();
        params.putString("onUserNotEligible", "onUserNotEligible");
        sendEvent(getReactApplicationContext(), EVENT, params);
    }

    //sets a notification listener when Pollfish Survey is completed
    @Override
    public void onPollfishSurveyReceived(boolean b, int i) {
        WritableMap params = Arguments.createMap();
        params.putBoolean("playfulSurveys", b);
        params.putInt("surveyPrice", i);
        sendEvent(getReactApplicationContext(), "onPollfishSurveyReceived", params);
    }
}
