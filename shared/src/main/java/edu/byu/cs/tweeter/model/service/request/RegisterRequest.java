package edu.byu.cs.tweeter.model.service.request;

public class RegisterRequest
{
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String imageURL;
    private byte[] image;
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

    public RegisterRequest(String username, String password, String firstName, String lastName, String imageURL)
    {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageURL = imageURL;
    }

    public RegisterRequest(){}

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
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
