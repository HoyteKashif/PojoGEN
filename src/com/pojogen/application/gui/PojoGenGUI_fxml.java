package com.pojogen.application.gui;

import java.io.FileInputStream;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PojoGenGUI_fxml extends Application {

	private final static double SCENE_HEIGHT = 600;
	private final static double SCENE_WIDTH = 700;

	public static void main(final String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();

		String fxmlDocPath = "fxml_layouts/PojoGenGui.fxml";

		FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

		final BorderPane root = (BorderPane) loader.load(fxmlStream);

		final Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

		primaryStage.setTitle("POJO Generator");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
