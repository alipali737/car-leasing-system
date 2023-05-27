package com.leasecompany.carleasingsystem.ui.creation;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.car.Car;
import com.leasecompany.carleasingsystem.database.data.car.CarDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
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

        InputValidation.createLengthCheck(validator, "bodyType", bodyTypeField, 3, 15);
        InputValidation.createLengthCheck(validator, "brand", brandField, 3, 25);
        InputValidation.createLengthCheck(validator, "model", modelField, 3, 25);
        InputValidation.createLengthCheck(validator, "spec", specField, 0, 25);
        InputValidation.createLengthCheck(validator, "prodYear", prodYearField, 4, 4);
        InputValidation.createLengthCheck(validator, "description", descriptionField, 0, 300);
        InputValidation.createLengthCheck(validator, "doors", doorsField, 1, 1);
        InputValidation.createLengthCheck(validator, "engineSize", engineSizeField, 1, 4);
        InputValidation.createLengthCheck(validator, "color", colorField, 3, 25);
        InputValidation.createLengthCheck(validator, "fuel", fuelTypeField, 3, 15);
        InputValidation.createLengthCheck(validator, "seats", seatsField, 1, 1);
        InputValidation.createLengthCheck(validator, "registration", registrationField, 8, 8);

        InputValidation.createOnlyLettersCheck(validator, "bodyType", bodyTypeField, false);
        InputValidation.createOnlyLettersCheck(validator, "brand", brandField, true);
        InputValidation.createOnlyLettersCheck(validator, "color", colorField, true);
        InputValidation.createOnlyLettersCheck(validator, "fuelType", fuelTypeField, false);

        InputValidation.createNumericCheck(validator, "prodYear", prodYearField, false);
        InputValidation.createNumericCheck(validator, "doors", doorsField, false);
        InputValidation.createNumericCheck(validator, "engineSize", engineSizeField, true);
        InputValidation.createNumericCheck(validator, "seats", seatsField, false);
        InputValidation.createNumericCheck(validator, "mileage", mileageField, true);
        InputValidation.createNumericCheck(validator, "value", valueField, true);

        InputValidation.createNumericRangeCheck(validator, "prodYear", prodYearField, 1950, Year.now().getValue());
        InputValidation.createNumericRangeCheck(validator, "doors", doorsField, 1, 9);
        InputValidation.createNumericRangeCheck(validator, "engineSize", engineSizeField, 0, 10);
        InputValidation.createNumericRangeCheck(validator, "seats", seatsField, 1, 9);
        InputValidation.createNumericRangeCheck(validator, "mileage", mileageField, 0, 999_999);
        InputValidation.createNumericRangeCheck(validator, "value", valueField, 0,9_999_999);

        InputValidation.createRegexCheck(validator, "registration", registrationField,
                InputValidation.ukRegistrationRegex,
                "Registration doesn't fit UK Standard: AA00 AAA or AA00 000.");

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
