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
    @Size(max = 255)
    private String categoryName;

    private String categoryDescription;

    private Long parentCategoryId;

    @NotNull(message = "Status is required (1=active, 0=inactive)")
    private Integer isActive;
}
