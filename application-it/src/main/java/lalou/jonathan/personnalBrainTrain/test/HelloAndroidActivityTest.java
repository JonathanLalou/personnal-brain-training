package lalou.jonathan.personnalBrainTrain.test;

import android.test.ActivityInstrumentationTestCase2;
import lalou.jonathan.personnalBrainTrain.activity.WelcomeActivity;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<WelcomeActivity> {

    public HelloAndroidActivityTest() {
        super(WelcomeActivity.class);
    }

    public void testActivity() {
        WelcomeActivity activity = getActivity();
        assertNotNull(activity);
    }
}

