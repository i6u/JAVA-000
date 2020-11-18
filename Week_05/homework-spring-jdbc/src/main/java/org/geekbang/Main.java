package org.geekbang;

import org.geekbang.db.TestCrudRepository;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        TestCrudRepository repository = new TestCrudRepository();
        List<Map<String, Object>> result = repository.query("select 1+1 as id");
        for (Map<String, Object> row : result) {
            for (String column : row.keySet()) {
                System.out.print(column + "|\t" + row.get(column));
            }
            System.out.println();
        }
    }
}
