package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest
{
    private final String username;
    private final String password;
    private final String firstName;
    private final String lastName;
    private final byte[] image;
    //private Bitmap imageBytes;
    //need image?

    /**
     * Creates an instance.
     *
     * @param username the username of the user to be logged in.
     * @param password the password of the user to be logged in.
     */
    public RegisterRequest(String username, String password, String firstName, String lastName, byte[] imageURL/*Bitmap imageBytes*/ ) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = imageURL;
        //this.imageBytes = imageBytes;
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
    public byte[] getImage()  { return image; }
    //public Bitmap getImageBytes() { return imageBytes; }
}
