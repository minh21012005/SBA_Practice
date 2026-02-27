package A2MinhNB_HE191060.SE1941JV.controller;

import A2MinhNB_HE191060.SE1941JV.dto.account.AccountRequest;
import A2MinhNB_HE191060.SE1941JV.dto.account.AccountResponse;
import A2MinhNB_HE191060.SE1941JV.dto.account.ProfileUpdateRequest;
import A2MinhNB_HE191060.SE1941JV.dto.common.PageResponse;
import A2MinhNB_HE191060.SE1941JV.security.AccountUserDetails;
import A2MinhNB_HE191060.SE1941JV.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("/me")
    public ResponseEntity<AccountResponse> getProfile(@AuthenticationPrincipal AccountUserDetails user) {
        if (user == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(accountService.getById(user.getAccountId()));
    }

    @PutMapping("/me")
    public ResponseEntity<AccountResponse> updateProfile(
            @AuthenticationPrincipal AccountUserDetails user,
            @Valid @RequestBody ProfileUpdateRequest request) {
        if (user == null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(accountService.updateProfile(user.getAccountId(), request));
    }

    @GetMapping
    public ResponseEntity<PageResponse<AccountResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "accountId") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(accountService.search(keyword, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable Long id, @Valid @RequestBody AccountRequest request) {
        return ResponseEntity.ok(accountService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
