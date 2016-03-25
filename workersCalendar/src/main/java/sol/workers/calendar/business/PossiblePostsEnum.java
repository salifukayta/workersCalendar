package sol.workers.calendar.business;

import java.util.ArrayList;
import java.util.List;

/**
 * An enum for all possible workers posts
 * 
 * @author safeki
 *
 */
public enum PossiblePostsEnum {

	WORK_POST_1 ("Poste 1"),
	WORK_POST_2 ("Poste 2"),
	WORK_POST_3 ("Poste 3"),
	WASHING ("Lavage"),
	REST ("Jour de Repos"),
	VACATION ("Vacance");
	
	private String postLabel;

	PossiblePostsEnum(String postLabel) {
		this.postLabel = postLabel;
	}

	/**
	 * Return the post of working
	 * 
	 * @return
	 */
	public static List<PossiblePostsEnum> getWroksValue() {
		List<PossiblePostsEnum> getWroksValue = new ArrayList<>();
		for (PossiblePostsEnum possiblePostsEnum : values()) {
			if (!possiblePostsEnum.equals(REST) && !possiblePostsEnum.equals(VACATION)) {
				getWroksValue.add(possiblePostsEnum);
			}
		}
		return getWroksValue;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return postLabel;
	}

	/**
	 * Get the next post of the currentPost
	 * 
	 * @param currentPost
	 * @return
	 */
	public static PossiblePostsEnum getNextWorkPost(PossiblePostsEnum currentPost) {
		PossiblePostsEnum nextWorkPost = null;
		switch (currentPost) {
		case WORK_POST_1:
			nextWorkPost = WORK_POST_2;
			break;
		case WORK_POST_2:
			nextWorkPost = WORK_POST_3;
			break;
		case WORK_POST_3:
			nextWorkPost = WORK_POST_1;
			break;
		default:
			break;
		}
		return nextWorkPost;
	}
	
}
