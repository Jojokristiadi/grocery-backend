package com.mp16.homemart.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequestDTO {

    @NotBlank(message = "Masukan nama Anda")
    private final String name;
    @NotBlank(message = "Masukan email Anda")
    @Email(message = "Masukan email yang valid")
    private final String email;
    @NotBlank(message = "Masukan kata sandi")
    @Length(min = 4, message = "Kata sandi harus minimal terdiri dari 4 karakter")
    private final String password;
    @NotBlank(message = "Masukan lagi password Anda")
    private final String rpassword;
}
