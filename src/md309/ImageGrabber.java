package md309;

import javafx.scene.image.Image;
import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scott on 3/1/14.
 */
final class ImageGrabber {
    private VideoCapture vc;
    private List<Point> bounds;


    private double frameHeight = 0;
    private double frameWidth = 0;

    public ImageGrabber() {
        // Should take device as a parameter
        vc = new VideoCapture(0);
        frameHeight =  640; //vc.get(Highgui.CV_CAP_PROP_FRAME_HEIGHT);
        frameWidth = 480; // vc.get(Highgui.CV_CAP_PROP_FRAME_WIDTH);
    }

    public void setTransform(Double[] points) {
        bounds = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Point p = new Point();
            p.x = points[2 * i];
            p.y = points[2 * i + 1];
            bounds.add(i, p);
        }
    }

    public void resetTransform() {
        bounds = null;
    }

    public Image grab() {
        Mat m = new Mat();
        vc.read(m);

        if (bounds != null) {
            MatOfPoint2f src = new MatOfPoint2f(bounds.get(0),
                    bounds.get(1),
                    bounds.get(2),
                    bounds.get(3));
            // TODO: Don't hardcode things
            MatOfPoint2f dst = new MatOfPoint2f(new Point(0,0),
                                                new Point(frameWidth,0),
                                                new Point(frameWidth,frameHeight),
                                                new Point(0,frameHeight));
            Mat transform = Imgproc.getPerspectiveTransform(src,dst);
            Mat result = new Mat();
            Imgproc.warpPerspective(m,result,transform,new Size(frameWidth,frameHeight));
            m = result;
        }

        MatOfByte mb = new MatOfByte();
        Highgui.imencode(".jpg",m,mb);
        return new Image(new ByteArrayInputStream(mb.toArray()));
    }
}
