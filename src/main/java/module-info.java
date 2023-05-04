module com.leasecompany.carleasingsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.naming;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;
    requires org.kordamp.bootstrapfx.core;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires org.hibernate.commons.annotations;
    requires java.sql;
    requires jakarta.persistence;
    requires jakarta.transaction;
    requires jakarta.cdi;
    requires jakarta.xml.bind;
    requires com.fasterxml.classmate;
    requires net.bytebuddy;

    exports com.leasecompany.carleasingsystem;
    exports com.leasecompany.carleasingsystem.ui.login;
    opens com.leasecompany.carleasingsystem.ui.login to javafx.fxml;
    exports com.leasecompany.carleasingsystem.utils.validation;
    opens com.leasecompany.carleasingsystem.utils.validation to javafx.fxml;
    exports com.leasecompany.carleasingsystem.ui.shared;
    opens com.leasecompany.carleasingsystem.ui.shared to javafx.fxml;
    exports com.leasecompany.carleasingsystem.ui.home;
    opens com.leasecompany.carleasingsystem.ui.home to javafx.fxml;

    exports com.leasecompany.carleasingsystem.database.data.car;
    opens com.leasecompany.carleasingsystem.database.data.car to org.hibernate.orm.core;
    exports com.leasecompany.carleasingsystem.database.data.inventoryItem;
    opens com.leasecompany.carleasingsystem.database.data.inventoryItem to org.hibernate.orm.core;
    exports com.leasecompany.carleasingsystem.database.data.user;
    opens com.leasecompany.carleasingsystem.database.data.user to org.hibernate.orm.core;
}