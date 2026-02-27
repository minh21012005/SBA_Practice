package A2MinhNB_HE191060.SE1941JV.dto.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequest {
    @Size(max = 255)
    private String accountName;

    @Email(message = "Invalid email format")
    @Size(max = 255)
    private String accountEmail;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String accountPassword; // optional
}
