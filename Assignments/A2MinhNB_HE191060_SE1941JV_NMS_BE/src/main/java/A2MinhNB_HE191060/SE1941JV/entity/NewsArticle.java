package A2MinhNB_HE191060.SE1941JV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "NewsArticle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NewsArticleID")
    Integer newsArticleId;

    @Column(name = "NewsTitle", length = 400)
    String newsTitle;

    @Column(name = "Headline", length = 400, nullable = false)
    String headline;

    @Column(name = "NewsContent", columnDefinition = "NVARCHAR(MAX)")
    String newsContent;

    @Column(name = "NewsSource", length = 400)
    String newsSource;

    @Column(name = "CreatedDate")
    LocalDateTime createdDate;

    @Column(name = "ModifiedDate")
    LocalDateTime modifiedDate;

    @Column(name = "NewsStatus")
    Boolean newsStatus;

    /**
     * Many news belong to one category
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    Category category;

    /**
     * Created by staff account
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CreatedByID")
    SystemAccount createdBy;

    /**
     * Updated by staff account
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UpdatedByID")
    SystemAccount updatedBy;

    /**
     * Many-to-many with Tag
     */
    @ManyToMany
    @JoinTable(
            name = "NewsTag",
            joinColumns = @JoinColumn(name = "NewsArticleID"),
            inverseJoinColumns = @JoinColumn(name = "TagID")
    )
    Set<Tag> tags;
}