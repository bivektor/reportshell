package reportshell.samples.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class LoggedInAdvice {

  @ModelAttribute("loggedIn")
  public boolean isLoggedIn(Authentication authentication) {
    return authentication != null && authentication.isAuthenticated();
  }
}
