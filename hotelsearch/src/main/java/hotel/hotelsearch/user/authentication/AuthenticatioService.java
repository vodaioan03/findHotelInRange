package hotel.hotelsearch.user.authentication;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import hotel.hotelsearch.config.jwtService;
import hotel.hotelsearch.user.model.myUser;
import hotel.hotelsearch.user.model.role;
import hotel.hotelsearch.user.repository.userRepository;
import hotel.hotelsearch.user.service.inputVerification;

@Service
public class AuthenticatioService {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  userRepository userRepository;
  @Autowired
  private jwtService jwtService;

  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private inputVerification verify;


  public AuthenticatioService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public AuthenticationResponse register(myUser request) throws Exception {
    verify.registerVerify(request);
    System.err.println("Intra");
    String base64 = request.getAvatarPhoto();
    myUser user = new myUser(request.getUsername(),passwordEncoder.encode(request.getPassword()),role.MEMBER,request.getEmail(),request.getPhoneNumber(),request.getFirstName(),request.getLastName(),request.getGender(),request.getAdress(),request.getBirthDate(),base64);
    user.setCreatedTime(LocalDateTime.now());
    userRepository.save(user);
    String token = jwtService.generateToken(user, generateExtraClaims(user));
    
    return new AuthenticationResponse(token);
  }

  public Optional<myUser> getUserRequest(AuthenticationRequest authenticationRequest){
    return userRepository.findUserByUsername(authenticationRequest.getUsername()); 
  }

  public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
      UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword());
      authenticationManager.authenticate(authToken);
      myUser user = userRepository.findUserByUsername(authenticationRequest.getUsername()).get();
      String jwt = jwtService.generateToken(user,generateExtraClaims(user));
      return new AuthenticationResponse(jwt); 
   }
  private Map<String, Object> generateExtraClaims(myUser user) {
    Map<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("firstname",user.getFirstName());
    extraClaims.put("lastname", user.getLastName());
    extraClaims.put("role",user.getRole());

    return extraClaims;
  }
}
