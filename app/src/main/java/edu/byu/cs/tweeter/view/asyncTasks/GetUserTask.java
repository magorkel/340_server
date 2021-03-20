package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.presenter.UserPresenter;

public class GetUserTask extends AsyncTask<UserRequest, Void, UserResponse> {
    private final UserPresenter presenter; //Presenters are connected to Fragments and activities, not tasks (tasks can have
    //more than one presenter).
    //Create a generic presenter task that the other tasks implement.
    private final GetUserTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void usersRetreived(UserResponse userResponse); //Observer
        void usersUnsuccessful(UserResponse userResponse);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetUserTask(UserPresenter presenter, GetUserTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected UserResponse doInBackground(UserRequest... userRequests) {

        UserResponse response = null;

        try {
            response = presenter.getUser(userRequests[0]);
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param userResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(UserResponse userResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(userResponse.isSuccess()) {
            observer.usersRetreived(userResponse);
        } else {
            observer.usersUnsuccessful(userResponse);
        }
    }
}

