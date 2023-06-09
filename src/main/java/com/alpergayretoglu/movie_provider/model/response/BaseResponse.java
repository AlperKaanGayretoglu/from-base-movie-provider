package com.alpergayretoglu.movie_provider.model.response;

import com.alpergayretoglu.movie_provider.model.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseResponse {

    private String id;
    private ZonedDateTime created;
    private ZonedDateTime updated;

    protected static <R extends BaseResponse, E extends BaseEntity> R setCommonValuesFromEntity(R response, E entity) {
        response.setId(entity.getId());
        response.setCreated(entity.getCreated());
        response.setUpdated(entity.getUpdated());
        return response;
    }

}
