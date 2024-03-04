package view;

import javax.swing.*;

import entites.ExcelUtil;
import entites.ObjetEmprunt;
import entites.TypeObjetEmprunt;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ObjetEmpruntView extends JPanel {
    private JComboBox<TypeObjetEmprunt> typeComboBox;
    private JButton addButton;
    private JButton deleteButton;
    private JButton nouvellesAcquisitionsButton;
    private JList<ObjetEmprunt> objetsEmpruntList;
    private java.util.List<ObjetEmprunt> allObjetsEmprunt;


    public ObjetEmpruntView() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        typeComboBox = new JComboBox<>(TypeObjetEmprunt.values());
        topPanel.add(new JLabel("Type:"));
        topPanel.add(typeComboBox);

        addButton = new JButton("Ajouter un article");
        topPanel.add(addButton);

        deleteButton = new JButton("Supprimer la selection");
        topPanel.add(deleteButton);

        nouvellesAcquisitionsButton = new JButton("Nouvelles acquisitions");
        topPanel.add(nouvellesAcquisitionsButton);

        add(topPanel, BorderLayout.NORTH);

        objetsEmpruntList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(objetsEmpruntList);
        add(scrollPane, BorderLayout.CENTER);

        setTypeComboBoxListener();
        setAddButtonListener();
        setDeleteButtonListener();
        setNouvellesAcquisitionsBtnListener();

        updateObjetsEmpruntList();
    }

    public  void updateObjetsEmpruntList() {
        this.allObjetsEmprunt = ExcelUtil.loadObjetsFromExcel("articles");
        TypeObjetEmprunt selectedType = (TypeObjetEmprunt) typeComboBox.getSelectedItem();
        DefaultListModel<ObjetEmprunt> listModel = new DefaultListModel<>();
    
        for (ObjetEmprunt objetEmprunt : allObjetsEmprunt) {
            if (objetEmprunt.getType() == selectedType) {
                listModel.addElement(objetEmprunt);
            }
        }
        objetsEmpruntList.setModel(listModel);
    }

    public int generateUniqueId() {
        int maxId = 0;
        for (ObjetEmprunt objetEmprunt : allObjetsEmprunt) {
            maxId = Math.max(maxId, objetEmprunt.getId());
        }
        return maxId + 1;
    }
    
    private void setTypeComboBoxListener(){
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateObjetsEmpruntList();
            }
        });
    }

    private void setAddButtonListener(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddObjetEmpruntDialog();
            }
        });
    }

    private void showAddObjetEmpruntDialog() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddArticleDialog addObjetEmpruntDialog = new AddArticleDialog(parentFrame, "articles");

        //lorsque la fenetre d'ajout d'article est fermee, on met a jours les articles 
        // de cette facon si un article est ajoute, il apparaitra
        addObjetEmpruntDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                updateObjetsEmpruntList();
            }
        });

        addObjetEmpruntDialog.setVisible(true);
    }

    private void setDeleteButtonListener() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObjetEmprunt selectedObjetEmprunt = objetsEmpruntList.getSelectedValue();
                if (selectedObjetEmprunt != null) {
                    int id = selectedObjetEmprunt.getId();
                    ExcelUtil.deleteObjetEmpruntById(id, "articles");
                    updateObjetsEmpruntList();
                } else {
                    JOptionPane.showMessageDialog(ObjetEmpruntView.this, "Aucun article à supprimer a été selectionné.", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void setNouvellesAcquisitionsBtnListener(){
        nouvellesAcquisitionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame nouvelleAcquisitionFrame = new JFrame("Gestion des nouvelles acquisitions");
                NouvellesAcquisitionView nouvellesAcquisitionView = new NouvellesAcquisitionView();
                nouvelleAcquisitionFrame.add(nouvellesAcquisitionView);
                nouvelleAcquisitionFrame.setSize(800, 600);
                nouvelleAcquisitionFrame.setLocationRelativeTo(null);
                nouvelleAcquisitionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                nouvelleAcquisitionFrame.setVisible(true);
            }
        });
    }
}
