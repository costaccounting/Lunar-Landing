import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * The main controller for the rocket landing game 
 * 
 * @author prodip
 * @since 7/23/2017
 */
public class MainController implements Initializable {

    PhysicsEngine rocket = new PhysicsEngine(); //rocket mathematical functions

    @FXML
    Button buttonStart; //start button
    @FXML
    Button buttonThrust; //thrust button

    @FXML
    TextField txtName; //text field for entering name
    @FXML
    Text txtVelocity; //text showing rocket velocity 

    @FXML
    Text txtMsg; //text showing final message

    @FXML
    Text txtHeight; //text showing rocket height

    @FXML
    ProgressBar barHeight; // bar displaying height of rocket

    @FXML
    ProgressBar barFuel; //bar displaying fuel remaining
    
    @FXML ImageView rocketImg; //image of a rocket
    @FXML ImageView moonImg; //image of the moon
    
    double imageInitial = 20; //rocket image initial Y value.
    boolean stoptrigger = false; //condition for game ended
    double imgDist =  130.0; //distance between top of rocket image and moon image
            

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Timeline oneSec = new Timeline(new KeyFrame(Duration.millis(100), (ActionEvent event) -> { 
            if (buttonStart.isPressed()) {
                onButtonStartClicked(); //start button activates the game
            }  
            if(rocket.isActive())rocketRunFuctions(); //rocket status update. 

            if (buttonThrust.isPressed() ) {
                rocket.thruster_active(); //update rocket status with thruster on
            } else {
                rocket.thruster_inactive(); //update rocket status with thruster off
            }
            
            if(rocket.getHeight()==0 && rocket.getVelocity()<0) {
                finish();
            } // finish game
    
        }));
        oneSec.setCycleCount(Timeline.INDEFINITE);
        oneSec.play();
        
    }
/**
 * method for staring the rocket
 */
    public void onButtonStartClicked() {
        txtMsg.setText("Player "+ txtName.getText() +" has the control. Land the rocket with "
                + "speed less than 5.");
        buttonThrust.setVisible(true);
        rocket.start();

    }
/**
 * method for updating rocket status. 
 */
    public void rocketRunFuctions() {
        if(!rocket.nextStep(rocket.isThrusterActive())){
            txtHeight.setText("" + (int) rocket.getHeight());
            barHeight.setProgress(rocket.getHeight() / rocket.getIntialHeight());
            double velocity = rocket.getVelocity();
            txtVelocity.setText("" + String.format("%.2g%n",velocity));
            barFuel.setProgress(rocket.getFuel() / 100);
            rocketImg.setY(imageInitial - imgDist* (rocket.getHeight() - rocket.getIntialHeight())/ rocket.getIntialHeight())  ;

            if (velocity >= -5 && velocity <0) {
                txtVelocity.setFill(Color.GREEN);
            }else {
                txtVelocity.setFill(Color.RED);
            }
        }
        else {
            finish();
            showalert();
                    
        }
    }
    /**
     * method for end status of rocket
     */
    public void finish(){
        stoptrigger = true;
        
    }
    /**
     * method generates an alert
     */
    public void showalert(){
        String name = "Player " + txtName.getText();
        Alert alert_success = new Alert(AlertType.INFORMATION,
            "Congratulations "+name+", you landed successfully.");
        Alert alert_fail = new Alert(AlertType.INFORMATION,
            "Sorry "+name+", your rocket crashed.");
        
        if(rocket.getVelocity()>-5 && rocket.getVelocity()<=0) alert_success.show();
        else alert_fail.show();

    }
    

}
