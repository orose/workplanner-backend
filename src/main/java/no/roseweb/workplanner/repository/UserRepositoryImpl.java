package no.roseweb.workplanner.repository;

import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.models.rowmappers.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(User user) {
        String sql = "insert into user (email, firstname, lastname, password) values (?, ?, ?, ?)";

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        jdbcTemplate.update(sql, user.getEmail(), user.getFirstname(), user.getLastname(), user.getPassword());
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
