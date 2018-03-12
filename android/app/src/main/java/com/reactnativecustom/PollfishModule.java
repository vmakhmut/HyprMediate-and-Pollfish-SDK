package com.reactnativecustom;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.pollfish.constants.Position;
import com.pollfish.constants.UserProperties;
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

    private static final String REACT_CLASS = "PollfishModule";
    private static final String EVENT = "onPollfishEvent";

    private static final int POSITION_TOP_LEFT = 0;
    private static final int POSITION_BOTTOM_LEFT = 1;
    private static final int POSITION_TOP_RIGHT = 2;
    private static final int POSITION_BOTTOM_RIGHT = 3;
    private static final int POSITION_MIDDLE_LEFT = 4;
    private static final int POSITION_MIDDLE_RIGHT = 5;

    private String apiKey = null;
    private String uid = null;
    private int position = -1;

    private UserProperties userProperties;

    public PollfishModule(ReactApplicationContext reactContext) {
        super(REACT_CLASS, reactContext);
    }

    //first initialize
    @ReactMethod
    public void initialize(String apiKey, String uid, int i) {
        PollFish.initWith(getCurrentActivity(), new PollFish.ParamsBuilder(apiKey)
                .requestUUID(uid)
                .pollfishSurveyNotAvailableListener(this)
                .pollfishClosedListener(this)
                .pollfishSurveyReceivedListener(this)
                .pollfishOpenedListener(this)
                .pollfishSurveyNotAvailableListener(this)
                .pollfishSurveyCompletedListener(this)
                .pollfishUserNotEligibleListener(this)
                .indicatorPosition(Position.values()[i])
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

    @ReactMethod
    public void setUserProperties() {
        userProperties = new UserProperties()
                .setGender(UserProperties.Gender.MALE)
                .setYearOfBirth(UserProperties.YearOfBirth._1984)
                .setMaritalStatus(UserProperties.MaritalStatus.SINGLE)
                .setParentalStatus(UserProperties.ParentalStatus.ZERO)
                .setEducation(UserProperties.EducationLevel.UNIVERSITY)
                .setEmployment(UserProperties.EmploymentStatus.EMPLOYED_FOR_WAGES)
                .setCareer(UserProperties.Career.TELECOMMUNICATIONS)
                .setRace(UserProperties.Race.WHITE)
                .setIncome(UserProperties.Income.MIDDLE_I)

                .setEmail("user_email@test.com")
                .setFacebookId("USER_FB")
                .setGoogleId("USER_GOOGLE")
                .setTwitterId("USER_TWITTER")
                .setLinkedInId("USER_LINKEDIN")
                .setPhone("USER_PHONE")
                .setName("USER_NAME")
                .setSurname("USER_SURNAME")
                .setCustomAttributes("MY_PARAM", "MY_VALUE");
    }

    @Override
    public Map<String, Object> getConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("top_left", POSITION_TOP_LEFT);
        constants.put("bottom_left", POSITION_BOTTOM_LEFT);
        constants.put("top_right", POSITION_TOP_RIGHT);
        constants.put("bottom_right", POSITION_BOTTOM_RIGHT);
        constants.put("middle_left", POSITION_MIDDLE_LEFT);
        constants.put("middle_right", POSITION_MIDDLE_RIGHT);
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
        sendEvent(getReactApplicationContext(), EVENT, params);
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
