package com.example.jdbcTemplate.model;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "personadb")
@Data
public class Persona {

    @Id
    public int id_personpers;
    public String usuario;
    public String password;
    public String name;
    public String surname;
    public String city;
    public Date created_date;


    // Para mensajes de error. Busca "empty" en PersonaController
    public Persona() {


    }




    public Persona(int id_personpers, String usuario, String password, String name, String surname, String city, Date created_date) {
        this.id_personpers = id_personpers;
        this.usuario = usuario;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.created_date = created_date;

    }


}

