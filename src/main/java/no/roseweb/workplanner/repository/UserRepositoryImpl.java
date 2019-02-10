package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.models.rowmappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(User user) {
        String sql = "insert into user (email, firstname, lastname, password, organization_id) values (?, ?, ?, ?, ?)";

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        jdbcTemplate.update(sql, user.getEmail(), user.getFirstname(), user.getLastname(), user.getPassword(), user.getOrganizationId());
    }

    @Override
    public User findByEmail(String email) {
        String sql = "select * from user where email = ?";

        User user = (User)jdbcTemplate.queryForObject(
                sql,
                new Object[] { email },
                new UserRowMapper()
        );

        return user;
    }
}
