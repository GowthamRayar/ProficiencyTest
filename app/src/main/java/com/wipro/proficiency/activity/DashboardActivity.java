package com.wipro.proficiency.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wipro.proficiency.ProficiencyTestApplication;
import com.wipro.proficiency.R;
import com.wipro.proficiency.adapters.NewsFeedAdapter;
import com.wipro.proficiency.di.component.ApplicationComponent;
import com.wipro.proficiency.di.component.DaggerDashboardComponent;
import com.wipro.proficiency.di.module.DashboardModule;
import com.wipro.proficiency.interactions.DashboardInteraction;
import com.wipro.proficiency.mvp.model.response.NewsFeedResponse;
import com.wipro.proficiency.mvp.presenter.DashboardPresenter;
import com.wipro.proficiency.mvp.view.DashboardView;
import com.wipro.proficiency.utils.AppUtils;
import com.wipro.proficiency.widgets.DoubleButtonDialog;
import com.wipro.proficiency.widgets.SingleButtonDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DashboardActivity extends AppCompatActivity implements DashboardView {

    @BindView(R.id.list_feed)
    RecyclerView feedView;
    @BindView(R.id.title_text)
    TextView toolBarTxt;
    @BindView(R.id.refresh)
    ImageView refreshIcon;
    @BindView(R.id.swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    DashboardPresenter mDashboardPresenter;
    @Inject
    DashboardInteraction mDashboardInteraction;

    private ProgressDialog mProgressDialog;
    private NewsFeedAdapter newsFeedAdapter;
    private NewsFeedResponse newsFeedResponse = new NewsFeedResponse();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initialize();

    }

    private void initialize() {
        ButterKnife.bind(this);

        ApplicationComponent appComponent = ((ProficiencyTestApplication)this.getApplication()).getApplicationComponent();
        DaggerDashboardComponent.builder()
                .applicationComponent(appComponent)
                .dashboardModule(new DashboardModule())
                .build()
                .inject(this);

        initializePresenter();
        initViews();

    }

    @OnClick(R.id.refresh)
    public void refresh() {
        showProgressDialog();
        updateData();
    }

    private void updateData(){
        if (AppUtils.isNetworkAvailable(DashboardActivity.this)) {
            mDashboardPresenter.getNewsFeed();
        }else {
            new DoubleButtonDialog.Builder(this)
                    .setTitleText(getString(R.string.error_no_internet_connection))
                    .setMessageText(getString(R.string.error_no_internet_available))
                    .setAcceptButton(getString(R.string.generic_retry), view -> refresh())
                    .setCancelButton(getString(R.string.generic_dismiss), view -> {
                        dismissProgressDialog();
                    })
                    .show();
        }
    }

    private void initViews(){
        mProgressDialog = new ProgressDialog(this, R.style.full_screen_dialog) {
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.custom_fullscreen_dialog);
                getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
            }
        };
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);

        newsFeedAdapter = new NewsFeedAdapter(getContext(), newsFeedResponse);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        feedView.setLayoutManager(mLayoutManager);
        feedView.setItemAnimator(new DefaultItemAnimator());
        feedView.setAdapter(newsFeedAdapter);

        swipeRefreshLayout.setOnRefreshListener(
                () -> updateData()
        );


        refresh();
    }

    private void initializePresenter()
    {
        mDashboardPresenter.attachView(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean isAdapterEmpty() {
        if (newsFeedAdapter.getItemCount() == 0){
            return true;
        }  else {
            return false;
        }
    }

    @Override
    public void updateView(NewsFeedResponse response) {
        toolBarTxt.setText(response.getTitle());
        newsFeedAdapter.updateAdapter(response);
        swipeRefreshLayout.setRefreshing(false);
        dismissProgressDialog();
    }

    @Override
    public void showProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showError(String message) {
        dismissProgressDialog();
        new SingleButtonDialog.Builder(this)
                .setTitleText(R.string.generic_alert)
                .setMessageText(R.string.generic_request_error)
                .setErrorMessageText("Response : "+message)
                .setAcceptButton(R.string.generic_ok, view -> {
                    //No Impl needed
                }).show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
