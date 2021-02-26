package ba.abh.AuctionApp.repositories.auction;

import ba.abh.AuctionApp.domain.Auction;
import ba.abh.AuctionApp.filters.AuctionFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

        if (filter.getSellerId() != null){
            predicates.add(criteriaBuilder.equal(root.get("seller"), filter.getSellerId()));
        }

        if (filter.getPriceMin() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("startPrice"), filter.getPriceMin()));
        }

        if (filter.getPriceMax() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("startPrice"), filter.getPriceMax()));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Auction> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        return new SliceImpl<>(typedQuery.getResultList());
    }
}
