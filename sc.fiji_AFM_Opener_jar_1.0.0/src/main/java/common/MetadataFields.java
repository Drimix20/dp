package common;

import ij.io.FileInfo;

/**
 *
 * @author Drimal
 */
public class MetadataFields {
    private String debugInfo;
    private String directory;
    private String fileName;
    
    public MetadataFields(FileInfo info) {
        if(info.debugInfo==null || info.debugInfo.isEmpty()) return;
        directory = info.directory;
        fileName = info.fileName;
        debugInfo = info.debugInfo;
    }
    
    
    public double getDoubleTag(long tag){
        return 0;
    }
    
}
