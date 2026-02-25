package co.com.tintolab.Services;

import co.com.tintolab.Dto.SaleDetailRequestDTO;
import co.com.tintolab.Dto.SaleRequestDTO;
import co.com.tintolab.Dto.SaleResponseDTO;
import co.com.tintolab.Models.*;
import co.com.tintolab.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class SaleService {
    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SaleDetailsRepository saleDetailsRepository;


    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO saleRequestDTO){
        UserModel userModel = userRepository.findById(saleRequestDTO.getUserId())
                .orElseThrow(()-> new RuntimeException(("The user not found")));

        ShopModel shopModel = shopRepository.findById(saleRequestDTO.getShopId())
                .orElseThrow(()-> new RuntimeException("The store not found"));

        SaleModel sale= new SaleModel();
        sale.setUser(userModel);
        sale.setShop(shopModel);
        sale.setDate_hour(LocalDateTime.now());
        sale.setPayMethod(saleRequestDTO.getPayMethod());

        BigDecimal total = BigDecimal.ZERO;

        List<SaleDetailsModel> saleDetailsModelList = new LinkedList<>();

        for(SaleDetailRequestDTO item : saleRequestDTO.getDetails()){
            ProductModel product = productRepository.findById(item.getProductId())
                    .orElseThrow(()-> new RuntimeException("Product not found"));

            BigDecimal unitPrice = product.getPrice();
            BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(item.getAmount()));

            total = total.add(subTotal);

            SaleDetailsModel saleDetailsModel = new SaleDetailsModel();
            saleDetailsModel.setAmount(item.getAmount());
            saleDetailsModel.setProduct(product);
            saleDetailsModel.setSubtotal(subTotal);
            saleDetailsModel.setUnit_price(unitPrice);
            saleDetailsModel.setSale(sale);
            saleDetailsModelList.add(saleDetailsModel);

            saleDetailsRepository.save(saleDetailsModel);
        }

        sale.setTotal(total);
        sale.setDetails(saleDetailsModelList);
        SaleModel saleSave = saleRepository.save(sale);
        String code = "SLE-" + String.format("%06d", saleSave.getId());
        saleSave.setCode(code);

        return SaleResponseDTO.builder()
                .saleId(sale.getId())
                .shopId(sale.getShop().getId_shop())
                .userId(sale.getUser().getId())
                .date_hour(sale.getDate_hour())
                .payMethod(sale.getPayMethod())
                .total(sale.getTotal())
                .code(sale.getCode()).build();

    }
}
