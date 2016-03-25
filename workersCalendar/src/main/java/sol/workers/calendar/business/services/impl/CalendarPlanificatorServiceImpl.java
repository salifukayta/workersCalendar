package sol.workers.calendar.business.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import sol.workers.calendar.business.PossiblePostsEnum;
import sol.workers.calendar.business.beans.DayWorkModel;
import sol.workers.calendar.business.beans.PostModel;
import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.business.exceptions.FunctionalException;
import sol.workers.calendar.business.services.CalendarPlanificatorService;

/**
 * Service the generates the calendar for workers
 * 
 * @author safeki
 *
 */
@Service
public class CalendarPlanificatorServiceImpl implements CalendarPlanificatorService {

	/**
	 * {@inheritDoc}
	 * @throws FunctionalException 
	 */
	public Collection<PostModel> generateCalendar(List<WorkerModel> workersList) throws FunctionalException {

		resetWorkersState(workersList);
		
		Map<Integer, List<WorkerModel>> workersMap = workersList.stream().collect(Collectors.groupingBy(worker -> worker.getNumberPossiblesPosts()));
		
		Map<PossiblePostsEnum, PostModel> workersCalendarMap = new HashMap<>();
		Map<Integer, DayWorkModel> workersCalendarWeek = new HashMap<>();
		
		int i = 0;
		for (Entry<Integer, List<WorkerModel>> workersByPossiblePostSize : workersMap.entrySet()) {
			System.out.println("i= " + i + ", nbPost= " + workersByPossiblePostSize.getKey());
			i++;
			if (workersByPossiblePostSize.getKey().equals(1) || workersByPossiblePostSize.getKey().equals(PossiblePostsEnum.getWroksValue().size()) ) {
				setWorkersWithoutConstraint(workersCalendarWeek, workersByPossiblePostSize);
			} else {
				setWorkersWithConstraint(workersCalendarWeek, workersByPossiblePostSize);
			}
		}

		// Re arrenge workers to be printed in the user table interface
		for (DayWorkModel dayWorkModel : workersCalendarWeek.values()) {
			
			for (Entry<PossiblePostsEnum, List<WorkerModel>> filledPost : dayWorkModel.getPosts().entrySet()) {
				if (!PossiblePostsEnum.getWroksValue().contains(filledPost.getKey())) {
					continue;
				}
				if (!workersCalendarMap.containsKey(filledPost.getKey())) {
					workersCalendarMap.put(filledPost.getKey(), new PostModel(filledPost.getKey()));
				}
				workersCalendarMap.get(filledPost.getKey()).addDailyWorkers(filledPost.getValue());
			}
		}
		
		return workersCalendarMap.values();
	}

	/**
	 * Set workers post in the week calendar. Workers are set in a circular order of permutation.
	 * 
	 * @param workersCalendarWeek
	 * @param workersByPossiblePostSize
	 * @throws FunctionalException 
	 */
	private void setWorkersWithConstraint(Map<Integer, DayWorkModel> workersCalendarWeek,
			Entry<Integer, List<WorkerModel>> workersByPossiblePostSize) throws FunctionalException {

		for (WorkerModel worker : workersByPossiblePostSize.getValue()) {
			
			for (Integer indexDay = 0; indexDay < 7; indexDay++) {
				DayWorkModel workersCalendarDay;
				if (workersCalendarWeek.containsKey(indexDay)) {
					workersCalendarDay = workersCalendarWeek.get(indexDay);
				} else {
					workersCalendarDay = new DayWorkModel(indexDay);
				}
				workersCalendarDay.setWorker(worker, new ArrayList<>(workersCalendarWeek.values()));
			
				workersCalendarWeek.put(indexDay, workersCalendarDay);
			}
		}
		
	}

	/**
	 * Set workers post in the week calendar for workers with only one post and workers with all posts.
	 * 
	 * @param workersCalendarWeek
	 * @param workersByPossiblePostSize
	 * @throws FunctionalException
	 */
	private void setWorkersWithoutConstraint(Map<Integer, DayWorkModel> workersCalendarWeek,
			Entry<Integer, List<WorkerModel>> workersByPossiblePostSize) throws FunctionalException {
		for (Integer indexDay = 0; indexDay < 7; indexDay++) {
			DayWorkModel workersCalendarDay;
			if (workersCalendarWeek.containsKey(indexDay)) {
				workersCalendarDay = workersCalendarWeek.get(indexDay);
			} else {
				workersCalendarDay = new DayWorkModel(indexDay);
			}
			for (WorkerModel worker : workersByPossiblePostSize.getValue()) {
				workersCalendarDay.setWorker(worker, new ArrayList<>(workersCalendarWeek.values()));
			}
			
			workersCalendarWeek.put(indexDay, workersCalendarDay);
		}
	}

	/**
	 * Reset workers state to replace them on the post
	 * 
	 * @param workersList
	 */
	private void resetWorkersState(List<WorkerModel> workersList) {
		for (WorkerModel workerModel : workersList) {
			workerModel.setReset();
		}
	}
}
