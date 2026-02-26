package com.bzvs.easydict.dto.request;

import com.bzvs.easydict.dto.UserTranslationStatus;
import lombok.Data;

@Data
public class SetWordStatusRequest {

    private UserTranslationStatus status;
}
