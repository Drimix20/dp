package common;

import ij.IJ;
import ij.io.FileInfo;
import ij.io.TiffDecoder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Drimal
 */
public class FilePreprocessor {
    //File choose by user
    private File parent;
    
    public FilePreprocessor(File parent) {
        this.parent = parent;
    }
    
    public Map<File, List<Integer>> preprocess(){
        Map<File, List<Integer>> map = new HashMap<File, List<Integer>>();
        
        if(parent.isDirectory()){
            File[] dirFiles = parent.listFiles();
            for(int i=0; i<dirFiles.length; i++){
                File dirFile = dirFiles[i];
                map.put(dirFile, processFile(dirFile));
            }
        }else{
            map.put(parent, processFile(parent));
        }
        
        return map;
    }

    public List<Integer> processFile(File file){
        List<Integer> slices = new ArrayList<Integer>();
        
        String dir = file.getParent();
        String name = file.getName();
        
        TiffDecoder td = new TiffDecoder(dir, name);
        td.enableDebugging();
        FileInfo[] fi = new FileInfo[1];
        
        try{
            fi = td.getTiffInfo();
        }catch (IOException ex) {
            IJ.log(ex.getMessage());
            return null;
        }
        
        if(fi == null){
            IJ.error("AFM Opener", "Unsupported file: \n"+name);
            return Collections.emptyList();
        }
        
        if(fi.length != 1){
            for(int i=1; i< fi.length; i++){
                slices.add(i);
            }
        }
        return slices;
    }
}
