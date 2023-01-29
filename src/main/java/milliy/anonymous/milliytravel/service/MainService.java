package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.response.MainResponseDTO;


public interface MainService {

     MainResponseDTO getMainPage(int page, int size);

}
