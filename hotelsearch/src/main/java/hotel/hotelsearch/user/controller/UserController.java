package hotel.hotelsearch.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.hotelsearch.Exceptions.UserException;
import hotel.hotelsearch.user.model.myUser;
import hotel.hotelsearch.user.service.userService;


@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  private userService service;

  @GetMapping("/getUser/{username}")
  public ResponseEntity<?> getUser(@PathVariable("username") String username) {
    //TODO: add verify for jwt and username
    try {
      return new ResponseEntity<myUser>(service.getUser(username),HttpStatus.OK);
    } catch (UserException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/changeEmail/{username}")
  public ResponseEntity<?> changeEmail(@PathVariable("username") String username,@RequestBody String request) {
    //TODO: add verify for jwt and username
    try {
      service.changeMail(username,request);
      return new ResponseEntity<myUser>(service.getUser(username),HttpStatus.OK);
    } catch (UserException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/changeNumber/{username}")
  public ResponseEntity<?> changeNumber(@PathVariable("username") String username,@RequestBody String request) {
    //TODO: add verify for jwt and username
    try {
      service.changeNumber(username,request);
      return new ResponseEntity<myUser>(service.getUser(username),HttpStatus.OK);
    } catch (UserException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/changeFirstName/{username}")
  public ResponseEntity<?> changeFirstName(@PathVariable("username") String username,@RequestBody String request) {
    //TODO: add verify for jwt and username
    try {
      service.changeFirstName(username,request);
      return new ResponseEntity<myUser>(service.getUser(username),HttpStatus.OK);
    } catch (UserException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/changeLastName/{username}")
  public ResponseEntity<?> changeLastName(@PathVariable("username") String username,@RequestBody String request) {
    //TODO: add verify for jwt and username
    try {
      service.changeLastName(username,request);
      return new ResponseEntity<myUser>(service.getUser(username),HttpStatus.OK);
    } catch (UserException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/changePassword/{username}")
  public ResponseEntity<?> changePassword(@PathVariable("username") String username,@RequestBody String request) {
    //TODO: add verify for jwt and username
    try {
      service.changePassword(username,request);
      return new ResponseEntity<myUser>(service.getUser(username),HttpStatus.OK);
    } catch (UserException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
    }
  }
}
