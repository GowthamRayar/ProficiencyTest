package com.wipro.proficiency.widgets;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wipro.proficiency.R;

public class SingleButtonDialog extends Dialog {
    private String mTitleText;
    private String mMessageText;
    private String mErrorMessage;
    private String mButtonText;

    SingleButtonDialog.AcceptListener mAcceptListener;

    public SingleButtonDialog(Context context) {
        super(context);
    }

    public SingleButtonDialog(Context context, int theme) {
        super(context, theme);
    }

    public static SingleButtonDialog showDialog(Context context, Builder builder) {
        SingleButtonDialog singleButtonDialog = new SingleButtonDialog(context, builder);
        if (!singleButtonDialog.isShowing()) singleButtonDialog.show();
        return singleButtonDialog;
    }

    public SingleButtonDialog(Context context, Builder builder) {
        super(context);
        this.mTitleText = builder.titleText;
        this.mMessageText = builder.messageText;
        this.mButtonText = builder.acceptText;
        this.mErrorMessage = builder.errorText;
        this.mAcceptListener = builder.acceptListener;

        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_single_button);

        TextView mTitleTextView;
        TextView mMessageTextView;
        TextView mAcceptButton;
        TextView mErrorMesaageTextView;

        mTitleTextView = findViewById(R.id.text_title);
        mMessageTextView = findViewById(R.id.text_message);
        mErrorMesaageTextView = findViewById(R.id.text_error_message);
        mAcceptButton = findViewById(R.id.button_ok);

        mTitleTextView.setText(mTitleText);
        mMessageTextView.setText(mMessageText);
        if (!TextUtils.isEmpty(mErrorMessage)) {
            mErrorMesaageTextView.setVisibility(View.VISIBLE);
            mErrorMesaageTextView.setText(mErrorMessage);
        }
        mAcceptButton.setText(mButtonText);

        mAcceptButton.setOnClickListener(new AcceptDialogListener(mAcceptListener, this));
    }

    public interface AcceptListener {
        void execute(View view);
    }

    public class AcceptDialogListener implements View.OnClickListener {
        private SingleButtonDialog mSingleButtonDialog;
        private AcceptListener mAcceptListener;

        public AcceptDialogListener(AcceptListener mAcceptListener, SingleButtonDialog mSingleButtonDialog) {
            this.mAcceptListener = mAcceptListener;
            this.mSingleButtonDialog = mSingleButtonDialog;
        }

        @Override
        public void onClick(View view) {
            mSingleButtonDialog.dismiss();
            if (mAcceptListener != null) {
                mAcceptListener.execute(view);
            }
        }
    }

    public static class Builder {
        String titleText;
        String messageText;
        String errorText;
        String acceptText;

        Context context;

        SingleButtonDialog.AcceptListener acceptListener;

        public Builder(Fragment fragment) {
            this(fragment.getActivity());
        }

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitleText(@StringRes int resId) {
            return setTitleText(context.getString(resId));
        }

        public Builder setTitleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public Builder setMessageText(@StringRes int resId, Object... formatArgs) {
            return setMessageText(context.getString(resId, formatArgs));
        }

        public Builder setMessageText(String messageText) {
            this.messageText = messageText;
            return this;
        }

        public Builder setErrorMessageText(String errorText) {
            this.errorText = errorText;
            return this;
        }

        public Builder setAcceptButton(@StringRes int resId, SingleButtonDialog.AcceptListener acceptListener) {
            return setAcceptButton(context.getString(resId), acceptListener);
        }

        public Builder setAcceptButton(String acceptText, SingleButtonDialog.AcceptListener acceptListener) {
            this.acceptText = acceptText;
            this.acceptListener = acceptListener;
            return this;
        }

        public SingleButtonDialog show() {
            return SingleButtonDialog.showDialog(context, this);
        }

    }

}
