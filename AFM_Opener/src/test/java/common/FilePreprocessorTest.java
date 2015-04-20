package common;

import java.io.File;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class FilePreprocessorTest {

    private static final Logger logger = Logger.getLogger(FilePreprocessorTest.class.getName());
    private String parentPath = FilePreprocessorTest.class.getResource("/").getPath();

    /**
     * Test of preloadJpkImageFiles method, of class FilePreprocessor2.
     */
    @Test
    public void prealoadJpkFileFromDirectory() {
        File parent = new File(parentPath);
        FilePreloader instance = new FilePreloader();

        List<ChannelContainer> result = instance.preloadJpkImageFiles(parent);
        assertEquals("Wrong size of result", 9, result.size());
        for (int i = 0; i < 2; i++) {
            ChannelContainer container = result.get(i);
            assertEquals("Channel index is not appropriate", container.getChannelIndex(), i);
            assertEquals(container.getFile().getName(), "RPA ACTA 0 stupnu podruhe-2013.10.25-15.08.38_height_retrace.jpk");
        }
        for (int i = 0; i < 7; i++) {
            ChannelContainer container = result.get(i + 2);
            assertEquals("Channel index is not appropriate", container.getChannelIndex(), i);
            assertEquals(container.getFile().getName(), "thyroglobulin 669 kDa-2013.08.13-13.59.14.jpk");
        }
    }

    @Test
    public void prealoadJpkFileFromOneFile() {
        File parent = new File(parentPath + "/thyroglobulin 669 kDa-2013.08.13-13.59.14.jpk");
        FilePreloader instance = new FilePreloader();

        List<ChannelContainer> result = instance.preloadJpkImageFiles(parent);
        assertEquals("Wrong size of result", 7, result.size());
        for (int i = 0; i < result.size(); i++) {
            ChannelContainer container = result.get(i);
            assertEquals("Channel index is not appropriate", container.getChannelIndex(), i);
            assertEquals(container.getFile().getName(), "thyroglobulin 669 kDa-2013.08.13-13.59.14.jpk");
        }
    }
}
