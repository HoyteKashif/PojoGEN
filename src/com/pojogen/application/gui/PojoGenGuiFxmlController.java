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
import com.pojogen.application.shared.util.StringHelper;

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
		final File oDirectory = new DirectoryChooser().showDialog(txtDestination.getScene().getWindow());

		if (null != oDirectory) {
			txtDestination.setText(oDirectory.getAbsolutePath());
		}
	}

	@FXML
	private void addMemberBox() {

		// create horizontal box
		final HBox horizontalBox = new HBox();
		horizontalBox.setPadding(new Insets(15, 12, 15, 12));
		horizontalBox.setSpacing(10);

		// add horizontal box to the vertical group of members box
		vboxMembers.getChildren().add(horizontalBox);

		// create member name text field
		final TextField _memberName = new TextField();
		_memberName.setPromptText("Member Name");

		// add member name text field to horizontal box
		horizontalBox.getChildren().add(_memberName);

		// add data type choice box to the horizontal box
		horizontalBox.getChildren().add(new ChoiceBox<>(FXCollections.observableArrayList(DataTypeEnum.getTypes())));

		// add remove button to the the horizontal box
		horizontalBox.getChildren().add(newRemoveButton());
	}

	private Button newRemoveButton() {

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
		return btnRemove;
	}

	private static final String FX_TEXT_BOX_BORDER_RED = "-fx-text-box-border: red";

	/**
	 * Method to validate class
	 * 
	 * @throws Exception
	 */
	private void validateEnteredValues() throws Exception {

		// validate class name
		if (hasValidClassName()) {
			if (null != txtClassName.getStyle() && txtClassName.getStyle().contains(FX_TEXT_BOX_BORDER_RED)) {
				txtClassName.setStyle(null);
			}
		} else {
			txtClassName.setStyle(FX_TEXT_BOX_BORDER_RED);
		}

		// validate class field names
		for (Node memberNode : vboxMembers.getChildren()) {

			final TextField memberName = getMemberNameTextField((HBox) memberNode);
			if (isValidMemberName(memberName.getText())) {
				if (null != memberName.getStyle() && memberName.getStyle().contains(FX_TEXT_BOX_BORDER_RED)) {
					memberName.setStyle(null);
				}
			} else {
				memberName.setStyle(FX_TEXT_BOX_BORDER_RED);
			}
		}
	}

	private boolean hasInvalidData() throws Exception {
		// validate class name
		if (!hasValidClassName()) {
			return true;
		}

		// validate class field names
		for (Node memberNode : vboxMembers.getChildren()) {

			if (!isValidMemberName(getMemberNameTextField((HBox) memberNode).getText())) {
				return true;
			}
		}

		return false;
	}

	private boolean hasValidClassName() {
		return SourceVersion.isName(txtClassName.getText());
	}

	private static boolean isValidMemberName(final String p_strMemberName) {
		if (null == p_strMemberName)
			return false;

		return SourceVersion.isIdentifier(p_strMemberName);
	}

	@FXML
	private void generatePOJO() throws Exception {

		// checks the fields and class name then changes the color of the field
		validateEnteredValues();
		if (hasInvalidData()) {
			return;
		}

		// get POJO member map
		final Map<String, DataTypeEnum> nameToTypeMap = new HashMap<>();
		for (Node memberNode : vboxMembers.getChildren()) {
			final TextField txtMemberName = getMemberNameTextField((HBox) memberNode);
			final ChoiceBox<String> chboxDataTypes = getMemberChoiceBox((HBox) memberNode);

			nameToTypeMap.put(getMemberName(txtMemberName), getMemberDataType(chboxDataTypes));
		}

		try {
			// build POJO then print
			printToFile(new Pojo.Builder(txtClassName.getText(), nameToTypeMap).build());

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private void validateMemberNode(final HBox p_Node) throws Exception {
		if (p_Node.getChildren().size() != 3) {
			throw new IllegalArgumentException("Invalid Node.");
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

	private File getDestinationDirectory() {
		if (StringHelper.hasText(txtDestination.getText())) {
			return new File(txtDestination.getText());
		}

		throw new IllegalArgumentException("non-null text destination is required.");
	}

	private String buildJavaFileName() {
		if (StringHelper.hasText(txtDestination.getText())) {
			return String.format("%s%s%s.java", txtDestination.getText(), System.getProperty("file.separator"),
					txtClassName.getText());
		}

		throw new IllegalArgumentException("non-null text destination is required.");
	}

	private void printToFile(final Pojo pojo) throws IOException {
		if (StringHelper.hasText(txtDestination.getText())) {

			final File destDirectory = new File(txtDestination.getText());
			if (Files.isDirectory(destDirectory.toPath(), LinkOption.NOFOLLOW_LINKS)) {

				final String filename = String.format("%s%s%s.java", destDirectory.getAbsolutePath(),
						System.getProperty("file.separator"), pojo.getPojoClassName());

				final File file = new File(filename);

				try (FileWriter writer = new FileWriter(file)) {
					writer.write(pojo.toString());
					writer.flush();
				}

			}
		}
	}
}
