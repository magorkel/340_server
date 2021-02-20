package edu.byu.cs.tweeter.model.service.request;

import android.graphics.Bitmap;

public class RegisterRequest
{
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    //private final String imageUrl;
    private Bitmap imageBytes;
    //need image?

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public RegisterRequest(String username, String password, String firstName, String lastName, Bitmap imageBytes ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageBytes = imageBytes;
    }

    /**
     * Returns the username of the user to be logged in by this request.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user to be logged in by this request.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Bitmap getImageBytes() { return imageBytes; }
}
