package sol.workers.calendar.presenter.factory;

/**
 * Cell Renderer for workers list names on Tuesday
 * 
 * @author safeki
 *
 */
public class WorkerTuesdayPostCell extends WorkerDailyPostCell {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getColumnIndex() {
		return 2;
	}

}
