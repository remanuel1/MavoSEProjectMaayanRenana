package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("testblue", 10, 16);
        for(int i=0; i<10; i++) {
            for (int j = 0; j < 16; j++) {
                imageWriter.writePixel(i, j, new Color(0d,0d,255d));
            }
        }
        imageWriter.writeToImage();
    }

}