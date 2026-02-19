package co.com.tintolab.Repository;

import co.com.tintolab.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    boolean existsByName(String name);
}
