package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class Controller {
    @FXML
    protected TextField lecture;
    @FXML
    protected TextField day;
    private Connectdb connectdb;
    @FXML
    private StackPane rootpane;
    public Controller() throws SQLException {
        connectdb = new Connectdb();

    }



    public void menuclose(ActionEvent event) {
        ((Stage) rootpane.getScene().getWindow()).close();
    }



    public void menuaddschedule(ActionEvent event) throws IOException {
        loadwindow("/AddSchedule/AddSchedule.fxml", "Add Schedule", "sample/img/a.png");

    }



    public void menuviewschedule(ActionEvent event) throws IOException {
        loadwindow("/Schedule/ViewSchedule.fxml", "View Schedule", "sample/img/a.png");

    }
    public void menufreeclass(ActionEvent event) throws IOException {
        loadwindow("/ViewfreeclassSchedule/ViewSchedule.fxml", "View Free classes", "sample/img/a.png");

    }

    public void loadwindow(String loc, String title, String img) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(loc));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(img));
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.show();


    }





    public void addschedule(ActionEvent event) throws IOException {
        loadwindow("/AddSchedule/AddSchedule.fxml", "Add Schedule", "sample/img/a.png");


    }


    public void listschedule(ActionEvent event) throws IOException {
        loadwindow("/Schedule/ViewSchedule.fxml", "View Schedule", "sample/img/a.png");


    }

    public void listfreeclass(ActionEvent event) throws IOException {
        loadwindow("/ViewfreeclassSchedule/ViewSchedule.fxml", "View FreeClasses", "sample/img/a.png");


    }
    public void search(ActionEvent event) throws SQLException {
        String mon,tue,wed,thu,fri;
        if (lecture.getText().isEmpty() || day.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Enter LectureNo and Day");
            alert.showAndWait();
            return;

        }
        Connection connection=Connectdb.getConnections();
        PreparedStatement pst=connection.prepareStatement("SELECT * FROM schedule WHERE Lecture='"+lecture.getText()+"'");
        ResultSet rs=pst.executeQuery();
        while(rs.next()) {
            String text;
            mon = rs.getString("Mon");
            tue = rs.getString("Tue");
            wed = rs.getString("Wed");
            thu = rs.getString("Thu");
            fri = rs.getString("Fri");

            if(day.getText().equalsIgnoreCase("Mon")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Free Class Availability Status");
                alert.setContentText(mon);
                alert.showAndWait();
                return;
            }
            else if (day.getText().equalsIgnoreCase("Tue")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Free Class Availability Status");
                alert.setContentText(tue);
                alert.showAndWait();
                return;
            }

            else if (day.getText().equalsIgnoreCase("Wed")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Free Class Availability Status");
                alert.setContentText(wed);
                alert.showAndWait();
                return;
            }

            else if (day.getText().equalsIgnoreCase("Thu")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Free Class Availability Status");
                alert.setContentText(thu);
                alert.showAndWait();
                return;
            }
            else if (day.getText().equalsIgnoreCase("Fri")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Free Class Availability Status");
                alert.setContentText(fri);
                alert.showAndWait();
                return;
            }

        }


    }




}
