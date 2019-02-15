package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Organization;
import no.roseweb.workplanner.models.rowmappers.OrganizationRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrganizationRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Organization create(Organization organization) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into organization ("
                + "name,"
                + "organization_number, "
                + "email "
                + ") values ("
                + ":name, :organization_number, :email)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("name", organization.getName())
                .addValue("organization_number", organization.getOrganizationNumber())
                .addValue("email", organization.getEmail());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        return this.findById(keyHolder.getKey().longValue());
    }

    @Override
    public List<Organization> findAllOrderedByName() {
        String sql = "select * from organization order by name";
        List<Organization> organizations = namedParameterJdbcTemplate.query(sql, new OrganizationRowMapper());

        return organizations;
    }

    @Override
    public Organization findById(Long id) {
        String sql = "select * from organization where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            return (Organization) namedParameterJdbcTemplate.queryForObject(
                    sql, parameters, new OrganizationRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
