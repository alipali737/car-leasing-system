package com.leasecompany.carleasingsystem.ui.creation;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.car.Car;
import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import com.leasecompany.carleasingsystem.utils.validation.CustomValidationDecoration;
import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import net.synedra.validatorfx.Validator;
import org.apache.commons.text.WordUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Year;

public class CreateVehicleController implements UIController {
    @FXML private TextField bodyTypeField;
    @FXML private TextField brandField;
    @FXML private TextField modelField;
    @FXML private TextField specField;
    @FXML private TextField prodYearField;
    @FXML private TextField descriptionField;
    @FXML private TextField doorsField;
    @FXML private TextField engineSizeField;
    @FXML private TextField colorField;
    @FXML private TextField fuelTypeField;
    @FXML private TextField seatsField;
    @FXML private TextField registrationField;
    @FXML private TextField mileageField;
    @FXML private TextField valueField;
    @FXML private Button submitButton;
    @FXML private Button uploadButton;

    private File selectedImageFile;

    private Validator validator;
    private CarDAO carDAO;

    public void initialize() {
        carDAO = DAOFactory.getInstance().newCarDAO();

        submitButton.setOnAction(this::handleSubmitButton);
        uploadButton.setOnAction(this::handleImageUploadButton);

        // Create validator checks
        validator = new Validator();

        createLengthCheck("bodyType", bodyTypeField, 3, 15);
        createLengthCheck("brand", brandField, 3, 25);
        createLengthCheck("model", modelField, 3, 25);
        createLengthCheck("spec", specField, 0, 25);
        createLengthCheck("prodYear", prodYearField, 4, 4);
        createLengthCheck("description", descriptionField, 0, 300);
        createLengthCheck("doors", doorsField, 1, 1);
        createLengthCheck("engineSize", engineSizeField, 1, 4);
        createLengthCheck("color", colorField, 3, 25);
        createLengthCheck("fuel", fuelTypeField, 3, 15);
        createLengthCheck("seats", seatsField, 1, 1);
        createLengthCheck("registration", registrationField, 8, 8);

        createOnlyLettersCheck("bodyType", bodyTypeField);
        createOnlyLettersCheck("brand", brandField);
        createOnlyLettersCheck("color", colorField);
        createOnlyLettersCheck("fuelType", fuelTypeField);

        createNumericCheck("prodYear", prodYearField, false);
        createNumericCheck("doors", doorsField, false);
        createNumericCheck("engineSize", engineSizeField, true);
        createNumericCheck("seats", seatsField, false);
        createNumericCheck("mileage", mileageField, true);
        createNumericCheck("value", valueField, true);

        createNumericRangeCheck("prodYear", prodYearField, 1950, Year.now().getValue());
        createNumericRangeCheck("doors", doorsField, 1, 9);
        createNumericRangeCheck("engineSize", engineSizeField, 0, 10);
        createNumericRangeCheck("seats", seatsField, 1, 9);
        createNumericRangeCheck("mileage", mileageField, 0, 999_999);
        createNumericRangeCheck("value", valueField, 0,9_999_999);

        // Check UK Reg regex
        validator.createCheck()
                .dependsOn("registration", registrationField.textProperty())
                .withMethod(c -> {
                    String var = c.get("registration");
                    if (!InputValidation.isUkRegistration(var)) {
                        c.error("Registration doesn't fit UK Standard: AA00 AAA or AA00 000.");
                    }
                })
                .decorates(registrationField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

    }

    private void createLengthCheck(String key, TextField textField, int min, int max) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.lengthInRange(var, min, max)) {
                        c.error("Please keep your name between " + min + " and " + max + " characters.");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    private void createOnlyLettersCheck(String key, TextField textField) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.onlyContainsLetters(var)) {
                        c.error("Please only enter letters, no special characters or numbers allowed.");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    private void createNumericCheck(String key, TextField textField, boolean doubleAllowed) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.isNumeric(var, doubleAllowed)) {
                        c.error("Please only enter a number.");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    private void createNumericRangeCheck(String key, TextField textField, double min, double max) {
        validator.createCheck()
                .dependsOn(key, textField.textProperty())
                .withMethod(c -> {
                    String var = c.get(key);
                    if (!InputValidation.numericRange(var, min, max)) {
                        c.error("Please keep the value between " + min + " and " + max + ".");
                    }
                })
                .decorates(textField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    private void handleImageUploadButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null && selectedFile.length() <= (200*1024)) {
            selectedImageFile = selectedFile;
        }
    }

    private void handleSubmitButton(ActionEvent event) {
        if (!validator.validate()) {
            return;
        }

        byte[] imageFileBytes = null;
        if (selectedImageFile != null) {
            try {
                imageFileBytes = carDAO.convertToByteArray(selectedImageFile);
            } catch (FileNotFoundException e) {
                System.err.println("Unable to find selected image file");
            }
        }

        Car newCar = new Car(
                Integer.parseInt(doorsField.getText()),
                Double.parseDouble(engineSizeField.getText()),
                WordUtils.capitalizeFully(colorField.getText()),
                WordUtils.capitalizeFully(fuelTypeField.getText()),
                Integer.parseInt(seatsField.getText()),
                WordUtils.capitalizeFully(bodyTypeField.getText()),
                WordUtils.capitalizeFully(brandField.getText()),
                WordUtils.capitalizeFully(modelField.getText()),
                WordUtils.capitalizeFully(specField.getText()),
                Integer.parseInt(prodYearField.getText()),
                descriptionField.getText(),
                registrationField.getText(),
                Integer.parseInt(mileageField.getText()),
                Double.parseDouble(valueField.getText()),
                imageFileBytes
        );
        
        if (!carDAO.create(newCar)) {
            System.err.println("Failed to create new car in database");
            return;
        }

        SceneController.changeScene(SceneController.creationFXMLPath, submitButton);
    }

    @Override
    public void recieveInformation(Object data) {
    }
}
