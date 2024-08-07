package com.bzvs.easydict.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDto implements UserDetails {

    private UUID uuid;

    private String email;
    private String password;
    private String telegram;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private boolean accountNonExpired;
    private boolean enabled;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }
}
