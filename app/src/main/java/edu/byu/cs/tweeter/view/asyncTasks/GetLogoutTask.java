package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;

public class GetLogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse>
{
    private final LogoutPresenter presenter; //Presenters are connected to Fragments and activities, not tasks (tasks can have
    //more than one presenter).
    //Create a generic presenter task that the other tasks implement.
    private final GetLogoutTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void logoutSuccessful(LogoutResponse response);
        void logoutUnsuccessful(LogoutResponse response);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetLogoutTask(LogoutPresenter presenter, GetLogoutTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected LogoutResponse doInBackground(LogoutRequest... logoutRequests) {

        LogoutResponse response = null;

        try {
            response = presenter.sendLogoutRequest(logoutRequests[0]);
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param logoutResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(LogoutResponse logoutResponse)
    {
        if (exception != null)
        {
            observer.handleException(exception);
        } else if (logoutResponse.isSuccess())
        {
            observer.logoutSuccessful(logoutResponse);
        } else
        {
            observer.logoutUnsuccessful(logoutResponse);
        }
    }
}

