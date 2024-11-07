package ru.java.myProject.OnlineLibrary.modules.user.dto.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import ru.java.myProject.OnlineLibrary.modules.admin.dto.UserEmailDto;
import ru.java.myProject.OnlineLibrary.modules.user.entity.User;
import ru.java.myProject.OnlineLibrary.modules.login.dto.LoginDto;
import ru.java.myProject.OnlineLibrary.modules.user.dto.*;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "username", source = "username")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "userRoles", source = "userRoles")
    UserDto convertToUserDto(User user);

    @InheritInverseConfiguration
    User convert(UserDto userDto);

    @InheritInverseConfiguration
    User convert(UserRegistrationDto userRegistrationDto);

    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    LoginDto toLoginDto(User user);

    @InheritInverseConfiguration
    User convert(LoginDto loginDto);

    @Mapping(target = "email", source = "email")
    User convert(UserEmailDto userEmailDto);
}
