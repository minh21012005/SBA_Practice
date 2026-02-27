package A2MinhNB_HE191060.SE1941JV.service;

import A2MinhNB_HE191060.SE1941JV.dto.account.AccountRequest;
import A2MinhNB_HE191060.SE1941JV.dto.account.AccountResponse;
import A2MinhNB_HE191060.SE1941JV.dto.account.ProfileUpdateRequest;
import A2MinhNB_HE191060.SE1941JV.dto.common.PageResponse;
import A2MinhNB_HE191060.SE1941JV.entity.SystemAccount;
import A2MinhNB_HE191060.SE1941JV.exception.BusinessRuleException;
import A2MinhNB_HE191060.SE1941JV.exception.ResourceNotFoundException;
import A2MinhNB_HE191060.SE1941JV.repository.NewsArticleRepository;
import A2MinhNB_HE191060.SE1941JV.repository.SystemAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final SystemAccountRepository accountRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public PageResponse<AccountResponse> search(String keyword, Pageable pageable) {
        String k = keyword != null && !keyword.isBlank() ? keyword.trim() : "";
        Page<SystemAccount> page = accountRepository
                .findByAccountNameContainingIgnoreCaseOrAccountEmailContainingIgnoreCase(k, k, pageable);
        return toPageResponse(page);
    }

    @Transactional(readOnly = true)
    public AccountResponse getById(Long id) {
        SystemAccount a = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        return toResponse(a);
    }

    @Transactional
    public AccountResponse create(AccountRequest request) {
        if (accountRepository.existsByAccountEmail(request.getAccountEmail())) {
            throw new BusinessRuleException("Email already registered: " + request.getAccountEmail());
        }
        if (request.getAccountPassword() == null || request.getAccountPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
        SystemAccount account = SystemAccount.builder()
                .accountName(request.getAccountName())
                .accountEmail(request.getAccountEmail())
                .accountRole(request.getAccountRole())
                .accountPassword(passwordEncoder.encode(request.getAccountPassword()))
                .build();
        account = accountRepository.save(account);
        return toResponse(account);
    }

    @Transactional
    public AccountResponse update(Long id, AccountRequest request) {
        SystemAccount account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        if (!account.getAccountEmail().equals(request.getAccountEmail())
                && accountRepository.existsByAccountEmail(request.getAccountEmail())) {
            throw new BusinessRuleException("Email already in use: " + request.getAccountEmail());
        }
        account.setAccountName(request.getAccountName());
        account.setAccountEmail(request.getAccountEmail());
        account.setAccountRole(request.getAccountRole());
        if (request.getAccountPassword() != null && !request.getAccountPassword().isBlank()) {
            account.setAccountPassword(passwordEncoder.encode(request.getAccountPassword()));
        }
        account = accountRepository.save(account);
        return toResponse(account);
    }

    /** Update own profile (name, email, password). Role is not changed. */
    @Transactional
    public AccountResponse updateProfile(Long accountId, ProfileUpdateRequest request) {
        SystemAccount account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + accountId));
        if (!account.getAccountEmail().equals(request.getAccountEmail())
                && accountRepository.existsByAccountEmail(request.getAccountEmail())) {
            throw new BusinessRuleException("Email already in use: " + request.getAccountEmail());
        }
        if (request.getAccountName() != null) account.setAccountName(request.getAccountName());
        if (request.getAccountEmail() != null) account.setAccountEmail(request.getAccountEmail());
        if (request.getAccountPassword() != null && !request.getAccountPassword().isBlank()) {
            account.setAccountPassword(passwordEncoder.encode(request.getAccountPassword()));
        }
        account = accountRepository.save(account);
        return toResponse(account);
    }

    @Transactional
    public void delete(Long id) {
        SystemAccount account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + id));
        long count = newsArticleRepository.countByCreatedByAccountId(id);
        if (count > 0) {
            throw new BusinessRuleException("Cannot delete account: account has created " + count + " news article(s).");
        }
        accountRepository.delete(account);
    }

    private AccountResponse toResponse(SystemAccount a) {
        return AccountResponse.builder()
                .accountId(a.getAccountId())
                .accountName(a.getAccountName())
                .accountEmail(a.getAccountEmail())
                .accountRole(a.getAccountRole())
                .build();
    }

    private PageResponse<AccountResponse> toPageResponse(Page<SystemAccount> page) {
        return PageResponse.<AccountResponse>builder()
                .content(page.getContent().stream().map(this::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }
}
