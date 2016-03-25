package sol.workers.calendar.presenter.listener;

import sol.workers.calendar.business.beans.WorkerModel;

/**
 * Listener interface implemented by presenter
 * 
 * @author safeki
 *
 */
public interface WorkersCalendarListener {

	/**
	 * Delete the worker
	 * 
	 * @param item
	 */
	void deleteWorker(WorkerModel workerModel);

}
