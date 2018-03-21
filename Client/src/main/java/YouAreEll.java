import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class YouAreEll {



    YouAreEll() {

    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        YouAreEll urlhandler = new YouAreEll();
//        Id ericB = new Id("EricB", "EricBarnaba");
//        try {
//            String json = mapper.writeValueAsString(ericB);
//            urlhandler.MakeURLCall("/ids", "POST", json );
//            //System.out.println(json);
//        }
//        catch(JsonProcessingException jpe){
//            System.out.println(jpe.getMessage());
//        }

        System.out.println(urlhandler.MakeURLCall("/ids", "GET", ""));
        System.out.println(urlhandler.MakeURLCall("/messages", "GET", ""));
    }

    public String get_ids() {
        return MakeURLCall("/ids", "GET", "");
    }

    public String get_messages() {
        return MakeURLCall("/messages", "GET", "");
    }

    public String MakeURLCall(String mainurl, String method, String jpayload) {
        String url = "http://zipcode.rocks:8085" + mainurl;

        if(method.equals("GET")){
            try{
                System.out.println(Request.Get(url)
                        .execute().returnContent());
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }

        }
        else if (method.equals("POST")){
            try {
                //StringEntity json = new StringEntity(jpayload);
                System.out.println(jpayload);
                Request.Post(url)
                        .useExpectContinue().bodyString(jpayload, ContentType.DEFAULT_TEXT)
                        .execute().returnContent();
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }


        }
        else if (method.equals("PUT")){

        }

        return "nada";
    }
}
