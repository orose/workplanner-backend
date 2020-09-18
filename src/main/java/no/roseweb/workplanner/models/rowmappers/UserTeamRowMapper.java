package no.roseweb.workplanner.models.rowmappers;

import no.roseweb.workplanner.models.UserTeam;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTeamRowMapper implements RowMapper<UserTeam> {
    @Override
    public UserTeam mapRow(ResultSet rs, int i) throws SQLException {
        UserTeam userTeam = new UserTeam();

        userTeam.setId(rs.getLong("id"));
        userTeam.setUserId(rs.getLong("user_id"));
        userTeam.setTeamId(rs.getLong("team_id"));
        userTeam.setPermissionKey(rs.getString("permission_key"));

        return userTeam;
    }
}
