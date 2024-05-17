package hotel.hotelsearch.user.service;

import java.util.regex.Pattern;
import java.util.Optional;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hotel.hotelsearch.Exceptions.UserException;
import hotel.hotelsearch.user.model.myUser;
import hotel.hotelsearch.user.model.role;
import hotel.hotelsearch.user.repository.userRepository;


@Service
public class inputVerification {
  private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
  private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

  @Autowired
  private userRepository repository;

  public boolean emailVerification(String mail) {
    Optional<myUser> userFound = repository.findUserByEmail(mail);
    if(userFound.isPresent()) return false;
    Matcher matcher = pattern.matcher(mail);
    if(!matcher.matches()) return false;
    return true;
  }

  public boolean numberExist(String number) {
    if(!number.matches("^[0-9]*$")) return true;
    Optional<myUser> userFound = repository.findUserByPhoneNumber(number);
    if(userFound.isPresent()) return true;
    return false;
  }

  public boolean usernameValid(String username) {
    Optional<myUser> userFound = repository.findUserByUsername(username);
    if(userFound.isPresent()) return false;
    if(!username.matches("^[a-z0-9A-Z]*$")) return false;

    return true;
  }

public void registerVerify(myUser userToVerify) throws UserException {
  String errors = "";
  if(!this.emailVerification(userToVerify.getEmail())) errors += "Email not valid! "; 
  if(this.numberExist(userToVerify.getPhoneNumber())) errors += "Phone Number already used. ";
  if(!this.usernameValid(userToVerify.getUsername())) errors +="User not valid! "; 
  if(userToVerify.getRole() != role.MEMBER) errors += "Only users with member role!";

  if(!errors.isEmpty()) throw new UserException(errors);
}

}
