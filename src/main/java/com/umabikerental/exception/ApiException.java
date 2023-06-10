package com.umabikerental.exception;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ApiException {
    private Map<String, Object> body;

    public ApiException(HttpStatus status, List<String> errors) {
        body = new LinkedHashMap<>();
        body.put("status", status.value());
        body.put("errors", errors);
        body.put("timestamp", LocalDateTime.now());
    }

    @JsonAnyGetter
    public Map<String, Object> any() {
        return body;
    }
}

