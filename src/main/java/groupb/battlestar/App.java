package groupb.battlestar;

/**
 * Hello world!
 *
 */
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;

public class App extends Application {
  public void start(Stage stage) {
    Label hello = new Label("Hello JavaFX world!!");
    Group root = new Group(hello);
    Scene scene = new Scene(root);
    stage.setTitle("Hello World!");
    stage.setScene(scene);
    stage.show();
  }
  public static void main(String[] args) {
    launch();
}
}