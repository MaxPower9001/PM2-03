package main.java.kalender.view;

import static main.java.kalender.model.WiederholungType.OHNE;
import static main.java.kalender.model.WiederholungType.TAEGLICH;
import static main.java.kalender.model.WiederholungType.WOECHENTLICH;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.util.Callback;
import javafx.util.converter.DateTimeStringConverter;
import main.java.kalender.controller.Main;
import main.java.kalender.model.DatumImpl;
import main.java.kalender.model.DauerImpl;
import main.java.kalender.model.TagImpl;
import main.java.kalender.model.TerminImpl;
import main.java.kalender.model.TerminKalenderImpl;
import main.java.kalender.model.TerminMitWiederholungImpl;
import main.java.kalender.model.UhrzeitImpl;
import main.java.kalender.model.WiederholungType;
import main.java.kalender.model.interfaces.Datum;
import main.java.kalender.model.interfaces.Dauer;
import main.java.kalender.model.interfaces.Tag;
import main.java.kalender.model.interfaces.Termin;
import main.java.kalender.model.interfaces.TerminKalender;
import main.java.kalender.model.interfaces.Uhrzeit;
import main.java.kalender.model.interfaces.Wiederholung;

public class CalenderController {
	@FXML
	private Group wiederholungInputs;
	@FXML
	private TextField beschreibungInput;
	@FXML
	private TextField zyklusInput;
	@FXML
	private TextField wiederholungInput;
	@FXML
	private TextField von;
	@FXML
	private TextField bis;
	@FXML
	private Label debugOutput;
	@FXML
	private Button speichernButton;
	@FXML
	private Button loeschenButton;
	@FXML
	private Button neuButton;
	@FXML
	private ChoiceBox<Termin> termine;
	@FXML
	private ComboBox<WiederholungType> wiederholung;
	@FXML
	private DatePicker vonDatePicker;
	@FXML
	private DatePicker bisDatePicker;
	private boolean verschiebenMode;
	// The actual calender
	private TerminKalender terminKalender;

	// 24H Time format HH:mm
	private SimpleDateFormat format24hTime;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public CalenderController() {
		verschiebenMode = false;
		format24hTime = new SimpleDateFormat("HH:mm");
		terminKalender = new TerminKalenderImpl();
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		initWiederholung();

		initTermine();

		limitTextfields();

		bindButtons();

		fancyUpDatePicker();

		emptyDialog();
	}

