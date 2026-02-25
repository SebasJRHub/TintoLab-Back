package co.com.tintolab.Controllers;

import co.com.tintolab.Dto.SaleRequestDTO;
import co.com.tintolab.Dto.SaleResponseDTO;
import co.com.tintolab.Services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sales")
public class SaleController {
    @Autowired
    private SaleService saleService;

    @PostMapping("/createSale")
    public ResponseEntity<SaleResponseDTO> createSale(@RequestBody SaleRequestDTO saleRequestDTO){
        SaleResponseDTO sale = saleService.createSale(saleRequestDTO);
        return ResponseEntity.ok(sale);
    }
}
