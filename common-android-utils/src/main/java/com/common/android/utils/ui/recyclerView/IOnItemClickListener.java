package common.android.utils.ui.recyclerView;

import android.view.View;

public interface IOnItemClickListener<T> {
    void onItemClick(T item, View rowView, int position);
}