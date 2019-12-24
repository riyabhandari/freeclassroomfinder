package Login;

import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Connectdb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginController {
    private Connectdb connectDb;
    @FXML
    private JFXButton btn_signin, btn_signup,btn_login,btn_signupscreen,close;
    @FXML
    private AnchorPane pn_signin, pn_signup;
    @FXML
    private JFXTextField email,signup_email,signup_name;
    @FXML
    private JFXPasswordField password,signup_password;
    @FXML
    private StackPane main;
    @FXML
    private AnchorPane anchorPane,anchorPane1;
    private double xOffset=0;
    private double yOffset=0;
    public LoginController() throws SQLException {
        connectDb=new Connectdb();

    }

    public void onclick(ActionEvent event) throws SQLException, IOException {
        if(event.getSource()==close){
            Stage stage = (Stage) anchorPane.getScene().getWindow();
            stage.close();


        }
        if (event.getSource() == btn_signin) {
            pn_signin.toFront();
            signup_name.setText("");
            signup_email.setText("");
            signup_password.setText("");
            email.setText("");
            password.setText("");



        }else if(event.getSource()==btn_login){

            if(email.getText().isEmpty()||password.getText().isEmpty()){
                loadDialog_emptyEmailPassword();


            }
            else {
                if(isLogin(email.getText(),password.getText())){
                    String type=check_type(email.getText(),password.getText());
                    System.out.println("Login successful");
                    Stage stage = (Stage) anchorPane.getScene().getWindow();
                    stage.close();
                    if(type.equals("AD")){
                        loadmain();

                    }
                    else if(type.equals("SD")){
                        System.out.println("SD");
                        loadsd();

                    }
                }


            }
        }
        else if(event.getSource()==btn_signupscreen){
            sign_up();
        }
        else {
            if (event.getSource() == btn_signup) {
                pn_signup.toFront();

                signup_name.setText("");
                signup_email.setText("");
                signup_password.setText("");
                email.setText("");
                password.setText("");

            }
        }
    }

    public boolean isLogin(String email,String password) throws SQLException {

        boolean notVerified=false;
        boolean wrongDetails=true;


        try {
            String query = "select * from user WHERE email= '" + email + "'";
            Connection connection = Connectdb.getConnections();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();



            String emaillocal = resultSet.getString("email");
            String passwordlocal = resultSet.getString("password");
            String allowed = resultSet.getString("allowed");
           // System.out.println(emaillocal + passwordlocal);
            if(emaillocal.equals(email)&&(!passwordlocal.equals(password))){
                loadDialog_wrongDetails();
                return false;
            }

            if (emaillocal.equals(email) && passwordlocal.equals(password)) {
                if (allowed.equals("true")) {
                    return true;
                } else {
                    notVerified = true;
                    wrongDetails=false;


                }
            } else {
                return false;
            }
        }catch (SQLException e){

        }

        if(wrongDetails)
            loadDialog_wrongDetails();
        return false;


    }
    public String check_type(String email,String password) throws SQLException {
        String type=null;




        try {
            String query = "select * from user WHERE email= '" + email + "'";
            Connection connection = Connectdb.getConnections();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            type= resultSet.getString("type");



        }catch (SQLException e){

        }


        return type;


    }
    public boolean check_user(String email)  {
        String emaillocal=null;

        String query="select * from user  WHERE email= '"+email+"'";
        Connection connection = null;
        try {
            connection = Connectdb.getConnections();
            PreparedStatement preparedStatement=connection.prepareStatement(query);
            ResultSet resultSet=preparedStatement.executeQuery();
            emaillocal=resultSet.getString("email");
            connection.close();
        } catch (SQLException e) {
            return false;

        }



        if(emaillocal.equals(email)){
            return true;


        }


        return false;

    }
    public void sign_up() throws SQLException {
        String email = signup_email.getText();
        String name = signup_name.getText();
        String password = signup_password.getText();
        String id = null;
        boolean firstuser=false;
        String q = "select max(email) from user;";
        Connection conn = null;
        try {
            conn = Connectdb.getConnections();
            PreparedStatement preparedStatement = conn.prepareStatement(q);
            ResultSet resultSet = preparedStatement.executeQuery();
            id = resultSet.getString(1);
            conn.close();

        } catch (SQLException e) {




        }
        System.out.println(id);
        if(id==null){
            firstuser=true;
        }


        if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
            loadDialog_emptyNameEmailPassword();
            return;

        }
        else if(check_user(email)){
            loadDialog_AlreadyRegistered();
            return;

        }
        else if(!isValid(email)){
            System.out.println(email);
            loadDialog_wrongEmail();
            return;
        }
        else if(isAlpha(name)){
            System.out.println(password);
            loadDialog_wrongName();
            return;

        }
        else {
            String query = "INSERT INTO user(email,name,password,type) VALUES(?,?,?,?)";
            Connection connection = Connectdb.getConnections();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, password);
            if(firstuser){
                preparedStatement.setString(4, "AD");

            }
            else {
                preparedStatement.setString(4, "SD");
            }
            preparedStatement.execute();
            if(firstuser){
                loadDialog_firstuserregistered();

            }
            else {
                loadDialog_registered();
            }
        }
    }
    @FXML
    private void loadDialog_registered(){
        BoxBlur boxBlur =new BoxBlur(3,3,3);
        signup_name.setText("");
        signup_email.setText("");
        signup_password.setText("");

        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("Verification"));


        content.setBody(new Text("Registration successful."));
        final JFXDialog dialog=new JFXDialog(main,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog.close();

            }
        });
        content.setActions(button);

        dialog.show();
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane1.setEffect(null);

            }
        });
        anchorPane1.setEffect(boxBlur);
    }
    @FXML
    private void loadDialog_firstuserregistered(){
        BoxBlur boxBlur =new BoxBlur(3,3,3);
        signup_name.setText("");
        signup_email.setText("");
        signup_password.setText("");

        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("Registration successful"));


        content.setBody(new Text("Registration successful. As this is the first account, you are admin now."));
        final JFXDialog dialog=new JFXDialog(main,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog.close();

            }
        });
        content.setActions(button);

        dialog.show();
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane1.setEffect(null);

            }
        });
        anchorPane1.setEffect(boxBlur);
    }
    @FXML
    private void loadDialog_AlreadyRegistered(){
        BoxBlur boxBlur =new BoxBlur(3,3,3);
        signup_name.setText("");
        signup_email.setText("");
        signup_password.setText("");

        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("User already exists"));


        content.setBody(new Text("This account is already registered"));
        final JFXDialog dialog=new JFXDialog(main,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog.close();

            }
        });
        content.setActions(button);

        dialog.show();
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane1.setEffect(null);

            }
        });
        anchorPane1.setEffect(boxBlur);
    }
    @FXML
    private void loadDialog_wrongEmail(){
        BoxBlur boxBlur =new BoxBlur(3,3,3);
        signup_email.setText("");

        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("Invalid Email"));


        content.setBody(new Text("Please enter valid email to sign up"));
        final JFXDialog dialog=new JFXDialog(main,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Try again");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();

            }
        });
        content.setActions(button);

        dialog.show();
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane1.setEffect(null);

            }
        });
        anchorPane1.setEffect(boxBlur);
    }
    @FXML
    private void loadDialog_wrongName(){
        BoxBlur boxBlur =new BoxBlur(3,3,3);
        signup_name.setText("");

        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("Invalid Name"));


        content.setBody(new Text("Please enter valid name to sign up"));
        final JFXDialog dialog=new JFXDialog(main,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Try again");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();

            }
        });
        content.setActions(button);

        dialog.show();
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane1.setEffect(null);

            }
        });
        anchorPane1.setEffect(boxBlur);
    }
    @FXML
    private void loadDialog_wrongDetails(){
        BoxBlur boxBlur =new BoxBlur(3,3,3);
        email.setText("");
        password.setText("");
        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("Incorrect details"));


        content.setBody(new Text("Incorrect email or password please try again."));
        final JFXDialog dialog=new JFXDialog(main,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Try again");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();

            }
        });
        content.setActions(button);

        dialog.show();
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane1.setEffect(null);

            }
        });
        anchorPane1.setEffect(boxBlur);
    }
    @FXML
    private void loadDialog_emptyEmailPassword(){
        BoxBlur boxBlur =new BoxBlur(3,3,3);

        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("Empty Field"));


        content.setBody(new Text("Please enter your email and password to continue"));
        final JFXDialog dialog=new JFXDialog(main,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                dialog.close();

            }
        });
        content.setActions(button);
        dialog.show();
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane1.setEffect(null);

            }
        });
        anchorPane1.setEffect(boxBlur);
    }
    @FXML
    private void loadDialog_emptyNameEmailPassword(){
        BoxBlur boxBlur =new BoxBlur(3,3,3);

        JFXDialogLayout content=new JFXDialogLayout();
        content.setHeading(new Text("Empty Field"));


        content.setBody(new Text("Please enter your details to sign up"));
        final JFXDialog dialog=new JFXDialog(main,content,JFXDialog.DialogTransition.CENTER);
        JFXButton button=new JFXButton("Okay");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();

            }
        });
        content.setActions(button);

        dialog.show();
        dialog.setOnDialogClosed(new EventHandler<JFXDialogEvent>() {
            @Override
            public void handle(JFXDialogEvent event) {
                anchorPane1.setEffect(null);

            }
        });
        anchorPane1.setEffect(boxBlur);
    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public boolean isAlpha(String s) {

        for(int i=0;i<s.length();i++){
            char ch = s.charAt(i);
            if (Character.isLetter(ch) || ch == ' ' || ch== '.') {
                continue;
            }
            return true;
        }
        return false;
    }
    public  void loadmain()throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/sample.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("sample/img/a.png"));
        stage.setTitle("Free Classroom Pro");
        stage.setScene(new Scene(root));
        stage.show();

    }
    public  void loadsd()throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/standard.fxml"));
        Parent root = (Parent) loader.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image("sample/img/a.png"));
        stage.setTitle("Free Classroom Pro");
        stage.setScene(new Scene(root));
        stage.show();

    }


}
