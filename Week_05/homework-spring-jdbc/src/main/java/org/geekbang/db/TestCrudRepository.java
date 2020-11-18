package org.geekbang.db;

import java.util.List;
import java.util.Map;

public class TestCrudRepository extends AbstractCrudRepository {

    public TestCrudRepository() {
        super(DataSource.getConnection());
    }

    public long create(String sql, Object... params) {
        return insertUpdate(sql, params);
    }

    public List<Map<String, Object>> read(String sql, Object... params) {
        return query(sql, params);
    }

    public boolean update(String sql, Object... params) {
        return otherUpdate(sql, params) > 0;
    }

    public boolean delete(String sql, Object... params) {
        return otherUpdate(sql, params) > 0;
    }

    public void tx() {
        String s = execTransactions(() -> {
            create("", "");
            create("", "");
            return "";
        });
    }
}
