package co.com.tintolab.Dto;

import co.com.tintolab.Models.ShopModel;
import co.com.tintolab.Models.UserModel;
import co.com.tintolab.Util.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SaleRequestDTO {
    Long shopId;
    Long userId;
    PayMethod payMethod;
    List<SaleDetailRequestDTO> details;
}
