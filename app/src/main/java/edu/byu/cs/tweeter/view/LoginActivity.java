package edu.byu.cs.tweeter.view;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

/**
 * Contains the minimum UI required to allow the user to login with a hard-coded user. Most or all
 * of this should be replaced when the back-end is implemented.
 */
public class LoginActivity extends AppCompatActivity implements LoginPresenter.View, LoginTask.Observer {

    private static final String LOG_TAG = "LoginActivity";

    private LoginPresenter presenter;
    private Toast loginInToast;
    private Toast registerToast;

    private EditTextWatcher userName = new EditTextWatcher();
    private EditTextWatcher passWord = new EditTextWatcher();
    private EditTextWatcher firstName = new EditTextWatcher();//only for register
    private EditTextWatcher lastName = new EditTextWatcher();//only for register
    //need the image?

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        EditText UserNameField = findViewById(R.id.userName);
        UserNameField.addTextChangedListener(userName);

        EditText PasswordField = findViewById(R.id.passWord);
        PasswordField.addTextChangedListener(passWord);

        EditText FirstNameField = findViewById(R.id.firstName);
        FirstNameField.addTextChangedListener(firstName);

        EditText LastNameField = findViewById(R.id.lastName);
        LastNameField.addTextChangedListener(lastName);

        loginButton = findViewById(R.id.LoginButton);

        //edit text listeners set here
        //loginButton.setEnabled(false);
        //registerButton.setEnabled(false);

        loginButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Makes a login request. The user is hard-coded, so it doesn't matter what data we put
             * in the LoginRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                loginInToast = Toast.makeText(LoginActivity.this, "Logging In", Toast.LENGTH_LONG);
                loginInToast.show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                LoginRequest loginRequest = new LoginRequest(userName.toString(), passWord.toString());
                LoginTask loginTask = new LoginTask(presenter, LoginActivity.this);
                loginTask.execute(loginRequest);
            }
        });

        registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Makes a login request. The user is hard-coded, so it doesn't matter what data we put
             * in the LoginRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                registerToast = Toast.makeText(LoginActivity.this, "Registering You", Toast.LENGTH_LONG);
                registerToast.show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                //RegisterRequest registerRequest = new RegisterRequest(userName.toString(), passWord.toString(), firstName.toString(), lastName.toString() /*picture*/);
                //RegisterTask registerTask = new RegisterTask(presenter, LoginActivity.this);///????? what???? can't change it to register activity because we don't want one
                //create register activity, register them, then return them to login activity to relogin?
                //registerTask.execute(registerRequest);
            }
        });
        enableDisableButtons();
    }

    /**
     * The callback method that gets invoked for a successful login. Displays the MainActivity.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginSuccessful(LoginResponse loginResponse) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra(MainActivity.CURRENT_USER_KEY, loginResponse.getUser());
        intent.putExtra(MainActivity.AUTH_TOKEN_KEY, loginResponse.getAuthToken());

        loginInToast.cancel();
        startActivity(intent);
    }

    /**
     * The callback method that gets invoked for an unsuccessful login. Displays a toast with a
     * message indicating why the login failed.
     *
     * @param loginResponse the response from the login request.
     */
    @Override
    public void loginUnsuccessful(LoginResponse loginResponse) {
        Toast.makeText(this, "Failed to login. " + loginResponse.getMessage(), Toast.LENGTH_LONG).show();
    }

    /**
     * A callback indicating that an exception was thrown in an asynchronous method called on the
     * presenter.
     *
     * @param exception the exception.
     */
    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to login because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }

    private class EditTextWatcher implements TextWatcher
    {
        private String passOut = "";
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            passOut = s.toString();
            enableDisableButtons();
        }

        @Override
        public void afterTextChanged(Editable s) {}

        String getPassOut() { return passOut; }
    }

    private void enableDisableButtons()
    {
        //if sign in fields filled
        //make sign in button clickable
        //else not clickable
        if (!userName.getPassOut().equals("") && !passWord.getPassOut().equals(""))
        {
            loginButton.setEnabled(true);
        }
        else
        {
            loginButton.setEnabled(false);
        }

        if (!userName.getPassOut().equals("") && !passWord.getPassOut().equals("") && !firstName.getPassOut().equals("") && !lastName.getPassOut().equals("") /*add picture*/)
        {
            registerButton.setEnabled(true);
        }
        else
        {
            registerButton.setEnabled(false);
        }
        //same for register
    }
}
