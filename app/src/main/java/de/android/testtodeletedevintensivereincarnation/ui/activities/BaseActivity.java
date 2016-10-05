package de.android.testtodeletedevintensivereincarnation.ui.activities;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import de.android.testtodeletedevintensivereincarnation.R;
import de.android.testtodeletedevintensivereincarnation.utils.ConstantManager;

public class BaseActivity extends AppCompatActivity {
    protected static final String TAG = ConstantManager.TAG_PREFIX + BaseActivity.class.getSimpleName();
    protected ProgressDialog progressDialog;


    @SuppressWarnings("unused")
    protected void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.custom_dialog);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_splash);
        }else {
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_splash);
        }
    }
    @SuppressWarnings("unused")
    protected void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.hide();
            }
        }
    }
    @SuppressWarnings("unused")
    protected void showError(String message, Exception error) {
        showToast(message);
        Log.e(TAG, String.valueOf(error));
    }
    @SuppressWarnings("unused")
    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
