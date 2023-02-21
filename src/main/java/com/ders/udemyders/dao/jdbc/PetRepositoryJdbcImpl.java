package com.ders.udemyders.dao.jdbc;

import com.ders.udemyders.dao.PetRepository;
import com.ders.udemyders.model.Pet;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PetRepositoryJdbcImpl implements PetRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Pet findById(Long id) {
        return null;
    }

    @Override
    public List<Pet> findByOwnerId(Long ownerId) {
        return null;
    }

    @Override
    public void create(Pet pet) {

    }

    @Override
    public Pet update(Pet pet) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void deleteByOwnerId(Long ownerId) {
        String sql = "delete from t_pet where owner_id=?";
        jdbcTemplate.update(sql,ownerId);
    }
}
