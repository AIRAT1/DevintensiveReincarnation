package de.android.testtodeletedevintensivereincarnation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import de.android.testtodeletedevintensivereincarnation.R;
import de.android.testtodeletedevintensivereincarnation.data.managers.DataManager;
import de.android.testtodeletedevintensivereincarnation.utils.ConstantManager;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = ConstantManager.TAG_PREFIX + MainActivity.class.getSimpleName();
    private DataManager dataManager;

    private int currentEditMode = 0;

    private ImageView callImg;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private DrawerLayout navigationDrawer;
    private FloatingActionButton fab;
    private EditText userPhone, userMail, userVk, userGit, userBio;
    private List<EditText> userInfoViews;
    private RelativeLayout profilePlaceholder;
    private CollapsingToolbarLayout collapsingToolbar;

    private AppBarLayout.LayoutParams appBarParams = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataManager = DataManager.getInstance();

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_container);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationDrawer = (DrawerLayout)findViewById(R.id.navigation_drawer);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        callImg = (ImageView)findViewById(R.id.call_img);
        profilePlaceholder = (RelativeLayout)findViewById(R.id.profile_placeholder);
        collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);

        userPhone = (EditText)findViewById(R.id.phone_et);
        userMail = (EditText)findViewById(R.id.email_et);
        userVk = (EditText)findViewById(R.id.vk_et);
        userGit = (EditText)findViewById(R.id.github_et);
        userBio = (EditText)findViewById(R.id.about_et);

        userInfoViews = new ArrayList<>();
        userInfoViews.add(userPhone);
        userInfoViews.add(userMail);
        userInfoViews.add(userVk);
        userInfoViews.add(userGit);
        userInfoViews.add(userBio);

        fab.setOnClickListener(this);
        setupToolbar();
        setupDrawer();
        loadUserInfoValue();

//        List<String> test = dataManager.getPreferencesManager().loadUserProfileData();

        if (savedInstanceState == null) {
            // активность запускается впервые
        }else {
            // активность уже создавалась
            currentEditMode = savedInstanceState.getInt(ConstantManager.EDIT_MODE_KEY, 0);
            changeEditMode(currentEditMode);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navigationDrawer.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                if (currentEditMode == 0) {
                    fab.setImageResource(R.drawable.ic_done_black_24dp);
                    changeEditMode(1);
                    currentEditMode = 1;
                }else {
                    fab.setImageResource(R.drawable.ic_create_black_24dp);
                    changeEditMode(0);
                    currentEditMode = 0;
                }
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantManager.EDIT_MODE_KEY, currentEditMode);
    }
    @SuppressWarnings("unused")
    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }
    private void setupToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        appBarParams = (AppBarLayout.LayoutParams) collapsingToolbar.getLayoutParams();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void setupDrawer() {
        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                showSnackbar(item.getTitle().toString());
                item.setChecked(true);
                navigationDrawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (navigationDrawer.isDrawerOpen(GravityCompat.START)) {
            navigationDrawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    /**
     * переключает режим редактирования
     * @param mode если 1 режим редактирования, если 0 режим просмотра
     */
    private void changeEditMode(int mode) {
        if (mode == 1) {
            for (EditText userValue : userInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);

                showProfilePlaceholder();
                lockToolbar();
            }
        }else {
            for (EditText userValue : userInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);

                hideProfilePlaceholder();
                unlockToolbar();

                saveUserInfoValue();
            }
        }
    }
    private void loadUserInfoValue() {
        List<String> userData = dataManager.getPreferencesManager().loadUserProfileData();
        for (int i = 0; i < userData.size(); i++) {
            userInfoViews.get(i).setText(userData.get(i));
        }
    }
    private void saveUserInfoValue() {
        List<String> userData = new ArrayList<>();
        for (EditText userFieldView : userInfoViews) {
            userData.add(userFieldView.getText().toString());
        }
        dataManager.getPreferencesManager().saveUserProfileData(userData);
    }
    private void loadPhotoFromGallery() {

    }
    private void loadPhotoFromCamera() {

    }

    /**
     * Получение результата из другой Activity (фото из камеры или галлереи)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void hideProfilePlaceholder() {
        profilePlaceholder.setVisibility(View.GONE);
    }
    private void showProfilePlaceholder() {
        profilePlaceholder.setVisibility(View.VISIBLE);
    }
    private void lockToolbar() {
        appBarParams.setScrollFlags(0);
        collapsingToolbar.setLayoutParams(appBarParams);
    }
    private void unlockToolbar() {
        appBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        collapsingToolbar.setLayoutParams(appBarParams);
    }
}
