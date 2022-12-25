module com.example.s_a_l {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.s_a_l to javafx.fxml;
    exports com.example.s_a_l;
}