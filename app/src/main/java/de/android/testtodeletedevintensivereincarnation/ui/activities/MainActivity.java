package de.android.testtodeletedevintensivereincarnation.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import de.android.testtodeletedevintensivereincarnation.R;
import de.android.testtodeletedevintensivereincarnation.utils.ConstantManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = ConstantManager.TAG_PREFIX + MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        if (savedInstanceState == null) {
            // активность запускается впервые
        }else {
            // активность уже создавалась
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
