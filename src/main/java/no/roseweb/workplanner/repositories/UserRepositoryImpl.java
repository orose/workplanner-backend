package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.models.rowmappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User create(User user) {
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
        return this.findByEmail(user.getEmail());
    }

    @Override
    public User findByEmail(String email) {
        String sql = "select * from user where email = ?";

        List<User> userList = jdbcTemplate.query(sql, new Object[] {email}, new UserRowMapper());

        return userList.isEmpty() ? null : userList.get(0);
    }
}
