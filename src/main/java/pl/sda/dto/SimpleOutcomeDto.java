package pl.sda.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SimpleOutcomeDto {
    private Long id;
    private String info;
    private Long categoryId;
}
