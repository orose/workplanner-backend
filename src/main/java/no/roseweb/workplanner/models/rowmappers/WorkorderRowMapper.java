package no.roseweb.workplanner.models.rowmappers;

import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.utils.RepositoryUtils;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkorderRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Workorder workorder = new Workorder();

        workorder.setId(RepositoryUtils.getLong(rs, "id"));
        workorder.setTitle(rs.getString("title"));
        workorder.setDescription(rs.getString("description"));
        workorder.setTeamId(RepositoryUtils.getLong(rs, "team_id"));
        workorder.setOrganizationId(RepositoryUtils.getLong(rs, "organization_id"));

        return workorder;
    }
}
