package org.example.Entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BackUrl{
    private String success;
    private String failure;
    private String pending;
}
