package com.airwatch.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class SubscribeRequest {
    @NotBlank @Email
    private String email;
    @NotNull
    private Long cityId;
    @Min(50) @Max(300)
    private Integer threshold = 150;
}