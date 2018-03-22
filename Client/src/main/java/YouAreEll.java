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

        YouAreEll urlhandler = new YouAreEll();
//        Id stinkyPete = new Id("Stinky Pete", "StinkyPete");
//        Message testMessage = new Message("EricBarnaba","JoeHendricks415", "Chicken on a BISCUIT");
//        try {
//            String json = mapper.writeValueAsString(testMessage);
//            urlhandler.MakeURLCall("/ids/EricBarnaba/messages", "POST", json );
//            //System.out.println(json);
//        }
//        catch(JsonProcessingException jpe){
//            System.out.println(jpe.getMessage());
//        }

        System.out.println(urlhandler.get_ids());
        System.out.println(urlhandler.get_messages());
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
                return Request.Get(url).execute().returnContent().asString();
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }

        }
        else if (method.equals("POST")){
            try {
                return Request.Post(url)
                        .bodyString(jpayload, ContentType.APPLICATION_JSON)
                        .execute().returnContent().asString();
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }

        else if (method.equals("PUT")){
            try {
                System.out.println(jpayload);
                Request.Put(url)
                        .useExpectContinue().bodyString(jpayload, ContentType.APPLICATION_JSON)
                        .execute().returnContent().asString();
            }
            catch(IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }
        return "nada";
    }
}
