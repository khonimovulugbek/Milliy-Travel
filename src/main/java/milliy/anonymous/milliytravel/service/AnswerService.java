package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.request.AnswerDTO;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;

public interface AnswerService {
    ActionDTO create(AnswerDTO dto);
     ActionDTO updateAnswer(AnswerDTO dto);

    ActionDTO deleteAnswer(String reviewId);

}
