package A2MinhNB_HE191060.SE1941JV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "system_accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SystemAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long accountId;

    @Column(nullable = false)
    String accountName;

    @Column(nullable = false, unique = true)
    String accountEmail;

    /**
     * 1 = Admin
     * 2 = Staff
     */
    @Column(nullable = false)
    Integer accountRole;

    @Column(nullable = false)
    String accountPassword;

    /**
     * One staff can create many news articles
     */
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    List<NewsArticle> newsArticles;
}