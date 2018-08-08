package Ex1;

import java.util.List;

public interface DAO<T> {
    public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;databaseName=Lab5;integratedSecurity=true";

    //If using credentials.
    //public static final String USERNAME = "root";
    //public static final String PASSWORD = "password";

    T get(int id);

    List<T> getAll();

    void save(T t);

    void delete(T t);

    void update(T t, String[] params);

}
