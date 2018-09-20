# Meetup Events in Serbia

A simple integration with meetup.com API
with the purpose of listing open events for the chosen city in Serbia.

## Program assumptions and limitations
1. For the sake of simplicity,
Event class does not contain all the values returned by the response.
Only couple of representative ones were chosen.
2. API key is a dummy value.
Please replace INSERTYOUROWN with your own API key.

### Prerequisites


You will need to add GSON library to the project.
The library can be found on the following link: https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.5
In IntelliJ the library can be imported without downloading by executing the following steps:
```File -> Project Structure -> Libraries -> click on "+" button -> select "from maven" and search for this lib```

