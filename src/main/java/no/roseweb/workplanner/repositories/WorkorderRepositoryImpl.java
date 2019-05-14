package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.rowmappers.WorkorderRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WorkorderRepositoryImpl implements WorkorderRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public WorkorderRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Workorder create(Workorder workorder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into workorder ("
                + "title,"
                + "description, "
                + "team_id "
                + ") values ("
                + ":title, :description, :team_id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", workorder.getTitle())
                .addValue("description", workorder.getDescription())
                .addValue("team_id", workorder.getTeamId());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        return this.findById(keyHolder.getKey().longValue());
    }

    @Override
    public Workorder update(Workorder workorder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "update workorder set "
                + "title = :title,"
                + "description = :description, "
                + "team_id = :team_id "
                + "where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", workorder.getTitle())
                .addValue("description", workorder.getDescription())
                .addValue("team_id", workorder.getTeamId())
                .addValue("id", workorder.getId());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        return this.findById(workorder.getId());
    }

    @Override
    public Workorder findById(Long id) {
        String sql = "select * from workorder where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            return (Workorder) namedParameterJdbcTemplate.queryForObject(
                    sql, parameters, new WorkorderRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
