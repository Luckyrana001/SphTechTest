package com.sphtech.application;

import android.Manifest;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
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
    private ProgressDialog mProgressDialog;
    private MaterialDialog errorDialog;
    private MaterialDialog mDialog;
    private MobileUsageDataAdapter mobileUsageDataAdapter;
    private MobileUsageViewModel mobileUsageViewModel;


    @Bind(R.id.md_contentRecyclerView)
    RecyclerView mdContentRecyclerView;

    ArrayList<YearDataModel> yearDataModels;
    private static final int REQUEST_STORAGE = 112;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ButterKnife.bind(this);
        BaseFlyContext.getInstant().setActivity(this);
        IRemoteServices remoteServices = new RemoteServices(this);
        MobileUsageViewModelProvider postProvider = new MobileUsageViewModelProvider(remoteServices);
        mobileUsageViewModel = ViewModelProviders.of(this, postProvider).get(MobileUsageViewModel.class);
        setUpObservers();
        yearDataModels = new ArrayList<>();

        setupRecylerView();
        requestPermissionForExternalStorage();

    }
    public void requestPermissionForExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "External Storage permission needed. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mobileUsageViewModel.getDataFromAPiOrCache();
                }
            }
        }
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

        yearDataModels.addAll(mobileUsageViewModels);
        mobileUsageDataAdapter.notifyDataSetChanged();
    }

    private void showProgress(boolean showed, String msg) {
        if (showed) {
            if (!mProgressDialog.isShowing()) {
                if (!msg.equals("")) {
                    mProgressDialog.setMessage(msg);
                }
                mProgressDialog.show();
            }
        } else {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


   /* private void setupProgressBar() {
        mProgressDialog = new ProgressDialog(this, R.style.AppCompatAlertDialogStyle);
        mProgressDialog.setMessage(getResources().getString(R.string.processing));
        mProgressDialog.setCancelable(false);
    }*/

    private void showLceStatus(LCEStatus status) {
        if (status.getStatus() == LCEStatus.Status.SUCCESS) {
            showProgress(false, "");
        } else if (status.getStatus() == LCEStatus.Status.ERROR) {
            showErrorAlertDialog(status.getTitle(), status.getMsg());
        } else if (status.getStatus() == LCEStatus.Status.LOADING) {
            showProgress(true, status.getMsg());
        }
    }

    private void showErrorAlertDialog(String title, String msg) {
        if (errorDialog != null && errorDialog.isShowing()) {
            errorDialog.dismiss();
        }

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
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
                    .onPositive((dialog, which) -> {
                        dialogModel.getOnPositiveBtnClicked().onclicked();
                    })
                    .onNegative(((dialog, which) -> {
                        dialogModel.getOnNegativeBtnClicked().onClicked();
                    }))
                    .build();

            mDialog.show();
        }
    }


    @Override
    public void imageClicked() {
        showToast("Image Clicked");
    }
}
