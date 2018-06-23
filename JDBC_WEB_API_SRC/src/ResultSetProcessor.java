import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ResultSetProcessor {

    public static ArrayList<String> getFirstColumnsStr(ResultSet rs) {
        ArrayList<String> lst = new ArrayList<String>();
        try {
            while (rs.next()) {
                lst.add((String) rs.getString(1));
            }
        } catch(SQLException ioe) {
            lst = new ArrayList<String>();
        }
        return lst;
    }

    public static ArrayList<Integer> getFirstColumnsInt(ResultSet rs) {
        ArrayList<Integer> lst = new ArrayList<Integer>();
        try {
            while (rs.next()) {
                lst.add((Integer) rs.getInt(1));
            }
        } catch(SQLException ioe) {
            lst = new ArrayList<Integer>();
        }
        return lst;
    }

    public static Table prepareTable(ResultSet rs,Table table) {
        Iterator itr = table.getColumnNames();
        try {
            while (rs.next()) {
                String row = "";
                for(int i=0;i<table.getColumnCount();i++){
                    row+=rs.getString(table.getColumnName(i));
                    if(i<table.getColumnCount()-1) {
                        row+=",";
                    }
                }
                if(!row.equals("")) {
                    table.addRow(row);
                }
            }
        } catch (SQLException sqle) {
            System.out.println("All rows deleted from result table ["+sqle+"]");
            table.deleteAllRows();
        }
        return table;
    }
}
