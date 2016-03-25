package sol.workers.calendar.business.repository;

import java.util.List;

import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.business.exceptions.FunctionalException;
import sol.workers.calendar.business.model.WorkerDTO;

/**
 * Repository worker interface
 * 
 * @author safeki
 */
public interface WorkerRepository {

	/**
	 * Get all workers
	 * 
	 * @return
	 */
	List<WorkerModel> getWorkers();

	/**
	 * Update the worker, his posts and vacations
	 * 
	 * @param workerModel
	 * @throws FunctionalException 
	 */
	void updateWorker(WorkerModel workerModel) throws FunctionalException;

	/**
	 * Delete the worker, his posts and vacations
	 * 
	 * @param workerModel
	 */
	void deleteWorker(WorkerModel workerModel);

	/**
	 * Add the worker, his posts and vacations
	 * 
	 * @param workerModel
	 * @throws FunctionalException 
	 */
	void addWorker(WorkerModel workerModel) throws FunctionalException;

	/**
	 * Get a worker by id
	 * 
	 * @param workerId
	 * @return
	 */
	WorkerDTO getWorker(Integer workerId);

	/**
	 * Edit the worker (add, edit or delete)
	 * 
	 * @param workerModel
	 * @throws FunctionalException 
	 */
	void editWorker(WorkerModel workerModel) throws FunctionalException;

	/**
	 * Delete all workers
	 */
	void deleteAllWorkers();

}
