package dtos.products;

import dtos.common.BaseDto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static common.Constants.LONG_ID_LENGTH;

@Data
public class ProductForTransactionDto extends BaseDto {
    @NotBlank(message = "code required")
    @Length(max = LONG_ID_LENGTH, min = LONG_ID_LENGTH, message = "code length violation")
    private String code;
    @NotBlank(message = "name required")
    private String name;
}
