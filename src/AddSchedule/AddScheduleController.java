package AddSchedule;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Connectdb;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddScheduleController {
    @FXML
    private JFXTextField branch;
    @FXML
    private JFXTextField day;
    @FXML
    private JFXTextField lecture1;
    @FXML
    private JFXTextField lecture2;
    @FXML
    private JFXTextField lecture3;
    @FXML
    private JFXTextField lecture4;
    @FXML
    private JFXTextField  lecture5;
    @FXML
    private JFXTextField  lecture6;

    @FXML
    private StackPane stackpane;
    @FXML
    private AnchorPane anchorpane;
    private String id;
    private Connectdb connectdb;

    public AddScheduleController() {
        connectdb = new Connectdb();
    }


    public void savebutton(ActionEvent event) throws SQLException {
        String a = branch.getText();
        String b= day.getText();
        String c = lecture1.getText();
        String d = lecture2.getText();
        String e = lecture3.getText();
        String f = lecture4.getText();
        String g = lecture5.getText();
        String h=lecture6.getText();
        if (a.isEmpty() || b.isEmpty()||c.isEmpty()||d.isEmpty()||e.isEmpty()||f.isEmpty()||g.isEmpty()||h.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Field should not be empty");
            alert.showAndWait();
            return;

        }    else if (isAlpha(b)) {
            invalid_day();
            return;
        }
        else {
            String sql = "INSERT INTO class_schedule(branch,day,lecture1,lecture2,lecture3,lecture4,lecture5,lecture6) VALUES(?,?,?,?,?,?,?,?)";
            Connection connection = Connectdb.getConnections();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, a);
            preparedStatement.setString(2, b);
            preparedStatement.setString(3,c);
            preparedStatement.setString(4,d);
            preparedStatement.setString(5,e);
            preparedStatement.setString(6,f);
            preparedStatement.setString(7,g);
            preparedStatement.setString(8,h);
            preparedStatement.execute();
            preparedStatement.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Class schedule added Successfully ");
            alert.showAndWait();
            connection.close();
            Stage stage = (Stage) anchorpane.getScene().getWindow();
            stage.close();
            return;
        }


    }

    public void cancelbutton(ActionEvent event) {
        Stage stage = (Stage) stackpane.getScene().getWindow();
        stage.close();

    }
    public void invalid_day() {
        day.setText("");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Please enter valid day");
        alert.showAndWait();
        return;


    }

    public boolean isAlpha(String s) {

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isLetter(ch) || ch == ' ' || ch == '.') {
                continue;
            }
            return true;
        }
        return false;
    }



}

