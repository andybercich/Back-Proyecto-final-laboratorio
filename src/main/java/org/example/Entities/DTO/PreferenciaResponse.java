package org.example.Entities.DTO;

import lombok.Data;

@Data
public class PreferenciaResponse {
    private String initPoint;
    private String externalReference;

    public PreferenciaResponse(String initPoint, String externalReference) {
        this.initPoint = initPoint;
        this.externalReference = externalReference;
    }

}
