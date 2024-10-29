package com.betek.ms_flies.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class DeleteResponse<T> {

    private String objectType;
    private String objectName;
    private String message;

    public DeleteResponse(String objectType, String objectName) {
        this.objectType = objectType;
        this.objectName = objectName;
        this.message = "Ha sido eliminado satisfactoriamente";
    }
}
