package de.android.testtodeletedevintensivereincarnation.ui.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import de.android.testtodeletedevintensivereincarnation.R;
import de.android.testtodeletedevintensivereincarnation.utils.ConstantManager;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = ConstantManager.TAG_PREFIX + MainActivity.class.getSimpleName();
    private ImageView callImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callImg = (ImageView)findViewById(R.id.call_img);
        callImg.setOnClickListener(this);

        if (savedInstanceState == null) {
            // активность запускается впервые
        }else {
            // активность уже создавалась
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_img:
//                showProgress();
//                runWithDelay();
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
//    private void runWithDelay() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                hideProgress();
//            }
//        }, 5000);
//    }
}
