package sol.workers.calendar.presenter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import sol.workers.calendar.business.beans.PostModel;
import sol.workers.calendar.business.beans.WorkerModel;
import sol.workers.calendar.business.exceptions.FunctionalException;
import sol.workers.calendar.business.repository.WorkerFileRepository;
import sol.workers.calendar.business.repository.WorkerRepository;
import sol.workers.calendar.business.services.CalendarPlanificatorService;
import sol.workers.calendar.config.ScreensConfig;
import sol.workers.calendar.presenter.factory.DeleteWorkerCell;
import sol.workers.calendar.presenter.factory.PossiblePostsCell;
import sol.workers.calendar.presenter.factory.RestDayCell;
import sol.workers.calendar.presenter.factory.VacationsCell;
import sol.workers.calendar.presenter.factory.WorkerFridayPostCell;
import sol.workers.calendar.presenter.factory.WorkerMondayPostCell;
import sol.workers.calendar.presenter.factory.WorkerSaturdayPostCell;
import sol.workers.calendar.presenter.factory.WorkerSundayPostCell;
import sol.workers.calendar.presenter.factory.WorkerThursdayPostCell;
import sol.workers.calendar.presenter.factory.WorkerTuesdayPostCell;
import sol.workers.calendar.presenter.factory.WorkerWednesdayPostCell;
import sol.workers.calendar.presenter.listener.WorkersCalendarListener;

public class WorkersCalendarPresenter extends AbstractPresenter implements WorkersCalendarListener {

	private static final String PLACEHOLDER_TABLE_CALENDAR = "Aucun calendrier généré, Cliquer sur Générer calendrier herdomadaire "
			+ "afin d'avoir le calendrier des ouvriers pour la semaine.";

	// TODO add a row factory to mark the new added workers
	// TODO mark something to notify the user that the data has been changed

	// TODO save this data in the data base or else where
//	private static final int FIXED_CELL_HEIGHT_TABLE_WORKERS = 50;
	private static final int FIXED_CELL_HEIGHT_TABLE_WORKERS = 171;
	private static final int INSET_TABLE_HEIGHT = 27;

	@Autowired
	private WorkerFileRepository workerFileRepository;
	@Autowired
	private WorkerRepository workerRepository;
	@Autowired
	private CalendarPlanificatorService calendarPlanificatorService;

	@FXML
	private MenuItem menuExportWorkersCalendar;
	@FXML
	private MenuItem menuExit;
	@FXML
	private MenuItem menuSaveEditWorkers;
	@FXML
	private MenuItem menuAddWorker;
	@FXML
	private MenuItem menuHelp;

	@FXML
	private TableView<PostModel> tableWorkersCalendar;
	@FXML
	private TableColumn<PostModel, String> columnRowHeader;
	@FXML
	private TableColumn<PostModel, PostModel> columnMonday;
	@FXML
	private TableColumn<PostModel, PostModel> columnTuesday;
	@FXML
	private TableColumn<PostModel, PostModel> columnWednesday;
	@FXML
	private TableColumn<PostModel, PostModel> columnThursday;
	@FXML
	private TableColumn<PostModel, PostModel> columnFriday;
	@FXML
	private TableColumn<PostModel, PostModel> columnSaturday;
	@FXML
	private TableColumn<PostModel, PostModel> columnSunday;

	@FXML
	private TableView<WorkerModel> tableSourceWorker;
	@FXML
	private TableColumn<WorkerModel, WorkerModel> columnFirstName;
	@FXML
	private TableColumn<WorkerModel, WorkerModel> columnLastName;
	@FXML
	private TableColumn<WorkerModel, WorkerModel> columnVacations;
	@FXML
	private TableColumn<WorkerModel, WorkerModel> columnRestDay;
	@FXML
	private TableColumn<WorkerModel, WorkerModel> columnPossiblesPosts;
	@FXML
	private TableColumn<WorkerModel, WorkerModel> columnDeleteWorker;

	@FXML
	private Button buttonGenerateWeeklyCalendar;

	private List<WorkerModel> workersList = new ArrayList<>();
	private List<WorkerModel> deletedWorkersList = new ArrayList<>();

	/**
	 * constructor
	 * 
	 * @param config
	 */
	public WorkersCalendarPresenter(ScreensConfig config) {
		super(config);
	}

