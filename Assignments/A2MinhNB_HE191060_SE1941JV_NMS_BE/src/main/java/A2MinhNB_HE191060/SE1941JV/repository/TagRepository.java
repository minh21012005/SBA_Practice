package A2MinhNB_HE191060.SE1941JV.repository;

import A2MinhNB_HE191060.SE1941JV.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findByTagNameContainingIgnoreCase(String name, Pageable pageable);
}