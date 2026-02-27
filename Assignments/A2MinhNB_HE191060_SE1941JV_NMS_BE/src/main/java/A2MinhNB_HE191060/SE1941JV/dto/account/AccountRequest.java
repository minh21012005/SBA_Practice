package A2MinhNB_HE191060.SE1941JV.dto.account;

import jakarta.validation.constraints.Email;
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
public class AccountRequest {
    @NotBlank(message = "Account name is required")
    @Size(max = 255)
    private String accountName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 255)
    private String accountEmail;

    @NotNull(message = "Role is required (1=Admin, 2=Staff)")
    private Integer accountRole;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String accountPassword; // optional on update; required on create
}
