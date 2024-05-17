package hotel.hotelsearch.user.model;

import java.util.Arrays;
import java.util.List;
public enum role {
  MEMBER(Arrays.asList(permission.USER_MEMBER)),

  ADMIN(Arrays.asList(permission.USER_ADMIN, permission.USER_MEMBER)),

  DEVELOPER(Arrays.asList(permission.USER_DEVELOPER, permission.USER_ADMIN, permission.USER_MEMBER));

  private List<permission> permissions;

  private role(List<permission> permissions) {
    this.permissions = permissions;
  }

  public List<permission> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<permission> permissions) {
    this.permissions = permissions;
  }
}
