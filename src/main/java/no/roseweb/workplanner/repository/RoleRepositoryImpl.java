package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.Role;
import no.roseweb.workplanner.models.rowmappers.RoleRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private JdbcTemplate jdbcTemplate;

    public RoleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Role addRole(Role role) {
        String sql = "insert into role (name) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, role.getName(), keyHolder);

        return this.findById(keyHolder.getKey().longValue());
    }

    @Override
    public List<Role> findAllOrderedByName() {
        String sql = "select * from role order by name";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        List<Role> roles = new ArrayList<>();

        for (Map row : rows) {
            Role r = new Role();
            r.setId((Long) row.get("id"));
            r.setName((String) row.get("name"));
        }

        return roles;
    }

    @Override
    public Role findById(Long id) {
        String sql = "select * from role where id = ?";

        return (Role) jdbcTemplate.queryForObject(sql, new Object[] {id}, new RoleRowMapper());
    }
}
