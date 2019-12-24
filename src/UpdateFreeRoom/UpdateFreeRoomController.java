package UpdateFreeRoom;

import ViewfreeclassSchedule.ViewSchedule;
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
import java.sql.SQLException;

public class UpdateFreeRoomController {
     @FXML
     private JFXTextField Lecture;
     @FXML
     private JFXTextField Mon;
     @FXML
     private JFXTextField Tue;
     @FXML
     private JFXTextField Wed;
     @FXML
     private JFXTextField Thu;
     @FXML
     private JFXTextField Fri;

     private Boolean editmode = Boolean.FALSE;
     @FXML
     private StackPane stackpane;
     @FXML
     private AnchorPane anchorpane;
     private Connectdb connectdb;
     public UpdateFreeRoomController(){
         connectdb = new Connectdb();
     }

    public void savebutton(ActionEvent event) throws SQLException {
        String a = Lecture.getText();
        String b = Mon.getText();
        String c = Tue.getText();
        String d = Wed.getText();
        String e = Thu.getText();
        String f= Fri.getText();
        if (editmode) {
            editoperation();
            return;
        }
        if (a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || e.isEmpty()||f.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Field should not be empty");
            alert.showAndWait();
            return;

        }
        else {
            String sql = "INSERT INTO schedule(Lecture,Mon,Tue,Wed,Thu,Fri) VALUES(?,?,?,?,?,?)";
            Connection connection = Connectdb.getConnections();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, a);
            preparedStatement.setString(2, b);
            preparedStatement.setString(3, c);
            preparedStatement.setString(4, d);
            preparedStatement.setString(5, e);
            preparedStatement.setString(6, f);
            preparedStatement.execute();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Free Room Added Successfully");
            alert.showAndWait();
            connection.close();
            Stage stage = (Stage) anchorpane.getScene().getWindow();
            stage.close();
            return;
        }


    }

    public void cancelbutton(ActionEvent event) {
        Stage stage = (Stage) anchorpane.getScene().getWindow();
        stage.close();

    }

    public void addinfo(ViewSchedule viewSchedule) {
        Lecture.setText(viewSchedule.getLecture());
        Mon.setText(viewSchedule.getMon());
        Tue.setText(viewSchedule.getTue());
        Wed.setText(viewSchedule.getWed());
        Thu.setText(viewSchedule.getThu());
        Fri.setText(viewSchedule.getFri());
        Lecture.setEditable(false);
        editmode = Boolean.TRUE;

    }

    public void editoperation() throws SQLException {
        ViewSchedule viewSchedule = new ViewSchedule(Lecture.getText(), Mon.getText(), Tue.getText(), Wed.getText(), Thu.getText(), Fri.getText());

        if (update(viewSchedule)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Free Room Info Updated Successfully");
            alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText(null);
            alert.setContentText("Failed to update Free Room Info");
            alert.showAndWait();
            return;


        }
    }

    public boolean update(ViewSchedule viewSchedule) throws SQLException {
        String sql = "UPDATE schedule SET Mon=?,Tue=?,Wed=?,Thu=?,Fri=? WHERE Lecture=?";
        Connection connection = Connectdb.getConnections();
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, viewSchedule.getMon());
        pst.setString(2, viewSchedule.getTue());
        pst.setString(3, viewSchedule.getWed());
        pst.setString(4, viewSchedule.getThu());
        pst.setString(5, viewSchedule.getFri());
        pst.setString(6, viewSchedule.getLecture());
        int res = pst.executeUpdate();
        return (res > 0);
    }







}


