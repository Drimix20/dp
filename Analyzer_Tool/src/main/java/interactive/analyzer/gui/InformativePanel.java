package interactive.analyzer.gui;

import javax.swing.JPanel;

/**
 *
 * @author Drimal
 */
public class InformativePanel extends JPanel {

    public InformativePanel() {
        super();
        initComponents();
    }

    public synchronized void setCountFieldValue(String count) {
        countField.setText(count);
        countField.setToolTipText(count);
    }

    public synchronized void setLowerBoundField(String lowerBound) {
        lowerBoundField.setText(lowerBound);
        lowerBoundField.setToolTipText(lowerBound);
    }

    public synchronized void setUpperBoundField(String upperBound) {
        upperBoundField.setText(upperBound);
        upperBoundField.setToolTipText(upperBound);
    }

    public synchronized void setBinSizeField(String binSize) {
        binSizeField.setText(binSize);
        binSizeField.setToolTipText(binSize);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {
        jLabel3 = new javax.swing.JLabel();
        countField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lowerBoundField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        upperBoundField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        binSizeField = new javax.swing.JTextField();

        jLabel3.setText("Count:");

        countField.setEditable(false);
        countField.setMinimumSize(new java.awt.Dimension(75, 20));
        countField.setPreferredSize(new java.awt.Dimension(75, 20));

        jLabel4.setText("Lower bound:");

        lowerBoundField.setEditable(false);
        lowerBoundField.setMinimumSize(new java.awt.Dimension(75, 20));
        lowerBoundField.setPreferredSize(new java.awt.Dimension(75, 20));

        jLabel5.setText("Upper bound:");

        upperBoundField.setEditable(false);
        upperBoundField.setMinimumSize(new java.awt.Dimension(75, 20));
        upperBoundField.setPreferredSize(new java.awt.Dimension(75, 20));

        jLabel6.setText("Bin size:");

        binSizeField.setEditable(false);
        binSizeField.setMinimumSize(new java.awt.Dimension(75, 20));
        binSizeField.setPreferredSize(new java.awt.Dimension(75, 20));

        javax.swing.GroupLayout informationPanelLayout = new javax.swing.GroupLayout(this);
        this.setLayout(informationPanelLayout);
        informationPanelLayout.setHorizontalGroup(
                informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(informationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(informationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(countField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(informationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lowerBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(informationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(upperBoundField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(informationPanelLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(binSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
        );
        informationPanelLayout.setVerticalGroup(
                informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(informationPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(countField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lowerBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel5)
                                .addComponent(upperBoundField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(informationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(binSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>

    private javax.swing.JTextField countField;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField lowerBoundField;
    private javax.swing.JTextField upperBoundField;
    private javax.swing.JTextField binSizeField;
}
