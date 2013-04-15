package lalou.jonathan.personnalBrainTrain.application;

import android.app.Application;

/**
 * Created with IntelliJ IDEA "Leda" 12 CE.
 * User: Jonathan LALOU
 * Date: 15/04/13
 * Time: 17:47
 */
public class PersonnalBrainTrainApplication extends Application {
    StateMemento stateMemento;

    @Override
    public void onCreate() {
        super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
        stateMemento = new StateMemento();
    }

    public StateMemento getStateMemento() {
        return stateMemento;
    }
}
