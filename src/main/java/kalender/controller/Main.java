package main.java.kalender.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			primaryStage.setTitle("Calender and then some");
			primaryStage.getIcons().add(new Image("https://image.freepik.com/free-icon/thumbs-up-hand-outline_318-41813.jpg"));
			
			Pane root = (Pane) FXMLLoader.load(getClass().getResource("../view/Calender.fxml"));
			
			Scene scene = new Scene(root,root.getPrefWidth(),root.getPrefHeight());
			
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	public static void main(String[] args)
	{
		System.out.println("Die Applikation wurde gestartet");
		launch(args);
	}

}
