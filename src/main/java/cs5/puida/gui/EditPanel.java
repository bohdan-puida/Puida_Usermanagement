package cs5.puida.gui;

import cs5.puida.User;
import cs5.puida.db.DatabaseException;
import cs5.puida.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;

public class EditPanel extends JPanel implements ActionListener {

    private MainFrame parent;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton cancelButton;
    private JButton okButton;
    private JTextField dateOfBirthField;
    private JTextField lastNameField;
    private JTextField firstNameField;
    private User user;

    public EditPanel(MainFrame frame) {
        parent = frame;
        initialize();
    }

    private void initialize() {
        this.setName("editPanel");
        this.setLayout(new BorderLayout());
        this.add(getFieldPanel(), BorderLayout.NORTH);
        this.add(getButtonPanel(), BorderLayout.SOUTH);
        resetFields();
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getOkButton());
            buttonPanel.add(getCancelButton());
        }
        return buttonPanel;
    }

    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText(Messages.getString("AddPanel.ok"));
            okButton.setName("okButton");
            okButton.setActionCommand("ok");
            okButton.addActionListener(this);
        }
        return okButton;
    }

    private JButton getCancelButton() {
        if (cancelButton == null) {
            cancelButton = new JButton();
            cancelButton.setText(Messages.getString("AddPanel.cancel"));
            cancelButton.setName("cancelButton");
            cancelButton.setActionCommand("cancel");
            cancelButton.addActionListener(this);
        }
        return cancelButton;
    }

    private JPanel getFieldPanel() {
        if (fieldPanel == null) { fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(3, 2));
            addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameField());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthField());
        }
        return fieldPanel;
    }

    private JTextField getDateOfBirthField() {
        if (dateOfBirthField == null) {
            dateOfBirthField = new JTextField();
            dateOfBirthField.setName("dateOfBirthField");
        }
        return dateOfBirthField;
    }

    private JTextField getLastNameField() {
        if (lastNameField == null) {
            lastNameField = new JTextField();
            lastNameField.setName("lastNameField");
        }
        return lastNameField;
    }

    private void addLabeledField(JPanel panel, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(textField);
        panel.add(label);
        panel.add(textField);

    }

    private JTextField getFirstNameField() {
        if (firstNameField == null) {
            firstNameField = new JTextField();
            firstNameField.setName("firstNameField");
        }
        return firstNameField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ok".equalsIgnoreCase(e.getActionCommand())) {
            user.setFirstName(getFirstNameField().getText());
            user.setLastName(getLastNameField().getText());
            DateFormat format = DateFormat.getDateInstance();
            try {
                user.setDateOfBirth(format.parse(getDateOfBirthField().getText()));
            } catch (ParseException e1) {
                getDateOfBirthField().setBackground(Color.RED);
                return;
            }
            try {
                parent.getDao().update(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.setVisible(false);
        parent.showBrowsePanel();
    }

    public void resetFields() {
        this.user = parent.getSelectedUser();
        firstNameField.setText(user.getFirstName());
        lastNameField.setText(user.getLastName());
        DateFormat formatter = DateFormat.getDateInstance();
        dateOfBirthField.setText(formatter.format(user.getDateOfBirth()));
    }

}