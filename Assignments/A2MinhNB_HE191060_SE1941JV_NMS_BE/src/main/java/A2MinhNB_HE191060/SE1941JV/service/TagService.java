package A2MinhNB_HE191060.SE1941JV.service;

import A2MinhNB_HE191060.SE1941JV.dto.common.PageResponse;
import A2MinhNB_HE191060.SE1941JV.dto.tag.TagRequest;
import A2MinhNB_HE191060.SE1941JV.dto.tag.TagResponse;
import A2MinhNB_HE191060.SE1941JV.entity.Tag;
import A2MinhNB_HE191060.SE1941JV.exception.ResourceNotFoundException;
import A2MinhNB_HE191060.SE1941JV.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public PageResponse<TagResponse> search(String keyword, Pageable pageable) {
        String k = keyword != null && !keyword.isBlank() ? keyword.trim() : null;
        Page<Tag> page = k != null
                ? tagRepository.findByTagNameContainingIgnoreCase(k, pageable)
                : tagRepository.findAll(pageable);
        return PageResponse.<TagResponse>builder()
                .content(page.getContent().stream().map(this::toResponse).toList())
                .page(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public TagResponse getById(Long id) {
        Tag t = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        return toResponse(t);
    }

    @Transactional
    public TagResponse create(TagRequest request) {
        Tag tag = Tag.builder()
                .tagName(request.getTagName())
                .note(request.getNote())
                .build();
        tag = tagRepository.save(tag);
        return toResponse(tag);
    }

    @Transactional
    public TagResponse update(Long id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        tag.setTagName(request.getTagName());
        tag.setNote(request.getNote());
        tag = tagRepository.save(tag);
        return toResponse(tag);
    }

    @Transactional
    public void delete(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id));
        tagRepository.delete(tag);
    }

    /** Resolve tags by IDs; creates missing tags by name if needed. Used by NewsArticleService. */
    @Transactional(readOnly = true)
    public Set<Tag> resolveTags(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return Set.of();
        return tagIds.stream()
                .map(id -> tagRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tag not found: " + id)))
                .collect(Collectors.toSet());
    }

    public TagResponse toResponse(Tag t) {
        return TagResponse.builder()
                .tagId(t.getTagId())
                .tagName(t.getTagName())
                .note(t.getNote())
                .build();
    }
}
