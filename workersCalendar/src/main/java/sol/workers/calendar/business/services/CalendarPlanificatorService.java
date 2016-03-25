package sol.workers.calendar.business.services;

import java.util.Collection;
import java.util.List;

import sol.workers.calendar.business.beans.PostModel;
import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.business.exceptions.FunctionalException;

/**
 * Service the generates the calendar for workers
 * 
 * @author safeki
 *
 */
public interface CalendarPlanificatorService {

	/**
	 * Generate Workers Calendar
	 * 
	 * @param workersList
	 * @throws FunctionalException 
	 */
	Collection<PostModel> generateCalendar(List<WorkerModel> workersList) throws FunctionalException;

}
