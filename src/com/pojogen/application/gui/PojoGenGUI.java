package com.pojogen.application.gui;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.pojogen.application.pojo.component.Pojo;
import com.pojogen.application.request.model.PojoGenRequestModel;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PojoGenGUI extends Application {

	private final static double BUTTON_PREF_WIDTH = 100;
	private final static double BUTTON_PREF_HEIGHT = 20;

	private final static double SCENE_HEIGHT = 600;
	private final static double SCENE_WIDTH = 700;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final PojoGenRequestModel oRequestModel = new PojoGenRequestModel();

		final BorderPane border = new BorderPane();

		final GridPane gridPane = new GridPane();
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		gridPane.setVgap(5);
		gridPane.setHgap(5);

		/***********************************************
		 ************ Destination **************
		 ***********************************************/
		final HBox destinationHBox = new HBox();
		destinationHBox.setPadding(new Insets(15, 12, 15, 12));
		destinationHBox.setSpacing(10);
		GridPane.setConstraints(destinationHBox, 0, 0);

		final Label lblDestination = new Label("Destination:");
		final TextField txtDestination = new TextField();
		final Button btnBrowse = new Button("Browse");
		btnBrowse.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				final DirectoryChooser dirChooser = new DirectoryChooser();
				File oDirectory = dirChooser.showDialog(primaryStage);

				if (null != oDirectory) {
					txtDestination.setText(oDirectory.getAbsolutePath());
				}
			}
		});

		destinationHBox.getChildren().addAll(lblDestination, txtDestination, btnBrowse);

		/***********************************************
		 ************ Class name **************
		 ***********************************************/
		final HBox horizontalBoxClsName = new HBox();
		horizontalBoxClsName.setPadding(new Insets(15, 12, 15, 12));
		horizontalBoxClsName.setSpacing(10);
		GridPane.setConstraints(horizontalBoxClsName, 0, 1);

		final Label classNameLabel = new Label("Class Name:");

		final TextField className = new TextField();
		className.setPromptText("Class Name");

		horizontalBoxClsName.getChildren().addAll(classNameLabel, className);

		/***********************************************
		 ************ POJO Member List **********
		 ***********************************************/
		final HBox memberListHBox = new HBox();
		memberListHBox.setPadding(new Insets(15, 12, 15, 12));
		memberListHBox.setSpacing(10);

		final TextField memberName = new TextField();
		memberName.setPromptText("Member Name");

		final ChoiceBox<String> choiceBox = new ChoiceBox<>(
				FXCollections.observableArrayList(DataTypeEnum.getTypes()));

		memberListHBox.getChildren().addAll(memberName, choiceBox);

		final VBox verticalBox = new VBox();
		verticalBox.getChildren().addAll(memberListHBox);

		final HBox buttonBox = new HBox();
		buttonBox.setPadding(new Insets(15, 12, 15, 12));
		buttonBox.setSpacing(10);
		GridPane.setConstraints(buttonBox, 0, 3);

		final Button btnAddMember = new Button("Add Member");
		btnAddMember.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				final HBox _horizontalBox = new HBox();
				_horizontalBox.setPadding(new Insets(15, 12, 15, 12));
				_horizontalBox.setSpacing(10);

				final TextField _memberName = new TextField();
				_memberName.setPromptText("Member Name");

				final ChoiceBox<String> _choiceBox = new ChoiceBox<>(
						FXCollections.observableArrayList(DataTypeEnum.getTypes()));

				_horizontalBox.getChildren().addAll(_memberName, _choiceBox);

				verticalBox.getChildren().addAll(_horizontalBox);
			}
		});

		buttonBox.getChildren().add(btnAddMember);

		// buttonGenerate.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);

		ScrollPane verticalScrollPane = new ScrollPane(verticalBox);
		GridPane.setConstraints(verticalScrollPane, 0, 2);
		verticalScrollPane.setFitToWidth(true);
		verticalScrollPane.setStyle("-fx-background-color:transparent;");

		gridPane.getChildren().addAll(destinationHBox, horizontalBoxClsName, verticalScrollPane, buttonBox);

		/** Add Grid Pane to Center **/
		border.setCenter(gridPane);

		/** Add Grid Button Horizontal Box **/
		final HBox horizontalBox = new HBox();
		horizontalBox.setPadding(new Insets(15, 12, 15, 12));
		horizontalBox.setSpacing(10);
		horizontalBox.setStyle("-fx-background-color: #336699;");
		// horizontalBox.setStyle("-fx-background-color: #D3D3D3;");

		final Button buttonCancel = new Button("Cancel");
		buttonCancel.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				primaryStage.close();
			}
		});

		final Button buttonGenerate = new Button("Generate");
		buttonGenerate.setPrefSize(BUTTON_PREF_WIDTH, BUTTON_PREF_HEIGHT);
		buttonGenerate.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				final Map<String, DataTypeEnum> nameToTypeMap = new HashMap<>();

				// get POJO class name from the TextField
				String strPojoClassName = null;
				for (Iterator<Node> itrNode = horizontalBoxClsName.getChildren().iterator(); itrNode.hasNext();) {
					final Node oNode = itrNode.next();
					if (oNode instanceof TextField) {
						strPojoClassName = ((TextField) oNode).getText();
					}
				}

				// get POJO member map
				for (Node ohBox : verticalBox.getChildren()) {
					final HBox horizontalBox = (HBox) ohBox;
					for (Iterator<Node> itrNode = horizontalBox.getChildren().iterator(); itrNode.hasNext();) {
						String strMemberName = null;
						DataTypeEnum eDataType = null;

						final Node oTextFieldNode = itrNode.next();
						if (oTextFieldNode instanceof TextField) {
							strMemberName = ((TextField) oTextFieldNode).getText();
							// check that the map does not contain an entry

							// if the map contains an entry throw an exception
							// to the user to inform them that they already have
							// an entry
							// with the same name
							// this should be done as they are typing, when they
							// leave a text box it should inform
							// them that they have done something wrong and
							// print the error to the screen immediately
						}

						final Node oChoiceBoxNode = itrNode.next();
						if (oChoiceBoxNode instanceof ChoiceBox) {
							@SuppressWarnings("unchecked")
							final ChoiceBox<String> oChoiceBox = (ChoiceBox<String>) oChoiceBoxNode;
							final String strChoice = oChoiceBox.getSelectionModel().getSelectedItem();

							eDataType = DataTypeEnum.getTypeByClazz(strChoice);
						}

						nameToTypeMap.put(strMemberName, eDataType);
					}
				}
				oRequestModel.setClazzname(strPojoClassName);
				oRequestModel.setMemberMap(nameToTypeMap);
				// use the nameToTypeMap to generate the POJO
				try {
					System.out.println(Pojo.PojoBuilder.getPojo(oRequestModel));
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});

		horizontalBox.setAlignment(Pos.CENTER);
		horizontalBox.getChildren().addAll(buttonCancel, buttonGenerate);

		border.setBottom(horizontalBox);

		Scene scene = new Scene(border, SCENE_WIDTH, SCENE_HEIGHT);

		primaryStage.setTitle("POJO Generator");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
