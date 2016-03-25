package sol.workers.calendar.presenter.factory;

/**
 * Cell Renderer for workers list names on Saturday
 * 
 * @author safeki
 *
 */
public class WorkerSaturdayPostCell extends WorkerDailyPostCell {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getColumnIndex() {
		return 6;
	}

}
