package com.google.android.gms.cast.framework.internal.featurehighlight;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import com.google.android.gms.cast.framework.R;
import com.google.android.gms.internal.cast.zzez;

@Keep
public class HelpTextView extends LinearLayout implements zzi {
    private TextView zzko;
    private TextView zzkp;

    @Keep
    public HelpTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Keep
    public View asView() {
        return this;
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.zzko = (TextView) zzez.checkNotNull((TextView) findViewById(R.id.cast_featurehighlight_help_text_header_view));
        this.zzkp = (TextView) zzez.checkNotNull((TextView) findViewById(R.id.cast_featurehighlight_help_text_body_view));
    }

    @Keep
    public void setText(@Nullable CharSequence charSequence, @Nullable CharSequence charSequence2) {
        zza(this.zzko, charSequence);
        zza(this.zzkp, charSequence2);
    }

    private static void zza(TextView textView, @Nullable CharSequence charSequence) {
        textView.setText(charSequence);
        textView.setVisibility(TextUtils.isEmpty(charSequence) != null ? 8 : null);
    }
}
