package cs5.puida.gui;

import cs5.puida.User;
import cs5.puida.db.DaoFactory;
import cs5.puida.db.UserDao;
import cs5.puida.util.Messages;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {


    private static final int FRAME_HEIGHT = 600;
    private static final int FRAME_WIDTH = 800;
    private JPanel contentPanel;
    private JPanel browsePanel;
    private AddPanel addPanel;
    private UserDao dao;
    private JPanel editPanel;
    private JPanel deletePanel;
    private JPanel detailsPanel;


    public MainFrame(){
        super();
        dao = DaoFactory.getInstance().getUserDao();
        initialize();
    }

    public UserDao getDao() {
        return dao;
    }

    private void initialize() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle(Messages.getString("MainFrame.user_management"));
        this.setContentPane(getControlPanel());

    }

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }

    private JPanel getControlPanel() {
        if (contentPanel == null) {
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getBrowsePanel());
        }

        return contentPanel;
    }

    private void showPanel(JPanel panel) {
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setVisible(true);
        panel.repaint();
    }

    private JPanel getBrowsePanel() {

        if (browsePanel == null) {
            browsePanel = new BrowsePanel(this);
        }

        ((BrowsePanel) browsePanel).initTable();
        return browsePanel;
    }

    public void showAddPanel() {
        showPanel(getAddPanel());
    }

    public void showBrowsePanel() {
        showPanel(getBrowsePanel());
    }

    public void showEditPanel() {
        showPanel(getEditPanel());
    }

    public void showDeletePanel() {
        showPanel(getDeletePanel());
    }

    public void showDetailsPanel() {
        showPanel(getDetailsPanel());
    }

    private AddPanel getAddPanel() {
        if (addPanel == null) {
            addPanel = new AddPanel(this);
        }
        return addPanel;
    }

    private JPanel getEditPanel() {
        if (editPanel == null) {
            editPanel = new EditPanel(this);
        }
        ((EditPanel) editPanel).resetFields();
        return editPanel;
    }

    private JPanel getDeletePanel() {
        if (deletePanel == null) {
            deletePanel = new DeletePanel(this);
        }
        ((DeletePanel) deletePanel).resetFields();
        return deletePanel;
    }

    private JPanel getDetailsPanel() {
        if (detailsPanel == null) {
            detailsPanel = new DetailsPanel(this);
        }
        ((DetailsPanel) detailsPanel).resetFields();
        return detailsPanel;
    }

    User getSelectedUser() {
        return ((BrowsePanel) browsePanel).getSelectedUser();
    }
}


