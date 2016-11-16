package seng302.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;

import static org.mockito.Mockito.verify;

/**
 * Null Command tests.
 *
 * @author adg62
 */
@RunWith(MockitoJUnitRunner.class)
public class NullCommandTest {
    @Mock private Environment mockEnv;

    @Test
    public void testNullCommandExecutes() {
        new NullCommand().execute(mockEnv);

        verify(mockEnv).error("Invalid command.");
    }
}
