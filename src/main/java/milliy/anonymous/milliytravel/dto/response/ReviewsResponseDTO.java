package milliy.anonymous.milliytravel.dto.response;

import milliy.anonymous.milliytravel.dto.request.CommentDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewsResponseDTO {

    private List<CommentDTO> comments;

    private Integer currentPageNumber;

    private Integer currentPageItems;

    private Integer totalPage;

    private Long totalItems;
}
