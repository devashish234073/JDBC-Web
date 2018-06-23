import java.sql.ResultSet;
import java.util.ArrayList;

public class BasicQueries {
    public static Table getAllRows(Database db,String tableName) {
        ResultSet cols = db.runQuery("describe " + tableName);
        if(cols==null) return null;
        ArrayList<String> colNames = ResultSetProcessor.getFirstColumnsStr(cols);
        if(colNames==null) return null;
        Table table = new Table();
        for(int i=0;i<colNames.size();i++) {
            table.addColumn(colNames.get(i),"str");
        }
        ResultSet allROws = db.runQuery("select * from " + tableName);
        ResultSetProcessor.prepareTable(allROws,table);
        return table;
    }
}
