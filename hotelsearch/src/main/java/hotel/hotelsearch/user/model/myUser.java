package hotel.hotelsearch.user.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Document(value="users")
public class myUser implements UserDetails {
  @Id
  private String id;
  private String username;
  private String password;
  private role role;
  private String email;
  private String phoneNumber;
  private String firstName;
  private String lastName;
  private String gender;
  private address adress;
  private LocalDateTime birthDate;
  private LocalDateTime createdTime;
  private String avatarPhoto;

  public String getAvatarPhoto() {
    return avatarPhoto;
  }

  public void setAvatarPhoto(String avatarPhoto) {
    this.avatarPhoto = avatarPhoto;
  }

  public myUser() {

  }

  public myUser(String username, String password, role role, String email, String phoneNumber, String firstName,
      String lastName, String gender, address adress, LocalDateTime birthDate, String avatar) {
    this.username = username;
    this.password = password;
    this.role = role;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.adress = adress;
    this.birthDate = birthDate;
    this.avatarPhoto = avatar;
  }
  public myUser(String username, String password, role role) {
    this.username = username;
    this.password = password;
    this.role = role;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = role.getPermissions().stream().map(
      permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name())
    ).collect(Collectors.toList());

    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
    return authorities;
  }
  
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  @Override
  public boolean isEnabled() {
    return true;
  }

  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public role getRole() {
    return role;
  }
  public void setRole(role role) {
    this.role = role;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getGender() {
    return gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public address getAdress() {
    return adress;
  }
  public void setAdress(address adress) {
    this.adress = adress;
  }
  public LocalDateTime getBirthDate() {
    return birthDate;
  }
  public void setBirthDate(LocalDateTime birthDate) {
    this.birthDate = birthDate;
  }
  public LocalDateTime getCreatedTime() {
    return createdTime;
  }
  public void setCreatedTime(LocalDateTime createdTime) {
    this.createdTime = createdTime;
  }
  public String getPassword() {
    return this.password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getUsername() {
    return this.username;
  }
  public void setUsername(String username) {
    this.username = username;
  }
  
}