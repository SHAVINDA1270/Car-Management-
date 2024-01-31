package lk.ijse.Car_Hire_Management.service.custom;

import lk.ijse.Car_Hire_Management.dto.UserDto;

public interface UserService extends SuperService{
    boolean saveUser(UserDto userDto);
    UserDto getUser(String text);
}
