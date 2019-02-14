package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Team;
import no.roseweb.workplanner.models.rowmappers.TeamRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TeamRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }


    @Override
    public Team create(Team team) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into team (name, organization_id) values (:name, :organization_id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("name", team.getName())
            .addValue("organization_id", team.getOrganizationId());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        return this.findById(keyHolder.getKey().longValue());
    }

    @Override
    public Team findById(Long id) {
        String sql = "select * from team where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            return (Team) namedParameterJdbcTemplate.queryForObject(
                    sql, parameters, new TeamRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void delete(Team team) {
        String sql = "delete from team where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", team.getId());

        namedParameterJdbcTemplate.update( sql, parameters);
    }
}
