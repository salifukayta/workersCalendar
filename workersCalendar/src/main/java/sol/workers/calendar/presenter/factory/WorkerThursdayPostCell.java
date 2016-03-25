package sol.workers.calendar.presenter.factory;

/**
 * Cell Renderer for workers list names on Thursday
 * 
 * @author safeki
 *
 */
public class WorkerThursdayPostCell extends WorkerDailyPostCell {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int getColumnIndex() {
		return 4;
	}

}
