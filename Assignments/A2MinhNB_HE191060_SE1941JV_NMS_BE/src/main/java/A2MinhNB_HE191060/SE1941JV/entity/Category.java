package A2MinhNB_HE191060.SE1941JV.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "Category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryID")
    Integer categoryId;

    @Column(name = "CategoryName", length = 100, nullable = false)
    String categoryName;

    @Column(name = "CategoryDesciption", length = 250, nullable = false)
    String categoryDesciption;

    @Column(name = "IsActive")
    Boolean isActive;

    /**
     * Self reference: parent category
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ParentCategoryID")
    Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    List<Category> subCategories;

    /**
     * One category has many news articles
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    List<NewsArticle> newsArticles;
}