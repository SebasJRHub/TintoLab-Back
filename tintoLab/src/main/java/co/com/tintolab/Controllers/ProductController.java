package co.com.tintolab.Controllers;

import co.com.tintolab.Dto.ProductRequestDTO;
import co.com.tintolab.Dto.ProductResponseDTO;
import co.com.tintolab.Models.ProductModel;
import co.com.tintolab.Repository.ProductRepository;
import co.com.tintolab.Services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @GetMapping("/getProducts")
    public ResponseEntity<List<ProductModel>> getAllProducts() {
        List<ProductModel> products = productRepository.findAll();
        if (products != null && !products.isEmpty()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.notFound().build();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createProduct")
    public ResponseEntity<ProductResponseDTO> saveOneProduct(@RequestBody @Valid ProductRequestDTO product) {
        ProductResponseDTO productRes = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRes);

    }
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PatchMapping("/updateProduct/{id)")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id , @RequestBody ProductRequestDTO productRequestDTO){
        ProductResponseDTO productRes = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productRes);
    }

}
