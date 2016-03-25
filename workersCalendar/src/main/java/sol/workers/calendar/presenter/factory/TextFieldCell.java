package sol.workers.calendar.presenter.factory;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Cell;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.Callback;
import javafx.util.StringConverter;
import sol.workers.calendar.business.utlis.CellUtils;

public class TextFieldCell<S, T> extends TableCell<S, T> {

	private static String DOUBLE_CLIC_STR = "Double clic pour saisir ";

	public static <S, T> Callback<TableColumn<S, T>, TableCell<S, T>> forTableColumn(String promptText,
			final StringConverter<T> converter) {
		return list -> new TextFieldCell<S, T>(promptText, converter);
	}

	private TextField textField;
	private String promptText;

	public TextFieldCell(String promptText, StringConverter<T> converter) {
		this.getStyleClass().add("text-field-table-cell");
		this.promptText = promptText;
		setConverter(converter);
	}

	private ObjectProperty<StringConverter<T>> converter = new SimpleObjectProperty<StringConverter<T>>(this, "converter");

	/**
	 * The {@link StringConverter} property.
	 */
	public final ObjectProperty<StringConverter<T>> converterProperty() {
		return converter;
	}

	/**
	 * Sets the {@link StringConverter} to be used in this cell.
	 */
	public final void setConverter(StringConverter<T> value) {
		converterProperty().set(value);
	}

	/**
	 * Returns the {@link StringConverter} used in this cell.
	 */
	public final StringConverter<T> getConverter() {
		return converterProperty().get();
	}

	/** {@inheritDoc} */
	@Override
	public void startEdit() {
		if (!isEditable() || !getTableView().isEditable() || !getTableColumn().isEditable()) {
			return;
		}
		super.startEdit();

		if (isEditing()) {
			if (textField == null) {
				textField = new TextField(getItemText(this, getConverter()));
				textField.setOnAction(event -> {
		            if (getConverter() == null) {
		                throw new IllegalStateException(
		                        "Attempting to convert text input into Object, but provided "
		                                + "StringConverter is null. Be sure to set a StringConverter "
		                                + "in your cell factory.");
		            }
		            commitEdit(getConverter().fromString(textField.getText()));
		            event.consume();
		        });
				textField.setPromptText(promptText.substring(DOUBLE_CLIC_STR.length()));
				textField.setOnKeyPressed(t -> {
		            if (t.getCode() == KeyCode.ESCAPE) {
		            	super.cancelEdit();
		        		CellUtils.cancelEdit(this, getConverter(), null);
		            }
		        });
			}

			CellUtils.startEdit(this, getConverter(), null, null, textField);

			if (textField.getText().equals(promptText)) {
				textField.setText("");
			}
			Platform.runLater(() -> textField.requestFocus());
		}
	}

	/** Do commit */
	@Override
	public void cancelEdit() {
		getTableView().edit(getTableRow().getIndex(), getTableColumn());
		commitEdit(getConverter().fromString(textField.getText()));
	}

	/** {@inheritDoc} */
	@Override
	public void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
		CellUtils.updateItem(this, getConverter(), null, null, textField);
	}
	 
    private static <T> String getItemText(Cell<T> cell, StringConverter<T> converter) {
        return converter == null ?
            cell.getItem() == null ? "" : cell.getItem().toString() :
            converter.toString(cell.getItem());
    }
}
