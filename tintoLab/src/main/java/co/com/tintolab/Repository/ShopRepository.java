package co.com.tintolab.Repository;

import co.com.tintolab.Models.ShopModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShopRepository extends JpaRepository<ShopModel,Long> {
}
