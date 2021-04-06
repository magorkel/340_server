package edu.byu.cs.tweeter.view.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.MakeFollowPresenter;
import edu.byu.cs.tweeter.presenter.MakeUnfollowPresenter;
import edu.byu.cs.tweeter.presenter.PostPresenter;
import edu.byu.cs.tweeter.view.LoginActivity;
import edu.byu.cs.tweeter.view.asyncTasks.GetLogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetMakeFollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetMakeUnfollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetPostTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class OtherUserActivity extends AppCompatActivity implements MakeFollowPresenter.View, GetMakeFollowTask.Observer, MakeUnfollowPresenter.View, GetMakeUnfollowTask.Observer,
        PostPresenter.View, GetPostTask.Observer, LogoutPresenter.View, GetLogoutTask.Observer
{
    //May not need this...
    public static final String CURRENT_USER_KEY = "CurrentUser"; //This lets us know who is going to be following you if you
    //click the follow button.
    public static final String OTHER_USER_KEY = "OtherUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    private Button followButton;
    private Toast followToast;

    private Button unfollowButton;
    private Toast unfollowToast;
    //public static final String CURRENT_STATUS_KEY = "CurrentStatus";

    private MakeFollowPresenter presenter;
    private MakeUnfollowPresenter unPresenter;
    private PostPresenter pPresenter;
    private LogoutPresenter logoutPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        presenter = new MakeFollowPresenter(this);
        unPresenter = new MakeUnfollowPresenter(this);
        pPresenter = new PostPresenter(this);
        logoutPresenter = new LogoutPresenter(this);


        User user = (User) getIntent().getSerializableExtra(OTHER_USER_KEY);
        //FIXME: Delete me
        //Status status = (Status) getIntent().getSerializableExtra(CURRENT_STATUS_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        OtherPagerAdapter otherPagerAdapter = new OtherPagerAdapter(this, getSupportFragmentManager(), user, authToken); //, status);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(otherPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddItemDialog(OtherUserActivity.this);
            }
        });


        //NEW STUFF_-------------------------------------------------------------------------------------------


        followButton = findViewById(R.id.FollowButton);

        followButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Makes a login request. The user is hard-coded, so it doesn't matter what data we put
             * in the LoginRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                //followToast = Toast.makeText(OtherUserActivity.this, "Following!", Toast.LENGTH_LONG);
                //followToast.show();

                //a button that sends a follows request to server
                //returns response saying ya or nay
                //create separate request and response
                //you need to be added to theirs and they need to be added to yours
                //request needs 2 aliases
                //response just needs a yes or no
                //can send data

                MakeFollowRequest request = new MakeFollowRequest(CURRENT_USER_KEY, OTHER_USER_KEY);
                GetMakeFollowTask followTask = new GetMakeFollowTask(presenter, OtherUserActivity.this);
                followTask.execute(request);
            }
        });



        unfollowButton = findViewById(R.id.UnfollowButton);
        unfollowButton.setEnabled(false);

        unfollowButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Makes a login request. The user is hard-coded, so it doesn't matter what data we put
             * in the LoginRequest object.
             *
             * @param view the view object that was clicked.
             */
            @Override
            public void onClick(View view) {
                //unfollowToast = Toast.makeText(OtherUserActivity.this, "Unfollowed", Toast.LENGTH_LONG);
                //unfollowToast.show();

                //a button that sends a follows request to server
                //returns response saying ya or nay
                //create separate request and response
                //you need to be added to theirs and they need to be added to yours
                //request needs 2 aliases
                //response just needs a yes or no
                //can send data

                User currUser = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
                User otherUser = (User) getIntent().getSerializableExtra(OTHER_USER_KEY);
                MakeUnfollowRequest request = new MakeUnfollowRequest(currUser.getAlias(), otherUser.getAlias());
                //MakeUnfollowRequest request = new MakeUnfollowRequest(CURRENT_USER_KEY, OTHER_USER_KEY);
                GetMakeUnfollowTask unfollowTask = new GetMakeUnfollowTask(unPresenter,OtherUserActivity.this);
                unfollowTask.execute(request);
            }
        });
        //--------------------------------------------------------------------------------------------------

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, 42)); //request this data from the server
        //or just grab it from the user that is passed in.

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, 27));
    }

    private void showAddItemDialog(Context c) {
        final EditText taskEditText = new EditText(c);
        AlertDialog dialog = new AlertDialog.Builder(c)
                .setTitle("Post")
                .setMessage("What do you want to post next?")
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
                        GetPostTask postTask = new GetPostTask(pPresenter, OtherUserActivity.this);
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
        GetLogoutTask logoutTask = new GetLogoutTask(logoutPresenter, OtherUserActivity.this);
        logoutTask.execute(logoutRequest);
    }

    @Override
    public void followSuccessful(MakeFollowResponse response)
    {
        Toast.makeText(this, "Successfully Followed", Toast.LENGTH_LONG).show();
        followButton.setEnabled(false);
        unfollowButton.setEnabled(true);
    }

    @Override
    public void followUnsuccessful(MakeFollowResponse response)
    {
        Toast.makeText(this, "Not Followed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void unfollowSuccessful(MakeUnfollowResponse response)
    {
        Toast.makeText(this, "Successfully Unfollowed", Toast.LENGTH_LONG).show();
        unfollowButton.setEnabled(false);
        followButton.setEnabled(true);
    }

    @Override
    public void unfollowUnsuccessful(MakeUnfollowResponse response)
    {
        Toast.makeText(this, "Hahaha, still following me!!!!", Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "ERROR: Not Followed", Toast.LENGTH_LONG).show();
    }
}
