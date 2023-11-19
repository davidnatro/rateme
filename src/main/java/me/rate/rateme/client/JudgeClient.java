package me.rate.rateme.client;

import java.util.List;
import me.rate.rateme.data.dto.LanguageDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "judge-client", url = "${application.judge.url}")
public interface JudgeClient {

    @GetMapping(value = "/languages", consumes = MediaType.APPLICATION_JSON_VALUE)
    List<LanguageDto> getLanguages();
}
