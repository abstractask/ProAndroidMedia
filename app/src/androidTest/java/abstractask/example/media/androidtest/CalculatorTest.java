package abstractask.example.media.androidtest;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class CalculatorTest {

    private Calculator mCalculator;

    @Before
    public void setUp() {
        mCalculator = new Calculator();
    }

    @Test
    public void addTwoNumbers() {
        double resultAdd = mCalculator.add(1d, 1d);
        assertThat(resultAdd, is(equalTo(2d)));
    }

    @Test
    public void subTwoNumbers() {
        double resultSub = mCalculator.sub(1d, 1d);
        assertThat(resultSub, is(equalTo(0d)));
    }

    @Test
    public void subWorksWithNegativeResult() {
        double resultSub = mCalculator.sub(1d, 17d);
        assertThat(resultSub, is(equalTo(-16d)));
    }

    @Test
    public void divTwoNumbers() {
        double resultDiv = mCalculator.div(32d, 2d);
        assertThat(resultDiv, is(equalTo(16d)));
    }



    @Test(expected = IllegalArgumentException.class)
    public void divDivideByZeroThrows() {
        mCalculator.div(32d, 0d);
    }

    @Test
    public void mulTwoNumbers() {
        double resultMul = mCalculator.mul(32d, 2d);
        assertThat(resultMul, is(equalTo(64d)));
    }

}