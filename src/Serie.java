import java.util.Arrays;

public class Serie {
    private String title;
    private int duration;
    private int year;
    private Actor actor;
    private String thumbnail;
    private float rating;
    private long votes;
    private int[] genres;

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public int getYear() {
        return year;
    }

    public Actor getActor() {
        return actor;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public float getRating() {
        return rating;
    }

    public long getVotes() {
        return votes;
    }

    public int[] getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "Serie{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                ", year=" + year +
                ", actor=" + actor +
                ", thumbnail='" + thumbnail + '\'' +
                ", rating=" + rating +
                ", votes=" + votes +
                ", genres=" + Arrays.toString(genres) +
                '}';
    }

    public void printAllDetails () {
        System.out.println("Title: " + this.getTitle());
        System.out.println("Duration: " + this.getDuration());
        System.out.println("Year: " + this.getYear());
        System.out.println("Actor: " + this.getActor().getName());
        System.out.println("Thumbnail: " + this.getThumbnail());
        System.out.println("Rating: " + this.getRating());
        System.out.println("Votes: " + this.getVotes());
        System.out.println("Genres: " + this.getGenres());
    }
}
