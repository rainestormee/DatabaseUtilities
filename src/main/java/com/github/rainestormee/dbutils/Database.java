package com.github.rainestormee.dbutils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Database {

    DataSource getDatasource();

    default void executeCommit(String query) throws Exception {
        executeQuery("begin;" + query + "; commit;");
    }

    default void executeQuery(String query) throws Exception {
        executeQuery(query, (o) -> (o));
    }

    default void executeQuery(String query, StatementTransformer transformer) throws Exception {
        executeQuery(query, false, transformer, (o) -> (o));
    }

    default <T> T executeQuery(String query, StatementTransformer transformer, OutputTransformer<T> ot) throws Exception {
        return executeQuery(query, true, transformer, ot);
    }

    default <T> T executeQuery(String query, boolean hasResults, StatementTransformer transformer, OutputTransformer<T> ot) throws Exception {
        try (Connection connection = getDatasource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(query);
            transformer.transform(ps);
            if (hasResults) {
                ps.getResultSet();
                return ot.transform(ps.executeQuery());
            } else {
                ps.execute();
                return null;
            }
        }
    }
}
