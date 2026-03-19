package A2MinhNB_HE191060.SE1941JV.service;

import A2MinhNB_HE191060.SE1941JV.dto.account.AccountResponse;
import A2MinhNB_HE191060.SE1941JV.dto.category.CategoryResponse;
import A2MinhNB_HE191060.SE1941JV.dto.common.PageResponse;
import A2MinhNB_HE191060.SE1941JV.dto.news.NewsArticleRequest;
import A2MinhNB_HE191060.SE1941JV.dto.news.NewsArticleResponse;
import A2MinhNB_HE191060.SE1941JV.entity.Category;
import A2MinhNB_HE191060.SE1941JV.entity.NewsArticle;
import A2MinhNB_HE191060.SE1941JV.entity.SystemAccount;
import A2MinhNB_HE191060.SE1941JV.entity.Tag;
import A2MinhNB_HE191060.SE1941JV.exception.ResourceNotFoundException;
import A2MinhNB_HE191060.SE1941JV.repository.CategoryRepository;
import A2MinhNB_HE191060.SE1941JV.repository.NewsArticleRepository;
import A2MinhNB_HE191060.SE1941JV.repository.SystemAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsArticleService {
    private static final Boolean ACTIVE = true;

    private final NewsArticleRepository newsArticleRepository;
    private final CategoryRepository categoryRepository;
    private final SystemAccountRepository accountRepository;
    private final TagService tagService;

    /** Public: list only active news (no auth). */
    @Transactional(readOnly = true)
    public PageResponse<NewsArticleResponse> findActive(String keyword, Pageable pageable) {
        String k = keyword != null && !keyword.isBlank() ? keyword.trim() : "";
        Page<NewsArticle> page = newsArticleRepository.searchActiveByKeyword(ACTIVE, k, pageable);
        return toPageResponse(page);
    }

    /** Public: get one article by id (only if active). */
    @Transactional(readOnly = true)
    public NewsArticleResponse findActiveById(Integer id) {
        NewsArticle n = newsArticleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News article not found: " + id));
        if (n.getNewsStatus() != ACTIVE) {
            throw new ResourceNotFoundException("News article not found or not active: " + id);
        }
        return toResponse(n);
    }

    /** Staff: list news created by current user (my news). */
    @Transactional(readOnly = true)
    public PageResponse<NewsArticleResponse> findMyNews(Integer accountId, String keyword, Pageable pageable) {
        String k = keyword != null && !keyword.isBlank() ? keyword.trim() : "";
        Page<NewsArticle> page = newsArticleRepository.searchByCreatedByAndKeyword(accountId, k, pageable);
        return toPageResponse(page);
    }

    @Transactional(readOnly = true)
    public NewsArticleResponse getById(Integer id) {
        NewsArticle n = newsArticleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News article not found: " + id));
        return toResponse(n);
    }

    @Transactional
    public NewsArticleResponse create(NewsArticleRequest request, Integer createdByAccountId) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + request.getCategoryId()));
        SystemAccount createdBy = accountRepository.findById(createdByAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found: " + createdByAccountId));
        Set<Tag> tags = request.getTagIds() != null ? tagService.resolveTags(request.getTagIds()) : Set.of();

        NewsArticle article = NewsArticle.builder()
                .newsTitle(request.getNewsTitle())
                .headline(request.getHeadline())
                .newsContent(request.getNewsContent())
                .newsSource(request.getNewsSource())
                .newsStatus(request.getNewsStatus())
                .category(category)
                .createdBy(createdBy)
                .tags(tags)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        article = newsArticleRepository.save(article);
        return toResponse(article);
    }

    @Transactional
    public NewsArticleResponse update(Integer id, NewsArticleRequest request) {
        NewsArticle article = newsArticleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News article not found: " + id));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + request.getCategoryId()));
        Set<Tag> tags = request.getTagIds() != null ? tagService.resolveTags(request.getTagIds()) : Set.of();

        article.setNewsTitle(request.getNewsTitle());
        article.setHeadline(request.getHeadline());
        article.setNewsContent(request.getNewsContent());
        article.setNewsSource(request.getNewsSource());
        article.setNewsStatus(request.getNewsStatus());
        article.setCategory(category);
        article.setTags(tags);
        article.setModifiedDate(LocalDateTime.now());
        article = newsArticleRepository.save(article);
        return toResponse(article);
    }

    @Transactional
    public void delete(Integer id) {
        NewsArticle article = newsArticleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News article not found: " + id));
        newsArticleRepository.delete(article);
    }

    private NewsArticleResponse toResponse(NewsArticle n) {
        return NewsArticleResponse.builder()
                .newsArticleId(n.getNewsArticleId())
                .newsTitle(n.getNewsTitle())
                .headline(n.getHeadline())
                .newsContent(n.getNewsContent())
                .newsSource(n.getNewsSource())
                .createdDate(n.getCreatedDate())
                .modifiedDate(n.getModifiedDate())
                .newsStatus(n.getNewsStatus())
                .category(categoryToResponse(n.getCategory()))
                .createdBy(accountToResponse(n.getCreatedBy()))
                .tags(n.getTags() != null ? n.getTags().stream().map(tagService::toResponse).collect(Collectors.toSet()) : Set.of())
                .build();
    }

    private CategoryResponse categoryToResponse(Category c) {
        if (c == null) return null;
        return CategoryResponse.builder()
                .categoryId(c.getCategoryId())
                .categoryName(c.getCategoryName())
                .categoryDesciption(c.getCategoryDesciption())
                .parentCategoryId(c.getParentCategory() != null ? c.getParentCategory().getCategoryId() : null)
                .isActive(c.getIsActive())
                .build();
    }

    private AccountResponse accountToResponse(SystemAccount a) {
        if (a == null) return null;
        return AccountResponse.builder()
                .accountId(a.getAccountId())
                .accountName(a.getAccountName())
                .accountEmail(a.getAccountEmail())
                .accountRole(a.getAccountRole())
                .build();
    }

    private PageResponse<NewsArticleResponse> toPageResponse(Page<NewsArticle> page) {
        return PageResponse.<NewsArticleResponse>builder()
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
