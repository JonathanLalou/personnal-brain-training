package lalou.jonathan.personnalBrainTrain.application;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA "Leda" 12 CE.
 * User: Jonathan LALOU
 * Date: 15/04/13
 * Time: 18:46
 */
public class StateMemento implements Serializable {
    private Integer score;
    private PlayMode playMode;

    public StateMemento() {
        reset();
    }

    public void incrementScore() {
        score++;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public PlayMode getPlayMode() {
        return playMode;
    }

    public void setPlayMode(PlayMode playMode) {
        this.playMode = playMode;
    }

    public void reset() {
        score = 0;
        playMode = null;
    }
}
