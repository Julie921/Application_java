package app_fake_quizzlet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Window extends JFrame {

    String name_app;
    String title_page;
    boolean close_operation;

    //division de la page en trois parties : header, body et footer
    JPanel panel_header = new JPanel();
    JPanel panel_footer = new JPanel();
    JPanel panel_body = new JPanel();

    /**
     * Constructeur de la classe Window. Elle permet de créer des nouvelles fenêtes pour l'interface.
     * @param name_app Le nom de notre application
     * @param title_page Le titre de la fenêtre
     * @param close_operation true si on veut que la fermeture de la fenêtre entraîne la fermeture de l'application. false, sinon.
     */
    public Window(String name_app, String title_page, boolean close_operation){

        //titre
        super(name_app);

        //choix des couleurs pour les différentes divisions de la fenêtre
        panel_header.setBackground(Color.ORANGE);
        panel_body.setBackground(Color.PINK);
        panel_footer.setBackground(Color.ORANGE);

        super.add(panel_header, BorderLayout.PAGE_START);
        super.add(panel_body, BorderLayout.CENTER);
        super.add(panel_footer, BorderLayout.PAGE_END);

        JLabel header = new JLabel("<html><h1>" + title_page + "</h1></html>");
        panel_header.add(header);


        JLabel texte_footer = new JLabel("<html><center>Julie Halbout<br/>Delphine Nguyen<br/>Master TAL 2022-2023</center></html>");
        panel_footer.add(texte_footer);

        //JButton but = new JButton("ici!");
        //but.addActionListener((this));
        //panel_body.add(but);

        super.setSize(700,700);
        setVisible(true);

        if(close_operation){
            super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
        else{
            super.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    //@Override
    //public void actionPerformed(ActionEvent e) {
    //    Window page_exercice = new Window("Fake Quizlet", "Page exercice", false);
    //}
}
