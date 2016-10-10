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

public class AuthActivity extends BaseActivity implements View.OnClickListener {
    private Button signIn;
    private TextView rememberPassword;
    private EditText login, password;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.main_coordinator_container);
        signIn = (Button)findViewById(R.id.login_btn);
        rememberPassword = (TextView) findViewById(R.id.remember_txt);
        login = (EditText)findViewById(R.id.login_email_et);
        password = (EditText)findViewById(R.id.login_password_et);

        rememberPassword.setOnClickListener(this);
        signIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn:
                loginSuccess();
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
    private void loginSuccess() {

    }
}
