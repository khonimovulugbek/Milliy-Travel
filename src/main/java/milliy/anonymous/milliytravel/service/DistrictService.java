package milliy.anonymous.milliytravel.service;


import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.detail.DistrictDTO;
import milliy.anonymous.milliytravel.enums.Region;

import java.util.List;


public interface DistrictService {
     DistrictDTO create(Integer id, DistrictDTO dto);
     List<String> getByRegion(Region region);
     List<DistrictDTO> getList();
}
