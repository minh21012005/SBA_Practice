package A2MinhNB_HE191060.SE1941JV.dto.news;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsArticleRequest {
    @Size(max = 400)
    private String newsTitle;

    @NotBlank(message = "Headline is required")
    @Size(max = 150)
    private String headline;

    private String newsContent;

    @Size(max = 255)
    private String newsSource;

    @NotNull(message = "News status is required")
    private Boolean newsStatus;

    @NotNull(message = "Category is required")
    private Integer categoryId;

    /** Tag IDs to associate; can be empty. */
    private Set<Integer> tagIds;
}
