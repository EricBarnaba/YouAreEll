import com.fasterxml.jackson.databind.ObjectMapper;

public class Mapper {
    public static ObjectMapper mapper;
    private static Mapper ourInstance = new Mapper();

    public static Mapper getInstance() {
        return ourInstance;
    }

    private Mapper() {
        mapper = new ObjectMapper();
    }
}
