package com.bzvs.easydict.entity;

import com.bzvs.easydict.dto.Language;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "word")
@Data
@EqualsAndHashCode(callSuper = true)
public class WordEntity extends BaseEntity {

    private String value;
    @Enumerated(EnumType.STRING)
    private Language language;
}
