package com.pojogen.application.gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.lang.model.SourceVersion;

import com.pojogen.application.pojo.component.Pojo;
import com.pojogen.application.shared.util.PojoDataTypeHelper.DataTypeEnum;
import com.pojogen.application.shared.util.PojoGenValidationHelper;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class PojoGenGuiFxmlController {

	@FXML
	private Button btnBrowse;
	@FXML
	private Button btnCancel;
	@FXML
	private TextField txtDestination;
	@FXML
	private TextField txtClassName;
	@FXML
	private VBox vboxMembers;

	public PojoGenGuiFxmlController() {

	}

	@FXML
	private void initialize() {
		addMemberBox();
	}

	@FXML
	private void closeWindow() {
		((Stage) btnCancel.getScene().getWindow()).close();
	}

	@FXML
	private void openFileBrowser() {
		final DirectoryChooser dirChooser = new DirectoryChooser();
		File oDirectory = dirChooser.showDialog(txtDestination.getScene().getWindow());

		if (null != oDirectory) {
			txtDestination.setText(oDirectory.getAbsolutePath());
		}
	}

	@FXML
	private void addMemberBox() {
		final HBox horizontalBox = new HBox();
		horizontalBox.setPadding(new Insets(15, 12, 15, 12));
		horizontalBox.setSpacing(10);

		final TextField _memberName = new TextField();
		_memberName.setPromptText("Member Name");

		final ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(DataTypeEnum.getTypes()));

		final Button btnRemove = new Button("remove");
		btnRemove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final Button _btnRemove = (Button) event.getSource();

				vboxMembers.getChildren().remove((HBox) _btnRemove.getParent());

				if (vboxMembers.getChildren().size() < 1) {
					addMemberBox();
				}
			}
		});

		horizontalBox.getChildren().addAll(_memberName, choiceBox, btnRemove);

		vboxMembers.getChildren().add(horizontalBox);
	}

	@FXML
	private void generatePOJO() throws Exception {
		final Map<String, DataTypeEnum> nameToTypeMap = new HashMap<>();
		boolean isValidPojo = true;
		// get POJO member map
		for (Node memberNode : vboxMembers.getChildren()) {
			final TextField txtMemberName = getMemberNameTextField((HBox) memberNode);
			final ChoiceBox<String> chboxDataTypes = getMemberChoiceBox((HBox) memberNode);

			// TODO: change the color of the HBox border to RED if the member
			// name is not valid

			// TODO: check that the member names are not duplicates

			// TODO: check that the member names are not JAVA keywords

			if (SourceVersion.isIdentifier(txtMemberName.getText())) {
				isValidPojo = false;
				txtMemberName.setStyle("-fx-text-box-border: red");
				System.out.println("Illegal member name.");
				System.out.println(txtMemberName.getStyle());
			} else {
				if (null != txtMemberName.getStyle() && txtMemberName.getStyle().contains("-fx-text-box-border: red")) {
					txtMemberName.setStyle(null);
				}
			}

			nameToTypeMap.put(getMemberName(txtMemberName), getMemberDataType(chboxDataTypes));
		}

		// validate member mapping
		performValidation();

		if (SourceVersion.isName(txtClassName.getText())) {
			System.out.println("Is a valid name.");
		}

		// TODO: Uncomment
		// if (isValidPojo) {
		// final PojoGenRequestModel oRequestModel = new PojoGenRequestModel();
		// oRequestModel.setClazzname(txtClassName.getText());
		// oRequestModel.setMemberMap(nameToTypeMap);
		// try {
		// printToFile(Pojo.PojoBuilder.getPojo(oRequestModel));
		// } catch (final Exception e) {
		// e.printStackTrace();
		// }
		// }

	}

	private boolean performValidation() {
		// TODO-
		/**
		 * check the class name against the JAVA Specification
		 * 
		 * check the member names against the JAVA Specification
		 * 
		 * 
		 *
		 */

		return true;
	}

	private void validateMemberNode(final HBox p_Node) throws Exception {
		if (p_Node.getChildren().size() != 3) {
			throw new IllegalArgumentException(
					"Incorrect use of the PojoGenGuiFxmlController:getTextFieldFromChildren)");
		}
	}

	private TextField getMemberNameTextField(final HBox p_Node) throws Exception {
		validateMemberNode(p_Node);

		for (Iterator<Node> nodes = p_Node.getChildren().iterator(); nodes.hasNext();) {
			final Node node = nodes.next();
			if (node instanceof TextField) {
				return (TextField) node;
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private ChoiceBox<String> getMemberChoiceBox(final HBox p_Node) throws Exception {
		validateMemberNode(p_Node);

		for (Iterator<Node> nodes = p_Node.getChildren().iterator(); nodes.hasNext();) {
			final Node node = nodes.next();
			if (node instanceof ChoiceBox) {
				return (ChoiceBox<String>) node;
			}
		}

		return null;
	}

	private String getMemberName(final Node p_oNode) {
		if (p_oNode instanceof TextField) {
			return ((TextField) p_oNode).getText();
		}
		return null;
	}

	private DataTypeEnum getMemberDataType(final ChoiceBox<String> p_DataTypeChoiceBox) {
		if (null != p_DataTypeChoiceBox) {
			return DataTypeEnum.getTypeByClazz(p_DataTypeChoiceBox.getSelectionModel().getSelectedItem());
		}
		return null;
	}

	private void printToFile(final Pojo pojo) throws IOException {
		if (PojoGenValidationHelper.hasText(txtDestination.getText())) {
			final File destinationDirectory = new File(txtDestination.getText());
			if (Files.isDirectory(destinationDirectory.toPath(), LinkOption.NOFOLLOW_LINKS)) {
				final File file = new File(destinationDirectory.getAbsolutePath() + System.getProperty("file.separator")
						+ pojo.getPojoClassName() + ".java");
				final FileWriter writer = new FileWriter(file);
				writer.write(pojo.toString());
				writer.flush();
				writer.close();
			}
		}
	}
}