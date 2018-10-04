import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ImageParser {

    ArrayList<Pixel> pixelArray;


public ImageParser(String img) throws IOException {

    BufferedImage in = ImageIO.read(new File(img));

    pixelArray = new ArrayList<>();

    getRLTBPixelValues(in);

    decode(testAlgorithm());

}

    public void printPixelRGB(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        pixelArray.add(new Pixel(red, green, blue));

    }

    private void getLRTBPixelValues(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("width, height: " + w + ", " + h);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = image.getRGB(j, i);
                printPixelRGB(pixel);
            }
        }
    }

    private void getRLTBPixelValues(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("width, height: " + w + ", " + h);

        for (int i = h-1; i >= 0; i--) {
            for (int j = w-1; j >=0; j--) {
                int pixel = image.getRGB(j, i);
                printPixelRGB(pixel);
            }
        }
    }


    public String testAlgorithm(){

    StringBuilder messageBinary = new StringBuilder();

        for (Pixel pixel : pixelArray){
            String red = Integer.toBinaryString(pixel.getRedValue());
            String green = Integer.toBinaryString(pixel.getGreenValue());
            String blue = Integer.toBinaryString(pixel.getBlueValue());

            String redLast = red.substring(red.length() - 1);
            String greenLast = green.substring(green.length() - 1);
            String blueLast = blue.substring(blue.length() - 1);

            messageBinary.append(redLast + greenLast + blueLast);
        }

        return messageBinary.toString();
    }



        private void decode(String messageBinary){

        System.out.println("Long binary - " + messageBinary);

            BigInteger bigInt = new BigInteger(messageBinary,2);
            byte[] outputArray = bigInt.toByteArray();
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDSS");
            String name = "output" + sdf.format(d);
            try {
                File outputFile = new File("ayy" + name + ".txt");
                FileOutputStream fos = new FileOutputStream(outputFile);
                fos.write(outputArray);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }








