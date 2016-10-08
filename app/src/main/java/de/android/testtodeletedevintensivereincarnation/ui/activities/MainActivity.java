package de.android.testtodeletedevintensivereincarnation.ui.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private AppBarLayout appBarLayout;
    private ImageView profileImage;

    private AppBarLayout.LayoutParams appBarParams = null;
    private File photoFile = null;
    private Uri selectedImage = null;

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
        appBarLayout = (AppBarLayout)findViewById(R.id.appbar_layout);
        profileImage = (ImageView)findViewById(R.id.user_photo);

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
        profilePlaceholder.setOnClickListener(this);

        setupToolbar();
        setupDrawer();
        loadUserInfoValue();
        Picasso.with(this)
                .load(dataManager.getPreferencesManager().loadUserPhoto())
                .placeholder(R.drawable.nicole_kidman_getty)
                .error(R.drawable.nicole_kidman_getty)
                .into(profileImage);

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

    @SuppressWarnings("deprecation")
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
            case R.id.profile_placeholder:
                //TODO сделать выбор откуда загружать фото
                showDialog(ConstantManager.LOAD_PROFILE_PHOTO);
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
    @SuppressWarnings("deprecation")
    private void changeEditMode(int mode) {
        if (mode == 1) {
            for (EditText userValue : userInfoViews) {
                userValue.setEnabled(true);
                userValue.setFocusable(true);
                userValue.setFocusableInTouchMode(true);
                userPhone.requestFocus();
                userPhone.setSelection(userPhone.getText().length());

                showProfilePlaceholder();
                lockToolbar();
                collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            }
        }else {
            for (EditText userValue : userInfoViews) {
                userValue.setEnabled(false);
                userValue.setFocusable(false);
                userValue.setFocusableInTouchMode(false);

                hideProfilePlaceholder();
                unlockToolbar();
                collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.white));

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
        Intent takeGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        takeGalleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(takeGalleryIntent, getString(R.string.user_profile_choice_message)),
                ConstantManager.REQUEST_GALLERY_PICTURE);
    }
    private void loadPhotoFromCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent takeCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                takeCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takeCaptureIntent, ConstantManager.REQUEST_CAMERA_PICTURE);
            }
        }else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, ConstantManager.CAMERA_REQUEST_PERMISSION_CODE);
            Snackbar.make(coordinatorLayout, "Для корректной работы необходимо дать требуемые разрешения",
                    Snackbar.LENGTH_LONG)
                    .setAction("Разрешить", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openApplicationSettings();
                        }
                    }).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ConstantManager.CAMERA_REQUEST_PERMISSION_CODE && grantResults.length == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // TODO
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // TODO
            }
        }
    }

    /**
     * Получение результата из другой Activity (фото из камеры или галлереи)
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ConstantManager.REQUEST_GALLERY_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    selectedImage = data.getData();
                    insertProfileImage(selectedImage);
                }
                break;
            case ConstantManager.REQUEST_CAMERA_PICTURE:
                if (resultCode == RESULT_OK && photoFile != null) {
                    selectedImage = Uri.fromFile(photoFile);
                    insertProfileImage(selectedImage);
                }
        }
    }

    private void hideProfilePlaceholder() {
        profilePlaceholder.setVisibility(View.GONE);
    }

    private void showProfilePlaceholder() {
        profilePlaceholder.setVisibility(View.VISIBLE);
    }
    private void lockToolbar() {
        appBarLayout.setExpanded(true, true);
        appBarParams.setScrollFlags(0);
        collapsingToolbar.setLayoutParams(appBarParams);
    }
    private void unlockToolbar() {
        appBarParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                | AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
        collapsingToolbar.setLayoutParams(appBarParams);
    }
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ConstantManager.LOAD_PROFILE_PHOTO:
                String[] selectItems = {getString(R.string.user_profile_dialog_gallery),
                        getString(R.string.user_profile_dialog_camera),
                        getString(R.string.user_profile_dialog_cancel)};
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle(R.string.user_profile_dialog_title)
                        .setItems(selectItems, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int choiceItem) {
                                switch (choiceItem) {
                                    case 0:
                                        // TODO Загрузить из галлереи
                                        loadPhotoFromGallery();
                                        break;
                                    case 1:
                                        // TODO Загрузить из камеры
                                        loadPhotoFromCamera();
                                        break;
                                    case 2:
                                        // TODO Отменить
                                        dialog.cancel();
                                        break;
                                }
                            }
                        });
                return builder.create();
            default:
                return null;
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, image.getAbsolutePath());

        this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return image;
    }
    private void insertProfileImage(Uri selectedImage) {
        Picasso.with(this)
                .load(selectedImage)
                .into(profileImage);
        dataManager.getPreferencesManager().saveUserPhoto(selectedImage);
    }
    public void openApplicationSettings() {
        Intent appSettingsIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + getPackageName()));
        startActivityForResult(appSettingsIntent, ConstantManager.PERMISSION_REQUEST_SETTINGS_CODE);
    }
}
