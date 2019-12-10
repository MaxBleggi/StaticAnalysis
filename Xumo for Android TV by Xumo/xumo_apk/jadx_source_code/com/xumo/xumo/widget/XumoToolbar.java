package com.xumo.xumo.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import com.xumo.xumo.tv.R;
import com.xumo.xumo.util.XumoImageUtil;

public class XumoToolbar extends Toolbar {
    private TextView mActionMenuButton;
    private RelativeLayout mBarLayout;
    private ImageView mCenterLogoView;
    private TextView mCenterTitleView;
    private ImageButton mLeftImageButton;
    private ViewGroup mSearchBar;
    private ImageView mSearchClearView;
    private EditText mSearchInputView;
    private SearchKeywordListener mSearchKeywordListener;
    private View mXumoBar;

    public interface SearchKeywordListener {
        void requestSearchVideo(String str);
    }

    public XumoToolbar(Context context) {
        super(context);
    }

    public XumoToolbar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public XumoToolbar(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public XumoToolbar setLogoInCenter(int i) {
        ensureApplicableView();
        this.mCenterLogoView.setImageResource(i);
        return this;
    }

    public XumoToolbar setLogoInCenter(Bitmap bitmap) {
        ensureApplicableView();
        this.mCenterLogoView.setImageBitmap(bitmap);
        return this;
    }

    public XumoToolbar setChannelLogoInCenter(String str) {
        ensureApplicableView();
        XumoImageUtil.setChannelImage(getContext(), str, this.mCenterLogoView, new int[0]);
        return this;
    }

    public XumoToolbar setTitleInCenter(int i) {
        return setTitleInCenter(i, -1);
    }

    public XumoToolbar setTitleInCenter(int i, int i2) {
        ensureApplicableView();
        this.mCenterTitleView.setText(i);
        if (i2 != -1) {
            this.mCenterTitleView.setTextColor(i2);
        }
        return this;
    }

    public XumoToolbar setTitleInCenter(String str, int i) {
        ensureApplicableView();
        this.mCenterTitleView.setText(str);
        if (i != -1) {
            this.mCenterTitleView.setTextColor(i);
        }
        return this;
    }

    public XumoToolbar setLeftImageButton(int i, OnClickListener onClickListener) {
        ensureApplicableView();
        this.mLeftImageButton.setImageResource(i);
        this.mLeftImageButton.setOnClickListener(onClickListener);
        if (onClickListener == null) {
            this.mLeftImageButton.setClickable(null);
        } else {
            this.mLeftImageButton.setClickable(true);
        }
        return this;
    }

    public XumoToolbar setLeftImageButton(Drawable drawable, OnClickListener onClickListener) {
        ensureApplicableView();
        this.mLeftImageButton.setImageDrawable(drawable);
        this.mLeftImageButton.setOnClickListener(onClickListener);
        if (onClickListener == null) {
            this.mLeftImageButton.setClickable(null);
        } else {
            this.mLeftImageButton.setClickable(true);
        }
        return this;
    }

    public void setLeftImageButtonVisibility(int i) {
        ensureApplicableView();
        this.mLeftImageButton.setVisibility(i);
    }

    public XumoToolbar setActionMenuButton(int i, OnClickListener onClickListener) {
        ensureApplicableView();
        this.mActionMenuButton.setText(i);
        this.mActionMenuButton.setOnClickListener(onClickListener);
        return this;
    }

    public void showSearchBar(SearchKeywordListener searchKeywordListener) {
        ensureApplicableView();
        this.mSearchBar.setVisibility(0);
        this.mSearchKeywordListener = searchKeywordListener;
    }

    public void clear() {
        this.mCenterLogoView.setImageDrawable(null);
        this.mCenterTitleView.setText("");
        this.mLeftImageButton.setImageDrawable(null);
        this.mLeftImageButton.setOnClickListener(null);
        this.mActionMenuButton.setText("");
        this.mActionMenuButton.setOnClickListener(null);
        this.mSearchBar.setVisibility(8);
        hideSoftKeyboard();
        this.mSearchInputView.clearFocus();
        this.mSearchKeywordListener = null;
    }

    private void hideSoftKeyboard() {
        if (getContext() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService("input_method");
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(getWindowToken(), 0);
            }
        }
    }

    private void ensureApplicableView() {
        if (this.mXumoBar == null) {
            this.mXumoBar = LayoutInflater.from(getContext()).inflate(R.layout.widget_xumo_toolbar, this, true);
            this.mBarLayout = (RelativeLayout) this.mXumoBar.findViewById(R.id.toolbar_parent_layout);
            this.mCenterLogoView = (ImageView) this.mXumoBar.findViewById(R.id.toolbar_center_logo);
            this.mCenterTitleView = (TextView) this.mXumoBar.findViewById(R.id.toolbar_center_title);
            this.mLeftImageButton = (ImageButton) this.mXumoBar.findViewById(R.id.toolbar_left_button);
            this.mActionMenuButton = (TextView) this.mXumoBar.findViewById(R.id.toolbar_action_button);
            ensureSearchBar();
        }
    }

    private void ensureSearchBar() {
        if (this.mSearchBar == null) {
            this.mSearchBar = (ViewGroup) this.mXumoBar.findViewById(R.id.toolbar_search_bar);
            this.mSearchInputView = (EditText) this.mSearchBar.findViewById(R.id.search_keyword_input);
            this.mSearchClearView = (ImageView) this.mSearchBar.findViewById(R.id.search_keyword_clear);
            this.mSearchBar.findViewById(R.id.search_logo).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    XumoToolbar.this.mSearchInputView.requestFocus();
                }
            });
            this.mSearchClearView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    XumoToolbar.this.mSearchInputView.setText("");
                }
            });
            this.mSearchInputView.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void afterTextChanged(Editable editable) {
                    if (editable != null) {
                        if (editable.length() > null) {
                            XumoToolbar.this.mSearchClearView.setVisibility(0);
                            return;
                        }
                    }
                    XumoToolbar.this.mSearchClearView.setVisibility(8);
                }
            });
            this.mSearchInputView.setOnEditorActionListener(new OnEditorActionListener() {
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    textView = textView.getText().toString();
                    if (textView.length() > 0) {
                        if (i != 3) {
                            if (keyEvent == null || keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 66) {
                                i = 0;
                                if (!(i == 0 || XumoToolbar.this.mSearchKeywordListener == 0)) {
                                    XumoToolbar.this.hideSoftKeyboard();
                                    XumoToolbar.this.mSearchKeywordListener.requestSearchVideo(textView);
                                    return true;
                                }
                            }
                        }
                        i = 1;
                        XumoToolbar.this.hideSoftKeyboard();
                        XumoToolbar.this.mSearchKeywordListener.requestSearchVideo(textView);
                        return true;
                    }
                    return false;
                }
            });
        }
    }
}
