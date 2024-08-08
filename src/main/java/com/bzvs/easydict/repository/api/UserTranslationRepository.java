package com.bzvs.easydict.repository.api;

import com.bzvs.easydict.entity.UserTranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTranslationRepository extends JpaRepository<UserTranslationEntity, Long> {
}
