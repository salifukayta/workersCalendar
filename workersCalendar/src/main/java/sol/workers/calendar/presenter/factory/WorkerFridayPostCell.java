package sol.workers.calendar.presenter.factory;

/**
 * Cell Renderer for workers list names on Friday
 * 
 * @author safeki
 *
 */
public class WorkerFridayPostCell extends WorkerDailyPostCell {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getColumnIndex() {
		return 5;
	}

}
