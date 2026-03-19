package A2MinhNB_HE191060.SE1941JV.repository;

import A2MinhNB_HE191060.SE1941JV.entity.NewsArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsArticleRepository extends JpaRepository<NewsArticle, Integer> {
    List<NewsArticle> findByNewsStatus(Boolean status);

    Page<NewsArticle> findByNewsStatus(Boolean status, Pageable pageable);

    Page<NewsArticle> findByCreatedByAccountId(Integer accountId, Pageable pageable);

    long countByCategoryCategoryId(Integer categoryId);

    long countByCreatedByAccountId(Integer accountId);

    @Query("SELECT n FROM NewsArticle n WHERE n.newsStatus = :status AND " +
            "(LOWER(n.newsTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.headline) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.newsContent) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<NewsArticle> searchActiveByKeyword(@Param("status") Boolean status, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT n FROM NewsArticle n WHERE n.createdBy.accountId = :accountId AND " +
            "(LOWER(n.newsTitle) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.headline) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(n.newsContent) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<NewsArticle> searchByCreatedByAndKeyword(@Param("accountId") Integer accountId, @Param("keyword") String keyword, Pageable pageable);
}