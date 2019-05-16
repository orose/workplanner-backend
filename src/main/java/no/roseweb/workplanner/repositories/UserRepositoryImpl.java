package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.User;
import no.roseweb.workplanner.models.rowmappers.UserRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        NamedParameterJdbcTemplate namedParameterJdbcTemplate =
            new NamedParameterJdbcTemplate(jdbcTemplate);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into user ("
            + "email, "
            + "firstname, "
            + "lastname, "
            + "password, "
            + "organization_id) values ("
            + ":email, :firstname, :lastname, :password, :organization_id)";

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("email", user.getEmail())
            .addValue("firstname", user.getFirstname())
            .addValue("lastname", user.getLastname())
            .addValue("password", user.getPassword())
            .addValue("organization_id", user.getOrganizationId());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);
        Long id = keyHolder.getKey().longValue();
        return this.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        String sql = "select * from user where email = ?";

        List<User> userList = jdbcTemplate.query(sql, new Object[] {email}, new UserRowMapper());

        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public User findById(Long id) {
        String sql = "select * from user where id = ?";

        List<User> userList = jdbcTemplate.query(sql, new Object[] {id}, new UserRowMapper());

        return userList.isEmpty() ? null : userList.get(0);
    }
}
