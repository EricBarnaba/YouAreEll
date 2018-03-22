import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import java.io.IOException;

public class YouAreEll {



    YouAreEll() {
    }

    public static void main(String[] args) {

        YouAreEll urlhandler = new YouAreEll();
        Id stinkyPete = new Id("Stinky Pete", "StinkyPete");
        try {
            String json = Mapper.mapper.writeValueAsString(stinkyPete);
            urlhandler.MakeURLCall("/ids", "PUT", json );
        }
        catch(JsonProcessingException jpe){
            System.out.println(jpe.getMessage());
        }

//        System.out.println(urlhandler.get_ids());
//        System.out.println(urlhandler.get_messages());
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
                return Request.Put(url)
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
