package edu.byu.cs.tweeter.view.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.PostPresenter;
import edu.byu.cs.tweeter.view.LoginActivity;
import edu.byu.cs.tweeter.view.asyncTasks.GetLogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetPostTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements PostPresenter.View, GetPostTask.Observer, LogoutPresenter.View, GetLogoutTask.Observer
{

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    //public static final String CURRENT_STATUS_KEY = "CurrentStatus";

    private PostPresenter pPresenter;
    private LogoutPresenter logoutPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pPresenter = new PostPresenter(this);
        logoutPresenter = new LogoutPresenter(this);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken); //, status);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog(MainActivity.this);
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, 42));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, 27));
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Add a new task")
                .setMessage("What do you want to do next?")
                .setView(taskEditText)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String task = String.valueOf(taskEditText.getText());
                        //send post request thing
                        //recieve response
                        //decide what happens
                        //all just carry the one status

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                        LocalDateTime now = LocalDateTime.now();

                        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
                        Status status = new Status(task, user, dtf.format(now));

                        PostRequest postRequest = new PostRequest(status);
                        GetPostTask postTask = new GetPostTask(pPresenter, MainActivity.this);
                        postTask.execute(postRequest);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onLogoutClick(MenuItem item)
    {
        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        LogoutRequest logoutRequest = new LogoutRequest(user);
        GetLogoutTask logoutTask = new GetLogoutTask(logoutPresenter, MainActivity.this);
        logoutTask.execute(logoutRequest);
    }

    @Override
    public void postSuccessful(PostResponse response)
    {
        Toast.makeText(this, "Successfully Posted", Toast.LENGTH_LONG).show();
    }

    @Override
    public void postUnsuccessful(PostResponse response)
    {
        Toast.makeText(this, "Could not Post", Toast.LENGTH_LONG).show();
    }

    @Override
    public void logoutSuccessful(LogoutResponse response)
    {
        Intent loginscreen=new Intent(this, LoginActivity.class);
        loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginscreen);
        this.finish();
    }

    @Override
    public void logoutUnsuccessful(LogoutResponse response)
    {
        Toast.makeText(this, "Haha, you're stuck now! (cannot log out)" , Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleException(Exception exception)
    {
        Toast.makeText(this, "Error in posting", Toast.LENGTH_LONG).show();
    }
}