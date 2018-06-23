
import java.util.ArrayList;
import java.util.Iterator;

public class Table {

    private ArrayList<String> columnNames;
    private ArrayList<String> columnTypes;
    private ArrayList<String> rows;

    public Table() {
        columnNames = new ArrayList<String>();
        columnTypes = new ArrayList<String>();
        rows = new ArrayList<String>();
    }

    public void addColumn(String columnName,String columnType) {
        columnNames.add(columnName);
        columnTypes.add(columnType);
        deleteAllRows();
    }

    public void deleteAllRows() {
        rows = new ArrayList<String>();
    }

    public int getColumnCount(){
        return columnNames.size();
    }

    public int getRowsCount() {
        return rows.size();
    }

    public String getColumnNamesStr() {
        String ret = "";
        for(int i=0;i<columnNames.size();i++) {
            ret+="\""+columnNames.get(i)+"\"";
            if(i<columnNames.size()-1) {
                ret+=",";
            }
        }
        return ret;
    }

    public Iterator getColumnNames() {
        Iterator itr = columnNames.iterator();
        return itr;
    }

    public String getColumnName(int indx) {
        if(indx<0 || indx>columnNames.size()-1) {
            return null;
        }
        return columnNames.get(indx);
    }

    public String getColumnType(String columnName) {
        int indx = columnNames.indexOf(columnName);
        if(indx==-1){
            return null;
        }
        return columnTypes.get(indx);
    }

    public void addRow(String row) {
        String[] rowSplit = row.split(",");
        if(rowSplit.length==columnNames.size()) {
            rows.add(row);
        }
    }

    public String[] getRow(int rowIndx) {
        if(rowIndx<0 || rowIndx>rows.size()-1) {
            return null;
        }
        String row = rows.get(rowIndx);
        return row.split(",");
    }

    public String getRowStr(int rowIndx) {
        String[] rowArr = getRow(rowIndx);
        String ret = "";
        for(int i=0;i<rowArr.length;i++) {
            ret+="\""+rowArr[i]+"\"";
            if(i<rowArr.length-1) {
                ret+=",";
            }
        }
        return ret;
    }
}
