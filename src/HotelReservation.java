import javafx.application.Application;
import javafx.stage.Stage;

public class HotelReservation extends Application {

    @Override
    public void start(Stage primaryStage) {
        HotelController controller = new HotelController();
        MainGUI mainGUI = new MainGUI(controller);
        mainGUI.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}