package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.itis.models.User;

@Data
@Builder
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer avatarId;
    private String email;
    private String hashPassword;

    public static UserDto fromUser(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .avatarId(user.getAvatarId())
                .email(user.getEmail())
                .hashPassword(user.getHashPassword())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.email)
                .hashPassword(userDto.getHashPassword())
                .avatarId(userDto.getAvatarId())
                .build();
    }
}
