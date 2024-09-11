package vn.edu.usth.outlook;

public class User {
    private String email;
    private String id;
    private String imageurl;

    public User(String email, String id, String imageurl) {
        this.email = email;
        this.id = id;
        this.imageurl = imageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
