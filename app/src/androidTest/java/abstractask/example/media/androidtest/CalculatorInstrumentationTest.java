package abstractask.example.media.androidtest;

import abstractask.example.media.R;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import junit.framework.TestSuite;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.builders.AllDefaultPossibilitiesBuilder;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * 다음 링크 참조
 * https://thdev.tech/androiddev/2016/05/04/Android-Test-Example/
 *
 * JUnit4 Ui Tests for {@link CalculatorActivity} using the {@link AndroidJUnitRunner}.
 * This class uses the JUnit4 syntax for tests.
 * <p>
 * With the new AndroidJUnit runner you can run both JUnit3 and JUnit4 tests in a single test
 * suite. The {@link AndroidRunnerBuilder} which extends JUnit's
 * {@link AllDefaultPossibilitiesBuilder} will create a single {@link
 * TestSuite} from all tests and run them.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CalculatorInstrumentationTest {

    @Rule
    public ActivityTestRule<CalculatorActivity> mActivityRule = new ActivityTestRule(CalculatorActivity.class);
    /*
    @Before
    public void launchActivity() {
        ActivityScenario.launch(CalculatorActivity.class);
    }
    */

    @Test
    public void noOperandShowsComputationError() {
        final String expectedResult = InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.computationError);
        onView(withId(R.id.operation_add_btn)).perform(click());
        onView(withId(R.id.operation_result_text_view)).check(matches(withText(expectedResult)));
    }

    @Test
    public void typeOperandsAndPerformAddOperation() {
        performOperation(R.id.operation_add_btn, "16.0", "16.0", "32.0");
    }

    @Test
    public void typeOperandsAndPerformSubOperation() {
        performOperation(R.id.operation_sub_btn, "32.0", "16.0", "16.0");
    }

    @Test
    public void typeOperandsAndPerformDivOperation() {
        performOperation(R.id.operation_div_btn, "128.0", "16.0", "8.0");
    }

    @Test
    public void divZeroForOperandTwoShowsError() {
        final String expectedResult = InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.computationError);
        performOperation(R.id.operation_div_btn, "128.0", "0.0", expectedResult);
    }

    @Test
    public void typeOperandsAndPerformMulOperation() {
        performOperation(R.id.operation_mul_btn, "16.0", "16.0", "256.0");
    }

    private void performOperation(int btnOperationResId, String operandOne,
                                  String operandTwo, String expectedResult) {
        // Type the two operands in the EditText fields
        onView(withId(R.id.operand_one_edit_text)).perform(typeText(operandOne),
                closeSoftKeyboard());
        onView(withId(R.id.operand_two_edit_text)).perform(typeText(operandTwo),
                closeSoftKeyboard());

        // Click on a given operation button
        onView(withId(btnOperationResId)).perform(click());

        // Check the expected test is displayed in the Ui
        onView(withId(R.id.operation_result_text_view)).check(matches(withText(expectedResult)));
    }

}