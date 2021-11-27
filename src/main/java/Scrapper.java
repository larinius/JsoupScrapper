import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ptsmods.mysqlw.Database;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Scrapper {

    public static String parseUrl = "https://www.imdb.com/chart/top/";
    public static String sqlitePath = "src/main/resources/imdb.db";
    public static String jsonPath = "src/main/resources/movies.json";

    public static void main(String[] args) throws Exception {

        Database db = Database.connect(new File(sqlitePath));
        Document document = Jsoup.connect(parseUrl).get();
        ArrayList<Movie> movies = new ArrayList<>();

        // Scrap data from page and store to array
        for (Element row: document.select(".lister-list tr")) {
            String thumb = row.select(".posterColumn img[src]").attr("src");
            String url = Objects.requireNonNull(row.select(".posterColumn a").first()).absUrl("href").split("\\?")[0];
            String title = row.select(".titleColumn a").text();
            Float rating = Float.parseFloat(row.select(".imdbRating").text());
            movies.add(new Movie(title, thumb, url, rating));
        }

        // Write data to json file
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File(jsonPath), movies);

        // Write data to sqlite db
        for (var m: movies) {
            db.insert("movies", new String[] {"title", "rating", "url", "thumb"},
                new Object[]{m.getTitle(), m.getRating(), m.getUrl(), m.getThumb()});
        }
    }
}
