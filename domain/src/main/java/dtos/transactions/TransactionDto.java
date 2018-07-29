package dtos.transactions;

import dtos.common.BaseDto;
import dtos.products.ProductForTransactionDto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import static common.Constants.SHORT_ID_LENGTH;

@Data
public class TransactionDto extends BaseDto {
    @NotBlank(message = "seller required")
    @Length(max = SHORT_ID_LENGTH, min = SHORT_ID_LENGTH, message = "seller length violation")
    private String seller;
    @NotBlank(message = "customer required")
    @Length(max = SHORT_ID_LENGTH, min = SHORT_ID_LENGTH, message = "customer length violation")
    private String customer;
    @Size(min = 1, message = "products required")
    @Valid
    private List<@NotNull ProductForTransactionDto> products;
}
