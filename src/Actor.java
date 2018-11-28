public class Actor {
    private String name;
    private int born;
    private String country;
    private boolean married;

    public boolean isMarried() {
        return married;
    }

    public String getName() {
        return name;
    }

    public int getBorn() {
        return born;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "name='" + name + '\'' +
                ", born=" + born +
                ", country='" + country + '\'' +
                ", married=" + married +
                '}';
    }

}
