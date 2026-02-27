package A2MinhNB_HE191060.SE1941JV.config;

import A2MinhNB_HE191060.SE1941JV.entity.SystemAccount;
import A2MinhNB_HE191060.SE1941JV.repository.SystemAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {
    private final SystemAccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        if (accountRepository.count() > 0) return;
        // Default admin: admin@funews.com / admin123
        accountRepository.save(SystemAccount.builder()
                .accountName("Admin")
                .accountEmail("admin@funews.com")
                .accountRole(1)
                .accountPassword(passwordEncoder.encode("admin123"))
                .build());
        // Default staff: staff@funews.com / staff123
        accountRepository.save(SystemAccount.builder()
                .accountName("Staff User")
                .accountEmail("staff@funews.com")
                .accountRole(2)
                .accountPassword(passwordEncoder.encode("staff123"))
                .build());
    }
}
