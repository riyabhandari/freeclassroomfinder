package ViewfreeclassSchedule;

import UpdateFreeRoom.UpdateFreeRoomController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Connectdb;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewScheduleController implements Initializable {
    @FXML
    private TableView<ViewSchedule> tableview = new TableView<>();
    @FXML
    private TableColumn<ViewSchedule, String> lecturecol = new TableColumn<>();
    @FXML
    private TableColumn<ViewSchedule, String> moncol = new TableColumn<>();
    @FXML
    private TableColumn<ViewSchedule, String> tuecol = new TableColumn<>();
    @FXML
    private TableColumn<ViewSchedule, String> wedcol = new TableColumn<>();
    @FXML
    private TableColumn<ViewSchedule, String> thucol = new TableColumn<>();
    @FXML
    private TableColumn<ViewSchedule, String> fricol = new TableColumn<>();
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private StackPane stackpane;

    private Connectdb connectdb;
    private ObservableList<ViewSchedule> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lecturecol.setCellValueFactory(new PropertyValueFactory<ViewSchedule, String>("lecture"));
        moncol.setCellValueFactory(new PropertyValueFactory<ViewSchedule, String>("mon"));
        tuecol.setCellValueFactory(new PropertyValueFactory<ViewSchedule, String>("tue"));
        wedcol.setCellValueFactory(new PropertyValueFactory<ViewSchedule, String>("wed"));
        thucol.setCellValueFactory(new PropertyValueFactory<ViewSchedule, String>("thu"));
        fricol.setCellValueFactory(new PropertyValueFactory<ViewSchedule, String>("fri"));
        tableview.setItems(list);

    }


    public ViewScheduleController() throws SQLException {
        connectdb = new Connectdb();
        loaddata();
    }



    public void loaddata() throws SQLException {
        list.clear();
        String sql = "SELECT *FROM schedule";
        Connection connection = Connectdb.getConnections();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String lecture = resultSet.getString("Lecture");
            String mon = resultSet.getString("Mon");
            String tue = resultSet.getString("Tue");
            String wed = resultSet.getString("Wed");
            String thu = resultSet.getString("Thu");
            String fri = resultSet.getString("Fri");
            list.add(new ViewSchedule(lecture,mon,tue,wed,thu,fri));

        }

    }
    public void freeroomedit(ActionEvent event) throws IOException {
        ViewSchedule  viewSchedule = tableview.getSelectionModel().getSelectedItem();
        if (viewSchedule == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Please Select a record to edit info");
            alert.showAndWait();
            return;

        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateFreeRoom/UpdateFreeRoom.fxml"));
        Parent root = (Parent) loader.load();
        UpdateFreeRoomController controller = (UpdateFreeRoomController) loader.getController();
        controller.addinfo(viewSchedule);
        Stage stage = new Stage();
        stage.getIcons().add(new Image("sample/img/a.png"));
        stage.setTitle("Edit Free Classes");
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void roomrefresh(ActionEvent event) throws SQLException {
        loaddata();
    }


}



