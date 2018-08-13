package com.wipro.proficiency.widgets;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.wipro.proficiency.R;



public class DoubleButtonDialog extends Dialog {
    private String mTitleText;
    private String mMessageText;
    private String mCancelText;
    private String mAcceptText;

    DoubleButtonDialog.CancelListener cancelListener;
    DoubleButtonDialog.AcceptListener acceptListener;

    public DoubleButtonDialog(Context context) {
        super(context);
    }

    public DoubleButtonDialog(Context context, int theme) {
        super(context, theme);
    }

    public static DoubleButtonDialog showDialog(Context context, Builder builder) {
        DoubleButtonDialog doubleButtonDialog = new DoubleButtonDialog(context, builder);
        doubleButtonDialog.show();
        return doubleButtonDialog;
    }

    public DoubleButtonDialog(Context context, Builder builder) {
        super(context);
        this.mTitleText = builder.titleText;
        this.mMessageText = builder.messageText;
        this.mCancelText = builder.cancelText;
        this.mAcceptText = builder.acceptText;

        this.acceptListener = builder.acceptListener;
        this.cancelListener = builder.cancelListener;

        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_double_button);

        TextView mTitleTextView;
        TextView mMessageTextView;
        Button mCanceButtonView;
        Button mAcceptButtonView;

        mTitleTextView = findViewById(R.id.text_title);
        mMessageTextView = findViewById(R.id.text_message);

        mAcceptButtonView = findViewById(R.id.button_retry);
        mCanceButtonView = findViewById(R.id.button_dismiss);

        mTitleTextView.setText(mTitleText);
        mMessageTextView.setText(mMessageText);

        mCanceButtonView.setText(mCancelText);
        mAcceptButtonView.setText(mAcceptText);

        mCanceButtonView.setOnClickListener(new CancelDialogListener(cancelListener, this));
        mAcceptButtonView.setOnClickListener(new AcceptDialogListener(acceptListener, this));
    }

    public interface CancelListener {
        void execute(View item);
    }

    public interface AcceptListener {
        void execute(View view);
    }

    public class CancelDialogListener implements View.OnClickListener {
        public CancelListener cancelListener;
        public DoubleButtonDialog doubleButtonDialog;

        public CancelDialogListener(CancelListener cancelListener, DoubleButtonDialog doubleButtonDialog) {
            this.cancelListener = cancelListener;
            this.doubleButtonDialog = doubleButtonDialog;
        }

        @Override
        public void onClick(View view) {
            doubleButtonDialog.dismiss();
            if (cancelListener != null) {
                cancelListener.execute(view);
            }
        }
    }

    public class AcceptDialogListener implements View.OnClickListener {
        public AcceptListener acceptListener;
        public DoubleButtonDialog doubleButtonDialog;

        public AcceptDialogListener(AcceptListener acceptListener, DoubleButtonDialog doubleButtonDialog) {
            this.acceptListener = acceptListener;
            this.doubleButtonDialog = doubleButtonDialog;
        }

        @Override
        public void onClick(View view) {
            doubleButtonDialog.dismiss();
            if (acceptListener != null) {
                acceptListener.execute(view);
            }
        }
    }

    public static class Builder {
        String titleText;
        String messageText;
        String cancelText;
        String acceptText;

        Context context;
        DoubleButtonDialog.CancelListener cancelListener;
        DoubleButtonDialog.AcceptListener acceptListener;

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

        public Builder setCancelButton(@StringRes int resId, DoubleButtonDialog.CancelListener cancelListener) {
            return setCancelButton(context.getString(resId), cancelListener);
        }

        public Builder setCancelButton(String cancelText, DoubleButtonDialog.CancelListener cancelListener) {
            this.cancelText = cancelText;
            this.cancelListener = cancelListener;
            return this;
        }

        public Builder setAcceptButton(@StringRes int resId, DoubleButtonDialog.AcceptListener acceptListener) {
            return setAcceptButton(context.getString(resId), acceptListener);
        }

        public Builder setAcceptButton(String acceptText, DoubleButtonDialog.AcceptListener acceptListener) {
            this.acceptText = acceptText;
            this.acceptListener = acceptListener;
            return this;
        }

        public DoubleButtonDialog show() {
            return DoubleButtonDialog.showDialog(context, this);
        }
    }
}
