package A2MinhNB_HE191060.SE1941JV.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Integer accountId;
    private String accountName;
    private String accountEmail;
    private Integer accountRole;
}
