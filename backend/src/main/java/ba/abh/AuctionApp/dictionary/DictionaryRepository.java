package ba.abh.AuctionApp.dictionary;

import ba.abh.AuctionApp.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictionaryRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from dictionary", nativeQuery = true)
    List<DictionaryEntry> getDictionaryEntries();
}
