package A2MinhNB_HE191060.SE1941JV.controller;

import A2MinhNB_HE191060.SE1941JV.dto.common.PageResponse;
import A2MinhNB_HE191060.SE1941JV.dto.news.NewsArticleRequest;
import A2MinhNB_HE191060.SE1941JV.dto.news.NewsArticleResponse;
import A2MinhNB_HE191060.SE1941JV.security.AccountUserDetails;
import A2MinhNB_HE191060.SE1941JV.service.NewsArticleService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsArticleController {
    private final NewsArticleService newsArticleService;

    // ---------- Public (no auth): active news only ----------
    @GetMapping("/news")
    public ResponseEntity<PageResponse<NewsArticleResponse>> listActive(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(newsArticleService.findActive(keyword, pageable));
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<NewsArticleResponse> getActiveById(@PathVariable Long id) {
        return ResponseEntity.ok(newsArticleService.findActiveById(id));
    }

    // ---------- Staff: my news (history) ----------
    @GetMapping("/my/news")
    public ResponseEntity<PageResponse<NewsArticleResponse>> myNews(
            @AuthenticationPrincipal AccountUserDetails user,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        if (user == null) return ResponseEntity.status(401).build();
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(newsArticleService.findMyNews(user.getAccountId(), keyword, pageable));
    }

    // ---------- Staff: full CRUD (get one for edit, create, update, delete) ----------
    @GetMapping("/news/{id}/edit")
    public ResponseEntity<NewsArticleResponse> getByIdForEdit(@PathVariable Long id) {
        return ResponseEntity.ok(newsArticleService.getById(id));
    }

    @PostMapping("/news")
    public ResponseEntity<NewsArticleResponse> create(
            @AuthenticationPrincipal AccountUserDetails user,
            @Valid @RequestBody NewsArticleRequest request) {
        if (user == null) return ResponseEntity.status(401).build();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newsArticleService.create(request, user.getAccountId()));
    }

    @PutMapping("/news/{id}")
    public ResponseEntity<NewsArticleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody NewsArticleRequest request) {
        return ResponseEntity.ok(newsArticleService.update(id, request));
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        newsArticleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
