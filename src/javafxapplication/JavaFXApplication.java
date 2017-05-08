/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Jacob
 */
public class JavaFXApplication extends Application {
    
    boolean defBox = true;
    
    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        
        final Tooltip tooltip = new Tooltip();
        tooltip.setText("Swap the lists!");
        
        btn.setTooltip(tooltip);
        
        final ObservableList<String> defaultSet = 
            FXCollections.observableArrayList(
            "Item A",
            "Item B",
            "Item C",
            "Item D",
            "Item E",
            "Item F",
            "Item G"
        );
        
        final ObservableList<String> webSet = getWebList();
        
        
        final ComboBox comboBox = new ComboBox();
        for(String s : defaultSet){
            comboBox.getItems().add(s);
        }
        
        btn.setText("Switch ComboBox Entries");
        btn.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                if(defBox){
                    comboBox.getItems().clear();
                    for(String s : webSet){
                        System.out.println("adding "+s+" to the list");
                        comboBox.getItems().add(s);
                    }
                    defBox = false;
                } else {
                    comboBox.getItems().clear();
                    for(String s : defaultSet){
                        System.out.println("adding "+s+" to the list");
                        comboBox.getItems().add(s);
                    }
                    defBox = true;
                }
            }
        });
        
        
        GridPane grid = new GridPane();
        grid.setVgap(4);
        grid.setHgap(4);
        grid.add(comboBox,9,10);
        grid.add(btn,10,10);
        
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Java FX Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private ObservableList<String> getWebList(){
        ObservableList<String> toRet = FXCollections.observableArrayList();
        try{
            URL url = new URL("https://jsonplaceholder.typicode.com/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");         

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            
            String output;
            System.out.println("JSON response : ");

            while ((output = br.readLine()) != null) {
                if(output.indexOf("\"id\"") != -1){
                    String s = output.split("\\s+")[2];
                    s = s.substring(0,s.length()-1);
                    toRet.add("id: "+s);
                }
                System.out.println(output);
            }
            conn.disconnect();
        } catch(Exception e){
            e.printStackTrace();
        }
        
        return toRet;
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
