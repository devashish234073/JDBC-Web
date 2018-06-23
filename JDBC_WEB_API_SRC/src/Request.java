import java.util.HashMap;

public class Request {

    private String req = "";
    private String method = "";
    private String path = "";
    private String host = "";
    private String action = "";
    private HashMap<String,String> attributes = new HashMap<String,String>();

    protected Request(String req) {
        this.req = req;
        parse();
    }

    protected boolean isNull() {
        return req==null;
    }

    protected String getMethod() {
        return method;
    }

    protected String getPath() {
        return path;
    }

    protected String getHost() {
        return host;
    }

    protected String getAttribute(String key) {
        return attributes.get(key);
    }

    protected String getAction() {
        return action;
    }

    private static String fixChars(String inpt) {
        inpt = inpt.replace("%40","@");
        return inpt;
    }

    private static HashMap<String,String> convertArgsToHash(String args) {
        HashMap<String,String> ret = null;
        String[] arr = args.split("&");
        if(arr.length>0) {
            ret = new HashMap<String,String>();
            for(int i=0;i<arr.length;i++) {
                String[] arrI = arr[i].split("=");
                if(arrI.length==2) {
                    ret.put(arrI[0], fixChars(arrI[1]));
                }
            }
        }
        return ret;
    }

    protected void parse() {
        if(!isNull()){
            String[] arr = req.split("\n");
            if(arr.length>0) {
                String[] line0 = arr[0].split(" ");
                if(line0.length==3) {
                    method = line0[0];
                    path   = line0[1];
                }
            }
            if(arr.length>1) {
                for(int i=1;i<arr.length;i++) {
                    if(arr[i].contains("Host")) {
                        host = arr[i].replace("Host:","").replace("Host :","").trim();
                        break;
                    }
                }
            }
            if(!path.equals("")) {
                String[] raw = path.split("[?]");
                if(raw.length == 2) {
                    action     = raw[0];
                    attributes = convertArgsToHash(raw[1]);
                }
            }
        }
    }
}
