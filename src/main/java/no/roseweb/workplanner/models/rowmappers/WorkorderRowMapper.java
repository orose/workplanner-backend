package no.roseweb.workplanner.models.rowmappers;

import no.roseweb.workplanner.models.Workorder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkorderRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Workorder workorder = new Workorder();

        workorder.setId(rs.getLong("id"));
        workorder.setTitle(rs.getString("title"));
        workorder.setDescription(rs.getString("description"));
        workorder.setTeamId(rs.getLong("team_id"));
        workorder.setOrganizationId(rs.getLong("organization_id"));

        return workorder;
    }
}
