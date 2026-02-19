package co.com.tintolab.Services;

import co.com.tintolab.Dto.ProductRequestDTO;
import co.com.tintolab.Dto.ProductResponseDTO;
import co.com.tintolab.Models.ProductModel;
import co.com.tintolab.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        if (productRepository.existsByName(productRequestDTO.getName())) {
            throw new RuntimeException("This user does exists");
        }
        ProductModel product = ProductModel.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .stock(productRequestDTO.getStock())
                .offer(productRequestDTO.getOffer())
                .image(productRequestDTO.getImage()).build();

        productRepository.save(product);

        return mapToDTO(product);
    }

    public ProductResponseDTO updateProduct(Long id,ProductRequestDTO productRequestDTO){

        ProductModel product = productRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Product does not exists"));

        if(productRequestDTO.getName() != null){
            product.setName(productRequestDTO.getName());
        }
        if(productRequestDTO.getDescription() != null){
            product.setDescription(productRequestDTO.getDescription());
        }
        if(productRequestDTO.getPrice() != null){
            product.setPrice(productRequestDTO.getPrice());
        }
        if(productRequestDTO.getImage() != null){
            product.setImage(productRequestDTO.getImage());
        }
        if(productRequestDTO.getOffer() != null){
            product.setOffer(productRequestDTO.getOffer());
        }
        if(productRequestDTO.getStock() != null){
            product.setStock(productRequestDTO.getStock());
        }

        productRepository.save(product);

        return mapToDTO(product);

    }
    public ProductResponseDTO addStock(Long id, Integer addStock){
        ProductModel product = productRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("This user not found"));

        product.setStock(product.getStock() + addStock);

        productRepository.save(product);

        return mapToDTO(product);

    }
    public ProductResponseDTO mapToDTO(ProductModel product){
        return ProductResponseDTO.builder()
                .id(product.getIdProduct())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .offer(product.getOffer())
                .image(product.getImage())
                .stock(product.getStock())
                .build();
    }
}
