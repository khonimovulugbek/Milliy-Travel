package milliy.anonymous.milliytravel.dto.request;

import lombok.*;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AnswerDTO {

    @NotBlank(message = "content required")
    private String content;


    @NotBlank(message = "reviewId required")
    private String reviewId;


}
