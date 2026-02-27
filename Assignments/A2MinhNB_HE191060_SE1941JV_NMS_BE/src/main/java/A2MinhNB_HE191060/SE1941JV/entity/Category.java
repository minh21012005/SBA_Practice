package A2MinhNB_HE191060.SE1941JV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long categoryId;

    @Column(nullable = false)
    String categoryName;

    String categoryDescription;

    /**
     * Self reference: parent category
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    List<Category> subCategories;

    /**
     * 1 = active
     * 0 = inactive
     */
    @Column(nullable = false)
    Integer isActive;

    /**
     * One category has many news articles
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<NewsArticle> newsArticles;
}