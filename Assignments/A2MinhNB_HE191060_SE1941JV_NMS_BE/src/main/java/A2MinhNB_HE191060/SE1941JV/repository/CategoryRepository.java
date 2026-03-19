package A2MinhNB_HE191060.SE1941JV.repository;

import A2MinhNB_HE191060.SE1941JV.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findByCategoryNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Category> findByCategoryNameContainingIgnoreCaseAndIsActive(String name, Boolean isActive, Pageable pageable);

    Page<Category> findByIsActive(Boolean isActive, Pageable pageable);
}