package co.com.tintolab.Controllers;

import co.com.tintolab.Models.ProductModel;
import co.com.tintolab.Repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping()
    public ResponseEntity<List<ProductModel>> findAll() {
        List<ProductModel> products = productRepository.findAll();

        if (products != null && !products.isEmpty()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProductModel> saveOne(@RequestBody @Valid ProductModel product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                productRepository.save(product)
        );

    }

}
