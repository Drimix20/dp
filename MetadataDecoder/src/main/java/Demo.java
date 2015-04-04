
import java.io.File;
import java.io.FileFilter;
import java.util.*;
import metadata.decoder.ChannelMetadata;
import metadata.decoder.MetadataDecoder;
import metadata.decoder.MetadataWriter;
import org.apache.log4j.Logger;

/**
 *
 * @author Drimal
 */
public class Demo {

    public static void main(String[] args) {
        File mainDirectory = new File("c:\\Users\\Drimal\\Downloads\\allDpFiles\\");

        File[] listFiles = mainDirectory.listFiles(new JpkFilter());
        List<ChannelMetadata> metadata = new ArrayList<>();
        MetadataDecoder decoder = new MetadataDecoder();
        for (File file : listFiles) {
            try {
                metadata.addAll(decoder.decodeMetadata(file));
            } catch (Exception ex) {
                Logger.getLogger(Demo.class.getName()).warn("Error while decoding", ex);
            }
        }

        for (ChannelMetadata channelMetadata : metadata) {
            System.out.println(channelMetadata.getTags());
        }

        Set<String> generalTag = new TreeSet<>(Arrays.asList(new String[]{
            "256", "257", "258", "259", "262", "273", "277", "278", "279", "320", "32768", "32769", "32770", "32771", "32772", "32773", "32774", "32775", "32776", "32777", "32789", "32790", "32791", "32816", "32817", "32818", "32819", "32820", "32821", "32822", "32823", "32824", "32825", "32826", "32830", "32832", "32833", "32834", "32835", "32836", "32837", "32838", "32839", "32840", "32841", "32842", "32843", "32846", "32848", "32864", "32896", "32897", "32912", "32913", "32928", "32929", "32931", "32960", "32961", "32962", "32976", "32977", "32978", "32979", "32980", "32981", "33008", "33009", "33010", "33024", "33025", "33026", "33027", "33028", "33029", "33056", "33057", "3307"}));
        System.out.println("GeneralTag: " + generalTag.size());
        Set<String> channelTag = new TreeSet<>(Arrays.asList(new String[]{"256", "257", "258", "259", "262", "273", "277", "278", "279", "339", "32848", "32849", "32850", "32851", "32896", "32897", "32912", "32913", "32928", "32929", "32931", "32960", "32961", "32962", "32976", "32977", "32978", "32979", "32980", "32981", "33008", "33009", "33010", "33024", "33025", "33026", "33027", "33028", "33029", "33056", "33057", "33058", "33072", "33073", "33074", "33075", "33076", "33077"}));
        System.out.println("ChannelTag: " + channelTag.size());
        generalTag.addAll(channelTag);
        System.out.println("TagHeader: " + generalTag.size());

        MetadataWriter writer = new MetadataWriter(new File(mainDirectory.getAbsolutePath() + "\\tagSummary.txt"));
        writer.setTagHeader(generalTag);
        //writer.setTagExclusion(new TreeSet<>(Arrays.asList(new String[]{"32830", "32791", "32851"})));
        writer.writeData(metadata);
    }

}

class JpkFilter implements FileFilter {

    @Override
    public boolean accept(File file) {
        return file.getName().contains("jpk");
    }

}
