package co.com.tintolab.Repository;

import co.com.tintolab.Models.SaleDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailsRepository extends JpaRepository<SaleDetailsModel, Long> {
}
