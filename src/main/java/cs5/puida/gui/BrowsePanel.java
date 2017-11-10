package cs5.puida.gui;

import cs5.puida.User;
import cs5.puida.db.DatabaseException;
import cs5.puida.util.Messages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowsePanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private MainFrame parent;
    private JPanel buttonPanel;
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton detailsButton;
    private JScrollPane tablePanel;
    private JTable userTable;

    public BrowsePanel(MainFrame frame) {
        parent = frame;
        initialize();
    }

    private void initialize() {
        this.setName("browsePanel"); //$NON-NLS-1$
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);

    }

    private JPanel getButtonsPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getAddButton());
            buttonPanel.add(getEditButton());
            buttonPanel.add(getDeleteButton());
            buttonPanel.add(getDetailsButton());
        }
        return buttonPanel;
    }

    private JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(Messages.getString("BrowsePanel.details"));
            detailsButton.setName("detailsButton");
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }
        return detailsButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Messages.getString("BrowsePanel.delete"));
            deleteButton.setName("deleteButton");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }
        return deleteButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(Messages.getString("BrowsePanel.edit"));
            editButton.setName("editButton");
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);
        }
        return editButton;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(Messages.getString("BrowsePanel.add"));
            addButton.setName("addButton");
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }
        return addButton;
    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }
        return tablePanel;
    }

    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            userTable.setName("userTable");
        }
        return userTable;
    }

    public void initTable() {
        UserTableModel model;
        try {
            model = new UserTableModel(parent.getDao().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList<>());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(model);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if ("add".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showAddPanel();
            return;
        }
        if (getSelectedUser() == null) {
            return;
        }
        if ("edit".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showEditPanel();
            return;
        }
        if ("delete".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showDeletePanel();
            return;
        }
        if ("details".equalsIgnoreCase(actionCommand)) {
            this.setVisible(false);
            parent.showDetailsPanel();
            return;
        }

    }

    public User getSelectedUser() {
        if (getUserTable().getSelectedRow() == -1)
            return null;
        try {
            return parent.getDao().find((Long) getUserTable().getValueAt(getUserTable().getSelectedRow(), 0));
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

}


