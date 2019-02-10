package no.roseweb.workplanner.models.rowmappers;

import no.roseweb.workplanner.models.Invite;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class InviteRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Invite invite = new Invite();

        invite.setEmail(rs.getString("email"));
        invite.setOrganizationId(rs.getLong("organization_id"));

        return invite;
    }
}
