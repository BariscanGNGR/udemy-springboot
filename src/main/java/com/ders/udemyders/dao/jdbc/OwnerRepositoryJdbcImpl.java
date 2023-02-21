package com.ders.udemyders.dao.jdbc;

import com.ders.udemyders.dao.OwnerRepository;
import com.ders.udemyders.dao.PetRepository;
import com.ders.udemyders.model.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OwnerRepositoryJdbcImpl implements OwnerRepository {

    private final JdbcTemplate jdbcTemplate;


    private RowMapper<Owner> rowMapper = new RowMapper<Owner>() {
        @Override
        public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
            Owner owner = Owner.builder()
                    //.id(rs.getLong("id"))
                    .firstName(rs.getString("first_name"))
                    .lastName(rs.getString("last_name"))
                    .build();
            owner.setId(rs.getLong("id"));
            return owner;
        }
    };
    @Override
    public List<Owner> findAll() {
        String sql = "select id,first_name,last_name from t_owner";
        return jdbcTemplate.query(sql,rowMapper);
    }

    @Override
    public Owner findById(Long id) {
        String sql = "select id,first_name,last_name from t_owner where id = ?";
        return DataAccessUtils.singleResult(jdbcTemplate.query(sql,rowMapper,id));
    }

    @Override
    public List<Owner> findByLastName(String lastName) {
        String sql = "select id,first_name,last_name from t_owner where last_name like ?";
        return jdbcTemplate.query(sql,rowMapper,"%"+lastName+"%");
    }

    @Override
    public void create(Owner owner) {
        String sql = "insert into t_owner (id,first_name,last_name) VALUES (?, ?,?);";
        jdbcTemplate.update(sql,owner.getId(),owner.getFirstName(),owner.getLastName());
    }

    @Override
    public Owner update(Owner owner) {
        String sql = "update t_owner SET first_name=?,last_name=? where id = ?";
        jdbcTemplate.update(sql,owner.getFirstName(),owner.getLastName(),owner.getId());
        return owner;
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from t_owner where id = ?";
        jdbcTemplate.update(sql,id);
    }
}
