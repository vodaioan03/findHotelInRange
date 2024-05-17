package hotel.hotelsearch.Exceptions;

public class UserException extends Exception{
  private static final long serialVersionUID = 2L;


  public UserException(String message) {
    super(message);
  }
  public static String invalidMail(String mail) {
    return "Wrong email inserted: " + mail;
  }

  public static String mailUsed(String mail) {
    return "Email: " + mail + " already used!";
  }

  public static String invalidUser(String username) {
    return "Username: " + username + " invalid!";
  }
  public static String usernameUsed(String username) {
    return "Username: " + username + " already used!";
  }

  public static String invalidNumber(String numberPhone) {
    return "Phone Number: " + numberPhone + " invalid!";
  }
  public static String numberUsed(String numberPhone) {
    return "Phone Number: " + numberPhone + " already used!";
  }
}
