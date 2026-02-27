package A2MinhNB_HE191060.SE1941JV.dto.auth;

import A2MinhNB_HE191060.SE1941JV.dto.account.AccountResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private AccountResponse account;
}
