package sol.workers.calendar.presenter.factory;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import sol.workers.calendar.business.beans.PostModel;
import sol.workers.calendar.business.beans.WorkerModel;

/**
 * Cell Renderer for workers list names
 * 
 * @author safeki
 *
 */
public abstract class WorkerDailyPostCell extends TableCell<PostModel, PostModel> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateItem(PostModel item, boolean empty) {
		super.updateItem(item, empty);

		if (item != null) {
			String workers = "";
			for (WorkerModel workerModel : item.getWorkersOnPost().get(getColumnIndex() - 1)) {
				workers = workers + workerModel.getCompleteName() + "\n";
			}

			if (workers.length() > 2) {
				setGraphic(new Label(workers.substring(0, workers.length() - 1)));
			} else {
				setGraphic(new Label(workers));
			}
		}
	}

	/**
	 * Return column index
	 * 
	 * @return
	 */
	protected abstract int getColumnIndex();
}