	@FXML
	public void initialize() {
		initMenuItems();
		initTableSourceWorkers();
		initTableSourceWorkersColumns();
		initTableWorkersCalendarColumns();
		tableWorkersCalendar.setPlaceholder(new Text(PLACEHOLDER_TABLE_CALENDAR));

		workersList = workerRepository.getWorkers();

		setTableSourceItemAndResize();
		tableSourceWorker.getSelectionModel().clearSelection();

		buttonGenerateWeeklyCalendar.setOnMouseClicked(event -> {
			try {
				Collection<PostModel> workersCalendarMap = calendarPlanificatorService.generateCalendar(workersList);
				tableWorkersCalendar.setItems(FXCollections.observableArrayList(workersCalendarMap));
				tableWorkersCalendar.getSortOrder().add(columnRowHeader);
				columnRowHeader.setSortable(true);
				setTableSourceItemAndResize();
				tableSourceWorker.refresh();
			} catch (FunctionalException e) {
				showErrorDoalog(e.getMessage());
			}
		});
	}

	private void setTableSourceItemAndResize() {
		tableSourceWorker.setItems(FXCollections.observableArrayList(workersList));
		tableSourceWorker.setFixedCellSize(FIXED_CELL_HEIGHT_TABLE_WORKERS);
		if (workersList.size() > 0) {
			tableSourceWorker.prefHeightProperty().bind(Bindings.size(tableSourceWorker.getItems())
					.multiply(tableSourceWorker.getFixedCellSize()).add(INSET_TABLE_HEIGHT));
		}
	}

	private void initMenuItems() {
		menuHelp.setOnAction(event -> {
			// TODO just to refill data base, to be disabled before release
			try {
				List<WorkerModel> workerModels = workerFileRepository.readData(new FileInputStream(WORKERS_SOURCE));
				workerRepository.deleteAllWorkers();
				for (WorkerModel workerModel : workerModels) {
					workerRepository.addWorker(workerModel);
				}
				workersList = workerRepository.getWorkers();
				setTableSourceItemAndResize();

			} catch (FileNotFoundException | FunctionalException e) {
				e.printStackTrace();
			}
		});
		menuExportWorkersCalendar.setOnAction(event -> System.out.println("export workers calendar"));
		menuExit.setOnAction(event -> Platform.exit());
		menuSaveEditWorkers.setOnAction(event -> {
			try {
				for (WorkerModel workerModel1 : workersList) {
					workerRepository.editWorker(workerModel1);
				}
				for (WorkerModel workerModel2 : deletedWorkersList) {
					workerRepository.editWorker(workerModel2);
				}
			} catch (FunctionalException e) {
				showErrorDoalog(e.getMessage());
			}
			deletedWorkersList.clear();
		});
		menuAddWorker.setOnAction(event -> {
			workersList.add(new WorkerModel());
			setTableSourceItemAndResize();
			tableSourceWorker.scrollTo(workersList.size() - 1);
		});
	}

	/**
	 * Initialise tableSourceWorker
	 */
	private void initTableSourceWorkers() {
		tableSourceWorker.setEditable(true);
		tableSourceWorker.setTableMenuButtonVisible(true);
		tableSourceWorker.getSelectionModel().selectedIndexProperty().addListener((observable, oldvalue, newValue) -> {
			Platform.runLater(() -> {
				tableSourceWorker.getSelectionModel().clearSelection();
			});

		});
		tableSourceWorker
				.setPlaceholder(new Text("Aucun ouvrier ajouté, cliquez sur gestion des ouvriers puis ajouter pour ajouter un."));
		setTableSourceItemAndResize();
	}

