package sol.workers.calendar.presenter.factory;

import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;

/**
 * Default Cell Renderer
 * 
 * @author safeki
 *
 * @param <E>
 * @param <T>
 */
public class ListTableCell<E, T, S> extends TableCell<E, T> {

	private final int ROW_HEIGHT = 24;

	/**
	 * Prepare List View For Table Cell
	 * 
	 * @param listSize
	 * @param listView
	 * @return
	 */
	protected ListView<S> prepareListViewCell (int listSize) {
		ListView<S> listView = new ListView<S>();
		listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		listView.setPrefHeight(listSize * ROW_HEIGHT + (listSize < 3 ? 2 : 0));
		return listView;
	}
}
