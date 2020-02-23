package no.roseweb.workplanner.repositories;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

@Repository
public class UserWorkorderRepositoryImpl implements UserWorkorderRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserWorkorderRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Integer addAssignment(String userId, Long workorderId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("Cannot assign user to workorder. Missing parameter userId.");
        }
        if (workorderId == null || workorderId < 0) {
            throw new IllegalArgumentException("Cannot assign user to workorder. Invalid parameter workorderId.");
        }
        if (assignmentExists(workorderId, userId)) {
            return 1;
        }
        String sql = "insert into user_workorder "
                + "(user_email, workorder_id) "
                + "values "
                + "(:userId, :workorderId)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("workorderId", workorderId);

        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public Integer removeAssignment(String userId, Long workorderId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("Cannot remove assignment. Missing parameter userId.");
        }
        if (workorderId == null || workorderId < 0) {
            throw new IllegalArgumentException("Cannot remove assignment. Invalid parameter workorderId.");
        }
        String sql = "delete from user_workorder "
                + "where user_email = :userId "
                + "and workorder_id = :workorderId ";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", userId)
                .addValue("workorderId", workorderId);

        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    @Override
    public Integer removeAllAssignments(Long workorderId) {
        if (workorderId == null || workorderId < 0) {
            throw new IllegalArgumentException("Cannot remove assignment. Invalid parameter workorderId.");
        }
        String sql = "delete from user_workorder "
            + "where workorder_id = :workorderId ";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("workorderId", workorderId);

        return namedParameterJdbcTemplate.update(sql, parameters);
    }

    private boolean assignmentExists(Long workorderId, String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("Missing parameter userId.");
        }
        if (workorderId == null || workorderId < 0) {
            throw new IllegalArgumentException("Missing parameter workorderId.");
        }
        String sql = "select count(*) "
            + "from user_workorder "
            + "where user_email = :userId "
            + "and workorder_id = :workorderId";

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("userId", userId)
            .addValue("workorderId", workorderId);

        Integer count = namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);

        return count != null && count > 0;
    }
}
