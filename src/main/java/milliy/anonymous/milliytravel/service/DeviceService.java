package milliy.anonymous.milliytravel.service;

import milliy.anonymous.milliytravel.dto.detail.DeviceDTO;

import java.util.List;


public interface DeviceService {
    DeviceDTO create(DeviceDTO dto, String profileId);
    List<DeviceDTO> devicesByProfile(String phoneNumber);
}
