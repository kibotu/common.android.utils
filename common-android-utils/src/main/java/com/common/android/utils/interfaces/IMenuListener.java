package common.android.utils.interfaces;


import com.adviqo.app.ui.mainMenu.MainMenuItem;

public interface IMenuListener {

    void onMenuItemSelected(MainMenuItem item);

    void closeMenu();

}