package sol.workers.calendar.business.repository;

import java.io.InputStream;
import java.util.List;

import sol.workers.calendar.business.beans.WorkerModel;

/**
 * Repository worker interface
 * 
 * @author safeki
 */
public interface WorkerFileRepository {

	List<WorkerModel> readData(InputStream inputStream);

	void updateWorkersRestDay(WorkerModel[] workersFixedRestDay, String workersSourceFile);
	
}
