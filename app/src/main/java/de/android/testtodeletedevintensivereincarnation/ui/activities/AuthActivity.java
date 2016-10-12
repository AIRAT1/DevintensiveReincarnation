package de.android.testtodeletedevintensivereincarnation.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import de.android.testtodeletedevintensivereincarnation.R;
import de.android.testtodeletedevintensivereincarnation.data.managers.DataManager;
import de.android.testtodeletedevintensivereincarnation.data.network.req.UserLoginReq;
import de.android.testtodeletedevintensivereincarnation.data.network.res.UserModelRes;
import de.android.testtodeletedevintensivereincarnation.utils.NetworkStatusChecker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends BaseActivity implements View.OnClickListener {
    private Button signIn;
    private TextView rememberPassword;
    private EditText login, password;
    private CoordinatorLayout coordinatorLayout;

    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        dataManager = DataManager.getInstance();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_coordinator_container);
        signIn = (Button) findViewById(R.id.login_btn);
        rememberPassword = (TextView) findViewById(R.id.remember_txt);
        login = (EditText) findViewById(R.id.login_email_et);
        password = (EditText) findViewById(R.id.login_password_et);

        rememberPassword.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                signIn();
                break;
            case R.id.remember_txt:
                rememberPassword();
                break;
        }
    }

    private void showSnackbar(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    private void rememberPassword() {
        Intent rememberIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://devintensive.softdesign-apps.ru/forgotpass"));
        startActivity(rememberIntent);
    }

    private void loginSuccess(Response<UserModelRes> response) {
        showSnackbar(response.body().getData().getToken());
        dataManager.getPreferencesManager().saveAuthToken(response.body().getData().getToken());
        dataManager.getPreferencesManager().saveUserId(response.body().getData().getUser().getId());

        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }

    private void signIn() {
        if (NetworkStatusChecker.isNetworkAvailable(this)) {

            Call<UserModelRes> call = dataManager.loginUser(new UserLoginReq(login.getText().toString(), password.getText().toString()));
            call.enqueue(new Callback<UserModelRes>() {
                @Override
                public void onResponse(Call<UserModelRes> call, Response<UserModelRes> response) {
                    if (response.code() == 200) {
                        loginSuccess(response);
                    } else if (response.code() == 404) {
                        showSnackbar("Неверный логин или пароль");
                    } else {
                        showSnackbar("Всё пропало, Шеф!");
                    }
                }

                @Override
                public void onFailure(Call<UserModelRes> call, Throwable t) {
                    // TODO обработать ошибки
                }
            });
        } else {
            showSnackbar("Сеть на данный момент не доступна, попробуйте позже");
        }
    }
}
