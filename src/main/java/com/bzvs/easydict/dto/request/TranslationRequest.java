package com.bzvs.easydict.dto.request;


import com.bzvs.easydict.dto.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationRequest {
    private String text;
    private Language source;
    private Language destination;
}
