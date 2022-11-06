package app_fake_quizzlet;
import javax.swing.*;
import java.awt.*;

/*
 * Classe "page" qui permet de créer des nouvelles fenêtres
 */
public class Page {

    JFrame window = new JFrame();
    
    //division de la page en trois parties : header, body et footer
    JPanel panel_header = new JPanel();
    JPanel panel_footer = new JPanel();
    JPanel panel_body = new JPanel();

    JButton bouton_prof = new JButton();

    String texte_header;
    boolean close_operation;

    int width;
    int height;

    /*
     * Constructeur pour la création d'une page 
     * @param texte_header le titre qui sera affiché dans le header
     * @param close_operation : true si on veut que la fermeture de la page ferme l'application, false sinon
     */
    Page(String texte_header, boolean close_operation, int width, int height){

        //choix des couleurs pour les différentes divisions de la fenêtre
        panel_header.setBackground(Color.ORANGE);
        panel_body.setBackground(Color.PINK);
        panel_footer.setBackground(Color.ORANGE);

        window.add(panel_header, BorderLayout.PAGE_START);
        window.add(panel_body, BorderLayout.CENTER);
        window.add(panel_footer, BorderLayout.PAGE_END);

        JLabel header = new JLabel("<html><h1>" + texte_header + "</h1></html>");
        panel_header.add(header);

        JLabel texte_footer = new JLabel("<html><center>Julie Halbout<br/>Delphine Nguyen<br/>Master TAL 2022-2023</center></html>");
        panel_footer.add(texte_footer);

        window.setSize(width,height);

        if(close_operation){
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        else{
            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
        window.setVisible(true);
    }

    /**
     * Méthode qui permet d'ajouter un bouton sur une page
     * @param texte le titre du bouton
     * **/
    void createButton(String texte) {
        JButton bouton = new JButton(texte);
        panel_body.add(bouton, BorderLayout.SOUTH);
    }

    /**
     * Méthode qui permet d'ajouter du texte à une page
     * @param texte le texte que l'on veut ajouter
     */
    void addText(String texte) {
        JLabel boite_texte = new JLabel(texte);
        panel_body.add(boite_texte);
    }
}
