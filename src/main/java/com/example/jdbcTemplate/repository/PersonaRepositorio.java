package com.example.jdbcTemplate.repository;

import com.example.jdbcTemplate.model.Persona;

import java.util.List;

public interface PersonaRepositorio {

    int save(Persona persona);                             //Añade nueva persona a la tabla

    List<Persona> findByName(String name);                 //Busca por nombre

    Persona findById_personpers(int id);                   //Busca por id

    List<Persona> findBySurnameContaining(String surname); //... por apellido conteniendo una subcadena

    List<Persona> findAll();                               //Muestra todas las filas

    int update(Persona persona);                           //Update sobre tres columnas de una persona con ID=nn

    int deleteById_personpers(int id);                     //Borra por ID=nn

    int deleteAll();                                       //Borra todas las filas
}