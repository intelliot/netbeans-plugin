
/*
 * CreateComponentGeneratorPanel.java
 *
 * Created on 18.6.2010, 20:04:41
 */

package org.netbeans.modules.php.nette.ui.generators;

/**
 *
 * @author Ondřej Brejla
 */
public class CreateComponentGeneratorPanel extends javax.swing.JPanel {

    /** Creates new form CreateComponentGeneratorPanel */
    public CreateComponentGeneratorPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPanel = new javax.swing.JTabbedPane();
        formPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        createValidSubmit = new javax.swing.JCheckBox();
        formClass = new javax.swing.JTextField();
        createInvalidSubmit = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        formName = new javax.swing.JTextField();
        useAppForm = new javax.swing.JCheckBox();
        componentPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        componentName = new javax.swing.JTextField();
        componentClass = new javax.swing.JTextField();
        registerInConstructor = new javax.swing.JCheckBox();

        jLabel3.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.jLabel3.text")); // NOI18N

        createValidSubmit.setSelected(true);
        createValidSubmit.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.createValidSubmit.text")); // NOI18N

        formClass.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.formClass.text")); // NOI18N
        formClass.setEnabled(false);

        createInvalidSubmit.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.createInvalidSubmit.text")); // NOI18N

        jLabel4.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.jLabel4.text")); // NOI18N

        formName.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.formName.text")); // NOI18N

        useAppForm.setSelected(true);
        useAppForm.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.useAppForm.text")); // NOI18N
        useAppForm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                useAppFormStateChanged(evt);
            }
        });

        javax.swing.GroupLayout formPanelLayout = new javax.swing.GroupLayout(formPanel);
        formPanel.setLayout(formPanelLayout);
        formPanelLayout.setHorizontalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, formPanelLayout.createSequentialGroup()
                        .addComponent(createValidSubmit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                        .addComponent(createInvalidSubmit))
                    .addGroup(formPanelLayout.createSequentialGroup()
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(formPanelLayout.createSequentialGroup()
                                .addComponent(formClass, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(useAppForm))
                            .addComponent(formName, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))))
                .addContainerGap())
        );
        formPanelLayout.setVerticalGroup(
            formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(formPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(formClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(useAppForm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(formName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(formPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(createInvalidSubmit)
                    .addComponent(createValidSubmit))
                .addContainerGap())
        );

        tabPanel.addTab(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.formPanel.TabConstraints.tabTitle"), formPanel); // NOI18N

        jLabel1.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.jLabel1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.jLabel2.text")); // NOI18N

        componentName.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.null.text")); // NOI18N

        componentClass.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.componentClass.text")); // NOI18N

        javax.swing.GroupLayout componentPanelLayout = new javax.swing.GroupLayout(componentPanel);
        componentPanel.setLayout(componentPanelLayout);
        componentPanelLayout.setHorizontalGroup(
            componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(componentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(componentName, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                    .addComponent(componentClass, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
                .addContainerGap())
        );
        componentPanelLayout.setVerticalGroup(
            componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(componentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(componentClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(componentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(componentName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        componentName.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.jTextField1.AccessibleContext.accessibleName")); // NOI18N

        tabPanel.addTab(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.componentPanel.TabConstraints.tabTitle"), componentPanel); // NOI18N

        registerInConstructor.setText(org.openide.util.NbBundle.getMessage(CreateComponentGeneratorPanel.class, "CreateComponentGeneratorPanel.registerInConstructor.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPanel)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(registerInConstructor)
                .addContainerGap(340, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tabPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(registerInConstructor)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

	private void useAppFormStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_useAppFormStateChanged
		formClass.setEnabled(!useAppForm.isSelected());
	}//GEN-LAST:event_useAppFormStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField componentClass;
    private javax.swing.JTextField componentName;
    private javax.swing.JPanel componentPanel;
    private javax.swing.JCheckBox createInvalidSubmit;
    private javax.swing.JCheckBox createValidSubmit;
    private javax.swing.JTextField formClass;
    private javax.swing.JTextField formName;
    private javax.swing.JPanel formPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JCheckBox registerInConstructor;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JCheckBox useAppForm;
    // End of variables declaration//GEN-END:variables

	public String getComponentName() {
		return componentName.getText();
	}

	public String getComponentClass() {
		return componentClass.getText();
	}

	public String getFormName() {
		return formName.getText();
	}

	public String getFormClass() {
		return formClass.getText();
	}

	public boolean isCreateInvalidSubmit() {
		return createInvalidSubmit.isSelected();
	}

	public boolean isCreateValidSubmit() {
		return createValidSubmit.isSelected();
	}

	public boolean isRegisterInConstructor() {
		return registerInConstructor.isSelected();
	}

	public boolean isFormTabSelected() {
		return tabPanel.getSelectedIndex() == 0;
	}

}