package com.bzvs.easydict.dto.request;

public record SignUpRequest(String email,
                            String password,
                            String telegram) {
}
