package A2MinhNB_HE191060.SE1941JV.service;

import A2MinhNB_HE191060.SE1941JV.dto.account.AccountResponse;
import A2MinhNB_HE191060.SE1941JV.dto.auth.LoginRequest;
import A2MinhNB_HE191060.SE1941JV.dto.auth.LoginResponse;
import A2MinhNB_HE191060.SE1941JV.entity.SystemAccount;
import A2MinhNB_HE191060.SE1941JV.security.AccountUserDetails;
import A2MinhNB_HE191060.SE1941JV.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AccountService accountService;

    public AccountResponse register(A2MinhNB_HE191060.SE1941JV.dto.auth.RegisterRequest request) {
        A2MinhNB_HE191060.SE1941JV.dto.account.AccountRequest accountRequest = A2MinhNB_HE191060.SE1941JV.dto.account.AccountRequest.builder()
                .accountName(request.getAccountName())
                .accountEmail(request.getAccountEmail())
                .accountPassword(request.getAccountPassword())
                .accountRole(2) // Default to Staff
                .build();
        return accountService.create(accountRequest);
    }

    public LoginResponse login(LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SystemAccount account = ((AccountUserDetails) auth.getPrincipal()).getAccount();
        String role = account.getAccountRole() == 1 ? "ADMIN" : "STAFF";
        String token = jwtUtil.generateToken(account.getAccountEmail(), account.getAccountId(), role);
        AccountResponse accountResponse = AccountResponse.builder()
                .accountId(account.getAccountId())
                .accountName(account.getAccountName())
                .accountEmail(account.getAccountEmail())
                .accountRole(account.getAccountRole())
                .build();
        return LoginResponse.builder()
                .token(token)
                .account(accountResponse)
                .build();
    }
}
