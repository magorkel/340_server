package edu.byu.cs.tweeter.view.main.feed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.presenter.FeedPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.main.OtherUserActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public class FeedFragment extends Fragment implements FeedPresenter.View, GetUserTask.Observer {
    private static final String LOG_TAG = "FeedFragment";
    private static final String USER_KEY = "UserKey";
    //private static final String STATUS_KEY = "StatusKey";
    private static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private static final String OTHER_USER_KEY = "OtherUserKey";

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private User user;//
    //private Status status;
    private AuthToken authToken;//
    private FeedPresenter presenter;

    private FeedFragment.FeedRecyclerViewAdapter feedRecyclerViewAdapter;
    private FeedFragment pass = this;

    /**
     * Creates an instance of the fragment and places the user and auth token in an arguments
     * bundle assigned to the fragment.
     *
     * @param user the logged in user.
     * @param authToken the auth token for this user's session.
     * @return the fragment.
     */
    public static FeedFragment newInstance(User user/*Status status*/, AuthToken authToken) {
        FeedFragment fragment = new FeedFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        //args.putSerializable(STATUS_KEY, status);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        //noinspection ConstantConditions
        user = (User) getArguments().getSerializable(USER_KEY);
        //status = (Status) getArguments().getSerializable(STATUS_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedFragment.FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerView.addOnScrollListener(new FeedFragment.FeedRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }

    @Override
    public void usersRetreived(UserResponse userResponse) {
        //launch intents here
        Intent intent = new Intent(getActivity(), OtherUserActivity.class);

        intent.putExtra(OtherUserActivity.CURRENT_USER_KEY, user);
        intent.putExtra(OtherUserActivity.OTHER_USER_KEY, userResponse.getUser());
        intent.putExtra(OtherUserActivity.AUTH_TOKEN_KEY, authToken);
        //extra dm for user were finding

        //Toast.cancel();
        startActivity(intent);
    }

    @Override
    public void usersUnsuccessful(UserResponse userResponse) {
        Toast.makeText(getContext(), "Failed to find User. ", Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        //return toast that shows no user - or other error.
        Toast.makeText(getContext(), "ERROR: Failed to find User. ", Toast.LENGTH_LONG).show();
    }

    /**
     * The ViewHolder for the RecyclerView that displays the Story data.
     */
    private class FeedHolder extends RecyclerView.ViewHolder { //FIXME: Change this for the specifics of feed.

        private final ImageView userImage; //avatar
        private final TextView userAlias;
        private final TextView userName; //fullname?
        private final TextView content;
        private final TextView postTime;

        //avatar, alias, full name, content(mentions and URLS clickable), time
        //need to make onclick listeners in holder for mentions and URLs, in FAQ

        /**
         * Creates an instance and sets an OnClickListener for the user's row.
         *
         * @param itemView the view on which the user will be displayed.
         */
        FeedHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage); //On this view box, add this...
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);
                content = itemView.findViewById(R.id.content);
                postTime = itemView.findViewById(R.id.postTime);
            } else {
                userImage = null;
                userAlias = null;
                userName = null;
                content = null;
                postTime = null;
            }
        }

        /**
         * Binds the user's data to the view.
         *
         * @param status the user.
         */
        void bindStatus(Status status) {
            userImage.setImageDrawable(ImageUtils.drawableFromByteArray(status.getUser().getImageBytes()));
            userAlias.setText(status.getUserAlias());
            userName.setText(status.getUser().getName());
            content.setText(stringBuilder(status.getContent()));
            postTime.setText(status.getTime());
        }

        private SpannableStringBuilder stringBuilder(String string)
        {
            SpannableStringBuilder builder = new SpannableStringBuilder(string);

            for (int i = 0; i < string.length(); i++)
            {
                try
                {
                    if (string.charAt(i) == '@')
                    {
                        int size = 0;
                        for (int j = i; j < string.length(); j++)
                        {
                            size++;
                            if (string.charAt(j) == ' ')
                            {
                                size -= 1;
                                break;
                            }
                        }
                        ///listener
                        int finalI = i;
                        int finalSize = size;
                        ClickableSpan firstwordClick = new ClickableSpan()
                        {
                            @Override
                            public void onClick(View widget)
                            {
                                //take us to that person
                                String alias = string.substring(finalI, finalI + finalSize);
                                //send a request to find user in database
                                //make a link to users page
                                Toast.makeText(getContext(), alias, Toast.LENGTH_SHORT).show();
                                // @Override
                                //public void loginSuccessful(LoginResponse loginResponse) {
                                UserRequest request = new UserRequest(alias);
                                GetUserTask task = new GetUserTask(presenter, pass);//Pass is a reference to the Containing
                                //fragment
                                task.execute(request);
                            }

                            @Override
                            public void updateDrawState(TextPaint ds)
                            {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(true);
                            }
                        };
                        builder.setSpan(firstwordClick, i, i + size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        i += size;
                    }
                    else if (string.charAt(i) == 'h' && string.charAt(i + 1) == 't' && string.charAt(i + 2) == 't' && string.charAt(i + 3) == 'p')
                    {
                        int size = 0;
                        for (int j = i; j < string.length(); j++)
                        {
                            size++;
                            if (string.charAt(j) == ' ')
                            {
                                size -= 1;
                                break;
                            }
                        }
                        int finalI = i;
                        int finalSize = size;
                        ClickableSpan firstwordClick = new ClickableSpan()
                        {
                            @Override
                            public void onClick(View widget)
                            {
                                String URL = string.substring(finalI, finalI + finalSize);
                                //make a link to the interweb
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                                startActivity(browserIntent);
                                Toast.makeText(getContext(), URL, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void updateDrawState(TextPaint ds)
                            {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(true);
                            }
                        };
                        builder.setSpan(firstwordClick, i, i + size, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        i += size;
                    }
                } catch (ArrayIndexOutOfBoundsException e)
                {
                    continue;
                }
            }
            content.setLinksClickable(true);
            content.setMovementMethod(LinkMovementMethod.getInstance());
            content.setText(builder, TextView.BufferType.SPANNABLE);
            return builder;
        }
    }

    /**
     * The adapter for the RecyclerView that displays the Story data.
     */
    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedFragment.FeedHolder> implements GetFeedTask.Observer {

        private final List<Status> statuses = new ArrayList<>();

        private edu.byu.cs.tweeter.model.domain.Status lastFeed;

        private boolean hasMorePages;
        private boolean isLoading = false;

        /**
         * Creates an instance and loads the first page of story data.
         */
        FeedRecyclerViewAdapter() {
            loadMoreItems();
        }

        /**
         * Adds new users to the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that items have been added.
         *
         * @param newStats the users to add.
         */
        void addItems(List<Status> newStats) {//adding status not user
            int startInsertPosition = statuses.size();
            statuses.addAll(newStats);
            this.notifyItemRangeInserted(startInsertPosition, newStats.size());
        }

        /**
         * Adds a single user to the list from which the RecyclerView retrieves the users it
         * displays and notifies the RecyclerView that an item has been added.
         *
         * @param status the user to add.
         */
        void addItem(Status status) {//same as above
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        /**
         * Removes a user from the list from which the RecyclerView retrieves the users it displays
         * and notifies the RecyclerView that an item has been removed.
         *
         * @param status the user to remove.
         */
        void removeItem(Status status) {//change to be able to delete a status
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }

        /**
         *  Creates a view holder for a followee to be displayed in the RecyclerView or for a message
         *  indicating that new rows are being loaded if we are waiting for rows to load.
         *
         * @param parent the parent view.
         * @param viewType the type of the view (ignored in the current implementation).
         * @return the view holder.
         */
        @NonNull
        @Override
        public FeedFragment.FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new FeedFragment.FeedHolder(view, viewType);
        }

        /**
         * Binds the followee at the specified position unless we are currently loading new data. If
         * we are loading new data, the display at that position will be the data loading footer.
         *
         * @param feedHolder the ViewHolder to which the followee should be bound.
         * @param position the position (in the list of followees) that contains the followee to be
         *                 bound.
         */
        @Override
        public void onBindViewHolder(@NonNull FeedFragment.FeedHolder feedHolder, int position) {
            if(!isLoading) {
                feedHolder.bindStatus(statuses.get(position));
            }
        }

        /**
         * Returns the current number of followees available for display.
         * @return the number of followees available for display.
         */
        @Override
        public int getItemCount() {
            return statuses.size();
        }

        /**
         * Returns the type of the view that should be displayed for the item currently at the
         * specified position.
         *
         * @param position the position of the items whose view type is to be returned.
         * @return the view type.
         */
        @Override
        public int getItemViewType(int position) {
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        /**
         * Causes the Adapter to display a loading footer and make a request to get more story
         * data.
         */
        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
            FeedRequest request = new FeedRequest(user.getAlias(), PAGE_SIZE, (lastFeed == null ? null : lastFeed));

            getFeedTask.execute(request);
        }

        /**
         * A callback indicating more story data has been received. Loads the new followees
         * and removes the loading footer.
         *
         * @param feedResponse the asynchronous response to the request to load more items.
         */
        @Override
        public void feedRetrieved(FeedResponse feedResponse) {
            List<Status> feed = feedResponse.getPosts();

            lastFeed = (feed.size() > 0) ? feed.get(feed.size() -1) : null;
            hasMorePages = feedResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            feedRecyclerViewAdapter.addItems(feed);
        }

        /**
         * A callback indicating that an exception was thrown by the presenter.
         *
         * @param exception the exception.
         */
        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * Adds a dummy user to the list of users so the RecyclerView will display a view (the
         * loading footer view) at the bottom of the list.
         */
        private void addLoadingFooter() {//FIXME status
            addItem(new Status("Dummy", new User(), ""));
        }

        /**
         * Removes the dummy user from the list of users so the RecyclerView will stop displaying
         * the loading footer at the bottom of the list.
         */
        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

    /**
     * A scroll listener that detects when the user has scrolled to the bottom of the currently
     * available data.
     */
    private class FeedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        /**
         * Creates a new instance.
         *
         * @param layoutManager the layout manager being used by the RecyclerView.
         */
        FeedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        /**
         * Determines whether the user has scrolled to the bottom of the currently available data
         * in the RecyclerView and asks the adapter to load more data if the last load request
         * indicated that there was more data to load.
         *
         * @param recyclerView the RecyclerView.
         * @param dx the amount of horizontal scroll.
         * @param dy the amount of vertical scroll.
         */
        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    feedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
