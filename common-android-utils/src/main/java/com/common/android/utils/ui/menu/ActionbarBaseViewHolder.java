package com.common.android.utils.ui.menu;


import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.common.android.utils.R;
import com.common.android.utils.ui.BaseViewHolder;

public class ActionbarBaseViewHolder extends BaseViewHolder {

    @NonNull
    View iconHitBox;

    @NonNull
    ImageView homeIcon;

    @NonNull
    TextView title;

    public ActionbarBaseViewHolder(@NonNull final View itemView) {
        super(itemView);
        iconHitBox = itemView.findViewById(R.id.icon_layout);
        homeIcon = (ImageView) itemView.findViewById(R.id.home_icon);
        title = (TextView) itemView.findViewById(R.id.actionbar_title);
    }
}