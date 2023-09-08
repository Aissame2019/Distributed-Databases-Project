package application.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import application.Main;
import application.Listners.MyListener;
import application.models.Product;

public class ItemController {
   
    @FXML
    private Label productName;
    
    @FXML
    private Label price;
    
    @FXML
    private ImageView imageItem;
    
    private Product product;
    
    private MyListener myListener;
    
    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(product);
    }
    
    public void setData(Product product, MyListener myListener) {
        this.product = product;
        this.myListener = myListener;
        productName.setText("  "+product.getProductName());
        price.setText("  "+product.getProductPrice()+" "+Main.CURRENCY);
        Image image = new Image(product.getProductURL());
        imageItem.setImage(image);
        price.setStyle("-fx-background-color: #" + product.getColor() + ";");
        productName.setStyle("-fx-background-color: #" + product.getColor() + ";");
    }
	
}
