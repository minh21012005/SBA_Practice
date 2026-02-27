package A2MinhNB_HE191060.SE1941JV.service;

import A2MinhNB_HE191060.SE1941JV.dto.category.CategoryRequest;
import A2MinhNB_HE191060.SE1941JV.dto.category.CategoryResponse;
import A2MinhNB_HE191060.SE1941JV.dto.common.PageResponse;
import A2MinhNB_HE191060.SE1941JV.entity.Category;
import A2MinhNB_HE191060.SE1941JV.exception.BusinessRuleException;
import A2MinhNB_HE191060.SE1941JV.exception.ResourceNotFoundException;
import A2MinhNB_HE191060.SE1941JV.repository.CategoryRepository;
import A2MinhNB_HE191060.SE1941JV.repository.NewsArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final NewsArticleRepository newsArticleRepository;

    @Transactional(readOnly = true)
    public PageResponse<CategoryResponse> search(String keyword, Integer isActive, Pageable pageable) {
        String k = keyword != null && !keyword.isBlank() ? keyword.trim() : null;
        Page<Category> page;
        if (k != null && isActive != null) {
            page = categoryRepository.findByCategoryNameContainingIgnoreCaseAndIsActive(k, isActive, pageable);
        } else if (k != null) {
            page = categoryRepository.findByCategoryNameContainingIgnoreCase(k, pageable);
        } else if (isActive != null) {
            page = categoryRepository.findByIsActive(isActive, pageable);
        } else {
            page = categoryRepository.findAll(pageable);
        }
        return toPageResponse(page);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category c = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        return toResponse(c);
    }

    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        Category parent = null;
        if (request.getParentCategoryId() != null) {
            parent = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found: " + request.getParentCategoryId()));
        }
        Category category = Category.builder()
                .categoryName(request.getCategoryName())
                .categoryDescription(request.getCategoryDescription())
                .parentCategory(parent)
                .isActive(request.getIsActive())
                .build();
        category = categoryRepository.save(category);
        return toResponse(category);
    }

    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        category.setCategoryName(request.getCategoryName());
        category.setCategoryDescription(request.getCategoryDescription());
        category.setIsActive(request.getIsActive());
        if (request.getParentCategoryId() != null) {
            Category parent = categoryRepository.findById(request.getParentCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParentCategory(parent);
        } else {
            category.setParentCategory(null);
        }
        category = categoryRepository.save(category);
        return toResponse(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + id));
        long count = newsArticleRepository.countByCategoryCategoryId(id);
        if (count > 0) {
            throw new BusinessRuleException("Cannot delete category: it is used by " + count + " news article(s).");
        }
        categoryRepository.delete(category);
    }

    private CategoryResponse toResponse(Category c) {
        return CategoryResponse.builder()
                .categoryId(c.getCategoryId())
                .categoryName(c.getCategoryName())
                .categoryDescription(c.getCategoryDescription())
                .parentCategoryId(c.getParentCategory() != null ? c.getParentCategory().getCategoryId() : null)
                .isActive(c.getIsActive())
                .build();
    }

    private PageResponse<CategoryResponse> toPageResponse(Page<Category> page) {
        return PageResponse.<CategoryResponse>builder()
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
