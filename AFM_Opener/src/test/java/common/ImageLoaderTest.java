package common;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import metadata.decoder.ChannelMetadata;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.mockito.Mockito.*;
import selector.ChannelContainer;

/**
 *
 * @author Drimal
 */
public class ImageLoaderTest {

    private static ChannelMetadata metadata = null;

    @BeforeClass
    public static void setUp() {
        metadata = mock(ChannelMetadata.class);
        when(metadata.getIFD()).thenReturn("123");
        when(metadata.getTagValue(anyInt())).thenReturn("channelTwo");
    }

    /**
     * Test of loadImages method, of class ImageLoader.
     */
    @Test
    public void testLoadImages_List() {
        System.out.println("loadImages");
        ChannelContainer container = new ChannelContainer(new File("c:\\Users\\Drimal\\Skola\\dp\\AFM_Opener\\target\\test-classes\\RPA ACTA 0 stupnu podruhe-2013.10.25-15.08.38_height_retrace.jpk"), 1, metadata);
        List<ChannelContainer> channels = Arrays.asList(container);
        ImageLoader instance = new ImageLoader();
        List<ChannelContainer> result = instance.loadImages(channels);
        assertEquals("channel size", 1, result.size());
        assertEquals("channel index", 1, result.get(0).getChannelIndex());
        assertEquals("channel image name", "RPA ACTA 0 stupnu podruhe-2013.10.25-15.08.38_height_retrace.jpk", result.get(0).getImagePlus().getTitle()
        );
    }
}
