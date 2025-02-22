package com.leasecompany.carleasingsystem.ui.login;

import com.leasecompany.carleasingsystem.database.data.DAOFactory;
import com.leasecompany.carleasingsystem.database.data.user.User;
import com.leasecompany.carleasingsystem.database.data.user.UserDAO;
import com.leasecompany.carleasingsystem.ui.UIController;
import com.leasecompany.carleasingsystem.utils.scene.SceneController;
import com.leasecompany.carleasingsystem.utils.validation.CustomValidationDecoration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.synedra.validatorfx.Validator;

public class LoginController implements UIController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink registerLink;

    private Validator validator;
    private UserDAO userDAO;

    public void initialize() {
        userDAO = DAOFactory.getInstance().newUserDAO();
        loginButton.setOnAction(this::handleLoginButtonClick);
        registerLink.setOnAction(this::handleRegisterLink);

        validator = new Validator();

        // Validate entry boxes aren't empty (length == 0)
        validator.createCheck()
                .dependsOn("username", usernameField.textProperty())
                .withMethod(c -> {
                    String username = c.get("username");
                    if (username.isEmpty()) {
                        c.error("Field cannot be empty.");
                    }
                })
                .decorates(usernameField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));

        validator.createCheck()
                .dependsOn("password", passwordField.textProperty())
                .withMethod(c -> {
                    String password = c.get("password");
                    if (password.isEmpty()) {
                        c.error("Field cannot be empty.");
                    }
                })
                .decorates(passwordField)
                .decoratingWith(msg -> new CustomValidationDecoration(msg.getText()));
    }

    private void handleLoginButtonClick(ActionEvent event) {
        if (!validator.validate()) {
            return;
        }

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (authenticate(username, password)) {
            // authenticated, move to home screen
            DAOFactory.setLoggedInUser(userDAO.findByUsername(username));
            SceneController.changeScene(SceneController.homeFXMLPath, loginButton);
        } else {
            // auth rejected, move to failed screen
            SceneController.changeScene(SceneController.loginFailedFXMLPath, loginButton);
        }
    }

    private void handleRegisterLink(ActionEvent event) {
        SceneController.changeScene(SceneController.registerFXMLPath, registerLink);
    }

    private boolean authenticate(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return false;
        }
        if (!user.getApproved()) {
            return false;
        }

        String hashedPassword = userDAO.hashPassword(password);
        return (hashedPassword.equals(user.getPasswordHash()));
    }

    @Override
    public void recieveInformation(Object data) {}
}
