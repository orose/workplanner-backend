package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.rowmappers.InviteRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InviteRepositoryImpl implements InviteRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public InviteRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Invite create(Invite invite) {
        String sql = "insert into invite (email, organization_id) values (:email, :organization_id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("email", invite.getEmail())
            .addValue("organization_id", invite.getOrganizationId());

        namedParameterJdbcTemplate.update(sql, parameters);

        return this.findByEmail(invite.getEmail());
    }

    @Override
    public Invite findByEmail(String email) {
        String sql = "select * from invite where email = :email";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("email", email);

        List<Invite> result = namedParameterJdbcTemplate.query(sql, parameters, new InviteRowMapper());

        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    public List<Invite> findAllByOrganizationId(Long organizationId, Integer offset, Integer limit) {
        String sql = "select * "
            + "from invite "
            + "where organization_id = :organizationId "
            + "offset :offset "
            + "limit :limit ";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("organizationId", organizationId)
                .addValue("offset", offset)
                .addValue("limit", limit);

        return namedParameterJdbcTemplate.query(sql, parameters, new InviteRowMapper());
    }

    @Override
    public Integer countAll(Long organizationId) {
        String sql = "select count(*) from invite where organization_id = :organizationId";

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("organizationId", organizationId);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }

    @Override
    public Integer delete(String email) {
        if (email == null) {
            return 0;
        }

        String sql = "delete from invite where email = :email";
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("email", email);

        return namedParameterJdbcTemplate.update(sql, parameters);
    }
}
