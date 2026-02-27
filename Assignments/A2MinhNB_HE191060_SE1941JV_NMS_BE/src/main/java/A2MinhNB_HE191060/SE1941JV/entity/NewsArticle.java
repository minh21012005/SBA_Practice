package A2MinhNB_HE191060.SE1941JV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "news_articles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long newsArticleId;

    @Column(nullable = false)
    String newsTitle;

    String headline;

    @Column(name = "NewsContent", columnDefinition = "NVARCHAR(4000)")
    String newsContent;

    String newsSource;

    LocalDateTime createdDate;

    LocalDateTime modifiedDate;

    /**
     * 1 = active
     * 0 = inactive
     */
    @Column(nullable = false)
    Integer newsStatus;

    /**
     * Many news belong to one category
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    Category category;

    /**
     * Created by staff account
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id", nullable = false)
    SystemAccount createdBy;

    /**
     * Many-to-many with Tag
     */
    @ManyToMany
    @JoinTable(
            name = "news_tags",
            joinColumns = @JoinColumn(name = "news_article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<Tag> tags;
}