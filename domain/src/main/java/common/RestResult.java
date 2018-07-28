package common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestResult {
    public static final RestResult OK = new RestResult(RestResultEnum.OK, "");
    private RestResultEnum result;
    private Object data;
}
