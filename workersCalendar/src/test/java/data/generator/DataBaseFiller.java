package data.generator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.business.exceptions.FunctionalException;
import sol.workers.calendar.business.repository.impl.WorkerFileRepositoryImpl;
import sol.workers.calendar.business.repository.impl.WorkerRepositoryImpl;

public class DataBaseFiller {

	protected static final String WORKERS_SOURCE = "src/main/resources/sol/workers/calendar/presenter/workers.txt";
	
	public static void main(String[] args) {
		
		WorkerFileRepositoryImpl workerFileRepository = new WorkerFileRepositoryImpl();

		WorkerRepositoryImpl workerRepository = new WorkerRepositoryImpl();
		
		try {
			List<WorkerModel> workerModels = workerFileRepository.readData(new FileInputStream(WORKERS_SOURCE));
			
			
			for (WorkerModel workerModel : workerModels) {
				try {
					workerRepository.addWorker(workerModel);
				} catch (FunctionalException e) {
					e.printStackTrace();
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
