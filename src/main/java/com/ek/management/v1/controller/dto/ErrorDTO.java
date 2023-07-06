package com.ek.management.v1.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
public class ErrorDTO {

    @JsonProperty
    private Integer status;

    @JsonProperty
    private OffsetDateTime timestamp;

    @JsonProperty
    private String title;

    @JsonProperty
    private List<String> messages;
}
