package A2MinhNB_HE191060.SE1941JV.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagRequest {
    @NotBlank(message = "Tag name is required")
    @Size(max = 50)
    private String tagName;

    @Size(max = 400)
    private String note;
}
