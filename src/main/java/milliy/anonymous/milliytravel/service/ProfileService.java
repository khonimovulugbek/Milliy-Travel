package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.Result;
import milliy.anonymous.milliytravel.dto.detail.AttachDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileDTO;
import milliy.anonymous.milliytravel.dto.profile.ProfileShortInfo;
import milliy.anonymous.milliytravel.dto.response.ActionDTO;
import milliy.anonymous.milliytravel.dto.response.ProfileResponseDTO;
import milliy.anonymous.milliytravel.entity.ProfileEntity;
import milliy.anonymous.milliytravel.enums.AppLang;
import milliy.anonymous.milliytravel.enums.ProfileRole;
import milliy.anonymous.milliytravel.mapper.GuideProfileInfoMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    ActionDTO create(ProfileDTO dto);

    ProfileDTO update(ProfileDTO dto);

    AttachDTO uploadPhoto(MultipartFile file);

    Boolean changeStatus();

    PageImpl<ProfileDTO> profilePaginationList(int page, int size);

    ActionDTO delete();

    void changeRole(ProfileRole tourist, String id);

    ActionDTO updateLanguage(AppLang lang);
}
