package md309;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {
    @FXML
    private ImageView camview;
    @FXML
    private Pane imagepane;
    @FXML
    private Polygon bounds;

    private ImageGrabber ig;

    public void initialize(URL location, ResourceBundle resources) {
        ig = new ImageGrabber();
        for (int i = 0; i < bounds.getPoints().size(); i += 2) {
            Circle handle = new Circle(bounds.getPoints().get(i), bounds.getPoints().get(i+1), 5);
            handle.setFill(Color.WHITE);
            handle.setStroke(Color.BLACK);
            handle.setStrokeWidth(1);
            setDragHandler(handle);
            final AtomicInteger polyCoordinateIndex = new AtomicInteger(i);
            handle.centerXProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    bounds.getPoints().set(polyCoordinateIndex.get(), newValue.doubleValue());
                }
            });
            handle.centerYProperty().addListener(new ChangeListener<Number>() {
                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    bounds.getPoints().set(polyCoordinateIndex.get() + 1, (Double) newValue);
                }
            });
            imagepane.getChildren().add(handle);
        }
    }

    private double dragDeltaX, dragDeltaY;

    private void setDragHandler(final Circle circle) {
        circle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                dragDeltaX = circle.getCenterX() - mouseEvent.getSceneX();
                dragDeltaY = circle.getCenterY() - mouseEvent.getSceneY();
            }
        });

        circle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                circle.setCenterX( mouseEvent.getSceneX() + dragDeltaX );
                circle.setCenterY( mouseEvent.getSceneY() + dragDeltaY );
                circle.setCursor(Cursor.MOVE);
            }
        });

        circle.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                circle.setCursor(Cursor.HAND);
            }
        });

        circle.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent mouseEvent) {
                circle.setCursor(Cursor.HAND);
            }
        });
    }

    public void doCapture(ActionEvent event) {
        camview.setImage(ig.grab());
    }

    public void doTransform(ActionEvent event) {
        Double[] points = new Double[8];
        ig.setTransform(bounds.getPoints().toArray(points));
    }

    public void doReset(ActionEvent event) {
        ig.resetTransform();
    }
}
