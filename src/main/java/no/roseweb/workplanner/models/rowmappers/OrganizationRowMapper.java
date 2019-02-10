package no.roseweb.workplanner.models.rowmappers;

import no.roseweb.workplanner.models.Organization;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizationRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        Organization organization = new Organization();

        organization.setId(rs.getLong("id"));
        organization.setEmail(rs.getString("email"));
        organization.setName(rs.getString("name"));
        organization.setOrganizationNumber(rs.getString("organization_number"));

        return organization;
    }
}
