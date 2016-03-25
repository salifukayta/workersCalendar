package sol.workers.calendar.presenter.factory;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import sol.workers.calendar.business.PossiblePostsEnum;
import sol.workers.calendar.business.beans.WorkerModel;

/**
 * Cell renderer for possible post list
 * 
 * @author safeki
 *
 */
public class PossiblePostsCell extends ListTableCell<WorkerModel, WorkerModel, PossiblePostsEnum> {

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateItem(WorkerModel WorkerModel, boolean empty) {
		super.updateItem(WorkerModel, empty);
		if (WorkerModel != null) {
			ListView<PossiblePostsEnum> prepareListViewCell = prepareListViewCell(WorkerModel.getPossiblesPosts().size());
			List<PossiblePostsEnum> possiblePostsEnumValues = PossiblePostsEnum.getWroksValue();
			prepareListViewCell.setItems(FXCollections.observableArrayList(possiblePostsEnumValues));
			for (int i = 0; i < possiblePostsEnumValues.size(); i++) {
				if (WorkerModel.getPossiblesPosts().contains(possiblePostsEnumValues.get(i))) {
					prepareListViewCell.getSelectionModel().select(i);
				}
			}

			prepareListViewCell.setOnMouseClicked(
					//FIXME ecrase la valeur rest post ?
					event -> WorkerModel.setPossiblesPosts(prepareListViewCell.getSelectionModel().getSelectedItems()));

			setGraphic(prepareListViewCell);
		}
	}
}
