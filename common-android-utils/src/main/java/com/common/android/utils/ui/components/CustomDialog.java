package common.android.utils.ui.components;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.adviqo.app.misc.FontCache;
import de.questico.app.R;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Jan Rabe on 27/07/15.
 */
public class CustomDialog extends Dialog {

    @NotNull
    final LinearLayout layout;
    @NotNull
    @Bind(R.id.title)
    TextView title;
    @NotNull
    @Bind(R.id.description)
    TextView description;
    @NotNull
    @Bind(R.id.ok)
    Button ok;
    @NotNull
    @Bind(R.id.concern)
    Button cancel;

    public CustomDialog(@NotNull final Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.custom_dialog, null);
        setContentView(layout);
        ButterKnife.bind(this, layout);
        setFonts();
        setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                dismiss();
            }
        });
    }

    private void setFonts() {
        title.setTypeface(FontCache.RosarioBold.getFont());
        description.setTypeface(FontCache.FontIstok.getFont());
        ok.setTypeface(FontCache.FontIstok.getFont());
        cancel.setTypeface(FontCache.FontIstok.getFont());
    }

    public void setOkClickListener(@NotNull final View.OnClickListener listener) {
        ok.setOnClickListener(listener);
    }

    public void setDescriptionText(final String text) {
        description.setText(text);
    }

    public void setCancelClickListener(@NotNull final View.OnClickListener listener) {
        cancel.setOnClickListener(listener);
    }

    public void setOkText(@NotNull final String okText) {
        ok.setText(okText);
    }

    public void setCancelText(@NotNull final String cancelText) {
        cancel.setText(cancelText);
    }

    public void setTitle(@NotNull final String title) {
        this.title.setText(title);
    }

    @Nullable
    public Button getCancelButton() {
        return cancel;
    }

    @Override
    public void show() {
        try {
            super.show();
            getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (Exception e) {
            // app dead
            e.printStackTrace();
        }
    }
}
