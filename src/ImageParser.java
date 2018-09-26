import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageParser {


public ImageParser(String img) throws IOException {

    BufferedImage in = ImageIO.read(new File(img));

}


}
