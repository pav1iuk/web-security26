package chnu.edu.websecurity26.auth;

import lombok.Builder;
import lombok.Data;

/*
  @author   Pavliuk
  @project   web-security26
  @class  AuthenticationResponse
  @version  1.0.0 
  @since 13.11.2026 - 23.56
*/
@Data
@Builder
public class AuthenticationResponse {
    private String token;
}
