package com.bzvs.easydict.service.api;

import com.bzvs.easydict.dto.request.UserSettingsRequest;
import com.bzvs.easydict.dto.response.UserSettingsResponse;

public interface UserSettingsService {

    UserSettingsResponse getSettingsForCurrentUser();

    UserSettingsResponse updateSettingsForCurrentUser(UserSettingsRequest request);
}
