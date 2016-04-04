package org.gradle;

import java.io.IOException;

public interface ICalendarQuickstart {

		public void create(com.google.api.services.calendar.Calendar service)throws IOException;
		public void delete(com.google.api.services.calendar.Calendar service)throws IOException;
		public void update(com.google.api.services.calendar.Calendar service)throws IOException;
		public void get(com.google.api.services.calendar.Calendar service)throws IOException;
		public void list(com.google.api.services.calendar.Calendar service)throws IOException;
		
}
