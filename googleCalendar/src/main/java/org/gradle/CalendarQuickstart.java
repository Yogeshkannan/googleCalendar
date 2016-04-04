package org.gradle;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class CalendarQuickstart implements ICalendarQuickstart {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Calendar API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/calendar-java-quickstart.json");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;
    
    

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/calendar-java-quickstart.json
     */
    private static final List<String> SCOPES =
        Arrays.asList(CalendarScopes.CALENDAR);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            CalendarQuickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Calendar client service.
     * @return an authorized Calendar client service
     * @throws IOException
     */
    public static com.google.api.services.calendar.Calendar
        getCalendarService() throws IOException {
        Credential credential = authorize();
        return new com.google.api.services.calendar.Calendar.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
   

    
    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        // Note: Do not confuse this class with the
        //   com.google.api.services.calendar.model.Calendar class.
    	/*com.google.api.services.calendar.Calendar service =
                getCalendarService();*/
        Calendar ser=CalendarQuickstart.service();
        CalendarQuickstart calen=new CalendarQuickstart();
        
          calen.create(ser);
           
       // calen.delete(ser);
       // calen.update(ser);
       // calen.get(ser);
          calen.list(ser);
        
       
    
   
        // List the next 10 events from the primary calendar.
       /* DateTime now = new DateTime(System.currentTimeMillis());
        Events events = service.events().list("primary")
            .setMaxResults(10)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute();
        List<Event> items = events.getItems();
        if (items.size() == 0) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }*/
    }
    
    public static com.google.api.services.calendar.Calendar service() throws IOException{
    	com.google.api.services.calendar.Calendar service =
                getCalendarService();
    	return service;
    }

	@Override
	public void create(com.google.api.services.calendar.Calendar service) throws IOException {
		//com.google.api.services.calendar.Calendar service = getCalendarService();
		
		 Event event = new Event()
	        .setSummary("Google I/O 2015")
	        .setLocation("800 Howard St., San Francisco, CA 94103")
	        .setDescription("A chance to hear more about Google's developer products.");

	    DateTime startDateTime = new DateTime("2016-04-04T09:00:00-07:00");
	    EventDateTime start = new EventDateTime()
	        .setDateTime(startDateTime)
	        .setTimeZone("America/Los_Angeles");
	    event.setStart(start);

	    DateTime endDateTime = new DateTime("2016-04-04T17:00:00-07:00");
	    EventDateTime end = new EventDateTime()
	        .setDateTime(endDateTime)
	        .setTimeZone("America/Los_Angeles");
	    event.setEnd(end);

	    String[] recurrence = new String[] {"RRULE:FREQ=DAILY;COUNT=2"};
	    event.setRecurrence(Arrays.asList(recurrence));

	    EventAttendee[] attendees = new EventAttendee[] {
	        new EventAttendee().setEmail("ayanyogesh@gmail.com"),
	        new EventAttendee().setEmail("ramkrish3070@gmail.com"),
	    };
	    event.setAttendees(Arrays.asList(attendees));

	    EventReminder[] reminderOverrides = new EventReminder[] {
	        new EventReminder().setMethod("email").setMinutes(24 * 60),
	        new EventReminder().setMethod("popup").setMinutes(10),
	    };
	    Event.Reminders reminders = new Event.Reminders()
	        .setUseDefault(false)
	        .setOverrides(Arrays.asList(reminderOverrides));
	    event.setReminders(reminders);

	    String calendarId = "primary";
	    event = service.events().insert(calendarId, event).execute();
	    System.out.printf("Event created: %s\n", event.getHtmlLink());
	    System.out.println(service.events().list("primary").execute()); 
	    System.out.println(event.getSummary());
	   
		
	}

	@Override
	public void delete(com.google.api.services.calendar.Calendar service) throws IOException {
		// System.out.println(service.events().list("primary").execute()); 
		
	
		
		 service.events().delete("primary", "72nf1bogunjvc4hvlb3uugc5ts").execute();
		 System.out.println("successfully deleted");
		 System.out.println(service.events().list("primary").execute());
		 

	}

	@Override
	public void update(com.google.api.services.calendar.Calendar service) throws IOException {
	 
		// Retrieve the event from the API
		Event event = service.events().get("primary", "km13sf8dql0hiok91cr8p87mhg").execute();

		// Make a change
		event.setSummary("Hello 10d friends");

		// Update the event
		Event updatedEvent = service.events().update("primary", event.getId(), event).execute();

		System.out.println(updatedEvent.getUpdated());
		
	}

	@Override
	public void get(com.google.api.services.calendar.Calendar service) throws IOException {
		// Retrieve an event
		
		Event event = service.events().get("primary", "eventId").execute();

		System.out.println(event.getSummary());
		
	}

	@Override
	public void list(com.google.api.services.calendar.Calendar service) throws IOException {
		// Iterate over the events in the specified calendar
				String pageToken = null;
				do {
				  Events events = service.events().list("primary").setPageToken(pageToken).execute();
				  List<Event> items = events.getItems();
				  for (Event event : items) {
				    System.out.println(event.getSummary()+"--"+event.getId());
				  }
				  pageToken = events.getNextPageToken();
				} while (pageToken != null);
				
		
	}
	
	

}