package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.UserTeam;
import no.roseweb.workplanner.models.rowmappers.UserTeamRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class UserTeamRepositoryImpl implements UserTeamRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserTeamRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public UserTeam create(UserTeam userTeam) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into user_team ("
            + "user_email,"
            + "team_id, "
            + "permission_key "
            + ") values ("
            + ":user_email, :team_id, :permission_key)";

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("user_email", userTeam.getUserEmail())
            .addValue("team_id", userTeam.getTeamId())
            .addValue("permission_key", userTeam.getPermissionKey());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        return this.findById(keyHolder.getKey().longValue());
    }

    @Override
    public void remove(UserTeam userTeam) {
        String sql = "delete from user_team where user_email = :user_email and team_id = :team_id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_email", userTeam.getUserEmail())
                .addValue("team_id", userTeam.getTeamId());

        namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public UserTeam findById(Long id) {
        String sql = "select * from user_team where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("id", id);

        try {
            return (UserTeam) namedParameterJdbcTemplate.queryForObject(
                    sql, parameters, new UserTeamRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
