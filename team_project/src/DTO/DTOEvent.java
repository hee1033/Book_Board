package DTO;
import java.util.Date;

import lombok.Data;

@Data
public class DTOEvent {
	private int eventId;
	private String eventName;
	private Date eventStartDate;
	private Date eventEndDate;
}
