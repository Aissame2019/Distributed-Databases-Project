package application.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import application.Main;
import application.Listners.MyListener;
import application.Connection.DbConnectionAgadir;
import application.Connection.DbConnectionCasa;
import application.Connection.DbConnectionLogin;
import application.models.Product;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;





public class ClientController implements Initializable  {

	private final Connection con;
	
    @FXML
    private ComboBox<Integer> comboBox;
	
	@FXML
    private Button addLineItem;
	
	@FXML
    private AnchorPane clientPage;
	
	@FXML
    private Label nameClient;
	
    @FXML
    private Label numItem;
    
    @FXML
    private Label total;
    
    @FXML
    private ImageView productImg;

    @FXML
    private Label productName;

    @FXML
    private Label productPrice;

    @FXML
    private ScrollPane scroll;
    
    @FXML
    private GridPane grid;
    
    private Image image;
    
    @FXML
    private VBox currCard;
    
    private List<Product> products = new ArrayList<Product>();
    
    private MyListener myListener;
    
    static public Product currProduct;
    
    private List<Product> getData() throws SQLException {
    	
        List<Product> products = new ArrayList<>();
        Product product;
        
        PreparedStatement ps;
        ResultSet rs;
        String query=null;
        if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
        	query = "Select * From product_agadir";
        }else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
        	query = "Select * From product_casa";
        }
        
        
        ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        
        
        while (rs.next()) {
        	product = new Product();
        	product.setProductID(rs.getInt("productID"));
        	product.setProductName(rs.getString("name"));
        	product.setProductType(rs.getString("type"));
        	product.setProductDescription(rs.getString("description"));
        	product.setProductPrice(rs.getDouble("price"));
        	product.setProductURL(rs.getString("imageurl"));
        	product.setColor(rs.getString("color"));
        	
        	products.add(product);
        }

        return products;
        }
    
    
    
    private void setChosenProduct(Product product) {
    	productName.setText("  "+product.getProductName());
        productPrice.setText(product.getProductPrice()+Main.CURRENCY);
        image = new Image(product.getProductURL());
        productImg.setImage(image);
        currCard.setStyle("-fx-background-color: #" + product.getColor() + ";\n" +"-fx-background-radius: 30;");
        currProduct=product;
    }
    
    
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
		
		
		

		
		
		
		
		
		
		
		
		//currProduct=products.get(0);
		
		for (int i = 1; i <= 10; i++) {
	        comboBox.getItems().add(i);
	    }
	    comboBox.setValue(1);
		
		
		PreparedStatement pss;
		ResultSet rss;
		
		String queryy=null;
		if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
        	queryy = "select orderid from orders_agadir";
        }else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
        	queryy = "select orderid from orders_casa";
        }
		try {
			pss = con.prepareStatement(queryy);
			rss = pss.executeQuery();
			
			if (rss.next()) {
				DbConnectionLogin.currentClient.setOrderid(rss.getInt("orderid"));
			}
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		nameClient.setText("  "+DbConnectionLogin.currentClient.getFirstname()+" "+DbConnectionLogin.currentClient.getLastname());

        PreparedStatement ps;
		ResultSet rs;
		
		String query=null;
		if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
        	query = "SELECT COUNT(*) FROM orders_agadir o JOIN lineitem_agadir l ON l.orderID = o.orderID WHERE userID = ? GROUP BY userID";
        }else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
        	query = "SELECT COUNT(*) FROM orders_casa o JOIN lineitem_casa l ON l.orderID = o.orderID WHERE userID = ? GROUP BY userID";
        }
		
		
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, DbConnectionLogin.currentClient.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				
				numItem.setText(String.valueOf(rs.getInt("COUNT(*)")));
				
				
			}else {numItem.setText("0");
			       total.setText("0");	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String query1=null;
		if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
        	query1 = "select totalAmount from orders_agadir where userID=?";
        }else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
        	query1 = "select totalAmount from orders_casa where userID=?";
        }
		
		try {
			ps = con.prepareStatement(query1);
			ps.setInt(1, DbConnectionLogin.currentClient.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				total.setText(String.valueOf(rs.getInt("totalAmount"))+" DH");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
        try {
        	products.addAll(getData());
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        //System.out.println(products.get(0).getProductName()); 
        
        if (products.size() > 0) {
            setChosenProduct(products.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Product product) {
                    setChosenProduct(product);
                }
            };
        }
        int column = 0;
        int row = 1;
		
        try {
            for (int i = 0; i < products.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("../Views/item.fxml"));
                Node anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(products.get(i),myListener);
                

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public ClientController(){
		
		if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
	        DbConnectionAgadir dbc = DbConnectionAgadir.getDatabaseConnection();
	        con = dbc.getConnection();
	        System.out.println("connected"); 
		}else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
	        DbConnectionCasa dbc = DbConnectionCasa.getDatabaseConnection();
	        con = dbc.getConnection();
		}else con=null;

	}
	
	@FXML
	private void addCommande(MouseEvent event) throws SQLException {
		PreparedStatement pps;
		
		String qquery=null;
		if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
        	qquery = "insert into lineitem_agadir values(?, ?, ?, ?)";
        }else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
        	qquery = "insert into lineitem_casa values(?, ?, ?, ?)";
        }
		
		pps = con.prepareStatement(qquery);
		
		pps.setInt(1, DbConnectionLogin.currentClient.getOrderid());
        pps.setInt(2, currProduct.getProductID());
        pps.setInt(3, comboBox.getValue());
        pps.setDouble(4, (currProduct.getProductPrice()*comboBox.getValue()));
        pps.execute();
        
        
        	Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Command Added");
            alert.setHeaderText(null);
            alert.setContentText("The command has been added to the order successfully!");

            // Apply custom CSS style to the alert
            alert.getDialogPane().setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));

            alert.showAndWait();
        
        
        
        PreparedStatement ps;
		ResultSet rs;
		
		String query=null;
		if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
        	query = "SELECT COUNT(*) FROM orders_agadir o JOIN lineitem_agadir l ON l.orderID = o.orderID WHERE userID = ? GROUP BY userID";
        }else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
        	query = "SELECT COUNT(*) FROM orders_casa o JOIN lineitem_casa l ON l.orderID = o.orderID WHERE userID = ? GROUP BY userID";
        }
		
		
		try {
			ps = con.prepareStatement(query);
			ps.setInt(1, DbConnectionLogin.currentClient.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				
				numItem.setText(String.valueOf(rs.getInt("COUNT(*)")));
				
				
			}else {numItem.setText("0");
			       total.setText("0");	}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String query1=null;
		if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
        	query1 = "select totalAmount from orders_agadir where userID=?";
        }else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
        	query1 = "select totalAmount from orders_casa where userID=?";
        }
		
		try {
			ps = con.prepareStatement(query1);
			ps.setInt(1, DbConnectionLogin.currentClient.getId());
			rs = ps.executeQuery();
			if (rs.next()) {
				total.setText(String.valueOf(rs.getInt("totalAmount"))+" DH");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
		
		
		
	}
	
	
	@FXML
	private void buy(MouseEvent event) throws SQLException {
		/*
		PreparedStatement pps;
		
		String qquery=null;
		if(DbConnectionLogin.currentClient.getVille().equals("AGADIR")) {
        	qquery = "insert into lineitem_agadir values(?, ?, ?, ?)";
        }else if(DbConnectionLogin.currentClient.getVille().equals("CASA")) {
        	qquery = "insert into lineitem_casa values(?, ?, ?, ?)";
        }
		
		pps = con.prepareStatement(qquery);*/
	}
	
	
	@FXML
    private void logout(MouseEvent event) throws IOException{
        Main.changeScene("Views/loginView.fxml");
        DbConnectionLogin.currentClient=null;
        currProduct = null;
    }
	
	
} 