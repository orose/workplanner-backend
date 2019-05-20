package no.roseweb.workplanner.models.rowmappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import no.roseweb.workplanner.models.User;

public class UserRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setOrganizationId(rs.getLong("organization_id"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
