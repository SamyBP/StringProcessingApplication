package com.app.restserver.endpoints.responses;

import com.app.restserver.entities.ExecutionModule;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter @Setter
public class ExecutionHistoryResponse {
    private LocalDateTime createdAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private String pipeName;
    private int version;
    private String input;
    private List<ExecutionModule> modules;
    private String result;
    private String status;
}
