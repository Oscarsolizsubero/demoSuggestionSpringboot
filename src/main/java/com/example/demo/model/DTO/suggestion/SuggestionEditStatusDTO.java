package com.example.demo.model.DTO.suggestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
public class SuggestionEditStatusDTO {

    @Length(min = 3, max = 125)
    private long id;

    @Length(min = 3, max = 8000)
    private long statusId;

    private long idAuthor;
}
