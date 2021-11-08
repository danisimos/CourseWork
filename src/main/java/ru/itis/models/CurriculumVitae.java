package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CurriculumVitae {
    private Integer id;
    private String content;
    private User author;
}
