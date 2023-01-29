package milliy.anonymous.milliytravel.mapper;

import milliy.anonymous.milliytravel.enums.DeviceType;

public interface ProfileDeviceInfoMapper {

    String getD_id();
    String getD_token();
    DeviceType getD_type();

}
