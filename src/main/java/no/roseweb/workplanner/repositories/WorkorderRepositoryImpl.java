package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.exceptions.EntityNotFoundException;
import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.WorkorderStatus;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import no.roseweb.workplanner.models.rowmappers.WorkorderRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class WorkorderRepositoryImpl implements WorkorderRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public WorkorderRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Workorder create(WorkorderCreateRequest request, ApplicationUser user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into workorder ("
                + "title,"
                + "description, "
                + "status, "
                + "organization_id, "
                + "created_by, "
                + "updated_at, "
                + "created_at "
                + ") values ("
                + ":title, :description, :status, :organization_id, :user_id, :now, :now)";

        Workorder w = new Workorder();
        w.setTitle(request.getTitle());
        w.setDescription(request.getDescription());
        w.setOrganizationId(user.getOrganizationId());
        w.setStatus(WorkorderStatus.NEW);
        w.validate();

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", w.getTitle())
                .addValue("description", w.getDescription())
                .addValue("status", w.getStatus().name())
                .addValue("organization_id", w.getOrganizationId())
                .addValue("user_id", user.getId())
                .addValue("now", LocalDateTime.now());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[] { "id" });
        Long id;
        if (keyHolder.getKeys() != null && keyHolder.getKeys().size() > 1) {
            id = (Long)keyHolder.getKeys().get("id");
        } else {
            id = keyHolder.getKey() != null ? keyHolder.getKey().longValue() : null;
        }

        return this.findById(id);
    }

    @Override
    public Workorder update(Workorder workorder, ApplicationUser user) {
        Workorder existing = this.findById(workorder.getId());
        workorder.setOrganizationId(existing.getOrganizationId());
        workorder.validate();

        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "update workorder set "
                + "title = :title,"
                + "description = :description, "
                + "team_id = :team_id, "
                + "status = :status, "
                + "updated_by = :user_id "
            + "where id = :id";


        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", workorder.getTitle())
                .addValue("description", workorder.getDescription())
                .addValue("team_id", workorder.getTeamId())
                .addValue("organization_id", workorder.getOrganizationId())
                .addValue("id", workorder.getId())
                .addValue("status", workorder.getStatus().name())
                .addValue("user_id", user.getId())
                .addValue("now", LocalDateTime.now());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        return this.findById(workorder.getId());
    }

    @Override
    public Workorder findById(Long id) {
        String sql = "select * from workorder where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        List result = namedParameterJdbcTemplate.query(
                sql, parameters, new WorkorderRowMapper());
        if (result.isEmpty()) {
            throw new EntityNotFoundException(Workorder.class.getSimpleName(), id);
        } else {
            return (Workorder) result.get(0);
        }
    }

    @Override
    public List<Workorder> getAll(Integer limit, Integer offset) {
        String sql = "select * from workorder limit :limit offset :offset";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);

        try {
            List<Map<String, Object>> rows = namedParameterJdbcTemplate.queryForList(sql, parameters);
            List<Workorder> workorders = new ArrayList<>();

            for (Map row : rows) {
                Workorder w = new Workorder();
                w.setId(new Long((Integer) row.get("id")));
                if (row.get("team_id") != null) {
                    w.setTeamId(new Long((Integer) row.get("team_id")));
                }
                w.setOrganizationId(new Long((Integer) row.get("organization_id")));
                w.setTitle((String) row.get("title"));
                w.setDescription((String) row.get("description"));

                workorders.add(w);
            }
            return workorders;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Integer countAll() {
        String sql = "select count(*) from workorder";

        try {
            Integer count = namedParameterJdbcTemplate.
                    queryForObject(sql, new MapSqlParameterSource(), Integer.class);
            return count;
        } catch (DataAccessException e) {
            return null;
        }
    }
}
