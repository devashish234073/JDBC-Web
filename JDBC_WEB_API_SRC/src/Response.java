public class Response {

    private String resp = "";

    protected Response(String resp,Request req) {
        String header = "HTTP/1.1 200 OK\r\n";
        header+="Host: "+req.getHost();
        if(resp.length()>0) {
            if (resp.charAt(0) == '{') {
                header += "Content-Type: application/json\r\n";
            } else {
                header += "Content-Type: text/html\r\n";
            }
        }
        header+="Content-Length: "+resp.length()+"\r\n";
        header+="\r\n";
        this.resp = header+resp;
    }

    public String toString() {
        return resp;
    }
}
