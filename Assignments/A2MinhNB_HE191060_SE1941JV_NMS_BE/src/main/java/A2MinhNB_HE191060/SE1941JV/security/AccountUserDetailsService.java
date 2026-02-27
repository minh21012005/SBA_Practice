package A2MinhNB_HE191060.SE1941JV.security;

import A2MinhNB_HE191060.SE1941JV.repository.SystemAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsService implements UserDetailsService {
    private final SystemAccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return accountRepository.findByAccountEmail(username)
                .map(AccountUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("Account not found: " + username));
    }
}
