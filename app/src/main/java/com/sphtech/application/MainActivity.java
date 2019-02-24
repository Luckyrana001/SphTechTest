package com.sphtech.application;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sphtech.application.Presenter.MobileUsageViewModel;
import com.sphtech.application.Presenter.MobileUsageViewModelProvider;
import com.sphtech.application.adapter.MobileUsageDataAdapter;
import com.sphtech.application.common.BaseFlyContext;
import com.sphtech.application.common.DialogModel;
import com.sphtech.application.common.LCEStatus;
import com.sphtech.application.common.Logger;
import com.sphtech.application.listener.ImageClickedListener;
import com.sphtech.application.model.YearDataModel;
import com.sphtech.application.services.IRemoteServices;
import com.sphtech.application.services.RemoteServices;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ImageClickedListener {
    private MaterialDialog errorDialog;
    private MaterialDialog mDialog;
    private MobileUsageDataAdapter mobileUsageDataAdapter;
    private MobileUsageViewModel mobileUsageViewModel;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.md_contentRecyclerView)
    RecyclerView mdContentRecyclerView;

    ArrayList<YearDataModel> yearDataModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        BaseFlyContext.getInstant().setActivity(this);

        IRemoteServices remoteServices = new RemoteServices(this);
        MobileUsageViewModelProvider postProvider = new MobileUsageViewModelProvider(remoteServices);

        setupViewModel(postProvider);

        setUpObservers();
        yearDataModels = new ArrayList<>();
        setupRecylerView();
        setUpPullToRefreshLayout();

    }

    public MobileUsageViewModel setupViewModel(MobileUsageViewModelProvider postProvider){

      return   mobileUsageViewModel = ViewModelProviders.of(this, postProvider).get(MobileUsageViewModel.class);

    }


    private void setUpPullToRefreshLayout() {
         mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
           // Refresh items
                mobileUsageViewModel.getDataFromAPiOrCacheOrFromDb();

        });
        // Configure the refreshing colors
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_light,
                android.R.color.holo_orange_dark,
                android.R.color.holo_green_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_purple
                );
    }

    private void setupRecylerView() {

        mobileUsageDataAdapter = new MobileUsageDataAdapter(this, yearDataModels, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mdContentRecyclerView.setLayoutManager(mLayoutManager);
        mdContentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mdContentRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mdContentRecyclerView.setAdapter(mobileUsageDataAdapter);

    }

    private void setUpObservers() {



        mobileUsageViewModel.getMobileDataUsageData()
                .observe(this, this::updateDataset);


        mobileUsageViewModel.getLceStatus()
                .observe(this, this::showLceStatus);

        mobileUsageViewModel.getWarning()
                .observe(this, this::showToast);

        mobileUsageViewModel.getDialog()
                .observe(this, this::setupDialog);


    }

    private void updateDataset(ArrayList<YearDataModel> mobileUsageViewModels) {

        yearDataModels.clear();
        yearDataModels.addAll(mobileUsageViewModels);
        mobileUsageDataAdapter.notifyDataSetChanged();
    }



    public void showToast(String message) {
        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }




    private void showLceStatus(LCEStatus status) {
        if (status.getStatus() == LCEStatus.Status.SUCCESS) {
            mSwipeRefreshLayout.setRefreshing(false);

        } else if (status.getStatus() == LCEStatus.Status.ERROR) {
            mSwipeRefreshLayout.setRefreshing(false);
            showErrorAlertDialog(status.getTitle(), status.getMsg());
        } else if (status.getStatus() == LCEStatus.Status.LOADING) {
            mSwipeRefreshLayout.setRefreshing(false);

        }
    }

    private void showErrorAlertDialog(String title, String msg) {
        if (errorDialog != null && errorDialog.isShowing()) {
            errorDialog.dismiss();
        }


        errorDialog = new MaterialDialog.Builder(this)
                .title(title)
                .content(msg)
                .positiveText("OK")
                .build();

        errorDialog.show();
    }

    private void setupDialog(DialogModel dialogModel) {
        Logger.d("errorDialog setup =>" + dialogModel);
        if (!dialogModel.isShowed()) {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } else {
            mDialog = new MaterialDialog.Builder(this)
                    .title(dialogModel.getTitle())
                    .content(dialogModel.getContent())
                    .positiveText(dialogModel.getPositiveBtnText())
                    .negativeText(dialogModel.getNegBtnText())
                    .onPositive((dialog, which) -> dialogModel.getOnPositiveBtnClicked().onclicked())
                    .onNegative(((dialog, which) -> dialogModel.getOnNegativeBtnClicked().onClicked()))
                    .build();

            mDialog.show();
        }
    }


    @Override
    public void imageClicked() {
        showToast("Image Clicked");
    }
}
