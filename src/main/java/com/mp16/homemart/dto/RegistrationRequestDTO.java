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

    @NotBlank(message = "Nama tidak boleh kosong")
    private final String name;
    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Masukan email yang valid")
    private final String email;
    @NotBlank(message = "Password tidak boleh kosong")
    @Length(min = 4, message = "Password harus minimal terdiri dari 4 karakter")
    private final String password;
    @NotBlank(message = "Konfirmasi password tidak boleh kosong")
    private final String rpassword;
}
