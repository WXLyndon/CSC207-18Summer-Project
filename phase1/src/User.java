import java.util.ArrayList;

public abstract class User{
    private String name;
    private String email;
    static ArrayList<User> userList;

    public User(String name, String email){
        this.name = name;
        this.email = email;
        userList.add(this);
    }
}
