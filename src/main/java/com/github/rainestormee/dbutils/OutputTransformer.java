package com.github.rainestormee.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface OutputTransformer<T> {

    T transform(ResultSet rs) throws SQLException;
}
