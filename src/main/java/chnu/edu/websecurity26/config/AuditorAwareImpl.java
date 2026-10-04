package chnu.edu.websecurity26.config;

/*
  @author   Pavliuk
  @project   web-security26
  @class  AuditorAwareImpl
  @version  1.0.0 
  @since 04.10.2026 - 22.43
*/
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication.getPrincipal().equals("anonymousUser")) {
            return Optional.of("SYSTEM");
        }

        return Optional.of(authentication.getName());
    }
}
