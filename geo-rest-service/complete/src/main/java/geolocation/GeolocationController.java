package geolocation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/onEarth/geo")
public class GeolocationController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private final String apiKey="AIzaSyCIsvTYVzmUd0V3vW_Z_uokTwxkomf1FKw";

    @RequestMapping(value = "/result/json",produces ={"application/json"},method = RequestMethod.GET )
    @ResponseBody
    public String json(@RequestParam(value="address", defaultValue="Avignon") String address)
    {
        String content="";
        try {
            address=URLEncoder.encode(address,"UTF-8");
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&key="+apiKey);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
            {
                content=content+inputLine+"\n";
                System.out.println(inputLine);
            }
            in.close();
        }catch (IOException e)
        {
            e.printStackTrace();
            content="Something wrong...";
        }
        return content;
    }

    @RequestMapping(value = "/result/html",produces ={"text/html"},method = RequestMethod.GET)
    @ResponseBody
    public String html(@RequestParam(value="address", defaultValue="Avignon") String address)
    {
        String content="";
        try {
            URL url = new URL("http://localhost:8080/geoTest_1.html");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String inputLine;
            content+="<script>var param='"+address+"';</script>\n";
            while ((inputLine = in.readLine()) != null)
            {
                content=content+inputLine+"\n";
                System.out.println(inputLine);
            }
            in.close();
        }catch (IOException e)
        {
            e.printStackTrace();
            content="Something wrong...";
        }

        String jsonContent = json(address);
        Geolocation geo =  new Geolocation(counter.incrementAndGet(), String.format(jsonContent));
        return content;
    }

    @RequestMapping("/")
    @ResponseBody
    public Geolocation greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Geolocation(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
