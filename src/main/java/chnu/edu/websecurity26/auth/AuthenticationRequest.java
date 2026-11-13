package chnu.edu.websecurity26.auth;

/*
  @author   Pavliuk
  @project   web-security26
  @class  AuthenticationRequest
  @version  1.0.0 
  @since 13.11.2026 - 23.56
*/
import lombok.Data;
import lombok.NonNull;
@Data
public class AuthenticationRequest {
    @NonNull
    private String login;
    @NonNull
    private String password;
}
