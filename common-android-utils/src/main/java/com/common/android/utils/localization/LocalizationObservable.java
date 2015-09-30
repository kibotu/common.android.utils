package common.android.utils.localization;

import java.util.Observable;

/**
 * Created by franziska.huth on 15.06.15.
 */
public class LocalizationObservable extends Observable {

    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
}
