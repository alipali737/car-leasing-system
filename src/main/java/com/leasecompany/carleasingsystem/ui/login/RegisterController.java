package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.user.User;
import com.leasecompany.carleasingsystem.database.data.user.UserDAO;
import com.leasecompany.carleasingsystem.ui.Controller;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import com.leasecompany.carleasingsystem.utils.validation.CustomValidationDecoration;
import com.leasecompany.carleasingsystem.utils.validation.InputValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

public class RegisterController implements Controller {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confPasswordField;
    @FXML
    private Button registerButton;
    @FXML
    private Hyperlink loginLink;

    private Validator validator;
    private UserDAO userDAO;

    public void initialize() {
        userDAO = DAOFactory.getInstance().newUserDAO();

        registerButton.setOnAction(this::handleRegisterButtonClick);
        loginLink.setOnAction(this::handleLoginLinkClick);

        validator = new Validator();

        // Validate name (length, characters)
        validator.createCheck()
                .dependsOn("username", usernameField.textProperty())
                .withMethod(c -> {
                    String name = c.get("username");
                    if (!InputValidation.lengthInRange(name, 3, 25)) {
                        c.error("Please keep your name between 3 and 25 characters.");
                    }
                })
                .decorates(usernameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        validator.createCheck()
                .dependsOn("username", usernameField.textProperty())
                .withMethod(c -> {
                    String username = c.get("username");
                    if (!InputValidation.onlyContainsLettersOrNumbers(username)) {
                        c.error("Please only include letters or Numbers, no special characters.");
                    }
                })
                .decorates(usernameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        // Validate username not already in db
        validator.createCheck()
                .dependsOn("username", usernameField.textProperty())
                .withMethod(c -> {
                    String username = c.get("username");
                    if (userDAO.usernameAlreadyExists(username)) {
                        c.error("Username already exists, please choose a new one.");
                    }
                })
                .decorates(usernameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        // Validate passwords (length, the same)
        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String name = c.get("password");
                    if (!InputValidation.lengthInRange(name, 8, 50)) {
                        c.error("Please keep your password between 8 and 50 characters.");
                    }
                })
                .decorates(passwordField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .dependsOn("conf-password", confPasswordField.textProperty())
                .withMethod(c -> {
                    String password = c.get("password");
                    String confPassword = c.get("conf-password");
                    if (!password.equals(confPassword)) {
                        c.error("Password entries do not match.");
                    }
                })
                .decorates(passwordField)
                .decorates(confPasswordField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }


    private void handleRegisterButtonClick(ActionEvent event) {
        if (validator.validate()) {
            String hashedPassword = userDAO.hashPassword(passwordField.getText());
            User newUser = new User(usernameField.getText(), hashedPassword);
            if (userDAO.create(newUser)) {
                SceneController.changeScene(SceneController.registerConfirmationFXMLPath, registerButton);
            } else {
                SceneController.changeScene(SceneController.registerFailedFXMLPath, registerButton);
            }
        }
    }

    private void handleLoginLinkClick(ActionEvent event) {
        SceneController.changeScene(SceneController.loginFXMLPath, loginLink);
    }

    @Override
    public void recieveInformation(Object data) {}
}
