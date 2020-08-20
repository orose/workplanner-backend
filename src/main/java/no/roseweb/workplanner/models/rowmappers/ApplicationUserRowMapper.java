package no.roseweb.workplanner.models.rowmappers;

import no.roseweb.workplanner.models.ApplicationUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationUserRowMapper implements RowMapper<ApplicationUser> {
    @Override
    public ApplicationUser mapRow(ResultSet rs, int i) throws SQLException {
        ApplicationUser user = new ApplicationUser();

        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setFirstname(rs.getString("firstname"));
        user.setLastname(rs.getString("lastname"));
        user.setOrganizationId(rs.getLong("organization_id"));
        user.setPassword(rs.getString("password"));

        return user;
    }
}
