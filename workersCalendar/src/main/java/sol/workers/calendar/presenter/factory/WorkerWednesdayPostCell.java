package sol.workers.calendar.presenter.factory;

/**
 * Cell Renderer for workers list names on Wednesday
 * 
 * @author safeki
 *
 */
public class WorkerWednesdayPostCell extends WorkerDailyPostCell {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getColumnIndex() {
		return 3;
	}

}
