module org.fxstudy.encoderdecryptofx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;
    requires java.desktop;


    opens org.fxstudy.encoderdecryptofx to javafx.fxml;
    exports org.fxstudy.encoderdecryptofx;
}