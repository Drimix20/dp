package gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.Map;
import java.util.List;
import javax.swing.AbstractButton;

/**
 *
 * @author Drimal
 */
public class ChanelListElement extends javax.swing.JPanel {
    private String label;
    private File imageFile;
    private int indexOfElement;
    private Map<File, List<Integer>> selectedImages;

    public ChanelListElement(String label, File file,int index, Map<File, List<Integer>> selectedImages, boolean setSelected) {
        initComponents();
        this.label = label;
        imageElemLabel.setText(label);
        imageElemLabel.setToolTipText(file.getName());
        this.imageFile = file;
        this.indexOfElement = index;
        this.selectedImages = selectedImages;

        if(setSelected){
            putImageIntoSelectedImages();
        }
    }

    private void putImageIntoSelectedImages() {
        openSpecificImage.setSelected(true);
        Integer elem = new Integer(indexOfElement);
        List<Integer> values = selectedImages.get(imageFile);
        values.add(elem);
        selectedImages.put(imageFile, values);
    }

    public String getLabel(){
        return label;
    }

    public int getIndex(){
        return indexOfElement;
    }

    public void selectOpenSpecificImage(boolean select){
        openSpecificImage.setSelected(select);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openSpecificImage = new javax.swing.JCheckBox();
        imageElemLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(150, 30));

        openSpecificImage.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        openSpecificImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openSpecificImagePerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageElemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(openSpecificImage)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(openSpecificImage, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imageElemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void openSpecificImagePerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openSpecificImagePerformed
        AbstractButton button = (AbstractButton) evt.getSource();
        Integer elem = new Integer(indexOfElement);
        List<Integer> values = selectedImages.get(imageFile);
        if(button.getModel().isSelected()){
            values.add(elem);
        }else{
            values.remove(elem);
        }
        selectedImages.put(imageFile, values);
    }//GEN-LAST:event_openSpecificImagePerformed

    public javax.swing.JCheckBox getCheckBox(){
        return openSpecificImage;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imageElemLabel;
    public javax.swing.JCheckBox openSpecificImage;
    // End of variables declaration//GEN-END:variables
}