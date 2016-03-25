package sol.workers.calendar.presenter.factory;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.presenter.listener.WorkersCalendarListener;

public class DeleteWorkerCell extends TableCell<WorkerModel, WorkerModel> {

	private WorkersCalendarListener listener;

	public DeleteWorkerCell(WorkersCalendarListener listener) {
		this.listener = listener;
	}

	@Override
	protected void updateItem(WorkerModel item, boolean empty) {
		super.updateItem(item, empty);
		
		Button button = new Button("Supprimer");
		button.setOnAction(event -> listener.deleteWorker(item));
		setGraphic(button);
	}
	
}
