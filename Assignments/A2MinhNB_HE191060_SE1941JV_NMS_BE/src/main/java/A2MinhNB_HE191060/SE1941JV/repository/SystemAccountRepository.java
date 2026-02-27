package A2MinhNB_HE191060.SE1941JV.repository;

import A2MinhNB_HE191060.SE1941JV.entity.SystemAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SystemAccountRepository extends JpaRepository<SystemAccount, Long> {
    boolean existsByAccountEmail(String email);

    Optional<SystemAccount> findByAccountEmail(String email);

    Page<SystemAccount> findByAccountNameContainingIgnoreCaseOrAccountEmailContainingIgnoreCase(
            String name, String email, Pageable pageable);
}
