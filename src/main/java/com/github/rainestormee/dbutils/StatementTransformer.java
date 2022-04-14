package com.github.rainestormee.dbutils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementTransformer {

    PreparedStatement transform(PreparedStatement ps) throws SQLException;
}
