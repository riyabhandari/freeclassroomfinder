package Schedule;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import sample.Connectdb;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewDetailController implements Initializable {
    @FXML
    private TableView<Schedule> tableview = new TableView<>();
    @FXML
    private TableColumn<Schedule, String> branchcol = new TableColumn<>();
    @FXML
    private TableColumn<Schedule, String> daycol = new TableColumn<>();
    @FXML
    private TableColumn<Schedule, String> lecture1col = new TableColumn<>();
    @FXML
    private TableColumn<Schedule, String> lecture2col = new TableColumn<>();
    @FXML
    private TableColumn<Schedule, String> lecture3col = new TableColumn<>();
    @FXML
    private TableColumn<Schedule, String> lecture4col = new TableColumn<>();
    @FXML
    private TableColumn<Schedule, String> lecture5col = new TableColumn<>();
    @FXML
    private TableColumn<Schedule, String> lecture6col = new TableColumn<>();
    @FXML
    private AnchorPane anchorpane;
    @FXML
    private StackPane stackpane;

    private Connectdb connectdb;
    private ObservableList<Schedule> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        branchcol.setCellValueFactory(new PropertyValueFactory<Schedule, String>("branch"));
        daycol.setCellValueFactory(new PropertyValueFactory<Schedule, String>("day"));
        lecture1col.setCellValueFactory(new PropertyValueFactory<Schedule, String>("lecture1"));
        lecture2col.setCellValueFactory(new PropertyValueFactory<Schedule, String>("lecture2"));
        lecture3col.setCellValueFactory(new PropertyValueFactory<Schedule, String>("lecture3"));
        lecture4col.setCellValueFactory(new PropertyValueFactory<Schedule, String>("lecture4"));
        lecture5col.setCellValueFactory(new PropertyValueFactory<Schedule, String>("lecture5"));
        lecture6col.setCellValueFactory(new PropertyValueFactory<Schedule, String>("lecture6"));
        tableview.setItems(list);


    }


    public ViewDetailController() throws SQLException {
        connectdb = new Connectdb();
        loaddata();
    }



    public void loaddata() throws SQLException {
        list.clear();
        String sql = "SELECT *FROM class_schedule";
        Connection connection = Connectdb.getConnections();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String branch = resultSet.getString("branch");
            String day = resultSet.getString("day");
            String lecture1 = resultSet.getString("lecture1");
            String lecture2 = resultSet.getString("lecture2");
            String lecture3= resultSet.getString("lecture3");
            String lecture4= resultSet.getString("lecture4");
            String lecture5= resultSet.getString("lecture5");
            String lecture6= resultSet.getString("lecture6");
            list.add(new Schedule(branch,day,lecture1,lecture2,lecture3,lecture4,lecture5,lecture6));

        }

    }

}
