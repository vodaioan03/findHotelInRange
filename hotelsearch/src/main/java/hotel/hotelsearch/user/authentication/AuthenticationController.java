package hotel.hotelsearch.user.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.hotelsearch.Exceptions.UserException;
import hotel.hotelsearch.user.model.myUser;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private AuthenticatioService authenticatioService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody myUser request) {
    try {
      return new ResponseEntity<AuthenticationResponse>(authenticatioService.register(request),HttpStatus.OK);
    } catch (ConstraintViolationException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    }catch (UserException e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    } 
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
    try {
      return new ResponseEntity<AuthenticationResponse>(authenticatioService.login(request),HttpStatus.OK);
    } catch (ConstraintViolationException e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.UNPROCESSABLE_ENTITY);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
    }
  }
}
