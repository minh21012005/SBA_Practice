package A2MinhNB_HE191060.SE1941JV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "SystemAccount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SystemAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    Integer accountId;

    @Column(name = "AccountName", length = 100)
    String accountName;

    @Column(name = "AccountEmail", length = 70, unique = true)
    String accountEmail;

    @Column(name = "AccountRole")
    Integer accountRole;

    @Column(name = "AccountPassword", length = 70)
    String accountPassword;

    /**
     * One staff can create many news articles
     */
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    List<NewsArticle> newsArticles;
}