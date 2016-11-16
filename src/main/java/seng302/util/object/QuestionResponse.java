package seng302.util.object;

import java.util.Map;

/**
 * Object for a schedule question.
 *
 * @author avh17.
 */
public class QuestionResponse {

    private String question;
    private Map<Object, Object> play;
    private String hint;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<Object, Object> getPlay() {
        return play;
    }

    public void setPlay(Map<Object, Object> play) {
        this.play = play;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
