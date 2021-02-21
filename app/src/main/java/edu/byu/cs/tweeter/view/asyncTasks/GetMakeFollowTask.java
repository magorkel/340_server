package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.MakeFollowRequest;
import edu.byu.cs.tweeter.model.service.response.MakeFollowResponse;
import edu.byu.cs.tweeter.presenter.MakeFollowPresenter;

public class GetMakeFollowTask extends AsyncTask<MakeFollowRequest, Void, MakeFollowResponse>
{
    private final MakeFollowPresenter presenter; //Presenters are connected to Fragments and activities, not tasks (tasks can have
    //more than one presenter).
    //Create a generic presenter task that the other tasks implement.
    private final GetMakeFollowTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void followSuccessful(MakeFollowResponse response);
        void followUnsuccessful(MakeFollowResponse response);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetMakeFollowTask(MakeFollowPresenter presenter, GetMakeFollowTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected MakeFollowResponse doInBackground(MakeFollowRequest... makeFollowRequests) {

        MakeFollowResponse response = null;

        try {
            response = presenter.sendFollowRequest(makeFollowRequests[0]);
        } catch (IOException ex) {
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
    protected void onPostExecute(MakeFollowResponse userResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(userResponse.isSuccess()) {
            observer.followSuccessful(userResponse);
        } else {
            observer.followUnsuccessful(userResponse);
        }
    }
}
