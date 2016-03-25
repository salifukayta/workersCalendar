package sol.workers.calendar.presenter.factory;

/**
 * Cell Renderer for workers list names on Sunday
 * 
 * @author safeki
 *
 */
public class WorkerSundayPostCell extends WorkerDailyPostCell {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getColumnIndex() {
		return 7;
	}

}
