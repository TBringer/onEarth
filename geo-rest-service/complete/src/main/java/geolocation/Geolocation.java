package geolocation;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.toIntExact;

public class Geolocation {

    private long id;
    private String content;
    private final String apiKey="AIzaSyCIsvTYVzmUd0V3vW_Z_uokTwxkomf1FKw";
    private Map<Integer,String> data =new HashMap<>();


    public Geolocation(long id, String content) {
        this.id = id;
        this.content = content;
        this.data.put(toIntExact(id),content);
    }


    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
