
public class Event {
    private String description;
    private String name;

    public Event(String name, String description) {
        this.description = description;
        this.name = name;
    }

    public String getDescription() {
        return !description.isEmpty() ? description : "The event doesn't have a description";
    }

    public String getName() {
        return !name.isEmpty() ? name : "The event doesn't have a name";
    }
}
