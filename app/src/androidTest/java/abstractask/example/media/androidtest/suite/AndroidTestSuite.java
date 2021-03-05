package abstractask.example.media.androidtest.suite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite that runs all tests, unit + instrumentation tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({UnitTestSuite.class, InstrumentationTestSuite.class})
public class AndroidTestSuite {}
