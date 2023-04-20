module com.leasecompany.carleasingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;

    opens com.leasecompany.carleasingsystem to javafx.fxml;
    exports com.leasecompany.carleasingsystem;
    exports com.leasecompany.carleasingsystem.ui.login;
    opens com.leasecompany.carleasingsystem.ui.login to javafx.fxml;
    exports com.leasecompany.carleasingsystem.utils.validation;
    opens com.leasecompany.carleasingsystem.utils.validation to javafx.fxml;
    exports com.leasecompany.carleasingsystem.ui.shared;
    opens com.leasecompany.carleasingsystem.ui.shared to javafx.fxml;
    exports com.leasecompany.carleasingsystem.ui.home;
    opens com.leasecompany.carleasingsystem.ui.home to javafx.fxml;
}