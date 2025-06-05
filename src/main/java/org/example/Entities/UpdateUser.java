package org.example.Entities;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class UpdateUser{
    private String nombre;
    private String dni;
    private String mail;
    private List<Direccion> direccion;
}