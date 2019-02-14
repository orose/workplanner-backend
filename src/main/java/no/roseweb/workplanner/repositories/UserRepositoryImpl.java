package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.models.rowmappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void add(User user) {
        String sql = "insert into user (email, firstname, lastname, password, organization_id) values (?, ?, ?, ?, ?)";

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        jdbcTemplate.update(
            sql,
            user.getEmail(),
            user.getFirstname(),
            user.getLastname(),
            user.getPassword(),
            user.getOrganizationId()
        );
    }

    @Override
    public User findByEmail(String email) {
        String sql = "select * from user where email = ?";

        return (User) jdbcTemplate.queryForObject(sql, new Object[] {email}, new UserRowMapper());
    }
}
