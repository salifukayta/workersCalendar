package sol.workers.calendar.business.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import sol.workers.calendar.business.PossiblePostsEnum;
import sol.workers.calendar.business.exceptions.FunctionalException;

/**
 * The day model, containing the day index, and all workers affected to posts
 * 
 * @author safeki
 *
 */
public class DayWorkModel {

	private static final String MSG_CALENDAR_GENERATION_ERROR_MANDATORY_POST = "\n\tImpossible de générer le calendrier.\n\n\tTous les ouvriers doivent avoir au moin un poste.";

	// lundi == 0, mardi == 1, mercredi == 2, jeudi == 3, vendredi == 4, samedi == 5, dimanche == 6.
	private int dayIndex;
	
	private Map<PossiblePostsEnum, List<WorkerModel>> posts;
	
	public DayWorkModel(int dayIndex) {
		this.dayIndex = dayIndex;
		posts = new TreeMap<PossiblePostsEnum, List<WorkerModel>>(Collections.reverseOrder());
		for (PossiblePostsEnum possiblePostEnum : PossiblePostsEnum.values()) {
			posts.put(possiblePostEnum, new ArrayList<WorkerModel>());
		}
	}

	/**
	 * Set the worker in a post respecting the needed constraint
	 * 
	 * @param worker
	 * @param workersCalendarWeek 
	 * @throws FunctionalException 
	 */
	public void setWorker(WorkerModel worker, List<DayWorkModel> workersCalendarWeek) throws FunctionalException {
		if (!worker.isPlacedOn(dayIndex)) {
			// rest if not rested yet
			boolean workerPlaced = setAndCheckRestDay(worker, PossiblePostsEnum.REST);
			// all workers that have only one post
			if (worker.hasOnlyOnePost()) {
				if (!workerPlaced) {
					posts.get(worker.getPossiblePost()).add(worker);
					worker.placeOn(dayIndex);
				}
			} else if (!worker.isJoker()){
				if (workerPlaced == false) {
					PossiblePostsEnum todayPost = worker.getCurrentPost();
					if (todayPost != null) {
						workerPlaced = setAndCheckRestDay(worker, todayPost);
					}
				}
				if (workerPlaced == false) {
					// the post that is compatible with worker and has no workers yet
					for (Entry<PossiblePostsEnum, List<WorkerModel>> currentPost : posts.entrySet()) {
						if (postEmptyAndPossibleForWorker(worker, currentPost)) {
							workerPlaced = setAndCheckRestDay(worker, currentPost.getKey());
							break;
						}
					}
					
				}
				// all the others
				if (workerPlaced == false) {
					PossiblePostsEnum postToWorkerEnum = findWorkedPostByMinWorkersWithConstraint(worker);
					setAndCheckRestDay(worker, postToWorkerEnum);
				}
			} else {
				if (workerPlaced == false) {
					// the post that is compatible with worker and has no workers yet
					for (Entry<PossiblePostsEnum, List<WorkerModel>> currentPost : posts.entrySet()) {
						if (postEmptyAndPossibleForWorker(worker, currentPost)) {
							workerPlaced = setAndCheckRestDay(worker, currentPost.getKey());
							break;
						}
					}
					
				}
				// all the others
				if (workerPlaced == false) {
					PossiblePostsEnum postToWorkerEnum = findWorkedPostByMinWorkers(worker);
					setAndCheckRestDay(worker, postToWorkerEnum);
				}
			}
		} else if (worker.isOnVacantionOn(dayIndex)) {
			posts.get(PossiblePostsEnum.VACATION).add(worker);
			
		}
	}

	/**
	 * Get worker post for the indexDay in the workersCalendarWeek
	 * 
	 * @param workersCalendarWeek
	 * @param indexDay
	 * @param worker
	 * @return
	 */
//	private PossiblePostsEnum getWorkerPostForTheDay(List<DayWorkModel> workersCalendarWeek, int indexDay, WorkerModel worker) {
//		
//		PossiblePostsEnum yesterdayWorkerPost = null;
//		if (workersCalendarWeek.size() > indexDay ) {
//			for (Entry<PossiblePostsEnum, List<WorkerModel>> workersPost : workersCalendarWeek.get(indexDay).getPosts().entrySet()) {
//				boolean done = false;
//				for (WorkerModel workerModel : workersPost.getValue()) {
//					if (workerModel.getId().equals(worker.getId())) {
//						yesterdayWorkerPost = workersPost.getKey();
//						done = true;
//						break;
//					}
//				}
//				if (done) {
//					break;
//				}
//			}
//		}
//		return yesterdayWorkerPost;
//	}
	
