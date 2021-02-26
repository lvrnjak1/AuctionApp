package ba.abh.AuctionApp.repositories.auction;

import ba.abh.AuctionApp.controllers.utility.SortOrder;
import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.domain.enums.Size;
import ba.abh.AuctionApp.filters.AuctionFilter;
import ba.abh.AuctionApp.filters.ProductFilter;
import ba.abh.AuctionApp.filters.SortSpecification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilteredAuctionRepositoryImpl implements FilteredAuctionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Slice<Auction> findAllByFilter(AuctionFilter filter, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Auction> criteriaQuery = criteriaBuilder.createQuery(Auction.class);

        Root<Auction> root = criteriaQuery.from(Auction.class);
        List<Predicate> predicates = new ArrayList<>();

        if (filter.getSellerId() != null) {
            predicates.add(criteriaBuilder.equal(root.get("seller"), filter.getSellerId()));
        }

        if (filter.getPriceMin() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startPrice"), filter.getPriceMin()));
        }

        if (filter.getPriceMax() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startPrice"), filter.getPriceMax()));
        }

        if (filter.getStartBefore() != null) {
            Path<Instant> startDateTimePath = root.get("startDateTime");
            predicates.add(criteriaBuilder.lessThanOrEqualTo(startDateTimePath, filter.getStartBefore()));
        }

        if (filter.getStartAfter() != null) {
            Path<Instant> startDateTimePath = root.get("startDateTime");
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(startDateTimePath, filter.getStartBefore()));
        }

        if (filter.getEndBefore() != null) {
            Path<Instant> endDateTimePath = root.get("endDateTime");
            predicates.add(criteriaBuilder.lessThanOrEqualTo(endDateTimePath, filter.getEndBefore()));
        }

        if (filter.getEndAfter() != null) {
            Path<Instant> endDateTimePath = root.get("endDateTime");
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(endDateTimePath, filter.getEndAfter()));
        }

        if (filter.getProductFilter() != null) {
            processProductFilter(filter.getProductFilter(), predicates, criteriaBuilder, root);
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        if (filter.getSortSpecification() != null) {
            sort(criteriaQuery, criteriaBuilder, root, filter.getSortSpecification());
        }

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        TypedQuery<Auction> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size + 1);

        List<Auction> resultList = typedQuery.getResultList();
        boolean hasNext = resultList.size() > size;

        return new SliceImpl<>(hasNext ? resultList.subList(0, size) : resultList, pageable, hasNext);
    }

    private void processProductFilter(ProductFilter productFilter,
                                      List<Predicate> predicates,
                                      CriteriaBuilder criteriaBuilder,
                                      Root<Auction> root) {
        String name = productFilter.getName();
        if (name != null) {
            name = name.toLowerCase();
            Path<String> namePath = root.get("product").get("name");
            Expression<String> target = criteriaBuilder.lower(namePath);
            String pattern = String.format("%%%s%%", name);
            predicates.add(criteriaBuilder.like(target, pattern));
        }

        Size size = productFilter.getSize();
        if (size != null) {
            Path<Size> sizePath = root.get("product").get("size");
            predicates.add(criteriaBuilder.equal(sizePath, size));
        }

        Long categoryId = productFilter.getCategoryId();
        if (categoryId != null) {
            Path<Category> categoryPath = root.get("product").get("category");
            Predicate inCategory = criteriaBuilder.equal(categoryPath.get("id"), categoryId);
            Predicate inSubCategory = criteriaBuilder.equal(categoryPath.get("parentCategory").get("id"), categoryId);
            predicates.add(criteriaBuilder.or(inCategory, inSubCategory));
        }
    }

    private void sort(CriteriaQuery<Auction> criteriaQuery,
                      CriteriaBuilder criteriaBuilder,
                      Root<Auction> root,
                      SortSpecification sortSpecification) {
        if (sortSpecification.getSortCriteria() != null) {
            String criteria = sortSpecification.getSortCriteria().getField();
            if (sortSpecification.getSortOrder().equals(SortOrder.ASC)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(criteria)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(criteria)));
            }
        }
    }
}
