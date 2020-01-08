package com.example.demo.model.DTO.suggestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
public class SuggestionAddDTO {
    @Length(min = 3, max = 125)
    private String title;

    @Length(min = 3, max = 8000)
    private String description;
}
