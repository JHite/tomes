package com.hitej.android.metalarchives;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by jhite on 6/21/16.
 *
 * To be continued once RxJava research is completed.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MASearchFragmentTest {


    public class HelloWorldEspressoTest {

        @Rule
        public ActivityTestRule<MASearchActivity> mActivityRule = new ActivityTestRule(MASearchResultActivity.class);

        @Test
        public void submitSearch() {

            //onView(withId(R.id.editTextUserInput))
                    //.perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());

// Press the button to submit the text change
            //onView(withId(R.id.changeTextBt)).perform(click());

        }
    }
}
