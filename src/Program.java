import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Program {

    private static final Logger LOGGER = Logger.getLogger(Program.class.getName());

    private static ArrayList<City> cityList;
    private static ArrayList<Event> eventList;

    public static void main(String[] args) {

        cityList = new ArrayList<>();
        eventList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        cityList = fetchCities();
        System.out.println("Choose a city from the list below by inputting its number to get the current events:");
        displayCities(cityList);
        int pickedRanking;
        try {
            pickedRanking = scanner.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Please give us a number.");
            return;
        }
        if (pickedRanking < 0 || pickedRanking >= cityList.size()) {
            System.out.println("The chosen city was not on the list.");
            return;
        }
        String chosenCity = cityList.get(pickedRanking).getCity();
        if (chosenCity.isEmpty()) {
            LOGGER.log(Level.SEVERE, "chosenCity is null");
            return;
        }
        eventList = fetchOpenEvents(chosenCity);
        if (eventList.size() == 0){
            System.out.println("We currently don't have any events in the chosen city.");
            return;
        }
        displayEvents(eventList);

    }

    private static String sendRequestAndGetResponse(String link) throws IOException {

        URL url = new URL(link);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();

    }

    private static ArrayList<City> fetchCities() {
        cityList = new ArrayList<>();
        String response = null;
        try {
            URI uri = new URI(
                    "https",
                    "api.meetup.com",
                    "/2/cities",
                    "&sign=true&key=INSERTYOUROWN&country=rs",
                    null);
            String request = uri.toASCIIString();
            response = sendRequestAndGetResponse(request);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(response);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonArray results = jsonObject.get("results").getAsJsonArray();
            for (int i = 0; i < results.size(); i++) {
                JsonObject city = results.get(i).getAsJsonObject();
                cityList.add(new City(city.get("zip").getAsString(), city.get("country").getAsString(),
                        city.get("localized_country_name").getAsString(), city.get("distance").getAsDouble(),
                        city.get("city").getAsString(), city.get("lon").getAsDouble(),
                        city.get("ranking").getAsInt(), city.get("id").getAsInt(), city.get("member_count").getAsInt(),
                        city.get("lat").getAsDouble()));
            }
        }
        return cityList;
    }

    private static ArrayList<Event> fetchOpenEvents(String chosenCity) {
        eventList = new ArrayList<>();
        String response = null;
        try {
            URI uri = new URI(
                    "https",
                    "api.meetup.com",
                    "/2/open_events",
                    "&sign=true&key=INSERTYOUROWN&country=rs&city=" + chosenCity,
                    null);
            String request = uri.toASCIIString();
            response = sendRequestAndGetResponse(request);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        JsonParser parser = new JsonParser();
        JsonElement jsonTree = parser.parse(response);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            JsonArray results = jsonObject.get("results").getAsJsonArray();
            for (int i = 0; i < results.size(); i++) {
                JsonObject event = results.get(i).getAsJsonObject();
                eventList.add(new Event(event.get("name").getAsString(), event.get("description").getAsString()));
            }
        }
        return eventList;
    }

    private static void displayCities(ArrayList<City> cityList) {
        for (int i = 0; i < cityList.size(); i++) {
            System.out.println(cityList.get(i).getRanking() + " " + cityList.get(i).getCity());
        }
    }

    private static void displayEvents(ArrayList<Event> eventList) {
        for (int i = 0; i < eventList.size(); i++) {
            System.out.println("\nEvent Name: " + eventList.get(i).getName() + "\nEvent Description: " + eventList.get(i).getDescription());
        }
    }

}
