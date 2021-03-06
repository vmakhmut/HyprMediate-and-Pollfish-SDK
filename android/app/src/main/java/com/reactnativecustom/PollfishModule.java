package com.reactnativecustom;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.pollfish.constants.Position;
import com.pollfish.constants.SurveyFormat;
import com.pollfish.constants.UserProperties;
import com.pollfish.interfaces.PollfishClosedListener;
import com.pollfish.interfaces.PollfishOpenedListener;
import com.pollfish.interfaces.PollfishSurveyCompletedListener;
import com.pollfish.interfaces.PollfishSurveyNotAvailableListener;
import com.pollfish.interfaces.PollfishSurveyReceivedListener;
import com.pollfish.interfaces.PollfishUserNotEligibleListener;
import com.pollfish.main.PollFish;

import java.util.Collections;
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

    private String GENDER = "gender";
    private String YEAR_OF_BIRTH = "year_of_birth";
    private String MARITAL_STATUS = "marital_status";
    private String PARENTAL = "parental";
    private String EDUCATION = "education";
    private String EMPLOYMENT = "employment";
    private String CAREER = "career";
    private String RACE = "race";
    private String INCOME = "income";
    private String EMAIL = "email";
    private String GOOGLE_ID = "google_id";
    private String LINKEDIN_ID = "linkedin_id";
    private String TWITTER_ID = "twitter_id";
    private String FACEBOOK_ID = "facebook_id";
    private String PHONE = "phone";
    private String NAME = "name";
    private String SURNAME = "surname";

    public PollfishModule(ReactApplicationContext reactContext) {
        super(REACT_CLASS, reactContext);
    }

    //sets a notification listener when Pollfish Survey is not available
    private PollfishSurveyNotAvailableListener pollfishSurveyNotAvailableListener = new PollfishSurveyNotAvailableListener() {
        @Override
        public void onPollfishSurveyNotAvailable() {
            WritableMap params = Arguments.createMap();
            params.putString("pollfishState", "onPollfishSurveyNotAvailable");
            sendEvent(EVENT, params);
        }
    };

    //Sets a notification listener when Pollfish Survey panel is closed
    private PollfishClosedListener pollfishClosedListener = new PollfishClosedListener() {
        @Override
        public void onPollfishClosed() {
            WritableMap params = Arguments.createMap();
            params.putString("pollfishPanel", "onPollfishClosed");
            sendEvent(EVENT, params);
        }
    };

    //sets a notification listener when Pollfish Survey is completed
    private PollfishSurveyReceivedListener pollfishSurveyReceivedListener = new PollfishSurveyReceivedListener() {
        @Override
        public void onPollfishSurveyReceived(boolean b, int i) {
            WritableMap params = Arguments.createMap();
            params.putBoolean("playfulSurveyReceived", b);
            params.putInt("surveyPriceReceived", i);
            sendEvent(EVENT, params);
        }
    };

    //sets a notification listener when Pollfish Survey panel is opened
    private PollfishOpenedListener pollfishOpenedListener = new PollfishOpenedListener() {
        @Override
        public void onPollfishOpened() {
            WritableMap params = Arguments.createMap();
            params.putString("pollfishPanel", "onPollfishOpened");
            sendEvent(EVENT, params);
        }
    };

    //sets a notification listener when Pollfish Survey is received
    private PollfishSurveyCompletedListener pollfishSurveyCompletedListener = new PollfishSurveyCompletedListener() {
        @Override
        public void onPollfishSurveyCompleted(boolean b, int i) {
            WritableMap params = Arguments.createMap();
            params.putBoolean("playfulSurveyCompleted", b);
            params.putInt("surveyPriceCompleted", i);
            sendEvent(EVENT, params);
        }
    };

    //sets a notification listener when a user is not eligible for a Pollfish survey
    private PollfishUserNotEligibleListener pollfishUserNotEligibleListener = new PollfishUserNotEligibleListener() {
        @Override
        public void onUserNotEligible() {
            WritableMap params = Arguments.createMap();
            params.putString("onUserNotEligible", "onUserNotEligible");
            sendEvent(EVENT, params);
        }
    };

    private void initPollfish(final PollFish.ParamsBuilder params) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                PollFish.initWith(getCurrentActivity(), params);
                hide();
            }
        });
    }

    private PollFish.ParamsBuilder getPollfishParams(final String apiKey, final String uid,
                                                     final boolean isReleaseMode, final String surveyFormat) {
        return new PollFish.ParamsBuilder(apiKey)
                .customMode(true)
                .releaseMode(isReleaseMode)
                .requestUUID(uid)
                .surveyFormat(SurveyFormat.valueOf(surveyFormat))
                .pollfishSurveyNotAvailableListener(pollfishSurveyNotAvailableListener)
                .pollfishClosedListener(pollfishClosedListener)
                .pollfishSurveyReceivedListener(pollfishSurveyReceivedListener)
                .pollfishOpenedListener(pollfishOpenedListener)
                .pollfishSurveyCompletedListener(pollfishSurveyCompletedListener)
                .pollfishUserNotEligibleListener(pollfishUserNotEligibleListener);
    }

    //initialize
    @ReactMethod
    public void initialize(final String apiKey, final String uid, final boolean isReleaseMode,
                           @Nullable final String surveyFormat) {
        initPollfish(getPollfishParams(apiKey, uid, isReleaseMode, surveyFormat).build());
    }

    //initialize with position on screen
    @ReactMethod
    public void initializeWithPosition(final String apiKey, final String uid, final boolean isReleaseMode,
                                       @Nullable final String surveyFormat, final int position) {
        initPollfish(getPollfishParams(apiKey, uid, isReleaseMode, surveyFormat)
                .customMode(false)
                .indicatorPosition(Position.values()[position])
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
        sendEvent(EVENT, params);
    }

    @ReactMethod
    public void isPollfishPanelOpen() {
        WritableMap params = Arguments.createMap();
        params.putBoolean("isPollfishPanelOpen", PollFish.isPollfishPanelOpen());
        sendEvent(EVENT, params);
    }

    @ReactMethod
    public void setUserProperties(WritableMap map) {
        UserProperties userProperties = new UserProperties();

        if (map.hasKey(GENDER))
            userProperties.setGender(map.getString(GENDER)); //UserProperties.Gender.MALE

        if (map.hasKey(YEAR_OF_BIRTH))
            userProperties.setYearOfBirth(map.getString(YEAR_OF_BIRTH)); //UserProperties.YearOfBirth._1984

        if (map.hasKey(MARITAL_STATUS))
            userProperties.setMaritalStatus(map.getString(MARITAL_STATUS)); //UserProperties.MaritalStatus.SINGLE

        if (map.hasKey(PARENTAL))
            userProperties.setParentalStatus(map.getString(PARENTAL)); //UserProperties.ParentalStatus.ZERO

        if (map.hasKey(EDUCATION))
            userProperties.setEducation(map.getString(EDUCATION)); //UserProperties.EducationLevel.UNIVERSITY

        if (map.hasKey(CAREER))
            userProperties.setCareer(map.getString(CAREER)); //UserProperties.Career.TELECOMMUNICATIONS

        if (map.hasKey(EMPLOYMENT))
            userProperties.setEmployment(map.getString(EMPLOYMENT)); //UserProperties.EmploymentStatus.EMPLOYED_FOR_WAGES

        if (map.hasKey(RACE))
            userProperties.setRace(map.getString(RACE)); //UserProperties.Race.WHITE

        if (map.hasKey(INCOME))
            userProperties.setIncome(map.getString(INCOME)); //UserProperties.Income.MIDDLE_I

        //not selectable
        if (map.hasKey(EMAIL))
            userProperties.setEmail(map.getString(EMAIL));

        if (map.hasKey(FACEBOOK_ID))
            userProperties.setFacebookId(map.getString(FACEBOOK_ID));

        if (map.hasKey(GOOGLE_ID))
            userProperties.setGoogleId(map.getString(GOOGLE_ID));

        if (map.hasKey(TWITTER_ID))
            userProperties.setTwitterId(map.getString(TWITTER_ID));

        if (map.hasKey(LINKEDIN_ID))
            userProperties.setLinkedInId(map.getString(LINKEDIN_ID));

        if (map.hasKey(PHONE))
            userProperties.setPhone(map.getString(PHONE));

        if (map.hasKey(NAME))
            userProperties.setName(map.getString(NAME));

        if (map.hasKey(SURNAME))
            userProperties.setSurname(map.getString(SURNAME));
    }

    @Override
    public Map<String, Object> getConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("Position", getPositionConstants());
                put("SurveyFormat", getSurveyFormatConstants());
                //put("UserProperties", getUserPropertiesNamesConstants());
            }
        });
    }

    private Map<String, Object> getPositionConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("TOP_LEFT", POSITION_TOP_LEFT);
        constants.put("BOTTOM_LEFT", POSITION_BOTTOM_LEFT);
        constants.put("TOP_RIGHT", POSITION_TOP_RIGHT);
        constants.put("BOTTOM_RIGHT", POSITION_BOTTOM_RIGHT);
        constants.put("MIDDLE_LEFT", POSITION_MIDDLE_LEFT);
        constants.put("MIDDLE_RIGHT", POSITION_MIDDLE_RIGHT);
        return constants;
    }

    private Map<String, Object> getSurveyFormatConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put("BASIC", SurveyFormat.BASIC.toString());
        constants.put("PLAYFUL", SurveyFormat.PLAYFUL.toString());
        constants.put("THIRD_PARTY", SurveyFormat.THIRD_PARTY.toString());
        constants.put("RANDOM", SurveyFormat.RANDOM.toString());
        return constants;
    }

    private Map<String, Object> getUserPropertiesNamesConstants() {
        final Map<String, Object> constants = new HashMap<>();
        constants.put(GENDER, GENDER);
        constants.put(YEAR_OF_BIRTH, YEAR_OF_BIRTH);
        constants.put(MARITAL_STATUS, MARITAL_STATUS);
        constants.put(PARENTAL, PARENTAL);
        constants.put(EDUCATION, EDUCATION);
        constants.put(EMPLOYMENT, EMPLOYMENT);
        constants.put(CAREER, CAREER);
        constants.put(RACE, RACE);
        constants.put(INCOME, INCOME);
        constants.put(EMAIL, EMAIL);
        constants.put(GOOGLE_ID, GOOGLE_ID);
        constants.put(LINKEDIN_ID, LINKEDIN_ID);
        constants.put(TWITTER_ID, TWITTER_ID);
        constants.put(FACEBOOK_ID, FACEBOOK_ID);
        constants.put(PHONE, PHONE);
        constants.put(NAME, NAME);
        constants.put(SURNAME, SURNAME);
        return constants;
    }

    private Map<String, Object> getUserGenderConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("MALE", UserProperties.Gender.MALE);
                put("FEMALE", UserProperties.Gender.FEMALE);
                put("OTHER", UserProperties.Gender.OTHER);
            }
        });
    }

    private Map<String, Object> getUserMarutalStatusConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("SINGLE", UserProperties.MaritalStatus.SINGLE);
                put("MARRIED", UserProperties.MaritalStatus.MARRIED);
                put("DIVORCED", UserProperties.MaritalStatus.DIVORCED);
                put("LIVING_WITH_PARTNER", UserProperties.MaritalStatus.LIVING_WITH_PARTNER);
                put("SEPARATED", UserProperties.MaritalStatus.SEPARATED);
                put("WIDOWED", UserProperties.MaritalStatus.WIDOWED);
                put("PREFER_NOT_TO_SAY", UserProperties.MaritalStatus.PREFER_NOT_TO_SAY);
            }
        });
    }

    private Map<String, Object> getUserParentalConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("ZERO", UserProperties.ParentalStatus.ZERO);
                put("ONE", UserProperties.ParentalStatus.ONE);
                put("TWO", UserProperties.ParentalStatus.TWO);
                put("THREE", UserProperties.ParentalStatus.THREE);
                put("FOUR", UserProperties.ParentalStatus.FOUR);
                put("FIVE", UserProperties.ParentalStatus.FIVE);
                put("SIX_OR_MORE", UserProperties.ParentalStatus.SIX_OR_MORE);
                put("PREFER_NOT_TO_SAY", UserProperties.ParentalStatus.PREFER_NOT_TO_SAY);
            }
        });
    }

    private Map<String, Object> getUserEducationConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("ELEMENTARY_SCHOOL", UserProperties.EducationLevel.ELEMENTARY_SCHOOL);
                put("MIDDLE_SCHOOL", UserProperties.EducationLevel.MIDDLE_SCHOOL);
                put("HIGH_SCHOOL", UserProperties.EducationLevel.HIGH_SCHOOL);
                put("VOCATIONAL_TECHNICAL_COLLEGE", UserProperties.EducationLevel.VOCATIONAL_TECHNICAL_COLLEGE);
                put("UNIVERSITY", UserProperties.EducationLevel.UNIVERSITY);
                put("POST_GRADUATE", UserProperties.EducationLevel.POST_GRADUATE);
            }
        });
    }

    private Map<String, Object> getUserEmploymentConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("EMPLOYED_FOR_WAGES", UserProperties.EmploymentStatus.EMPLOYED_FOR_WAGES);
                put("SELF_EMPLOYED", UserProperties.EmploymentStatus.SELF_EMPLOYED);
                put("UNEMPLOYED_LOOKING", UserProperties.EmploymentStatus.UNEMPLOYED_LOOKING);
                put("UNEMPLOYED_NOT_LOOKING", UserProperties.EmploymentStatus.UNEMPLOYED_NOT_LOOKING);
                put("HOMEMAKER", UserProperties.EmploymentStatus.HOMEMAKER);
                put("STUDENT", UserProperties.EmploymentStatus.STUDENT);
                put("MILITARY", UserProperties.EmploymentStatus.MILITARY);
                put("RETIRED", UserProperties.EmploymentStatus.RETIRED);
                put("UNABLE_TO_WORK", UserProperties.EmploymentStatus.UNABLE_TO_WORK);
                put("OTHER", UserProperties.EmploymentStatus.OTHER);
            }
        });
    }

    private Map<String, Object> getUserRaceConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("ARAB", UserProperties.Race.ARAB);
                put("ASIAN", UserProperties.Race.ASIAN);
                put("BLACK", UserProperties.Race.BLACK);
                put("WHITE", UserProperties.Race.WHITE);
                put("HISPANIC", UserProperties.Race.HISPANIC);
                put("LATINO", UserProperties.Race.LATINO);
                put("MULTIRACIAL", UserProperties.Race.MULTIRACIAL);
                put("OTHER", UserProperties.Race.OTHER);
                put("PREFER_NOT_TO_SAY", UserProperties.Race.PREFER_NOT_TO_SAY);
            }
        });
    }

    private Map<String, Object> getUserIncomeConstants() {
        return Collections.unmodifiableMap(new HashMap<String, Object>() {
            {
                put("LOWER_I", UserProperties.Income.LOWER_I);
                put("LOWER_II", UserProperties.Income.LOWER_II);
                put("MIDDLE_I", UserProperties.Income.MIDDLE_I);
                put("MIDDLE_II", UserProperties.Income.MIDDLE_II);
                put("HIGH_I", UserProperties.Income.HIGH_I);
                put("HIGH_II", UserProperties.Income.HIGH_II);
                put("HIGH_III", UserProperties.Income.HIGH_III);
                put("PREFER_NOT_TO_SAY", UserProperties.Income.PREFER_NOT_TO_SAY);
            }
        });
    }
}
