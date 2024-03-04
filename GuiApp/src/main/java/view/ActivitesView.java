package view;

import entites.Activite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivitesView extends JPanel {
    private JList<String> activitesList;

    public ActivitesView() {
        setLayout(new BorderLayout());

        activitesList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(activitesList);
        add(scrollPane, BorderLayout.CENTER);

        activitesList.setFont(new Font("Arial", Font.PLAIN, 24));

        List<Activite> activites = creerActivites();
        updateActivitesList(activites);

        setupActiviteDetailsClickListener();
    }

    private List<Activite> creerActivites() {
        List<Activite> activites = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            activites.add(new Activite("Alice", "Lecture en groupe", "Hiver", "101", sdf.parse("2023-01-10"), sdf.parse("2023-01-31"), "Hebdomadaire", "1", "Enfants"));
            activites.add(new Activite("Bob", "Atelier d'écriture", "Printemps", "102", sdf.parse("2023-04-05"), sdf.parse("2023-05-24"), "Hebdomadaire", "2", "Adultes"));
            activites.add(new Activite("Carol", "Club de lecture", "Automne", "103", sdf.parse("2023-09-15"), sdf.parse("2023-11-30"), "Mensuelle", "1.5", "Adultes"));
            activites.add(new Activite("Dave", "Atelier de bricolage", "Printemps", "104", sdf.parse("2023-04-10"), sdf.parse("2023-05-29"), "Hebdomadaire", "1", "Enfants"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return activites;
    }

    private void updateActivitesList(List<Activite> activites) {
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (Activite activite : activites) {
            listModel.addElement(activite.getTitre() + " - " + activite.getPeriode());
        }
        activitesList.setModel(listModel);
    }

    private void setupActiviteDetailsClickListener(){
        activitesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Check for double click
                    int index = activitesList.locationToIndex(e.getPoint());
                    Activite selectedActivite = creerActivites().get(index);
                    showActiviteDetailsDialog(selectedActivite);
                }
            }
        });
    }

    private void showActiviteDetailsDialog(Activite activite) {
        JDialog detailsDialog = new JDialog();
        detailsDialog.setTitle("Détails de l'activité");
        detailsDialog.setModal(true);
        detailsDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        detailsDialog.setLayout(new GridLayout(0, 2));
        
        // Set the preferred size for the dialog
        detailsDialog.setPreferredSize(new Dimension(800, 400));
        
        // Set the font size
        Font labelFont = new Font("Arial", Font.PLAIN, 20);
    
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
        JLabel[] labels = {
            new JLabel("Titre:"), new JLabel(activite.getTitre()),
            new JLabel("Animateur:"), new JLabel(activite.getNomAnimateur()),
            new JLabel("Période:"), new JLabel(activite.getPeriode()),
            new JLabel("Numéro local:"), new JLabel(activite.getNumeroLocal()),
            new JLabel("Date de début:"), new JLabel(sdf.format(activite.getDateDebut())),
            new JLabel("Date de fin:"), new JLabel(sdf.format(activite.getDateFin())),
            new JLabel("Occurrence:"), new JLabel(activite.getOccurence()),
            new JLabel("Durée en heure:"), new JLabel(activite.getDureeEnHeure()),
            new JLabel("Clientèle visée:"), new JLabel(activite.getClienteleVisee())
        };
    
        for (JLabel label : labels) {
            label.setFont(labelFont);
            detailsDialog.add(label);
        }
    
        detailsDialog.pack();
        detailsDialog.setLocationRelativeTo(null);
        detailsDialog.setVisible(true);
    }
}
