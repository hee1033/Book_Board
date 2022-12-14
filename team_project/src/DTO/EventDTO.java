package project.DTO;
import java.util.Date;

import lombok.Data;

@Data
public class EventDTO {
	private int eventId;
	private String eventName;
	private Date eventStartDate;
	private Date eventEndDate;
	private String eventContent;
	private int bookId;
	private String eventStart;
	private String eventEnd;
}
