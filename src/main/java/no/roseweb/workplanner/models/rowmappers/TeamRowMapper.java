package no.roseweb.workplanner.models.rowmappers;

import no.roseweb.workplanner.models.Team;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Team team = new Team();

        team.setId(rs.getLong("id"));
        team.setName(rs.getString("name"));
        team.setOrganizationId(rs.getLong("organization_id"));

        return team;
    }
}
