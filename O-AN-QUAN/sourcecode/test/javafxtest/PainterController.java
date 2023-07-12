package test.javafxtest;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PainterController {

    @FXML
    private ToggleGroup Tools;

    @FXML
    private Pane drawingAreaPane;

    @FXML
    private RadioButton eraser;

    @FXML
    private RadioButton pen;

    @FXML
    void clearButtonPressed(ActionEvent event) {
        drawingAreaPane.getChildren().clear();

    }

    @FXML
    void drawingAreaMouseDragged(MouseEvent event) {
        Circle draw = new Circle(event.getX(),event.getY(),4,Color.BLACK);
        Circle clear = new Circle(event.getX(),event.getY(),10,Color.WHITE);

        if (pen.isSelected()){
            drawingAreaPane.getChildren().add(draw);
        }
        else if (eraser.isSelected()){
            drawingAreaPane.getChildren().add(clear);

        }

    }

}

// way 2: handle togglePress

// public class PainterController {
//     private  Color color;
//     @FXML
//     private Pane drawingAreaPane;
//     @FXML
//     void clearPress(ActionEvent e){
//         drawingAreaPane.getChildren().clear();
//     }
//     @FXML
//     ToggleButton pen;
//     @FXML
//     ToggleButton eraser;

//     @FXML
//     void togglePress(ActionEvent e){
//         if (e.getSource() == pen){
//             color = Color.BLACK;
//         } else if (e.getSource() == eraser){
//             color = Color.WHITE;
//         }

//     }
//     @FXML
//     void drawingAreaMouseDragged(MouseEvent e){
//         if (e.getX()>0) {
//             Circle newCircle = new Circle(e.getX(), e.getY(), 4, color);

//             drawingAreaPane.getChildren().add(newCircle);
//         }
//     }
// }

