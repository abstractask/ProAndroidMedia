package abstractask.example.media.androidtest.suite;

import abstractask.example.media.androidtest.CalculatorInstrumentationTest;
import abstractask.example.media.androidtest.OperationHintLegacyInstrumentationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all Junit3 and Junit4 Instrumentation tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({CalculatorInstrumentationTest.class, OperationHintLegacyInstrumentationTest.class})
public class InstrumentationTestSuite {}
