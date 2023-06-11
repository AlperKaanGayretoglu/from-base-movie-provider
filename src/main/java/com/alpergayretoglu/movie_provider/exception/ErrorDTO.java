package com.alpergayretoglu.movie_provider.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class ErrorDTO {

    private ZonedDateTime timestamp;
    @NotNull
    private int status;
    private String error;
    private String message;


    public ErrorDTO() {

    }
}
