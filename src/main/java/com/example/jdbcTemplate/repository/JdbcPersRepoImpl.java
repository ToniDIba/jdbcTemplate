package com.example.jdbcTemplate.repository;


import java.util.List;

import com.example.jdbcTemplate.model.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcPersRepoImpl implements PersonaRepositorio {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public List<Persona> findByName(String name) {
        return jdbcTemplate.query("SELECT * from mispers1 WHERE name=?",
                BeanPropertyRowMapper.newInstance(Persona.class), name);
    }


    @Override
    public int save(Persona persona) {
        return jdbcTemplate.update("INSERT INTO mispers1 (id_personpers, usuario, password, name, surname, city, created_date) " +
                        "                      VALUES(?,?,?,?,?,?,?)",
                new Object[] {persona.getId_personpers(), persona.getUsuario(), persona.getPassword(),
                                                          persona.getName(),    persona.getSurname(),
                                                          persona.getCity(),    persona.getCreated_date() });
    }




    @Override
    public Persona findById_personpers(int id) {
        try {
            Persona persona = jdbcTemplate.queryForObject("SELECT * FROM mispers1 WHERE id_personpers=?",
                    BeanPropertyRowMapper.newInstance(Persona.class), id);

            return persona;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }



    @Override
    public List<Persona> findBySurnameContaining(String substring) {
        String q = "SELECT * from mispers1 WHERE surname ILIKE '%" + substring + "%'";

        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Persona.class));
    }



    @Override
    public List<Persona> findAll() {
        return jdbcTemplate.query("SELECT * from mispers1", BeanPropertyRowMapper.newInstance(Persona.class));
    }


    @Override
    public int update(Persona persona) {
        return jdbcTemplate.update("UPDATE mispers1 SET name=?, surname=?, city=? WHERE id_personpers=?",
                new Object[]{persona.getName(), persona.getSurname(), persona.getCity(), persona.getId_personpers()});
    }


    @Override
    public int deleteById_personpers(int id) {
        return jdbcTemplate.update("DELETE FROM mispers1 WHERE id_personpers=?", id);
    }


    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from mispers1");
    }
}