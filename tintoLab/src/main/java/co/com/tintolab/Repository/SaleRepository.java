package co.com.tintolab.Repository;

import co.com.tintolab.Models.SaleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<SaleModel, Long> {
    Optional<SaleModel> findByCode(String code);
    boolean existsByCode(String code);
}
