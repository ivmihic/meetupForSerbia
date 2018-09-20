
public class City {
    private String zip;
    private String country;
    private String localized_country_name;
    private double  distance;
    private String city;
    private double lon;
    private int ranking;
    private int id;
    private int member_count;
    private double lat;

    public City(String zip, String country, String localized_country_name, double distance, String city, double lon,
                int ranking, int id, int member_count, double lat) {
        this.zip = zip;
        this.country = country;
        this.localized_country_name = localized_country_name;
        this.distance = distance;
        this.city = city;
        this.lon = lon;
        this.ranking = ranking;
        this.id = id;
        this.member_count = member_count;
        this.lat = lat;
    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getLocalized_country_name() {
        return localized_country_name;
    }

    public double getDistance() {
        return distance;
    }

    public String getCity() {
        return city;
    }

    public double getLon() {
        return lon;
    }

    public int getRanking() {
        return ranking;
    }

    public int getId() {
        return id;
    }

    public int getMember_count() {
        return member_count;
    }

    public double getLat() {
        return lat;
    }
}
