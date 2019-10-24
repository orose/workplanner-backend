package no.roseweb.workplanner.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RepositoryUtils {
    public static Integer getInt(ResultSet rs, String label) throws SQLException {
        Integer value = rs.getInt(label);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }

    public static Long getLong(ResultSet rs, String label) throws SQLException {
        Long value = rs.getLong(label);
        if (rs.wasNull()) {
            return null;
        }
        return value;
    }
}
