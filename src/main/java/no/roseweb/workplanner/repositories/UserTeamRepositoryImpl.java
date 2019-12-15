package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.UserTeam;
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
    public int create(UserTeam userTeam) {
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

        return namedParameterJdbcTemplate.update(sql, parameters, keyHolder);
    }

    @Override
    public int remove(String userId, Long teamId) {
        String sql = "delete from user_team where user_email = :user_email and team_id = :team_id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("user_email", userId)
                .addValue("team_id", teamId);

        return namedParameterJdbcTemplate.update(sql, parameters);
    }
}
