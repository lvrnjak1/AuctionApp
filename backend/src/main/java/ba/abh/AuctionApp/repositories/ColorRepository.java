package ba.abh.AuctionApp.repositories;

import ba.abh.AuctionApp.domain.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Set<Color> getColorByIdIn(List<Long> colorIds);
}