	/**
	 * Check the rest day if postToWorkerEnum is a PossiblePostsEnum.REST or set the worker on postToWorkerEnum 
	 * 
	 * @param worker
	 * @param postToWorkerEnum
	 * @return
	 */
	private boolean setAndCheckRestDay(WorkerModel worker, PossiblePostsEnum postToWorkerEnum) {
		if (postToWorkerEnum.equals(PossiblePostsEnum.REST)) {
			if (!worker.hasRested()) {
				
				if (worker.hasFixedRestDay() && worker.getRestDay() == -1) {
					// Only one worker rest day for workers fixed rest day
					for (WorkerModel restedWorker : posts.get(PossiblePostsEnum.REST)) {
						if (restedWorker.hasFixedRestDay()) {
							return false;
						}
					}
					posts.get(postToWorkerEnum).add(worker);
					worker.setRested(true);
					worker.setRestDay(dayIndex);
				} else if (worker.hasFixedRestDay() && (worker.getRestDay() == dayIndex)) {
					posts.get(postToWorkerEnum).add(worker);
					worker.setRested(true);
					worker.setRestDay(dayIndex);
				} else {
					return false;
				}
				
			} else {
				return false;
			}
		} else {

			if (dayIndex == 0) {
				System.out.println(worker.getFirstName() + " " + postToWorkerEnum);
			}
			posts.get(postToWorkerEnum).add(worker);
			worker.setCurrentPost(postToWorkerEnum);
		}
		worker.placeOn(dayIndex);
		return true;
	}

	/**
	 * Return the working post with minimum workers that fits the worker with constraint.
	 * 
	 * @param worker
	 * @return
	 * @throws FunctionalException 
	 */
	private PossiblePostsEnum findWorkedPostByMinWorkersWithConstraint(WorkerModel worker) throws FunctionalException {
		PossiblePostsEnum postWithMinWorkers = null;
		int numberWorkerInPost = Integer.MAX_VALUE;
		boolean postMinWorkersHasConstrainedWorker = false;
		for (Entry<PossiblePostsEnum, List<WorkerModel>> currentPost : posts.entrySet()) {
			// No rest post
			if (currentPost.getKey().equals(PossiblePostsEnum.REST)) {
//				if (currentPost.getKey().equals(PossiblePostsEnum.REST) && worker.hasRested()) {
				continue;
			}
			// Post with minimum workers and has to be adapted to worker
			if (worker.getPossiblesPosts().contains(currentPost.getKey())) {
				if (currentPost.getValue().size() < numberWorkerInPost) {
					postWithMinWorkers = currentPost.getKey();
					numberWorkerInPost = currentPost.getValue().size();
					postMinWorkersHasConstrainedWorker = false;
					for (WorkerModel workerModel : currentPost.getValue()) {
						if (!workerModel.isJoker() && !workerModel.hasOnlyOnePost()) {
							postMinWorkersHasConstrainedWorker = true;
						}
					}
				} else if (currentPost.getValue().size() == numberWorkerInPost && postMinWorkersHasConstrainedWorker) {
					boolean postHasConstrainedWorker = false;
					for (WorkerModel workerModel : currentPost.getValue()) {
						if (!workerModel.isJoker() && !workerModel.hasOnlyOnePost()) {
							postHasConstrainedWorker = true;
						}
					}
					if (!postHasConstrainedWorker) {
						postWithMinWorkers = currentPost.getKey();
						numberWorkerInPost = currentPost.getValue().size();
					}
				}
			}
			
		}
		if (postWithMinWorkers == null) {
			throw new FunctionalException(MSG_CALENDAR_GENERATION_ERROR_MANDATORY_POST);
		}
		return postWithMinWorkers;
	}
	
	/**
	 * Return the working post with minimum workers that fits the worker.
	 * 
	 * @param worker
	 * @return
	 * @throws FunctionalException 
	 */
	private PossiblePostsEnum findWorkedPostByMinWorkers(WorkerModel worker) throws FunctionalException {
		PossiblePostsEnum postWithMinWorkers = null;
		int numberWorkerInPost = Integer.MAX_VALUE;
		for (Entry<PossiblePostsEnum, List<WorkerModel>> currentPost : posts.entrySet()) {
			// No rest post
			if (currentPost.getKey().equals(PossiblePostsEnum.REST)) {
//				if (currentPost.getKey().equals(PossiblePostsEnum.REST) && worker.hasRested()) {
				continue;
			}
			// Post with minimum workers and has to be adapted to worker 
			if ((currentPost.getValue().size() < numberWorkerInPost)
					&& worker.getPossiblesPosts().contains(currentPost.getKey())) {
				postWithMinWorkers = currentPost.getKey();
				numberWorkerInPost = currentPost.getValue().size();
			}
		}
		if (postWithMinWorkers == null) {
			throw new FunctionalException(MSG_CALENDAR_GENERATION_ERROR_MANDATORY_POST);
		}
		return postWithMinWorkers;
	}

	/**
	 * Return true if no workers are on the currentPost and the worker can be in the currentPost
	 * 
	 * @param worker
	 * @param currentPost
	 * @return
	 */
	private boolean postEmptyAndPossibleForWorker(WorkerModel worker, Entry<PossiblePostsEnum, List<WorkerModel>> currentPost) {
		return currentPost.getValue().isEmpty() && worker.getPossiblesPosts().contains(currentPost.getKey());
	}

	public Map<PossiblePostsEnum, List<WorkerModel>> getPosts() {
		return posts;
	}

	public int getDayIndex() {
		return dayIndex;
	}

}
