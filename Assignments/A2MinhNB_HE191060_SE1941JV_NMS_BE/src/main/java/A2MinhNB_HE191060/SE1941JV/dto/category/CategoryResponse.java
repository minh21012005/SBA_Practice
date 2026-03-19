package A2MinhNB_HE191060.SE1941JV.dto.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    private Integer categoryId;
    private String categoryName;
    private String categoryDesciption;
    private Integer parentCategoryId;
    private Boolean isActive;
}
