package A2MinhNB_HE191060.SE1941JV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "Tag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TagID")
    Integer tagId;

    @Column(name = "TagName", length = 50)
    String tagName;

    @Column(name = "Note", length = 400)
    String note;

    /**
     * Many-to-many with NewsArticle
     */
    @ManyToMany(mappedBy = "tags")
    Set<NewsArticle> newsArticles;
}