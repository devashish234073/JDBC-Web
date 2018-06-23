import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class FileParser {

    public static String parse(String path, HashMap<String,String> replacements){
        String ret = "";
        try {
            FileInputStream fis = new FileInputStream(path);
            int c;
            do {
                c=fis.read();
                if(c!=-1) {
                    ret+=(char)c;
                }
            } while(fis.available()>0);

            if(replacements!=null) {
                Iterator itr = replacements.keySet().iterator();
                while (itr.hasNext()) {
                    String key = (String) itr.next();
                    ret = ret.replace(key, replacements.get(key));
                }
            }
            ret = removeRemainingPlaceHolders(ret);
            return ret;
        } catch (FileNotFoundException fnfe) {
            System.out.println("File '"+path+"' not found. ["+fnfe+"]");
        } catch (IOException ioe) {
            System.out.println("Unable to read '"+path+"' ["+ioe+"]");
        }
        return ret;
    }

    private static HashSet<String> getAllPlaceholders(String txt) {
        HashSet<String> placeholders = new HashSet<String>();
        int indx = txt.indexOf("${");
        while(indx>-1) {
            int endIndx = indx;
            while(endIndx<txt.length() && txt.charAt(++endIndx)!='}');
            String toAdd = "";
            if(txt.charAt(endIndx)=='}'){
                toAdd = txt.substring(indx,endIndx+1);
                placeholders.add(toAdd);
            }
            txt=txt.substring(endIndx);
            if(!toAdd.equals("")) {
                txt = txt.replace(toAdd,"");
            }
            indx = txt.indexOf("${");
        }
        return placeholders;
    }

    private static String removeRemainingPlaceHolders(String txt) {
        txt = txt.replace("${}","");
        HashSet<String> placeholders = getAllPlaceholders(txt);
        Iterator itr = placeholders.iterator();
        while(itr.hasNext()) {
            txt = txt.replace((String)itr.next(),"");
        }
        return txt;
    }
}
