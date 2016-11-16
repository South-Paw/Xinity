package seng302.command.tutor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import seng302.Environment;
import seng302.util.enumerator.Error;

import java.util.Arrays;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Signature Tutor test class.
 */
@RunWith(MockitoJUnitRunner.class)
public class SignatureTutorTest {
    @Mock
    private Environment mockEnv;

    private String noArgsError = Error.SIGNATURETUTORNOARGSGIVEN.getError();
    private String tooManyArgsError = Error.SIGNATURETUTORTOOMANYARGSGIVEN.getError();
    private String invalidArgsError = Error.SIGNATURETUTORINVALIDARGUMENTS.getError();

    @Test
    public void noArgumentsGiven() {
        mockEnv = mock(Environment.class);
        new SignatureTutor().execute(mockEnv);
        verify(mockEnv).error(noArgsError);
    }

    @Test
    public void tooManyArgumentsGiven() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("A", "B", "C", "D")).execute(mockEnv);
        verify(mockEnv).error(tooManyArgsError);
    }

    @Test
    public void notEnoughArgumentsGiven() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("A")).execute(mockEnv);
        verify(mockEnv).error(noArgsError);
    }

    @Test
    public void badNumberOfQuestionsOne() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("5", "Notes", "Both")).execute(mockEnv);
        verify(mockEnv).error(invalidArgsError);
    }

    @Test
    public void badNumberOfQuestionsTwo() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("x-5", "Notes", "Both")).execute(mockEnv);
        verify(mockEnv).error(invalidArgsError);
    }

    @Test
    public void badNumberOfQuestionsThree() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("x0", "Notes", "Both")).execute(mockEnv);
        verify(mockEnv).error(invalidArgsError);
    }

    @Test
    public void badNumberOfQuestionsFour() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("bad", "Notes", "Both")).execute(mockEnv);
        verify(mockEnv).error(invalidArgsError);
    }

    @Test
    public void wrongSignatureSpecifier() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("bad", "Both")).execute(mockEnv);
        verify(mockEnv).error(invalidArgsError);
    }

    @Test
    public void wrongScaleNameType() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("Notes", "bad")).execute(mockEnv);
        verify(mockEnv).error(invalidArgsError);
    }

    @Test
    public void twoWrongArguments() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("bad", "worse")).execute(mockEnv);
        verify(mockEnv).error(invalidArgsError);
    }

    @Test
    public void threeWrongArguments() {
        mockEnv = mock(Environment.class);
        new SignatureTutor(Arrays.asList("fail", "bad", "worse")).execute(mockEnv);
        verify(mockEnv).error(invalidArgsError);
    }
}