package sol.workers.calendar.business.beans;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import sol.workers.calendar.business.PossiblePostsEnum;
import sol.workers.calendar.business.model.WorkerDTO;

@Entity
public class WorkerModel extends WorkerDTO {

	private List<PossiblePostsEnum> possiblesPosts;
	private Boolean[] vacations;

	private Boolean[] workerPlaced;
	private boolean rested;

	private boolean deleted;
	private PossiblePostsEnum currentPost = null;
	private int nbDaysWorkedInCurrentPost = 1;

	public WorkerModel() {
		setRestDay(-1);

		this.deleted = false;
		this.rested = false;
		workerPlaced = new Boolean[7];
		vacations = new Boolean[7];
		for (int i = 0; i < 7; i++) {
			vacations[i] = false;
			workerPlaced[i] = false;
		}
		possiblesPosts = new ArrayList<PossiblePostsEnum>();
		// Beware not to forget each worker has to have a rest day in the week
		possiblesPosts.add(PossiblePostsEnum.REST);
	}
	
	public WorkerModel(WorkerDTO workerDTO, List<PossiblePostsEnum> possiblesPosts, Boolean[] vacations) {

		setId(workerDTO.getId());
		setFirstName(workerDTO.getFirstName());
		setLastName(workerDTO.getLastName());
		setRestDay(workerDTO.getRestDay());

		this.deleted = false;
		this.rested = false;
		this.workerPlaced = vacations.clone();
		this.vacations = vacations.clone();
		this.possiblesPosts = possiblesPosts;
	}

	@Deprecated
	public WorkerModel(String[] workerRow) {
		super();

		setFirstName(workerRow[0].trim());
		setLastName(workerRow[1].trim());
		setRestDay(Integer.valueOf(workerRow[3].trim()));

		rested = false;
		workerPlaced = new Boolean[7];
		vacations = new Boolean[7];
		for (int i = 4; i < 11; i++) {
			vacations[i - 4] = Boolean.valueOf(workerRow[i].trim());
			workerPlaced[i - 4] = Boolean.valueOf(workerRow[i].trim());
		}

		possiblesPosts = new ArrayList<PossiblePostsEnum>();
		// Beware not to forget each worker has to have a rest day in the week
		possiblesPosts.add(PossiblePostsEnum.REST);
		for (int i = 11; i < workerRow.length; i++) {
			possiblesPosts.add(PossiblePostsEnum.valueOf(workerRow[i].trim()));
		}
	}
	
	public boolean hasRested() {
		return rested;
	}

	public void setRested(boolean rested) {
		this.rested = rested;
	}

	public int getNumberPossiblesPosts() {
		return possiblesPosts.size() - 1;
	}
	
	public List<PossiblePostsEnum> getPossiblesPosts() {
		return possiblesPosts;
	}

	public void setPossiblesPosts(List<PossiblePostsEnum> possiblesPosts) {
		this.possiblesPosts = possiblesPosts;
	}

	public void placeOn(int dayIndex) {
		workerPlaced[dayIndex] = true;
	}

	public boolean isPlacedOn(int dayIndex) {
		return workerPlaced[dayIndex];
	}

	public boolean isOnVacantionOn(int dayIndex) {
		return vacations[dayIndex];
	}

	public PossiblePostsEnum getPossiblePost() {
		if (hasOnlyOnePost()) {
			for (PossiblePostsEnum possiblePostsEnum : possiblesPosts) {
				if (!possiblePostsEnum.equals(PossiblePostsEnum.REST)) {
					return possiblePostsEnum;
				}
			}
		}
		return null;
	}

	public boolean hasOnlyOnePost() {
		return possiblesPosts.size() == 2;
	}

	public boolean isJoker () {
		return (possiblesPosts.size() - 1) == PossiblePostsEnum.getWroksValue().size();
	}
	
	public Boolean[] getVacations() {
		return vacations;
	}

	public void setVacations(Boolean[] vacations) {
		this.vacations = vacations;
	}

	/**
	 * Reset the worker to replace him on a post each for day
	 */
	public void setReset() {
		for (int i = 0; i < 7; i++) {
			workerPlaced[i] = vacations[i];
		}
		rested = false;
		nbDaysWorkedInCurrentPost = 1;
		currentPost = null;
	}

	public String getCompleteName() {
		return getFirstName() + " " + getLastName();
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isDeleted() {
		return deleted;
	}
		
	public PossiblePostsEnum getCurrentPost() {
		if (getFirstName().equals("Chokri")) {
//			System.out.println("ici");
		}
		if (nbDaysWorkedInCurrentPost == 2) {
			currentPost = PossiblePostsEnum.getNextWorkPost(currentPost);
			nbDaysWorkedInCurrentPost = 1;
		} else if (!PossiblePostsEnum.REST.equals(currentPost) && !PossiblePostsEnum.VACATION.equals(currentPost)) {
			nbDaysWorkedInCurrentPost++;
		}
		if (getFirstName().equals("Chokri")) {
//			System.out.println(currentPost);
		}
		return currentPost;
	}

	public void setCurrentPost(PossiblePostsEnum currentPost) {
		if (!isJoker() && !hasOnlyOnePost()) {
			if (this.currentPost == null) {
				this.currentPost = currentPost;
			}
		} else { 
			this.currentPost = currentPost;
		}
	}

	@Override
	public String toString() {
		return getFirstName() + ",  currentPost= " + currentPost + ", nbDaysWorkedInCurrentPost= " + nbDaysWorkedInCurrentPost;
	}
}
