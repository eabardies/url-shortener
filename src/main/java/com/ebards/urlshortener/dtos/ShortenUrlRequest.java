package com.ebards.urlshortener.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShortenUrlRequest {
    @NotBlank(message = "Provide the URL to shorten.")
    @Pattern(
            regexp = "^(https?://)([\\w-]+\\.)+[\\w-]{2,}(/\\S*)?$",
            message = "Provide a valid and resolvable URL"
    )
    private String url;
}
