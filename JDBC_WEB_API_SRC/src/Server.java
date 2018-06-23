import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        final int PORT = getPort(args);
        System.out.println("Starting server on PORT: " + PORT);
        while(true) {
            try (ServerSocket server = new ServerSocket(PORT)) {
                Socket client = server.accept();
                InputStream is = client.getInputStream();
                OutputStream os = client.getOutputStream();

                Request req = new Request(readInput(is));
                Response resp = new Response("",req);
                if(req.getPath().equals("/")) {
                    resp = new Response(RequestProcessor.getHome(req),req);
                } else if(!req.getPath().equals("/favicon.ico")){
                    resp = RequestProcessor.processRequest(req);
                }
                os.write(resp.toString().getBytes());

                is.close();
                os.close();
                client.close();
            } catch (IOException ioe) {
                System.out.println("Server interrupted unexpectedly. Exiting...."+ioe);
                System.exit(1);
            }
        }
    }

    private static String readInput(InputStream is) {
        try {
            String ret="";
            int c = 0;
            do {
                c = is.read();
                if(c!=-1) {
                    ret+=(char)c;
                }
            } while(is.available()>0);
            return ret;
        } catch (IOException ioe) {
            System.out.println("Error while reading input stream! "+ioe);
        }
        return null;
    }

    private static int getPort(String[] args) {
        int PORT = 8888;
        if(args.length==0) {
            return PORT;
        }
        try {
            int p = Integer.parseInt(args[0]);
            PORT = p;
        } catch (NumberFormatException nfe) {
            System.out.println("Unable to interpret port: '"+args[0]+"' defaulted to PORT: "+PORT);
        }
        return PORT;
    }
}
