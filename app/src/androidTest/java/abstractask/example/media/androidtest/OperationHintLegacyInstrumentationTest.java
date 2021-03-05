package abstractask.example.media.androidtest;

import abstractask.example.media.R;
import android.support.test.filters.LargeTest;
import android.test.ActivityInstrumentationTestCase2;

import static abstractask.example.media.androidtest.HintMatcher.withHint;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by jw-labtop on 2021-01-23.
 */
@LargeTest
public class OperationHintLegacyInstrumentationTest
        extends ActivityInstrumentationTestCase2<CalculatorActivity> {

    private CalculatorActivity mActivity;

    public OperationHintLegacyInstrumentationTest() {
        super(CalculatorActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Espresso does not start the Activity for you we need to do this manually here.
        mActivity = getActivity();
    }

    public void testPreconditions() {
        assertThat(mActivity, notNullValue());
    }

    public void testEditText_OperandOneHint() {
        String operandOneHint = mActivity.getString(R.string.type_operand_one_hint);
        onView(withId(R.id.operand_one_edit_text)).check(matches(withHint(operandOneHint)));
    }

    public void testEditText_OperandTwoHint() {
        String operandTwoHint = mActivity.getString(R.string.type_operant_two_hint);
        onView(withId(R.id.operand_two_edit_text)).check(matches(withHint(operandTwoHint)));
    }

}