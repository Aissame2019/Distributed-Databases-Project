package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import application.Main;
import application.Connection.DbConnectionCasa;
import application.Connection.DbConnectionLogin;
import application.models.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LoginController implements Initializable {

	private final Connection con;
	
    @FXML
    private TextField username1;

    @FXML
    private TextField password1;

    @FXML
    private Button loginButton1;
    
    @FXML
    private AnchorPane loginPage;

    Window window;	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
    }
	
    public LoginController() {
        DbConnectionLogin dbc = DbConnectionLogin.getDatabaseConnection();
        con = dbc.getConnection();
    	
    }
    
    
    @FXML
    private void login(ActionEvent e) throws Exception {
    	if(e.getSource() == loginButton1) {
    		
    		if (this.isValidatedLogin()) {
                PreparedStatement ps;
                PreparedStatement ps1;
                ResultSet rs;
                ResultSet rs1;

                String query = "select * from users WHERE username = ? and password = ?";
                try {
                    ps = con.prepareStatement(query);
                    ps.setString(1, username1.getText());
                    ps.setString(2, password1.getText());
                    rs = ps.executeQuery();
                    
                    if (rs.next()) {
                    	
                    	if(rs.getString("role").equals("admin")) {
                    		
                            Stage stage = (Stage) loginButton1.getScene().getWindow();
                            stage.close();

                            //Parent root = FXMLLoader.load(getClass().getResource("/view/MainPanelView.fxml"));

                            //Scene scene = new Scene(root);

                            //stage.setScene(scene);
                            stage.setTitle("Admin Panel");
                            //stage.getIcons().add(new Image("/asset/icon.png"));
                            stage.show();
                    	
                            System.out.println("admin");
                            
                    	}else {
                    		System.out.println("Bind Client");
                    		
                    		
                    		
                        	String query1 = "select * from clients where clientid=?";
                        	ps1 = con.prepareStatement(query1);
                        	
                        	System.out.println(rs.getInt("userid"));
                        	ps1.setInt(1, rs.getInt("userid"));
                            
                            rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                DbConnectionLogin.currentClient = new Client(rs1.getInt("clientid"), rs1.getString("firstname"), rs1.getString("lastname"), rs1.getString("address"), rs1.getString("ville"), rs1.getString("email"),  rs1.getString("phone"),rs1.getInt("code_postal"));
                                
                                Main.changeScene("Views/ClientView.fxml");
                                
                                
                            } 

                            
                    	}
                    	


                    } else {
                    	
                        AlertHelper.showAlert(AlertType.ERROR, window, "Error", "Invalid username and password.");
                        username1.requestFocus();
                    }
                } catch (SQLException ex) {
                    System.out.println(ex);
                } 
                //Parent clientPage = FXMLLoader.load(getClass().getResource("../Views/ClientView.fxml"));
        		
                //loginPage.getChildren().removeAll();
                //loginPage.getChildren().setAll(clientPage);
            }
    		
    	}
        
}
    


    private boolean isValidatedLogin() {

        window = loginButton1.getScene().getWindow();
        if (username1.getText().equals("")) {
            //AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
            //        "Username text field cannot be blank.");
            //username.requestFocus();
        } else if (username1.getText().length() < 5 || username1.getText().length() > 25) {
           // AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
           //         "Username text field cannot be less than 5 and greator than 25 characters.");
            //username.requestFocus();
        } else if (password1.getText().equals("")) {
           // AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
           //         "Password text field cannot be blank.");
            //password.requestFocus();
        } else if (password1.getText().length() < 5 || password1.getText().length() > 25) {
            //AlertHelper.showAlert(Alert.AlertType.ERROR, window, "Error",
             //       "Password text field cannot be less than 5 and greator than 25 characters.");
            //password.requestFocus();
        } 
            return true;
       
    }

    @FXML
    private void showRegisterDialog() throws IOException {
        //Stage stage = (Stage) loginButton.getScene().getWindow();
        //stage.close();

        //System.out.println("show sign up");
        
        //Parent root = FXMLLoader.load(getClass().getResource("/view/RegisterView.fxml"));

        //Scene scene = new Scene(root);

        //stage.setScene(scene);
        //stage.setTitle("User Registration");
        //stage.getIcons().add(new Image("/asset/icon.png"));
        //stage.show();
    	
		Dialog<Client> dialog = new Dialog<>();
        dialog.setTitle("Add User");
        dialog.setHeaderText("Enter your information");
        dialog.setResizable(false);
        
        ButtonType addBtn = new ButtonType("Create User", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addBtn, ButtonType.CANCEL);
        
        Label username = new Label("Username: ");
        TextField usernameField = new TextField();
   
        Label firstname = new Label("First name: ");
        TextField firstnameField = new TextField();
        
        Label lastname = new Label("Last name: ");
        TextField lastnameField = new TextField();
        
        Label address = new Label("Address: ");
        TextField addressField = new TextField();
        
        Label code_postal = new Label("Code postal: ");
        TextField code_postalField = new TextField();
        
        Label ville = new Label("City: ");
        RadioButton agadir = new RadioButton("Agadir");
        RadioButton casa = new RadioButton("Casa");
        ToggleGroup villeGroup = new ToggleGroup();
        agadir.setToggleGroup(villeGroup);
        casa.setToggleGroup(villeGroup);
        HBox villeBox = new HBox(10, agadir, casa);
        //TextField villeField = new TextField();
        
        Label email = new Label("email: ");
        TextField emailField = new TextField();
        
        Label phone = new Label("Phone number: ");
        TextField phoneField = new TextField();
        
        Label password = new Label("Password: ");
        TextField passwordField = new TextField();
    	
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.addRow(0, username, usernameField);
        grid.addRow(1, firstname, firstnameField);
        grid.addRow(2, lastname, lastnameField);
        grid.addRow(3, ville, villeBox);
        grid.addRow(4, address, addressField);
        grid.addRow(5, code_postal, code_postalField);
        grid.addRow(6, email, emailField);
        grid.addRow(7, phone, phoneField);
        grid.addRow(8, password, passwordField);
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(button -> {
        	if(button == addBtn) {
        		
        		
        		String city = agadir.isSelected() ? "AGADIR":"CASA";
        		
        		
        		Client c = new Client(usernameField.getText(), passwordField.getText(), firstnameField.getText(), lastnameField.getText(), addressField.getText(), city, emailField.getText(), phoneField.getText(),Integer.parseInt(code_postalField.getText()));
        		if(this.isValidatedRegister(c)) {
        			return c;
        		}else { 
        			JOptionPane.showMessageDialog(null, "Invalid Input !");
        			return null;
        		}
        		
        		
        	}
        	return null;
        });
        
        Optional<Client> result = dialog.showAndWait();
        result.ifPresent(cl -> {
            
        	//add Client to data base      
        	try {
				this.addUser(cl);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        	PreparedStatement ps1;
            String query2=null;
            String query3=null;
            
            if(cl.getVille().equals("AGADIR")) {
            	query2 = "insert into orders_agadir(orderid, userid, totalamount) values(sqNumOrder.nextval, sqNumUser.currval, ?)";
            }else if(cl.getVille().equals("CASA")) {
            	query3 = "insert into orders_casa(orderid, userid, totalamount) values(sqorder.nextval, ?, ?)";
            	System.out.println("query ...");
            }
            
            try {
            	if(cl.getVille().equals("CASA")) {
            	ps1 = con.prepareStatement("select userid from users where ville='CASA' and username=? and password=?");
	            ps1.setString(1, cl.getUsername());
	            ps1.setString(2, cl.getPassword());
	            ResultSet rss = ps1.executeQuery();
            	if(rss.next()) {
            		DbConnectionCasa dbc = DbConnectionCasa.getDatabaseConnection();
                    Connection conn = dbc.getConnection();
                    ps1 = conn.prepareStatement(query3);
                    ps1.setInt(1, rss.getInt("userid"));
                    ps1.setInt(2, 0);
                    ps1.execute();
                    System.out.println("insert succes");
            	}
            	}
            	if(cl.getVille().equals("AGADIR")) {
    				ps1 = con.prepareStatement(query2);
    	            ps1.setDouble(1, 0);;
    	            ps1.execute();
            	}
            	
                
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        	
        });
        
    }
    
   
	private void addUser(Client cl) throws SQLException {
    	
    	
            PreparedStatement ps;
            
            

            String query = "insert into users(userid, username, password, role, ville) values(sqNumUser.nextval, ?, ?, ?, ?)";
            String query1 = "insert into clients(clientid, firstname, lastname, address, code_postal, email, phone, ville) values(sqNumUser.currval, ?, ?, ?, ?, ?, ?, ?)";

            
            
            
            try {
                ps = con.prepareStatement(query);
                
                ps.setString(1, cl.getUsername()); //usernameRfield.getText()
                ps.setString(2, cl.getPassword()); //passwordR.getText()
                ps.setString(3, "client");
                ps.setString(4, cl.getVille());
                ps.execute();
                
                
                
                //or create a trigger that insert into client where role == client
                ps = con.prepareStatement(query1);
                
                ps.setString(1, cl.getFirstname()); //firstnameRfield.getText()
                ps.setString(2, cl.getLastname()); //lastnameRfield.getText()
                ps.setString(3, cl.getAddress());	//addressRfield.getText()
                ps.setInt(4, cl.getCode_postal()); // code_postalRfield.getText()
                ps.setString(7, cl.getVille()); //villeRfield.getText()
                ps.setString(5, cl.getEmail()); //emailRfield.getText()
                ps.setString(6, cl.getPhone());	//phoneRfield.getText()
                ps.execute();
                
                
               
                
                
                
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        
    	
    }

	private boolean isValidatedRegister(Client cl) {
		
		return true;
	}
    
}

	

