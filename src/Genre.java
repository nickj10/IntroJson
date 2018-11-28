public class Genre {
    private int id;
    private String name;
    private int ranking;
    private String description;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRanking() {
        return ranking;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ranking=" + ranking +
                ", description='" + description + '\'' +
                '}';
    }
}
