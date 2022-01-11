package com.example.jdbcTemplate.controller;


import java.util.ArrayList;
import java.util.List;
import com.example.jdbcTemplate.model.Persona;
import com.example.jdbcTemplate.repository.PersonaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class PersonaController {

    @Autowired
    PersonaRepositorio personaRepositorio;



    /*
     * Añade una nueva persona a la tabla: http://localhost:8085/Personas/addnewpers/
     *   {
     *     "id_personpers": 1,
     *     "usuario": "usuario1",
     *     "password": "pass1",
     *     "name": "Toni",
     *     "surname": "López",
     *     "city": "Sabadell",
     *     "created_date": "2001-01-01T00:00:00.000+00:00"
     *   }
     * */

    @PostMapping("/Personas/addnewpers")
    public ResponseEntity<String> createPersona(@RequestBody Persona persona) {
        try {
            personaRepositorio.save(new Persona(persona.getId_personpers(), persona.getUsuario(), persona.getPassword(),
                    persona.getName(), persona.getSurname(), persona.getCity(),
                    persona.getCreated_date()));

            return new ResponseEntity<>("Persona añadida de forma correcta.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /*
     * Busca persona por nombre:  http://localhost:8085/Personas/pornombre/Carla
     * */
    @GetMapping("/Personas/pornombre/{nombre}")
    public ResponseEntity<List<Persona>> findByName(@PathVariable("nombre") String nombre) {

        try {

            List<Persona> personaList = personaRepositorio.findByName(nombre);

            if (personaList.isEmpty()) {
                Persona empty = new Persona();
                empty.setUsuario("No encuentro personas con nombre = " + nombre);
                personaList.add(empty);
                //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                return new ResponseEntity<>(personaList, HttpStatus.OK);
            }

            return new ResponseEntity<>(personaList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /*
     * Busca por número de id: http://localhost:8085/Personas/buscaporid/1
     * */

    @GetMapping("/Personas/buscaporid/{id}")
    public ResponseEntity<Persona> getPersonaById(@PathVariable("id") int id) {
        Persona Persona = personaRepositorio.findById_personpers(id);

        if (Persona != null) {
            return new ResponseEntity<>(Persona, HttpStatus.OK);
        } else {
            Persona empty = new Persona();
            empty.setId_personpers(id);
            empty.setUsuario("No encuentro persona con ID = " + id);
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(empty, HttpStatus.OK);

        }

    }





    /*
     * Busca personas cuyo apellido contenga una cadena pasada por parámetro "substring"
     * Por ejemplo, para: http://localhost:8085/Personas/apellidocontiene/?substring=Mart
     * ...retornará todos los "Martín", "Martinez", "Martillo"... que existan en la tabla.
     *
     * Si el parámetro "substring" llega vacío:
     * http://localhost:8085/Personas/apellidocontiene/
     * ...se retornarán todas las filas de la tabla
     * */

    @GetMapping("/Personas/apellidocontiene/")
    public ResponseEntity<List<Persona>> getAllPersonas(@RequestParam(required = false) String substring) {

        try {
            List<Persona> personaList = new ArrayList<Persona>();

            if (substring == null)
                personaRepositorio.findAll().forEach(personaList::add);
            else
                personaRepositorio.findBySurnameContaining(substring).forEach(personaList::add);

            if (personaList.isEmpty()) {
                Persona empty = new Persona();
                empty.setUsuario("No encuentro apellidos que contengan = " + substring);
                personaList.add(empty);
                //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                return new ResponseEntity<>(personaList, HttpStatus.OK);
            }

            return new ResponseEntity<>(personaList, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /*
     * Hace un update de columnas "name", "surname" y "city" de un persona con el "id" entrado por parámetro:
     * Por ejemplo: http://localhost:8085/Personas/modif/1
     * Si la persona no existe, retorna: "No encuentro persona con id = nnn"
     *
     * {
     *
     *   "name": "Tomas",
     *   "surname": "Delhort",
     *   "city": "Sanabria"
     *
     * }
     * */

    @PutMapping("/Personas/modif/{id_personpers}")
    public ResponseEntity<String> updatePersona(@PathVariable("id_personpers") int id_personpers, @RequestBody Persona Persona) {

        Persona persRepo = personaRepositorio.findById_personpers(id_personpers);

        if (persRepo != null) {
            persRepo.setName(Persona.getName());
            persRepo.setSurname(Persona.getSurname());
            persRepo.setCity(Persona.getCity());

            personaRepositorio.update(persRepo);
            return new ResponseEntity<>("Persona modificada de forma correcta.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No encuentro persona con id = " + id_personpers, HttpStatus.NOT_FOUND);
        }
    }




    /*
     * Borra por número de id: http://localhost:8085/Personas/deletebyid/8
     * */
    @DeleteMapping("/Personas/deletebyid/{id}")
    public ResponseEntity<String> deletePersona(@PathVariable("id") int id) {
        try {
            int result = personaRepositorio.deleteById_personpers(id);
            if (result == 0) {
                return new ResponseEntity<>("No encuentro para borrar persona con id = " + id, HttpStatus.OK);
            }
            return new ResponseEntity<>("Persona borrada de forma satisfactoria.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error desconocido borrando Persona con id = " + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    /*
     * Borra todas las personas de la tabla:  http://localhost:8085/Personas/deleteall
     * */
    @DeleteMapping("/Personas/deleteall")
    public ResponseEntity<String> deleteAllPersonas() {
        try {
            int numRows = personaRepositorio.deleteAll();
            return new ResponseEntity<>("Borradas " + numRows + " Persona(s) de forma satisfactoria.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Algo pasa. No puedo borrar Personas.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}