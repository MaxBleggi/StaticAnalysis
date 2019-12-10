package com.xumo.xumo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.xumo.xumo.tv.R;

public class ExitDialog extends Dialog {
    private int mExitIndex = null;
    private TextView mNoTv;
    private TextView mYesTv;

    public ExitDialog(Context context) {
        super(context, R.style.Theme.AppCompat.Dialog);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_exit);
        setCanceledOnTouchOutside(null);
        initView();
    }

    private void initView() {
        this.mYesTv = (TextView) findViewById(R.id.yes_tv);
        this.mNoTv = (TextView) findViewById(R.id.no_tv);
    }

    public boolean onKeyDown(int i, @NonNull KeyEvent keyEvent) {
        if (i != 96) {
            switch (i) {
                case 21:
                    if (this.mExitIndex > 0) {
                        this.mExitIndex--;
                    }
                    this.mYesTv.setBackgroundResource(R.drawable.shape_white_round);
                    this.mNoTv.setBackgroundResource(R.drawable.shape_blue);
                    return true;
                case 22:
                    if (this.mExitIndex < 1) {
                        this.mExitIndex++;
                    }
                    this.mYesTv.setBackgroundResource(R.drawable.shape_blue);
                    this.mNoTv.setBackgroundResource(R.drawable.shape_white_round);
                    return true;
                case 23:
                    break;
                default:
                    return super.onKeyDown(i, keyEvent);
            }
        }
        if (this.mExitIndex == 0) {
            dismiss();
        } else {
            dismiss();
            System.exit(0);
        }
        return true;
    }
}
