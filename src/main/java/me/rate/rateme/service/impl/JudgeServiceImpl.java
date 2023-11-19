package me.rate.rateme.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.rate.rateme.client.JudgeClient;
import me.rate.rateme.data.dto.LanguageDto;
import me.rate.rateme.service.JudgeService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class JudgeServiceImpl implements JudgeService {

    private final JudgeClient judgeClient;

    @Override
    public List<LanguageDto> getLanguages() {
        return judgeClient.getLanguages();
    }
}
