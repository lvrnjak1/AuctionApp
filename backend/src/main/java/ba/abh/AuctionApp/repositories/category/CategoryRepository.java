package ba.abh.AuctionApp.repositories.category;

import ba.abh.AuctionApp.domain.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c as category, count(a) as count " +
            "from Category c, Product p " +
            "left join Auction a on c = p.category and a.product = p and a.startDateTime <= ?1 and a.endDateTime >= ?1 " +
            "group by c, c.parentCategory.id " +
            "order by c.parentCategory.id desc ")
    List<ExtendedCategory> findAllCategoriesWithActiveAuctionCount(final Instant now);

    List<Category> findAllByParentCategoryIsNotNull(final Pageable pageable);
}
