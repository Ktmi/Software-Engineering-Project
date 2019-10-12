import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;

public class SQLDatabaseConnection {

    private Connection dbconnect;

    public SQLDatabaseConnection()
    {
        Context initCtx = new InitialContext();
        Context envCtx = (Context) initCtx.lookup("java:comp/env");
        DataSource ds = (DataSource) envCtx.lookup("jbdc/dbconnection");
        dbconnect = ds.getConnection();
    }
    public String getPost(int id)
    {
        PreparedStatement st = dbconnect.prepareStatement("SELECT * FROM ");
        st.setInt(1,id);
        ResultSet rs = st.executeQuery();
        return null;
    }
}