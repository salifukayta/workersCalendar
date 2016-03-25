package sol.workers.calendar.business.comparator;

import java.util.Comparator;

import sol.workers.calendar.business.beans.WorkerModel;

public class WorkerComparator implements Comparator<WorkerModel> {
	
	public int compare(WorkerModel w1, WorkerModel w2) {
		if (w1 == null) {
			return 1;
		}
		if (w2 == null) {
			return -1;
		}
		if (w1 == w2) {
			return 0;
		}
		
		return w1.getPossiblesPosts().size() - w2.getPossiblesPosts().size();
	}

}
