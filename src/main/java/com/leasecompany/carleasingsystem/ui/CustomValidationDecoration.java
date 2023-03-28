package com.leasecompany.carleasingsystem.ui;

import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;
import net.synedra.validatorfx.Decoration;

public class CustomValidationDecoration implements Decoration {

    private final String errorMessage;

    public CustomValidationDecoration(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void remove(Node node) {
        node.setStyle(null);
        Tooltip tooltip = (Tooltip) node.getUserData();
        if (tooltip != null) {
            Tooltip.uninstall(node, tooltip);
        }
    }

    @Override
    public void add(Node node) {
        node.setStyle("-fx-border-color: red; -fx-border-width: 2px; -fx-border-style: solid;");
        Tooltip tooltip = new Tooltip(errorMessage);
        tooltip.setStyle("-fx-font-size: 14px; -fx-background-color: red; -fx-text-fill: white;");
        tooltip.setShowDelay(Duration.ZERO);
        tooltip.setShowDuration(Duration.INDEFINITE);
        Tooltip.install(node, tooltip);
        node.setUserData(tooltip);
    }
}
