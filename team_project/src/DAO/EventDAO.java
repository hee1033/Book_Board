package DAO;
import java.util.Date;

import lombok.Data;

@Data
public class EventDAO {
	private int eventId;
	private String eventName;
	private Date eventStartDate;
	private Date eventEndDate;
}
