package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.presenter.PostPresenter;

public class GetPostTask extends AsyncTask<PostRequest, Void, PostResponse>
{
    private final PostPresenter presenter; //Presenters are connected to Fragments and activities, not tasks (tasks can have
    //more than one presenter).
    //Create a generic presenter task that the other tasks implement.
    private final GetPostTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void postSuccessful(PostResponse response);
        void postUnsuccessful(PostResponse response);
        void handleException(Exception exception);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetPostTask(PostPresenter presenter, GetPostTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected PostResponse doInBackground(PostRequest... postRequests) {

        PostResponse response = null;

        try {
            response = presenter.sendPostRequest(postRequests[0]);
        } catch (IOException | TweeterRemoteException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param postResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(PostResponse postResponse)
    {
        if (exception != null)
        {
            observer.handleException(exception);
        } else if (postResponse.isSuccess())
        {
            observer.postSuccessful(postResponse);
        } else
        {
            observer.postUnsuccessful(postResponse);
        }
    }
}
