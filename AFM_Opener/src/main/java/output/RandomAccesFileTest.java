package output;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Drimal
 */
public class RandomAccesFileTest {

    public static void main(String[] args) {
        try {
            File file = new File("c:\\Users\\Drimal\\Downloads\\allDpFiles\\0 stupnu-2013.11.26-14.03.22_height_trace.jpk");
//    32912, "???", value=1918990080, count=4

            byte[] loadedBytes = readFromFile(file, 1442840576, 2);
            System.out.println(new String(loadedBytes, "UTF-8"));

            Path path = Paths.get("c:\\Users\\Drimal\\Downloads\\zasilka-CHKRI8DLZPAYS4EY\\thyroglobulin 669 kDa-2013.08.13-13.59.14_height_trace.jpk");
            byte[] data = Files.readAllBytes(path);
            System.out.println("nio data size: " + data.length);
        } catch (IOException ex) {
            Logger.getLogger(RandomAccesFileTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static byte[] readFromFile(File file, int position, int size)
            throws IOException {
        System.out.println("File size: " + file.length());
        RandomAccessFile randomAccessor = new RandomAccessFile(file, "r");
        randomAccessor.seek(position);
        byte[] bytes = new byte[size];
        randomAccessor.read(bytes);
        randomAccessor.close();
        return bytes;

    }
}
