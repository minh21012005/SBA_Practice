package A2MinhNB_HE191060.SE1941JV.security;

import A2MinhNB_HE191060.SE1941JV.entity.SystemAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
public class AccountUserDetails implements UserDetails {
    private final SystemAccount account;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = account.getAccountRole() == 1 ? "ROLE_ADMIN" : "ROLE_STAFF";
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return account.getAccountPassword();
    }

    @Override
    public String getUsername() {
        return account.getAccountEmail();
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

    public Integer getAccountId() {
        return account.getAccountId();
    }

    public SystemAccount getAccount() {
        return account;
    }
}
