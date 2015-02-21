
import ij.ImagePlus;
import ij.io.FileInfo;
import ij.io.FileOpener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Drimal
 */
public class JpkFileOpener {

    public JpkFileOpener(){}

    public ImagePlus openImage(FileInfo info){
        FileOpener fo = new FileOpener(info);
        ImagePlus imp = fo.open(false);
        return imp;
    }
}
