package no.roseweb.workplanner.models.rowmappers;

import no.roseweb.workplanner.models.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Role role = new Role();

        role.setName(rs.getString("name"));
        role.setId(rs.getLong("id"));

        return role;
    }
}
