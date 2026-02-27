package A2MinhNB_HE191060.SE1941JV.dto.news;

import A2MinhNB_HE191060.SE1941JV.dto.account.AccountResponse;
import A2MinhNB_HE191060.SE1941JV.dto.category.CategoryResponse;
import A2MinhNB_HE191060.SE1941JV.dto.tag.TagResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticleResponse {
    private Long newsArticleId;
    private String newsTitle;
    private String headline;
    private String newsContent;
    private String newsSource;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private Integer newsStatus;
    private CategoryResponse category;
    private AccountResponse createdBy;
    private Set<TagResponse> tags;
}
