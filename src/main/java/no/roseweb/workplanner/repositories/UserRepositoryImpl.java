package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.rowmappers.ApplicationUserRowMapper;
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
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserRepositoryImpl(JdbcTemplate jdbcTemplate, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public ApplicationUser create(ApplicationUser user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into application_user ("
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

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[] {"id"});
        Long id;
        if (keyHolder.getKeys() != null && keyHolder.getKeys().size() > 1) {
            id = (Long) keyHolder.getKeys().get("id");
        } else {
            id = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
        }
        return this.findById(id);
    }

    @Override
    public ApplicationUser findByEmail(String email) {
        String sql = "select * from application_user where email = ?";

        List<ApplicationUser> userList = jdbcTemplate.query(sql, new Object[] {email}, new ApplicationUserRowMapper());

        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public ApplicationUser findById(Long id) {
        String sql = "select * from application_user where id = ?";

        List<ApplicationUser> userList = jdbcTemplate.query(sql, new Object[] {id}, new ApplicationUserRowMapper());

        return userList.isEmpty() ? null : userList.get(0);
    }

    @Override
    public List<ApplicationUser> findByOrganizationId(Long organizationId, Integer offset, Integer limit) {
        String sql = "select * "
                + "from application_user "
                + "where organization_id = :organizationId "
                + "limit :limit offset :offset ";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("organizationId", organizationId)
                .addValue("limit", limit)
                .addValue("offset", offset);

        List<ApplicationUser> users = namedParameterJdbcTemplate.query(sql, parameters, new ApplicationUserRowMapper());
        users.forEach(user -> user.setPassword(null));

        return users;
    }

    @Override
    public Integer countAllByOrganizationId(Long organizationId) {
        String sql = "select count(*) "
            + "from application_user "
            + "where organization_id = :organizationId";

        SqlParameterSource parameters = new MapSqlParameterSource()
            .addValue("organizationId", organizationId);

        return namedParameterJdbcTemplate.queryForObject(sql, parameters, Integer.class);
    }
}
