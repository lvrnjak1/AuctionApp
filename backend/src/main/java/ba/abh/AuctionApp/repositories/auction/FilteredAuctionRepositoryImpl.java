package ba.abh.AuctionApp.repositories.auction;

import ba.abh.AuctionApp.controllers.utility.SortOrder;
import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.domain.Category;
import ba.abh.AuctionApp.domain.enums.Size;
import ba.abh.AuctionApp.filters.AuctionFilter;
import ba.abh.AuctionApp.filters.ProductFilter;
import ba.abh.AuctionApp.filters.SortSpecification;
import ba.abh.AuctionApp.responses.PriceChartResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
import javax.persistence.criteria.Subquery;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class FilteredAuctionRepositoryImpl implements FilteredAuctionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Auction> findAllByFilter(final AuctionFilter filter, final Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Auction> criteriaQuery = criteriaBuilder.createQuery(Auction.class);

        Root<Auction> root = criteriaQuery.from(Auction.class);
        List<Predicate> predicates = getFilterPredicates(criteriaBuilder, root, filter);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        sort(criteriaQuery, criteriaBuilder, root, filter.getSortSpecification());

        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        TypedQuery<Auction> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(page * size);
        typedQuery.setMaxResults(size);

        List<Auction> resultList = typedQuery.getResultList();
        Long total = getTotal(criteriaBuilder, predicates);

        return new PageImpl<>(resultList, pageable, total);
    }

    private List<Predicate> getFilterPredicates(final CriteriaBuilder criteriaBuilder,
                                                final Root<Auction> root,
                                                final AuctionFilter filter) {
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

        return predicates;
    }

    private Long getTotal(final CriteriaBuilder criteriaBuilder, final List<Predicate> predicates) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Auction> booksRootCount = countQuery.from(Auction.class);
        countQuery
                .select(criteriaBuilder.count(booksRootCount))
                .where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private void processProductFilter(final ProductFilter productFilter,
                                      final List<Predicate> predicates,
                                      final CriteriaBuilder criteriaBuilder,
                                      final Root<Auction> root) {
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

        List<Long> categoryIds = productFilter.getCategoryIds();
        if (categoryIds != null) {
            List<Predicate> categoryPredicates = new ArrayList<>();
            for (Long categoryId : categoryIds) {
                Path<Category> categoryPath = root.get("product").get("category");
                Predicate inSubCategory = criteriaBuilder.equal(categoryPath.get("id"), categoryId);
                Predicate inCategory = criteriaBuilder.equal(categoryPath.get("parentCategory").get("id"), categoryId);
                categoryPredicates.add(criteriaBuilder.or(inCategory, inSubCategory));
            }
            predicates.add(criteriaBuilder.or(categoryPredicates.toArray(new Predicate[0])));
        }
    }

    private void sort(final CriteriaQuery<Auction> criteriaQuery,
                      final CriteriaBuilder criteriaBuilder,
                      final Root<Auction> root,
                      final SortSpecification sortSpecification) {
        if (sortSpecification.getSortCriteria() != null) {
            String criteria = sortSpecification.getSortCriteria().getField();
            if (sortSpecification.getSortOrder().equals(SortOrder.ASC)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(criteria)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(criteria)));
            }
        }else {
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("product").get("name")));
        }
    }

    private List<Double> getMinMax(final CriteriaBuilder criteriaBuilder, final AuctionFilter auctionFilter) {
        CriteriaQuery<Object[]> minMaxQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Auction> minMaxRoot = minMaxQuery.from(Auction.class);
        List<Predicate> predicates = getFilterPredicates(criteriaBuilder, minMaxRoot, auctionFilter);

        Subquery<Auction> subquery = minMaxQuery.subquery(Auction.class);
        Root<Auction> root = subquery.from(Auction.class);
        subquery.select(root).where(predicates.toArray(new Predicate[0]));

        minMaxQuery.multiselect(criteriaBuilder.min(minMaxRoot.get("startPrice")),
                criteriaBuilder.max(minMaxRoot.get("startPrice")));
        minMaxQuery.where(criteriaBuilder.in(minMaxRoot).value(subquery));
        TypedQuery<Object[]> typedQuery = entityManager.createQuery(minMaxQuery);
        Object[] resultList = typedQuery.getSingleResult();

        List<Double> result = new ArrayList<>();
        result.add(0d);
        result.add(0d);
        if (resultList.length != 0 && resultList[0] != null && resultList[1] != null) {
            result.set(0, (double) resultList[0]);
            result.set(1, (double) resultList[1]);
        }

        return result;
    }

    private List<Expression<Integer>> getRanges(final CriteriaBuilder criteriaBuilder,
                                                final Root<Auction> auctionRoot,
                                                final double min,
                                                final double max,
                                                final double step,
                                                final List<String> labels) {
        List<Expression<Integer>> ranges = new ArrayList<>();
        double price = min;
        while (price <= max + step) {
            labels.add(price + " - " + (price + step));
            Predicate ge = criteriaBuilder.greaterThanOrEqualTo(auctionRoot.get("startPrice"), price);
            Predicate lt = criteriaBuilder.lessThan(auctionRoot.get("startPrice"), (price + step));
            Predicate between = criteriaBuilder.and(ge, lt);
            Expression<Integer> sumEx = criteriaBuilder.sum(
                    criteriaBuilder.<Integer>selectCase().when(between, 1).otherwise(0)
            );
            ranges.add(sumEx);
            price += step;
        }

        return ranges;
    }

    @Override
    public PriceChartResponse getPriceChartData(final AuctionFilter filter) {
        filter.setPriceMin(null);
        filter.setPriceMax(null);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        List<Double> minMax = getMinMax(criteriaBuilder, filter);
        double min = minMax.get(0);
        double max = minMax.get(1);
        double step = 20d;

        CriteriaQuery<Object[]> countQuery = criteriaBuilder.createQuery(Object[].class);
        Root<Auction> auctionRoot = countQuery.from(Auction.class);
        List<Predicate> predicates = getFilterPredicates(criteriaBuilder, auctionRoot, filter);
        Subquery<Auction> sA = countQuery.subquery(Auction.class);
        Root<Auction> rA = sA.from(Auction.class);
        sA.select(rA).where(predicates.toArray(new Predicate[0]));

        List<String> labels = new ArrayList<>();
        List<Expression<Integer>> ranges = getRanges(criteriaBuilder, auctionRoot, min, max, step, labels);

        countQuery.multiselect(ranges
                .stream()
                .map(range -> range.alias(String.valueOf(Math.random())))
                .collect(Collectors.toList())

        ).where(criteriaBuilder.in(auctionRoot).value(sA));

        TypedQuery<Object[]> typedCountQuery = entityManager.createQuery(countQuery);
        Object[] count = typedCountQuery.getSingleResult();

        List<Long> values = new ArrayList<>();
        if (count != null && count[0] != null && count[1] != null) {
            values = Arrays.stream(count).mapToLong(el -> (long) el).boxed().collect(Collectors.toList());
        }
        return new PriceChartResponse(labels, values, min, max + step, step);
    }
}
