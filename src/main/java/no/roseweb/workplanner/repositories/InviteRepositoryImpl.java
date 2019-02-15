package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.Invite;
import no.roseweb.workplanner.models.rowmappers.InviteRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class InviteRepositoryImpl implements InviteRepository {

    private JdbcTemplate jdbcTemplate;

    public InviteRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Invite create(Invite invite) {
        String sql = "insert into invite (email, organization_id) values (?, ?)";

        jdbcTemplate.update(sql,
            invite.getEmail(),
            invite.getOrganizationId()
        );

        return this.findByEmail(invite.getEmail());
    }

    @Override
    public Invite findByEmail(String email) {
        String sql = "select * from invite where email = ?";
        Invite invite;

        try {
            invite = (Invite) jdbcTemplate.queryForObject(sql, new Object[] {email}, new InviteRowMapper());
        } catch (DataAccessException e) {
            return null;
        }

        return invite;
    }

    @Override
    public void delete(Invite invite) {
        String sql = "delete from invite where email = ?";

        jdbcTemplate.update(sql, invite.getEmail());
    }
}
