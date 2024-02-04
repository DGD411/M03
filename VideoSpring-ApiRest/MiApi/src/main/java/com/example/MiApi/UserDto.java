package com.example.MiApi;


import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    private String email;
    private String fullName;

    public UserDto(Integer id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public UserDto(User user){
        id = user.getId();
        email = user.getEmail();
        fullName = user.getFullName();
    }

    public UserDto() {
    }
}
