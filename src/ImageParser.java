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

    //Reads Image In
    BufferedImage in = ImageIO.read(new File(img));

    pixelArray = new ArrayList<>();

    //Direction of reading pixels, storing in a Pixel array
    getRLBTPixelValues(in);

    //Decodes the Array of pixels from the pixelArray and creates the .txt output
    decode();

}

    public void addPixelRGB(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        pixelArray.add(new Pixel(red, green, blue));

    }

    private void getLRTBPixelValues(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pixel = image.getRGB(j, i);
                addPixelRGB(pixel);
            }
        }
    }

    private void getRLBTPixelValues(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("Getting values for Right to Left, Bottom to Top...");

        for (int i = h-1; i >= 0; i--) {
            for (int j = w-1; j >=0; j--) {
                int pixel = image.getRGB(j, i);
                addPixelRGB(pixel);
            }
        }
    }


    public void decode(){

    StringBuilder messageBinary = new StringBuilder();

        System.out.println("Building Binary String...");

        for (Pixel pixel : pixelArray){
            String red = Integer.toBinaryString(pixel.getRedValue());
            String green = Integer.toBinaryString(pixel.getGreenValue());
            String blue = Integer.toBinaryString(pixel.getBlueValue());

            String redLast = red.substring(red.length() - 1);
            String greenLast = green.substring(green.length() - 1);
            String blueLast = blue.substring(blue.length() - 1);

            messageBinary.append(redLast + greenLast + blueLast);
        }

        System.out.println("Creating .txt file...");


        BigInteger bigInt = new BigInteger(messageBinary.toString(),2);
        byte[] outputArray = bigInt.toByteArray();

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDDSS");

        String name = "text-output-" + sdf.format(d);
        try {
            File outputFile = new File(name + ".txt");
            FileOutputStream fos = new FileOutputStream(outputFile);
            fos.write(outputArray);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");


    }

    }








