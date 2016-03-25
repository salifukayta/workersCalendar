package sol.workers.calendar.presenter.factory;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;
import sol.workers.calendar.business.beans.WorkerModel;

/**
 * Cell renderer for fixedRestDay dropdown list
 * 
 * @author safeki
 *
 */
public class RestDayCell extends TableCell<WorkerModel, WorkerModel> {

	private Map<Integer, String> dates = new HashMap<>();
	
	public RestDayCell() {
		super();
		dates.put(0, "A déterminer");
		dates.put(1, "Lundi");
		dates.put(2, "Mardi");
		dates.put(3, "Mercredi");
		dates.put(4, "Jeudi");
		dates.put(5, "Vendredi");
		dates.put(6, "Samedi");
		dates.put(7, "Dimanche");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void updateItem(WorkerModel workerModel, boolean empty) {
		super.updateItem(workerModel, empty);
		if (workerModel != null) {
			ComboBox<Integer> restDayCombobox = new ComboBox<>();
			restDayCombobox.setItems(FXCollections.observableArrayList(dates.keySet()));

			if (workerModel.getRestDay() >= -1 && workerModel.getRestDay() <= 6) {
				restDayCombobox.getSelectionModel().select(workerModel.getRestDay() + 1);
			}
			
			restDayCombobox.setConverter(new StringConverter<Integer>() {
				
				@Override
				public String toString(Integer dateIndex) {
					return dates.get(dateIndex);
				}
				
				@Override
				public Integer fromString(String newDate) {
					for (int i = 0; i < dates.values().size(); i++) {
						if (newDate.equals(dates.get(i))) {
							return i;
						}
					}
					return 0;
				}
			});
			
			restDayCombobox.setOnAction(event -> workerModel.setRestDay(restDayCombobox.getSelectionModel().getSelectedItem() - 1));
			setGraphic(restDayCombobox);
		}
	}
}