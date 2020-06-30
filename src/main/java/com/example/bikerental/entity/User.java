package com.example.bikerental.entity;

import com.example.bikerental.util.PageConstant;
import lombok.Data;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@SessionAttributes("newUser")
public class User extends Entity {
    private static final long serialVersionUID = 7164993002496265554L;
    @Pattern(regexp = "[a-zA-Zа-яА-ЯЁё]{3,15}")
    private String name;
    @Pattern(regexp = "[a-zA-Zа-яА-ЯЁё]{3,15}")
    private String surname;
    @Pattern(regexp = "[a-zA-Z]{1}[a-zA-Z0-9]{2,20}", message = "login wrong")
    private String login;
    @Pattern(regexp = "^[^ ]{8,20}$" , message = "password wrong")
    private String password;
    private String salt;
    private UserRoleEnum role;
    private String tel;
    private UserStateEnum state;
    @NotNull(message = "must not null")
    private double balance;
    @Pattern(regexp = "[a-zA-Z]{1}\\w{1,15}@[a-zA-Z]{1,10}\\.[a-z]{2,3}",message = "wrong e-mail")
    private String email;

    public enum UserRoleEnum{
        ADMIN(PageConstant.ADMIN_PAGE, AccessLevel.ADMIN),
        USER(PageConstant.USER_PAGE, AccessLevel.USER);

        private String homePage;
        private AccessLevel accessLevel;

        UserRoleEnum(String homePage, AccessLevel accessLevel) {
            this.homePage = homePage;
            this.accessLevel = accessLevel;
        }
        public String getHomePage() {
            return homePage;
        }

        public AccessLevel getAccessLevel() {
            return accessLevel;
        }

    }
}
