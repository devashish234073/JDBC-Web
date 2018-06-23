import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestProcessor {

    static private Database db;

    private static String getStyle() {
        String ret="";
        ret+="<style>";
        ret+=".dbNames{width:100%;height:30px;border:none;background-color:blue;color:white;}";
        ret+=".e0{border-top-left-radius:5px;}";
        ret+=".e1{border-top-right-radius:5px;}";
        ret+=".e2{border-bottom-left-radius:5px;}";
        ret+=".e3{border-bottom-right-radius:5px;}";
        ret+="</style>";
        return ret;
    }

    protected static Response processRequest(Request req) {
        String res = "";
        if(!req.isNull()) {
            if(req.getMethod().equals("GET")) {
                res = doAction(req);
            }
        }
        Response resp = new Response(res,req);
        return resp;
    }

    private static String doAction(Request req) {
        String[] raw = req.getAction().split("_");
        String ret = "";
        if(raw.length==2){
            String function = raw[0].substring(1);
            String retType  = raw[1];
            if(function.equals("showDatabases")) {
                ret = connect(retType,req);
            } else if(function.equals("selectDb")) {
                ret = selectDb(retType,req);
            } else if(function.equals("viewTable")) {
                ret = viewTable(retType,req);
            }
        }
        return ret;
    }

    private static String viewTable(String retType, Request req) {
        Table table = BasicQueries.getAllRows(db,req.getAttribute("tableName"));
        String ret = "";
        HashMap<String,String> replacements = new HashMap<String,String>();
        String colNames = table.getColumnNamesStr();
        String rows = "";
        for(int i=0;i<table.getRowsCount();i++) {
            rows+="["+table.getRowStr(i)+"]";
            if(i<table.getRowsCount()-1) {
                rows+=",";
            }
        }
        if(table!=null) {
            if(retType.equals("json")){
                ret+="{\"columnNames\":["+colNames+"],\"rows\":["+rows+"]}";
            } else {
                replacements.put("${colNames}",colNames);
                replacements.put("${rows}",rows);
                ret+=FileParser.parse("./html/tableView.html",replacements);
            }
        }
        return ret;
    }

    protected static String getHome(Request req) {
        String ret = "";
        ret+=FileParser.parse("./html/home.html",null);
        return ret;
    }

    private static String selectDb(String retType, Request req) {
        db.setDb(req.getAttribute("dbName"));
        db.connect();
        ResultSet tables = db.runQuery("show tables");
        ArrayList<String> tbls = ResultSetProcessor.getFirstColumnsStr(tables);
        String ret = "";
        if(tbls!=null) {
            if(retType.equals("json")) {
                ret+="{'tables':[";
                for(int i=0;i<tbls.size();i++) {
                    ret+="'"+ tbls.get(i)+"'";
                    if(i!=tbls.size()-1) {
                        ret+=":";
                    }
                }
                ret+="]}";
            } else {
                HashMap<String,String> replacements = new HashMap<String,String>();
                String tablesHTML =  "";
                for(int i=0;i<tbls.size();i++) {
                    tablesHTML+="<option>"+tbls.get(i)+"</option>";
                }
                replacements.put("${tableNames}",tablesHTML);
                ret+=FileParser.parse("./html/tableSelection.html",replacements);
            }
        }
        return ret;
    }

    private static String connect(String retType, Request req) {
        db = new Database(req.getAttribute("dbType"),
                req.getAttribute("host"),
                Integer.parseInt(req.getAttribute("port")),
                req.getAttribute("USERNAME"),
                req.getAttribute("PASSWORD"));
        db.connect();
        ResultSet rs = db.runQuery("show databases");
        ArrayList<String> dbs = ResultSetProcessor.getFirstColumnsStr(rs);
        String ret = "";
        if(dbs!=null) {
            if(retType.equals("json")) {
                ret+="{'dbs':[";
                for(int i=0;i<dbs.size();i++) {
                    ret+="'"+ dbs.get(i)+"'";
                    if(i!=dbs.size()-1) {
                        ret+=":";
                    }
                }
                ret+="]}";
            } else {
                HashMap<String,String> replacements = new HashMap<String,String>();
                String dbHTML =  "";
                for(int i=0;i<dbs.size();i++) {
                    dbHTML+="<option>"+dbs.get(i)+"</option>";
                }
                replacements.put("${dbs}",dbHTML);
                ret+=FileParser.parse("./html/dbSelection.html",replacements);
            }
        }
        return ret;
    }

    private static String connect() {
        return "";
    }
}