	private void fancyUpDatePicker() {
		// Set DatePicker to today
		vonDatePicker.setValue(LocalDate.now());

		// Configure DayCellFactory to deactivate all past days
		vonDatePicker.setDayCellFactory((DatePicker dp) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.isBefore(LocalDate.now())) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}
			}
		});

		// Configure DayCellFactory to deactivate all days before vonDatePicker
		bisDatePicker.setDayCellFactory((DatePicker dp) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.isBefore(vonDatePicker.getValue())) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}

				// Add fancy tooltip for date duration
				long stay = ChronoUnit.DAYS.between(vonDatePicker.getValue(), item);
				setTooltip(new Tooltip("Ihr Termin dauert " + stay + " Tage."));
			};
		});

	}

	private void bindButtons() {
		// Bind new Button to reset every Node with emptyDialog()
		neuButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				emptyDialog();
				debugOutput.setText("");
			}
		});
	
		// Make the save button save the actual Date
		speichernButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// If inputs are not filled correctly, bail out and display
				// error message
				if (!checkInputs()) {
					debugOutput.setStyle("-fx-text-fill: red;");
					debugOutput.setText("Angaben nicht vollständig!");
				} else {
					// Otherwise check if we are in moveMode
					if (verschiebenMode) {
						verschiebeTermin();
					} else {
						speichereNeuenTermin();
					}
					emptyDialog();
					verschiebenMode = false;
				}
			}

			private void verschiebeTermin() {
				// get Tag from datePicker
				Tag vonTag = new TagImpl(vonDatePicker.getValue().getYear(), vonDatePicker.getValue().getDayOfYear());

				// Get Date from von field
				Date vonDate;
				try {
					vonDate = format24hTime.parse(von.getText());
				} catch (ParseException e) {
					e.printStackTrace();
					return;
				}
				// Get calendar with get instance
				Calendar vonCalendar = Calendar.getInstance();

				// Put date into calendar
				vonCalendar.setTime(vonDate);

				// transform to uhrzeit with calendar
				Uhrzeit vonUhrzeit = new UhrzeitImpl(vonCalendar.get(Calendar.HOUR_OF_DAY),
						vonCalendar.get(Calendar.MINUTE));

				// create datum with tag and uhrzeit
				Datum vonDatum = new DatumImpl(vonTag, vonUhrzeit);

				// remove old date from combobox, move it and add the new one to
				// combobox
				Termin alterTermin = termine.getValue();
				termine.getItems().remove(alterTermin);
				Termin neuerTermin = terminKalender.verschiebenAuf(alterTermin, vonDatum);
				termine.getItems().add(neuerTermin);
			}

			private void speichereNeuenTermin() {
				debugOutput.setStyle("-fx-text-fill: green;");
				debugOutput.setText("Angaben vollständig, sehr gut!");

				Termin terminZuSpeichern;

				// Get beschreibung from textfield
				String beschreibung = beschreibungInput.getText();

				// Get vonTag and bisTag based on datePickers
				Tag vonTag = new TagImpl(vonDatePicker.getValue().getYear(), vonDatePicker.getValue().getDayOfYear());
				Tag bisTag = new TagImpl(bisDatePicker.getValue().getYear(), bisDatePicker.getValue().getDayOfYear());

				// Transform vonZeit and bisZeit into date
				Date vonDate;
				Date bisDate;
				try {
					vonDate = format24hTime.parse(von.getText());
					bisDate = format24hTime.parse(bis.getText());
				} catch (ParseException e) {
					e.printStackTrace();
					return;
				}
				Calendar vonCalendar = Calendar.getInstance();
				Calendar bisCalendar = Calendar.getInstance();

				// set those times to calendar
				vonCalendar.setTime(vonDate);
				bisCalendar.setTime(bisDate);

				// transform into Uhrzeit with help of calendar
				Uhrzeit vonUhrzeit = new UhrzeitImpl(vonCalendar.get(Calendar.HOUR_OF_DAY),
						vonCalendar.get(Calendar.MINUTE));
				Uhrzeit bisUhrzeit = new UhrzeitImpl(bisCalendar.get(Calendar.HOUR_OF_DAY),
						vonCalendar.get(Calendar.MINUTE));

				// create vonDatum and bisDatum with Tag and uhrzeit
				Datum vonDatum = new DatumImpl(vonTag, vonUhrzeit);
				Datum bisDatum = new DatumImpl(bisTag, bisUhrzeit);

				// calculate dauer based on datum
				Dauer dauer = new DauerImpl(vonDatum, bisDatum);

				// if its not a date with wiederholung create Termin
				if (wiederholungInputs.isDisabled()) {
					terminZuSpeichern = new TerminImpl(beschreibung, vonDatum, dauer);
				} else {
					// otherwise get wiederholung specific inputs
					WiederholungType type = wiederholung.getValue();
					int anzahl = Integer.parseInt(wiederholungInput.getText());
					int zyklus = Integer.parseInt(zyklusInput.getText());

					// and create TerminMitWiederholung
					terminZuSpeichern = new TerminMitWiederholungImpl(beschreibung, vonDatum, dauer, type, anzahl,
							zyklus);
				}

				// if combo box does not already contain termin, add it to it
				// and the internal terminKalendar
				if (!termine.getItems().contains(terminZuSpeichern)) {
					terminKalender.eintragen(terminZuSpeichern);
					termine.getItems().add(terminZuSpeichern);
				}
			}
		});
		// Delete the selected date from list and internal calendar
		loeschenButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (termine.getValue() != null) {
					terminKalender.terminLoeschen(termine.getValue());
					termine.getItems().remove(termine.getValue());
					emptyDialog();
				}
			}
		});
	}

	private boolean checkInputs() {
		// every input has to be filled
		if (!(beschreibungInput.getText().isEmpty() || vonDatePicker.getValue() == null
				|| bisDatePicker.getValue() == null || !vonBisValid())) {
			if (!wiederholungInputs.isDisabled()) {
				// if TerminMitWiederholung zyklus and wiederholung need to be
				// filled
				if (!(zyklusInput.getText().isEmpty() || wiederholungInput.getText().isEmpty()))
					return true;
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if the from-date and time is before to-dateand time
	 * 
	 * @return true if its before and false if its not
	 */
	private boolean vonBisValid() {
		Date vonTime;
		Date bisTime;
		try {
			vonTime = format24hTime.parse(von.getText());
			bisTime = format24hTime.parse(bis.getText());
		} catch (ParseException e) {
			return false;
		}
		LocalDate vonDate = vonDatePicker.getValue();
		LocalDate bisDate = bisDatePicker.getValue();

		// either the dates are not equal or the from time is before to time

		if (verschiebenMode) {
			return true;
		} else {
			return vonTime.before(bisTime) || !vonDate.equals(bisDate);
		}
	}

	private void limitTextfields() {
		zyklusInput.textProperty().addListener(createChangelistener(zyklusInput));
		wiederholungInput.textProperty().addListener(createChangelistener(wiederholungInput));

		// Add a TextFormatter to from and to time
		von.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format24hTime)));
		bis.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format24hTime)));
	}

	private ChangeListener<String> createChangelistener(TextField tf) {
		return new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// only allows digits
				if (!newValue.matches("\\d*")) {
					tf.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		};
	}

	private void initWiederholung() {
		// set wiederholung combobox to contain repetition enums
		wiederholung.getItems().addAll(OHNE, WOECHENTLICH, TAEGLICH);

		// select base value
		wiederholung.setValue(OHNE);

		// add listener to check if repetition selected to unlock/lock
		// repetition-based input fields
		wiederholung.valueProperty().addListener(
				(ObservableValue<? extends WiederholungType> ov, WiederholungType oldSel, WiederholungType newSel) -> {
					// moveMode overrides this listener
					// if(!verschiebenMode){
					if (newSel.equals(OHNE))
						wiederholungInputs.setDisable(true);
					else
						wiederholungInputs.setDisable(false);
					// }
				});
	}

	private void initTermine() {
		// add null to combobox
		termine.getItems().add(null);

		// listener to transfer all values from selected date into input fields,
		// if so
		// movemode is activated and only the from date and time are changeable
		// if null selected inputs get cleared
		termine.valueProperty().addListener((ObservableValue<? extends Termin> ov, Termin oldSel, Termin newSel) -> {
			if (newSel != null) {
				verschiebenMode = true;
				speichernButton.setText("Termin verschieben");
				beschreibungInput.setDisable(true);
				wiederholung.setDisable(true);
				wiederholungInputs.setDisable(true);
				bis.setDisable(true);
				bisDatePicker.setDisable(true);
				wiederholungInputs.setDisable(true);

				Termin termin = termine.getValue();

				Datum vonDatum = termin.getDatum();
				Datum bisDatum = termin.getDatum().add(termin.getDauer());

				Date vonDate = vonDatum.inBasis().getTime();
				Date bisDate = bisDatum.inBasis().getTime();

				vonDatePicker.setValue(vonDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
				bisDatePicker.setValue(bisDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

				beschreibungInput.setText(termin.getBeschreibung());

				if (termin.getClass() == TerminMitWiederholungImpl.class) {
					TerminMitWiederholungImpl terminMitW = (TerminMitWiederholungImpl) termin;
					Wiederholung w = terminMitW.getWdh();

					wiederholung.setValue(w.getType());
					zyklusInput.setText("" + w.getZyklus());
					wiederholungInput.setText("" + w.anzahl());
				}
			} else {
				emptyDialog();
			}
		});
	}

	/**
	 * resets the gui to its standard input values
	 */
	private void emptyDialog() {
		beschreibungInput.clear();
		vonDatePicker.setValue(LocalDate.now());
		bisDatePicker.setValue(null);
		zyklusInput.clear();
		wiederholungInput.clear();
		wiederholung.setValue(OHNE);
		von.clear();
		bis.clear();
		speichernButton.setText("Termin speichern");
		beschreibungInput.setDisable(false);
		wiederholung.setDisable(false);
		bis.setDisable(false);
		bisDatePicker.setDisable(false);
		termine.setValue(null);
	}
}
