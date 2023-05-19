package com.alpergayretoglu.movie_provider.model.request.user;

import com.alpergayretoglu.movie_provider.entity.UserRole;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class CreateUserRequest {

    @Email(message = "Geçersiz e-posta adresi.")
    @NotEmpty(message = "Mail adresi boş bırakılamaz!")
    private String email;

    @NotEmpty(message = "İsim boş bırakılamaz!")
    private String name;

    @NotEmpty(message = "Soyisim boş bırakılamaz!")
    private String surname;

    @NotNull(message = "Kullanıcı rolü boş bırakılamaz!")
    private UserRole userRole;
    
    private String photoId;

    @NotNull(message = "E-posta adresi görünürlüğü boş bırakılamaz!")
    private Boolean emailVisible;

    @NotBlank(message = "Açıklama boş olamaz!")
    @Length(min = 20, max = 1500)
    private String description;

}