	/**
	 * Initialise tableSourceWorker's columns
	 */
	private void initTableSourceWorkersColumns() {
		columnFirstName.prefWidthProperty().bind(tableSourceWorker.widthProperty().divide(5).subtract(100 / 5));
		columnFirstName.setCellValueFactory(cellData -> new SimpleObjectProperty<WorkerModel>(cellData.getValue()));
		columnFirstName.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<WorkerModel>() {

			private WorkerModel workerModel;

			@Override
			public String toString(WorkerModel workerModel) {
				this.workerModel = workerModel;
//				if (workerModel.getFirstName() == null || workerModel.getFirstName().equals("")) {
//					return DOUBLE_CLIC_SAISIR_PRENOM_OUVRIER;
//				} else {
					return workerModel.getFirstName();
//				}
			}

			@Override
			public WorkerModel fromString(String firstName) {
				workerModel.setFirstName(firstName);
				return workerModel;
			}
		}));

		columnLastName.prefWidthProperty().bind(tableSourceWorker.widthProperty().divide(5).subtract(100 / 5));
		columnLastName.setCellValueFactory(cellData -> new SimpleObjectProperty<WorkerModel>(cellData.getValue()));
		// DOUBLE_CLIC_SAISIR_NOM_OUVRIER
		columnLastName.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<WorkerModel>() {

			private WorkerModel workerModel;

			@Override
			public String toString(WorkerModel workerModel) {
				this.workerModel = workerModel;
//				if (workerModel.getLastName() == null || workerModel.getLastName().equals("")) {
//					return DOUBLE_CLIC_SAISIR_NOM_OUVRIER;
//				} else {
					return workerModel.getLastName();
//				}
			}

			@Override
			public WorkerModel fromString(String lastName) {
				workerModel.setLastName(lastName);
				return workerModel;
			}
		}));

		columnVacations.prefWidthProperty().bind(tableSourceWorker.widthProperty().divide(5).subtract(100 / 5));
		columnVacations.setCellValueFactory(cellData -> new SimpleObjectProperty<WorkerModel>(cellData.getValue()));
		columnVacations.setCellFactory(param -> new VacationsCell());

		columnRestDay.prefWidthProperty().bind(tableSourceWorker.widthProperty().divide(5).subtract(100 / 5));
		columnRestDay.setCellValueFactory(cellData -> new SimpleObjectProperty<WorkerModel>(cellData.getValue()));
		columnRestDay.setCellFactory(param -> new RestDayCell());

		columnPossiblesPosts.prefWidthProperty().bind(tableSourceWorker.widthProperty().divide(5).subtract(100 / 5 + 20));
		columnPossiblesPosts.setCellValueFactory(cellData -> new SimpleObjectProperty<WorkerModel>(cellData.getValue()));
		columnPossiblesPosts.setCellFactory(param -> new PossiblePostsCell());

		columnDeleteWorker.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
		columnDeleteWorker.setCellFactory(param -> new DeleteWorkerCell(WorkersCalendarPresenter.this));
	}

	/**
	 * Initialise tableWorkersCalendar's column
	 */
	private void initTableWorkersCalendarColumns() {
		columnRowHeader.setCellValueFactory(
				cellData -> new SimpleObjectProperty<String>(cellData.getValue().getPossiblePostsEnum().toString()));

		columnMonday.prefWidthProperty().bind(tableWorkersCalendar.widthProperty().divide(7).subtract(100 / 7));
		columnMonday.setCellValueFactory(cellData -> new SimpleObjectProperty<PostModel>(cellData.getValue()));
		columnMonday.setCellFactory(param -> new WorkerMondayPostCell());

		columnTuesday.prefWidthProperty().bind(tableWorkersCalendar.widthProperty().divide(7).subtract(100 / 7));
		columnTuesday.setCellValueFactory(cellData -> new SimpleObjectProperty<PostModel>(cellData.getValue()));
		columnTuesday.setCellFactory(param -> new WorkerTuesdayPostCell());

		columnWednesday.prefWidthProperty().bind(tableWorkersCalendar.widthProperty().divide(7).subtract(100 / 7));
		columnWednesday.setCellValueFactory(cellData -> new SimpleObjectProperty<PostModel>(cellData.getValue()));
		columnWednesday.setCellFactory(param -> new WorkerWednesdayPostCell());

		columnThursday.prefWidthProperty().bind(tableWorkersCalendar.widthProperty().divide(7).subtract(100 / 7));
		columnThursday.setCellValueFactory(cellData -> new SimpleObjectProperty<PostModel>(cellData.getValue()));
		columnThursday.setCellFactory(param -> new WorkerThursdayPostCell());

		columnFriday.prefWidthProperty().bind(tableWorkersCalendar.widthProperty().divide(7).subtract(100 / 7));
		columnFriday.setCellValueFactory(cellData -> new SimpleObjectProperty<PostModel>(cellData.getValue()));
		columnFriday.setCellFactory(param -> new WorkerFridayPostCell());

		columnSaturday.prefWidthProperty().bind(tableWorkersCalendar.widthProperty().divide(7).subtract(100 / 7));
		columnSaturday.setCellValueFactory(cellData -> new SimpleObjectProperty<PostModel>(cellData.getValue()));
		columnSaturday.setCellFactory(param -> new WorkerSaturdayPostCell());

		columnSunday.prefWidthProperty().bind(tableWorkersCalendar.widthProperty().divide(7).subtract(100 / 7).subtract(10));
		columnSunday.setCellValueFactory(cellData -> new SimpleObjectProperty<PostModel>(cellData.getValue()));
		columnSunday.setCellFactory(param -> new WorkerSundayPostCell());
	}

	/**
	 * Delete worker from workersList and from database
	 */
	@Override
	public void deleteWorker(WorkerModel workerModel) {
		workerModel.setDeleted(true);
		workersList.remove(workerModel);
		deletedWorkersList.add(workerModel);
		setTableSourceItemAndResize();
	}

}
