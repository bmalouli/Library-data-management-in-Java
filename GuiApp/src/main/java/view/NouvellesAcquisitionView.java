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

public class NouvellesAcquisitionView extends JPanel {
    private JComboBox<TypeObjetEmprunt> typeComboBox;
    private JButton addButton;
    private JButton deleteButton;
    private JList<ObjetEmprunt> nouvellesAcq;
    private java.util.List<ObjetEmprunt> allNouvellesAcquisitionsEmprunt;

    public NouvellesAcquisitionView() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        typeComboBox = new JComboBox<>(TypeObjetEmprunt.values());
        topPanel.add(new JLabel("Type:"));
        topPanel.add(typeComboBox);

        addButton = new JButton("Ajouter une nouvelle acquisition");
        topPanel.add(addButton);

        deleteButton = new JButton("Supprimer la selection");
        topPanel.add(deleteButton);

        add(topPanel, BorderLayout.NORTH);

        nouvellesAcq = new JList<>();
        JScrollPane scrollPane = new JScrollPane(nouvellesAcq);
        add(scrollPane, BorderLayout.CENTER);

        setTypeComboBoxListener();
        setAddButtonListener();
        setDeleteButtonListener();

        updateNouvellesAcquisitonList();
        
    }

    public void updateNouvellesAcquisitonList() {
        this.allNouvellesAcquisitionsEmprunt = ExcelUtil.loadObjetsFromExcel("nouvelles_acquisitions");
        TypeObjetEmprunt selectedType = (TypeObjetEmprunt) typeComboBox.getSelectedItem();
        DefaultListModel<ObjetEmprunt> listModel = new DefaultListModel<>();
    
        for (ObjetEmprunt objetEmprunt : allNouvellesAcquisitionsEmprunt) {
            if (objetEmprunt.getType() == selectedType) {
                listModel.addElement(objetEmprunt);
            }
        }
        nouvellesAcq.setModel(listModel);
    }

    public int generateUniqueId() {
        int maxId = 0;
        for (ObjetEmprunt objetEmprunt : allNouvellesAcquisitionsEmprunt) {
            maxId = Math.max(maxId, objetEmprunt.getId());
        }
        return maxId + 1;
    }
    
    private void setTypeComboBoxListener(){
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateNouvellesAcquisitonList();
            }
        });
    }

    private void setAddButtonListener(){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNouvelleAcquisitonDialog();
            }
        });
    }

    private void showAddNouvelleAcquisitonDialog() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddArticleDialog addObjetEmpruntDialog = new AddArticleDialog(parentFrame, "nouvelles_acquisitions");

        //lorsque la fenetre d'ajout d'article est fermee, on met a jours les articles 
        // de cette facon si un article est ajoute, il apparaitra
        addObjetEmpruntDialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                updateNouvellesAcquisitonList();
            }
        });

        addObjetEmpruntDialog.setVisible(true);
    }

    private void setDeleteButtonListener() {
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ObjetEmprunt selectedObjetEmprunt = nouvellesAcq.getSelectedValue();
                if (selectedObjetEmprunt != null) {
                    int id = selectedObjetEmprunt.getId();
                    ExcelUtil.deleteObjetEmpruntById(id, "nouvelles_acquisitions");
                    updateNouvellesAcquisitonList();
                } else {
                    JOptionPane.showMessageDialog(NouvellesAcquisitionView.this, "Aucune nouvelles acquisitions à supprimer a été selectionné.", "Erreur", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }
}
