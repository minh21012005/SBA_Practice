package A2MinhNB_HE191060.SE1941JV.controller;

import A2MinhNB_HE191060.SE1941JV.dto.account.AccountResponse;
import A2MinhNB_HE191060.SE1941JV.dto.auth.LoginRequest;
import A2MinhNB_HE191060.SE1941JV.dto.auth.LoginResponse;
import A2MinhNB_HE191060.SE1941JV.security.AccountUserDetails;
import A2MinhNB_HE191060.SE1941JV.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<AccountResponse> me(@AuthenticationPrincipal AccountUserDetails user) {
        if (user == null) {
            return ResponseEntity.status(401).build();
        }
        AccountResponse dto = AccountResponse.builder()
                .accountId(user.getAccountId())
                .accountName(user.getAccount().getAccountName())
                .accountEmail(user.getAccount().getAccountEmail())
                .accountRole(user.getAccount().getAccountRole())
                .build();
        return ResponseEntity.ok(dto);
    }
}
