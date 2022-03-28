module com.example.inzsysfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.xml;
    requires dom4j;
    requires java.sql;
    requires java.sql.rowset;
    requires mysql.connector.java;

    opens com.example.inzsysfx to javafx.fxml;
    exports com.example.inzsysfx;
    exports com.example.inzsysfx.utils;
    opens com.example.inzsysfx.entities to javafx.base;
    opens com.example.inzsysfx.controllers to javafx.fxml;
    opens com.example.inzsysfx.utils to javafx.fxml;
}