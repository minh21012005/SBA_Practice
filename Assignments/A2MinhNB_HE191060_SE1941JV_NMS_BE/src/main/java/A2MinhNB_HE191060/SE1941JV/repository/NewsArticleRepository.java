package A2MinhNB_HE191060.SE1941JV.repository;

import A2MinhNB_HE191060.SE1941JV.entity.NewsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findByNewsStatus(Integer status);

    Page<NewsArticle> findByNewsStatus(Integer status, Pageable pageable);

    Page<NewsArticle> findByCreatedByAccountId(Long accountId, Pageable pageable);

    long countByCategoryCategoryId(Long categoryId);

    long countByCreatedByAccountId(Long accountId);

    @Query("SELECT n FROM NewsArticle n WHERE n.newsStatus = :status AND " +
            "(LOWER(n.newsTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.headline) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.newsContent) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<NewsArticle> searchActiveByKeyword(@Param("status") Integer status, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT n FROM NewsArticle n WHERE n.createdBy.accountId = :accountId AND " +
            "(LOWER(n.newsTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.headline) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.newsContent) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<NewsArticle> searchByCreatedByAndKeyword(@Param("accountId") Long accountId, @Param("keyword") String keyword, Pageable pageable);
}