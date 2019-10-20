package no.roseweb.workplanner.repositories;

import no.roseweb.workplanner.models.ApplicationUser;
import no.roseweb.workplanner.models.Workorder;
import no.roseweb.workplanner.models.requests.WorkorderCreateRequest;
import no.roseweb.workplanner.models.rowmappers.WorkorderRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

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
                + "organization_id "
                + ") values ("
                + ":title, :description, :organization_id)";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", request.getTitle())
                .addValue("description", request.getDescription())
                .addValue("organization_id", user.getOrganizationId());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        return this.findById(keyHolder.getKey().longValue());
    }

    @Override
    @PreAuthorize("hasPermission(#workorder, 'edit')")
    public Workorder update(Workorder workorder) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "update workorder set "
                + "title = :title,"
                + "description = :description, "
                + "team_id = :team_id, "
                + "organization_id = :organization_id "
                + "where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("title", workorder.getTitle())
                .addValue("description", workorder.getDescription())
                .addValue("team_id", workorder.getTeamId())
                .addValue("organization_id", workorder.getOrganizationId())
                .addValue("id", workorder.getId());

        namedParameterJdbcTemplate.update(sql, parameters, keyHolder);

        return this.findById(workorder.getId());
    }

    @Override
    public Workorder findById(Long id) {
        String sql = "select * from workorder where id = :id";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            return (Workorder) namedParameterJdbcTemplate.queryForObject(
                    sql, parameters, new WorkorderRowMapper());
        } catch (DataAccessException e) {
            return null;
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
