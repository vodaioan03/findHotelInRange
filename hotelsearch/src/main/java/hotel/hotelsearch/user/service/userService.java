package hotel.hotelsearch.user.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hotel.hotelsearch.Exceptions.UserException;
import hotel.hotelsearch.user.model.myUser;
import hotel.hotelsearch.user.repository.userRepository;
import jakarta.validation.ConstraintViolationException;

@Service
public class userService implements UserDetailsService {

  @Autowired
  private userRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private inputVerification verify;


  
  public void saveEntity(myUser user) throws ConstraintViolationException,Exception{
    if (this.entityPresent(user)) {
      throw new Exception("User already exist "+user.getUsername());
    }
    else{
      user.setCreatedTime(LocalDateTime.now());
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
    }
  }

  public myUser getUser(String username) throws UserException{
    Optional<myUser> userFound = userRepository.findUserByUsername(username);
    if(userFound.isPresent()) return userFound.get();
    else throw new UserException("User not found");
  }

  public void changeMail(String username, String email) throws UserException{
    Optional<myUser> userFound = userRepository.findUserByUsername(username);
    if(userFound.isPresent()) {
      myUser user = userFound.get();
      if(verify.emailVerification(email)) {
        user.setEmail(email);
        userRepository.save(user);
      }
      else throw new UserException(UserException.invalidMail(email));
    }
    else throw new UserException(UserException.invalidUser(username));
  }

  public void changeNumber(String username, String phoneNumber) throws UserException{
    Optional<myUser> userFound = userRepository.findUserByUsername(username);
    if(userFound.isPresent()) {
      myUser user = userFound.get();
      if(!verify.numberExist(phoneNumber)) {
        user.setPhoneNumber(phoneNumber);
        userRepository.save(user);
      }
      else throw new UserException(UserException.invalidNumber(phoneNumber));
    }
    else throw new UserException(UserException.invalidUser(username));
  }

  public void changeFirstName(String username, String firstName) throws UserException{
    Optional<myUser> userFound = userRepository.findUserByUsername(username);
    if(userFound.isPresent()) {
      myUser user = userFound.get();
      user.setFirstName(firstName);
      userRepository.save(user);
    }
    else throw new UserException(UserException.invalidUser(username));
  }

  public void changeLastName(String username, String lastName) throws UserException{
    Optional<myUser> userFound = userRepository.findUserByUsername(username);
    if(userFound.isPresent()) {
      myUser user = userFound.get();
      user.setLastName(lastName);
      userRepository.save(user);
    }
    else throw new UserException(UserException.invalidUser(username));
  }

  public void changePassword(String username, String password) throws UserException{
    Optional<myUser> userFound = userRepository.findUserByUsername(username);
    if(userFound.isPresent()) {
      myUser user = userFound.get();
      user.setPassword(passwordEncoder.encode(password));
      userRepository.save(user);
    }
    else throw new UserException(UserException.invalidUser(username));
  }

  public Boolean entityPresent(myUser user) {
    Optional<myUser> userFound = userRepository.findUserByUsername(user.getUsername());
    return userFound.isPresent();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<myUser> userFound = userRepository.findUserByUsername(username);
    if(userFound.isPresent()){
      myUser object = userFound.get();
      return User.builder()
                              .username(object.getUsername())
                              .password(object.getPassword())
                              .authorities(object.getAuthorities())
                              .build();
    }
    else{
      throw new UsernameNotFoundException(username);
    }
  }
  
}
