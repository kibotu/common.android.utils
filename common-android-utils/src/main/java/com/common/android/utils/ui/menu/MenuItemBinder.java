package com.common.android.utils.ui.menu;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.common.android.utils.R;
import com.common.android.utils.ui.BaseViewHolder;
import com.common.android.utils.ui.recyclerView.Presenter;
import com.common.android.utils.ui.recyclerView.PresenterAdapter;

import static com.common.android.utils.ContextHelper.getAppCompatActivity;
import static com.common.android.utils.ContextHelper.getContext;

/**
 * Created by Nyaruhodo on 20.02.2016.
 */
public class MenuItemBinder extends Presenter<MenuItem, MenuItemBinder.ViewHolder> {

    public MenuItemBinder(@NonNull final PresenterAdapter<MenuItem> presenterAdapter) {
        super(presenterAdapter);
    }

    @Override
    public int getLayout() {
        return R.layout.menu_item;
    }

    @NonNull
    @Override
    protected ViewHolder createViewHolder(final int layout, final ViewGroup parent) {
        return new ViewHolder(layout, parent);
    }

    @Override
    public void bindViewHolder(@NonNull ViewHolder viewHolder, MenuItem item, int position) {
        Glide.with(getContext())
                .load(item.icon)
                .into(viewHolder.icon);

        viewHolder.label.setText(getContext().getString(item.name));

        viewHolder.itemView.setOnClickListener(v -> {
            final Fragment fragment = item.getFragment();
            getAppCompatActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(fragment.getTag())
                    .commit();
            MainMenuProvider.provide().closeDrawers();
        });
    }

    public static class ViewHolder extends BaseViewHolder {

        @NonNull
        ImageView icon;

        @NonNull
        TextView label;

        public ViewHolder(@LayoutRes final int layout, @Nullable final ViewGroup parent) {
            super(layout, parent);

            icon = (ImageView) itemView.findViewById(R.id.icon);
            label = (TextView) itemView.findViewById(R.id.label);
        }
    }
}
