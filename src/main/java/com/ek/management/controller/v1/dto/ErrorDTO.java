package com.ek.management.controller.v1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Getter
public class ErrorDTO {

    @Schema(name = "status", description = "Http Status Code")
    private Integer status;

    @Schema(name = "timestamp", description = "Data/hora")
    private OffsetDateTime timestamp;

    @Schema(name = "title", description = "Issue title")
    private String title;

    @Schema(name = "messages", description = "Detailed messages about the issue")
    private List<String> messages;
}
