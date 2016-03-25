package sol.workers.calendar.presenter.factory;

/**
 * Cell Renderer for workers list names on Monday
 * 
 * @author safeki
 *
 */
public class WorkerMondayPostCell extends WorkerDailyPostCell {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getColumnIndex() {
		return 1;
	}

}
