package common.android.utils.interfaces;

import com.adviqo.app.model.UserProfile;

/**
 * Created by conrad on 2015/03/16.
 */
public interface ILoginListener {

    void onSuccessfulLogin(String userName, String password, UserProfile userProfile);
}
