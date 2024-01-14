package com.bzvs.easydict.service;

import com.bzvs.easydict.dto.WordDto;
import com.bzvs.easydict.service.api.WordService;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl implements WordService {

    @Override
    public WordDto add(WordDto dto) {
        return dto;
    }
}
