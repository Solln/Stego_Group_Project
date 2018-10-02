import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class ImageParser {

    ArrayList<Pixel> pixelArray;


public ImageParser(String img) throws IOException {

    BufferedImage in = ImageIO.read(new File(img));

    pixelArray = new ArrayList<>();

    getPixelValues(in);

    decode(testAlgorithm());

}

    public void printPixelRGB(int pixel) {
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
//        System.out.println("rgb: " + red + ", " + green + ", " + blue);
//        System.out.println("Bit Values: " + Integer.toBinaryString(red) + ", " + Integer.toBinaryString(green) + ", " + Integer.toBinaryString(blue));
        pixelArray.add(new Pixel(red, green, blue));

    }

    private void getPixelValues(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("width, height: " + w + ", " + h);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
//                System.out.println("x,y: " + j + ", " + i);
                int pixel = image.getRGB(j, i);
                printPixelRGB(pixel);
//                System.out.println("");
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

//            messageBinary.append(redLast + greenLast + blueLast);
//            messageBinary.append(redLast);
//            messageBinary.append(greenLast);
            messageBinary.append(blueLast);

        }

        return messageBinary.toString();
    }


    private void decode(String messageBinary){

    System.out.println("Long binary - " + messageBinary);

        StringBuilder sb = new StringBuilder(); // Some place to store the chars

            Arrays.stream( // Create a Stream
                    messageBinary.toString().split("(?<=\\G.{8})") // Splits the input string into 8-char-sections (Since a char has 8 bits = 1 byte)
            ).forEach(s -> // Go through each 8-char-section...
                    sb.append((char) Integer.parseInt(s, 2)) // ...and turn it into an int and then to a char
            );

        String output = sb.toString(); // Output text (t)
        System.out.println("output - " + output);

    }


}






