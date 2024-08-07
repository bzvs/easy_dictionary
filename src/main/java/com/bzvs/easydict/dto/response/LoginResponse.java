package com.bzvs.easydict.dto.response;


public record LoginResponse(String token,
                            long expiresIn) {
}
