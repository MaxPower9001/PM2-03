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
	private Label sout;
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

	private void initTermine() {
		termine.getItems().add(null);
		termine.valueProperty().addListener(
				(ObservableValue<? extends Termin> ov, Termin oldSel, Termin newSel) -> {
					if(newSel != null)
					{
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
						
						if(termin.getClass() == TerminMitWiederholungImpl.class)
						{
							TerminMitWiederholungImpl terminMitW = (TerminMitWiederholungImpl) termin;
							Wiederholung w = terminMitW.getWdh();							
							
							wiederholung.setValue(w.getType());
							zyklusInput.setText("" + w.getZyklus());
							wiederholungInput.setText("" + w.anzahl());							
						}
					}
					else{
						emptyDialog();
					}
				});		
	}

	private void fancyUpDatePicker() {
		vonDatePicker.setValue(LocalDate.now());
		Callback<DatePicker, DateCell> callback = (DatePicker dp) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.isBefore(LocalDate.now())) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}
			}
		};
		
		vonDatePicker.setDayCellFactory(callback);
		
		bisDatePicker.setDayCellFactory((DatePicker dp) -> new DateCell() {
			@Override
			public void updateItem(LocalDate item, boolean empty) {
				super.updateItem(item, empty);
				if (item.isBefore(vonDatePicker.getValue())) {
					setDisable(true);
					setStyle("-fx-background-color: #ffc0cb;");
				}
				long stay = ChronoUnit.DAYS.between(
						vonDatePicker.getValue(), item);
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
			}
		});
		// Make the save button save the actual Date
		speichernButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (!checkInputs()) {
					sout.setStyle("-fx-text-fill: red;");
					sout.setText("Angaben nicht vollständig!");
				} else {
					if(verschiebenMode){
						verschiebeTermin();
					}
					else {
						speichereNeuenTermin();
					}

					verschiebenMode = false;
				}
			}

			private void verschiebeTermin() {
				Tag vonTag = new TagImpl(vonDatePicker.getValue().getYear(), vonDatePicker.getValue().getDayOfYear());
				Date vonDate;				
				try {
					vonDate = format24hTime.parse(von.getText());
				} catch (ParseException e) {							
					e.printStackTrace();
					return;
				}
				Calendar vonCalendar = Calendar.getInstance();
				
				vonCalendar.setTime(vonDate);
				
				Uhrzeit vonUhrzeit = new UhrzeitImpl(vonCalendar.get(Calendar.HOUR_OF_DAY),vonCalendar.get(Calendar.MINUTE)); 
				
				Datum vonDatum = new DatumImpl(vonTag, vonUhrzeit);
				
				Termin alterTermin = termine.getValue();
				termine.getItems().remove(alterTermin);
				Termin neuerTermin = terminKalender.verschiebenAuf(alterTermin, vonDatum);
				termine.getItems().add(neuerTermin);				
			}

			private void speichereNeuenTermin() {
				sout.setStyle("-fx-text-fill: green;");
				sout.setText("Angaben vollständig, sehr gut!");

				Termin terminZuSpeichern;
				
				String beschreibung = beschreibungInput.getText();
				Tag vonTag = new TagImpl(vonDatePicker.getValue().getYear(), vonDatePicker.getValue().getDayOfYear());
				Tag bisTag = new TagImpl(bisDatePicker.getValue().getYear(), bisDatePicker.getValue().getDayOfYear());
				
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
				
				vonCalendar.setTime(vonDate);
				bisCalendar.setTime(bisDate);
				
				Uhrzeit vonUhrzeit = new UhrzeitImpl(vonCalendar.get(Calendar.HOUR_OF_DAY),vonCalendar.get(Calendar.MINUTE)); 
				Uhrzeit bisUhrzeit = new UhrzeitImpl(bisCalendar.get(Calendar.HOUR_OF_DAY),vonCalendar.get(Calendar.MINUTE));
				
				Datum vonDatum = new DatumImpl(vonTag, vonUhrzeit);
				Datum bisDatum = new DatumImpl(bisTag, bisUhrzeit);
				
				Dauer dauer = new DauerImpl(vonDatum, bisDatum);
				
				if (wiederholungInputs.isDisabled()) {	
					terminZuSpeichern = new TerminImpl(beschreibung,vonDatum,dauer);
				} else {
					WiederholungType type = wiederholung.getValue();
					int anzahl = Integer.parseInt(wiederholungInput.getText());
					int zyklus = Integer.parseInt(zyklusInput.getText());
					
					terminZuSpeichern = new TerminMitWiederholungImpl(beschreibung, vonDatum, dauer, type, anzahl, zyklus);
				}
				
				if(!termine.getItems().contains(terminZuSpeichern))
				{
					terminKalender.eintragen(terminZuSpeichern);
					termine.getItems().add(terminZuSpeichern);
				}
			}
		});
		
		loeschenButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(termine.getValue() != null)
				{
					terminKalender.terminLoeschen(termine.getValue());
					termine.getItems().remove(termine.getValue());
					emptyDialog();
				}
			}
		});
	}

	private boolean checkInputs() {
		if (!(beschreibungInput.getText().isEmpty() || vonDatePicker.getValue() == null || bisDatePicker.getValue() == null || !vonBisValid())) {
			if (!wiederholungInputs.isDisabled()) {
				if (!(zyklusInput.getText().isEmpty() || wiederholungInput.getText().isEmpty()))
					return true;
				return false;
			}
			return true;
		}
		return false;
	}

	private boolean vonBisValid() {
		Date vonTime;
		Date bisTime;
		try {
			vonTime = format24hTime.parse(von.getText());
			bisTime = format24hTime.parse(bis.getText());
		} catch (ParseException e) {
			return false;
		}
		return vonTime.before(bisTime);
	}

	private void limitTextfields() {
		zyklusInput.textProperty().addListener(createChangelistener(zyklusInput));
		wiederholungInput.textProperty().addListener(createChangelistener(wiederholungInput));
		
		try {
			von.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format24hTime), format24hTime.parse("12:00")));
			bis.setTextFormatter(new TextFormatter<>(new DateTimeStringConverter(format24hTime), format24hTime.parse("13:00")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ChangeListener<String> createChangelistener(TextField tf) {
		return new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					tf.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		};
	}

	private void initWiederholung() {
		wiederholung.getItems().addAll(OHNE, WOECHENTLICH, TAEGLICH);
		wiederholung.setValue(OHNE);
		wiederholung.valueProperty().addListener(
				(ObservableValue<? extends WiederholungType> ov, WiederholungType oldSel, WiederholungType newSel) -> {
					if(!verschiebenMode){
						if (newSel.equals(OHNE))
							wiederholungInputs.setDisable(true);
						else
							wiederholungInputs.setDisable(false);
					}
				});
	}

	private void emptyDialog() {
		beschreibungInput.clear();
		vonDatePicker.setValue(LocalDate.now());
		bisDatePicker.setValue(null);
		zyklusInput.clear();
		wiederholungInput.clear();
		wiederholung.setValue(OHNE);
		von.setPromptText("12:30");
		bis.setPromptText("13:30");
		sout.setText("");
		speichernButton.setText("Termin speichern");
		beschreibungInput.setDisable(false);
		wiederholung.setDisable(false);
		bis.setDisable(false);
		bisDatePicker.setDisable(false);
		termine.setValue(null);
	}
}
