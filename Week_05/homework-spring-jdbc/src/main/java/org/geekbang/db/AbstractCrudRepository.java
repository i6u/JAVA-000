package org.geekbang.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AbstractCrudRepository {

    private final Connection conn;

    protected AbstractCrudRepository(Connection conn) {
        this.conn = conn;
    }

    public long insertUpdate(String sql, Object... params) {
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            if (params != null && params.length > 0) {
                for (int i = 1; i < params.length; i++) {
                    ps.setObject(i, params[i - 1]);
                }
            }
            int i = ps.executeUpdate();
            if (i > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public long otherUpdate(String sql, Object... params) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (params != null && params.length > 0) {
                for (int i = 1; i < params.length; i++) {
                    ps.setObject(i, params[i - 1]);
                }
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Map<String, Object>> query(String sql, Object... params) {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (params != null && params.length > 0) {
                for (int i = 1; i < params.length; i++) {
                    ps.setObject(i, params[i - 1]);
                }
            }

            try (ResultSet rs = ps.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                List<Map<String, Object>> rowList = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i < columnCount; ++i) {
                        row.put(metaData.getCatalogName(i), rs.getObject(i));
                    }
                    rowList.add(row);
                }
                if (!rowList.isEmpty())
                    return rowList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用事务
     */
    public <T> T execTransactions(Supplier<T> supplier) {
        try {
            conn.setAutoCommit(false);
            T t = supplier.get();
            conn.commit();
            return t;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
