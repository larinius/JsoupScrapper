public class Movie {

    private String title;
    private Float rating;
    private String url;
    private String thumb;

    public Movie(String title, String thumb, String url, Float rating) {
        this.title = title;
        this.thumb = thumb;
        this.url = url;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }
}
