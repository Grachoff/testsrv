package dtos.common;

import lombok.Data;

import java.util.Calendar;

@Data
public abstract class BaseDto {
    private Long id;
    private Calendar created;
}
