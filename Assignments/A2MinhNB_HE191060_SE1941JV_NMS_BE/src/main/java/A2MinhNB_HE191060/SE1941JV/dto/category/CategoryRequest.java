package A2MinhNB_HE191060.SE1941JV.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    private String categoryName;

    @NotBlank(message = "Category description is required")
    @Size(max = 250)
    private String categoryDesciption;

    private Integer parentCategoryId;

    @NotNull(message = "Status is required")
    private Boolean isActive;
}
