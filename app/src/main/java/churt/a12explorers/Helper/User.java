package churt.a12explorers.Helper;

/**
 * Created by Christopher Hurt on 9/13/2017.
 */

public class User {
    public String firstName, lastName, email, avatar;
    public int points;

    public User(String firstName, String lastName, String email, String avatar, int points) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.avatar = avatar;
        this.points = points;
    }
}
