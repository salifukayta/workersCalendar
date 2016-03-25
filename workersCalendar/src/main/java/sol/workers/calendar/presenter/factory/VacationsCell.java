package sol.workers.calendar.presenter.factory;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.business.comparator.DateComparator;

/**
 * Cell renderer for vacation list
 * 
 * @author safeki
 *
 */
public class VacationsCell extends ListTableCell<WorkerModel, WorkerModel, String> {

	private Map<String, Integer> dates = new HashMap<>();

	public VacationsCell() {
		super();
		dates.put("Lundi", 0);
		dates.put("Mardi", 1);
		dates.put("Mercredi", 2);
		dates.put("Jeudi", 3);
		dates.put("Vendredi", 4);
		dates.put("Samedi", 5);
		dates.put("Dimanche", 6);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateItem(WorkerModel workerModel, boolean empty) {
		super.updateItem(workerModel, empty);
		
		if (workerModel != null) {
			ListView<String> prepareListViewCell = prepareListViewCell(dates.size());
			ObservableList<String> observableArrayList = FXCollections.observableArrayList(dates.keySet());
			observableArrayList.sort(new DateComparator());
			prepareListViewCell.setItems(observableArrayList);
			
			for (int i = 0; i < workerModel.getVacations().length; i++) {
				if (workerModel.getVacations()[i]) {
					prepareListViewCell.getSelectionModel().select(i);
				}
			}
			
			prepareListViewCell.setOnMouseClicked(event -> {
				ObservableList<String> selectedItems = prepareListViewCell.getSelectionModel().getSelectedItems();

				Boolean[] vacations = {false, false, false, false, false, false, false};
				for (String selectedItem : selectedItems) {
					if (dates.containsKey(selectedItem)) {
						vacations[dates.get(selectedItem)] = true;
					}
				}
				
				workerModel.setVacations(vacations);
			});
			
			setGraphic(prepareListViewCell);
		}
	}
}
