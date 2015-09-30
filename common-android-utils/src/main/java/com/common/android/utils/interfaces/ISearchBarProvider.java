package common.android.utils.interfaces;

import android.support.v7.widget.SearchView;
import android.view.View;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Jan Rabe on 16/09/15.
 */
public interface ISearchBarProvider {
    @NotNull
    SearchView.OnQueryTextListener getOnQueryTextListener();

    @NotNull
    View.OnClickListener getOnCloseListener();

    @NotNull
    View.OnFocusChangeListener getOnFocusChangeListener();

    void setSearchView(SearchView mSearchView);

    void clearSearch();
}
