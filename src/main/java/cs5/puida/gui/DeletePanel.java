package cs5.puida.gui;
import cs5.puida.User;
import cs5.puida.db.DatabaseException;
import cs5.puida.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;

public class DeletePanel extends JPanel implements ActionListener {

    private MainFrame parent;
    private JPanel buttonPanel;
    private JPanel fieldPanel;
    private JButton cancelButton;
    private JButton okButton;
    private JLabel dateOfBirthLabel;
    private JLabel lastNameLabel;
    private JLabel firstNameLabel;
    private User user;

    public DeletePanel(MainFrame frame) {
        parent = frame;
        initialize();
    }

    private void initialize() {
        this.setName("deletePanel");
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
        if (fieldPanel == null) {
            fieldPanel = new JPanel();
            fieldPanel.setLayout(new GridLayout(4, 2));
            addLabeledField(fieldPanel, Messages.getString("AddPanel.first_name"), getFirstNameLabel());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.last_name"), getLastNameLabel());
            addLabeledField(fieldPanel, Messages.getString("AddPanel.date_of_birth"), getDateOfBirthLabel());
            fieldPanel.add(new JLabel(Messages.getString("DeletePanel.accept_question")));
        }
        return fieldPanel;
    }

    private JLabel getDateOfBirthLabel() {
        if (dateOfBirthLabel == null) {
            dateOfBirthLabel = new JLabel();
            dateOfBirthLabel.setName("dateOfBirthLabel");
        }
        return dateOfBirthLabel;
    }

    private JLabel getLastNameLabel() {
        if (lastNameLabel == null) {
            lastNameLabel = new JLabel();
            lastNameLabel.setName("lastNameLabel");
        }
        return lastNameLabel;
    }

    private void addLabeledField(JPanel panel, String labelText, JLabel userLabel) {
        JLabel label = new JLabel(labelText);
        label.setLabelFor(userLabel);
        panel.add(label);
        panel.add(userLabel);

    }

    private JLabel getFirstNameLabel() {
        if (firstNameLabel == null) {
            firstNameLabel = new JLabel();
            firstNameLabel.setName("firstNameLabel");
        }
        return firstNameLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("ok".equalsIgnoreCase(e.getActionCommand())) {
            try {
                parent.getDao().delete(user);
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        this.setVisible(false);
        parent.showBrowsePanel();
    }

    public void resetFields() {
        this.user = parent.getSelectedUser();
        firstNameLabel.setText(user.getFirstName());
        lastNameLabel.setText(user.getLastName());
        DateFormat formatter = DateFormat.getDateInstance();
        dateOfBirthLabel.setText(formatter.format(user.getDateOfBirth()));
    }}