package abstractask.example.media.androidtest.suite;

import abstractask.example.media.androidtest.CalculatorAddParameterizedTest;
import abstractask.example.media.androidtest.CalculatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CalculatorTest.class, CalculatorAddParameterizedTest.class})
public class UnitTestSuite {}