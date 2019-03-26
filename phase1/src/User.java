import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable {

  private static final long serialVersionUID = 1L;
  String name;
  String email;
  //    static ArrayList<User> userList = new ArrayList<>();
  /**
   * @param name
   * @param email
   */
  public User(String name, String email) {
    this.name = name;
    this.email = email;
  }

  /**
   * @param
   * @return String
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @return String
   */
  @Override
  public String toString() {
    return this.name + ": " + this.email;
  }
}
