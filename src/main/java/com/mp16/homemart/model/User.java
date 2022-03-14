package com.mp16.homemart.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@Getter
@Setter
//@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotBlank(message = "Nama tidak boleh kosong")
    private String name;

    @Column
    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Masukan email yang valid")
    private String email;

    @Column
    @NotBlank(message = "Password tidak boleh kosong")
    @Length(min = 4, message = "Password harus minimal terdiri dari 4 karakter")
    private String password;

    private String rpassword;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role user tidak boleh kosong")
    private AppUserRole role;

    private boolean locked = false;
    private boolean enabled = true;

    /*@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> role;*/

    public User(String name, String email, String password, String role) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = AppUserRole.valueOf(role);
    }

    public User(String name, String email, String password, AppUserRole role, boolean locked, boolean enabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.locked = locked;
        this.enabled = enabled;
    }

    /*
    * Override from user details
    * yep i don't why but it worked
    * */
    public String getFullName(){
        return getName();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}