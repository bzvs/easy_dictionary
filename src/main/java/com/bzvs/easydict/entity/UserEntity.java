package com.bzvs.easydict.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "user_info")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity {

    private String email;
    private String password;
    private String telegram;
}
