package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.MakeUnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeUnfollowResponse;
import edu.byu.cs.tweeter.presenter.MakeUnfollowPresenter;

public class GetMakeUnfollowTask extends AsyncTask<MakeUnfollowRequest, Void, MakeUnfollowResponse>
{
    private final MakeUnfollowPresenter presenter; //Presenters are connected to Fragments and activities, not tasks (tasks can have
    //more than one presenter).
    //Create a generic presenter task that the other tasks implement.
    private final GetMakeUnfollowTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void unfollowSuccessful(MakeUnfollowResponse response);
        void unfollowUnsuccessful(MakeUnfollowResponse response);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetMakeUnfollowTask(MakeUnfollowPresenter presenter, GetMakeUnfollowTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected MakeUnfollowResponse doInBackground(MakeUnfollowRequest... makeUnfollowRequests) {

        MakeUnfollowResponse response = null;

        try {
            response = presenter.sendUnfollowRequest(makeUnfollowRequests[0]);
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(MakeUnfollowResponse response) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(response.isSuccess()) {
            observer.unfollowSuccessful(response);
        } else {
            observer.unfollowUnsuccessful(response);
        }
    }
}
