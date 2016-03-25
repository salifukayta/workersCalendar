package sol.workers.calendar.business.beans;

import java.util.ArrayList;
import java.util.List;

import sol.workers.calendar.business.PossiblePostsEnum;
import sol.workers.calendar.business.model.PostDTO;

/**
 * Model for a post, many workers can be on a post, and these workers can change each day
 * 
 * @author safeki
 *
 */
public class PostModel extends PostDTO {

	private PossiblePostsEnum possiblePostsEnum;
	
	private List<List<WorkerModel>> workersOnPost;
	
	public PostModel(PossiblePostsEnum possiblePostsEnum) {
		workersOnPost = new ArrayList<>();
		this.possiblePostsEnum = possiblePostsEnum;
	}

	public PossiblePostsEnum getPossiblePostsEnum() {
		return possiblePostsEnum;
	}

	public List<List<WorkerModel>> getWorkersOnPost() {
		return workersOnPost;
	}

	public void addDailyWorkers(List<WorkerModel> workers) {
		workersOnPost.add(workers);
	}

	
}
