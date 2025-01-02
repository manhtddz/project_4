package com.example.e_project_4_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UnlikeModelRequest {
    @NotNull(message = "userId is required")
    private Integer userId;
    @NotNull(message = "unlikeId is required")
    private Integer unlikeId;

}
