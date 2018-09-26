import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        try {

            ImageParser img1 = new ImageParser("stego_cover18.bmp");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


