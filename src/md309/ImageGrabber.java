package md309;

import javafx.scene.image.Image;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by scott on 3/1/14.
 */
final class ImageGrabber {
    private VideoCapture vc;
    private Point[] bounds;

    public ImageGrabber() {
        // Should take device as a parameter
        vc = new VideoCapture(0);

    }

    public void setTransform(Double[] points) {
        bounds = new Point[4];
        for (int i = 0; i < 4; i++) {
            Point p = new Point();
            p.x = points[2 * i];
            p.y = points[2 * i + 1];
            bounds[i] = p;
        }
    }

    public void resetTransform() {
        bounds = null;
    }

    public Image grab() {
        Mat m = new Mat();
        vc.read(m);

        if (bounds != null) {
            // DO THE TRANSFORM
        }

        MatOfByte mb = new MatOfByte();
        Highgui.imencode(".jpg",m,mb);
        return new Image(new ByteArrayInputStream(mb.toArray()));
    }
}
