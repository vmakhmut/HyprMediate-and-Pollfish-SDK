package com.reactnativecustom;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

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

public class PollfishModule extends BaseModule {

    private static final String REACT_CLASS = "PollfishModule";
    private static final String EVENT = "onPollfishEvent";

    private static final int POSITION_TOP_LEFT = 0;
    private static final int POSITION_BOTTOM_LEFT = 1;
    private static final int POSITION_TOP_RIGHT = 2;
    private static final int POSITION_BOTTOM_RIGHT = 3;
    private static final int POSITION_MIDDLE_LEFT = 4;
    private static final int POSITION_MIDDLE_RIGHT = 5;

    private UserProperties userProperties;

    public PollfishModule(ReactApplicationContext reactContext) {
        super(REACT_CLASS, reactContext);
    }

    //first initialize
    @ReactMethod
    public void initialize(final String apiKey, final String uid, final int position) {

        Log.d(getName(), "initialize whith apiKey = " + apiKey);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                PollFish.initWith(getCurrentActivity(), new PollFish.ParamsBuilder(apiKey)
                        .customMode(true)
                        .requestUUID(uid)

                        //sets a notification listener when Pollfish Survey is not available
                        .pollfishSurveyNotAvailableListener(new PollfishSurveyNotAvailableListener() {
                            @Override
                            public void onPollfishSurveyNotAvailable() {
                                WritableMap params = Arguments.createMap();
                                params.putString("pollfishState", "onPollfishSurveyNotAvailable");
                                sendEvent(EVENT, params);
                            }
                        })
                        //Sets a notification listener when Pollfish Survey panel is closed
                        .pollfishClosedListener(new PollfishClosedListener() {
                            @Override
                            public void onPollfishClosed() {
                                WritableMap params = Arguments.createMap();
                                params.putString("pollfishState", "onPollfishClosed");
                                sendEvent(EVENT, params);
                            }
                        })
                        //sets a notification listener when Pollfish Survey is completed
                        .pollfishSurveyReceivedListener(new PollfishSurveyReceivedListener() {
                            @Override
                            public void onPollfishSurveyReceived(boolean b, int i) {
                                WritableMap params = Arguments.createMap();
                                params.putBoolean("playfulSurveysReceived", b);
                                params.putInt("surveyPriceReceived", i);
                                sendEvent(EVENT, params);
                            }
                        })
                        //sets a notification listener when Pollfish Survey panel is opened
                        .pollfishOpenedListener(new PollfishOpenedListener() {
                            @Override
                            public void onPollfishOpened() {
                                WritableMap params = Arguments.createMap();
                                params.putString("pollfishState", "onPollfishOpened");
                                sendEvent(EVENT, params);
                            }
                        })
                        //sets a notification listener when Pollfish Survey is received
                        .pollfishSurveyCompletedListener(new PollfishSurveyCompletedListener() {
                            @Override
                            public void onPollfishSurveyCompleted(boolean b, int i) {
                                WritableMap params = Arguments.createMap();
                                params.putBoolean("playfulSurveysCompleted", b);
                                params.putInt("surveyPriceCompleted", i);
                                sendEvent(EVENT, params);
                            }
                        })
                        //sets a notification listener when a user is not eligible for a Pollfish survey
                        .pollfishUserNotEligibleListener(new PollfishUserNotEligibleListener() {
                            @Override
                            public void onUserNotEligible() {
                                WritableMap params = Arguments.createMap();
                                params.putString("onUserNotEligible", "onUserNotEligible");
                                sendEvent(EVENT, params);
                            }
                        })
                        .indicatorPosition(Position.values()[position])
                        .build());
            }
        });
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
        sendEvent(EVENT, params);
    }

    @ReactMethod
    public void isPollfishPanelOpen() {
        WritableMap params = Arguments.createMap();
        params.putBoolean("isPollfishPanelOpen", PollFish.isPollfishPanelOpen());
        sendEvent(EVENT, params);
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
}
