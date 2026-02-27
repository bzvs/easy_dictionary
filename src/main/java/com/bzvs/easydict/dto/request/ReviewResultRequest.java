package com.bzvs.easydict.dto.request;

import lombok.Data;

@Data
public class ReviewResultRequest {

    /** true = «Помню», false = «Не помню» */
    private Boolean remembered;
}
