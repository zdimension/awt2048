package tp2;

import package2048.Jeu2048;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Classe principale du programme
 */
public class Main extends Frame
{
    // Paramètres de la partie
    private static final int NB_LIG = 4;
    private static final int NB_COL = 4;
    private static final int NB_BUT = 2048;

    /**
     * Initialise la fenêtre principale
     *
     * @param model modèle de 2048 à utiliser
     */
    public Main(Jeu2048 model)
    {
        // paramètres graphiques
        setTitle("2048 - INFO403 - Tom Niget");
        setLayout(new BorderLayout());
        setSize(500, 680);

        // zone de dessin
        var grille = new JeuCanvas(this, model);
        add(grille, BorderLayout.CENTER);

        // fermeture de la fenêtre
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Logger.INSTANCE.log(Logger.INFO, "Fermeture de la fenêtre et enregistrement du score");
                grille.dispose();
                dispose();
            }
        });

        // activation des évènements d'entrée clavier
        grille.requestFocusInWindow();
        grille.setFocusable(true);
    }

    public static void main(String[] args)
    {
        Logger.INSTANCE.log(Logger.INFO, "Démarrage");

        // évite le scintillement
        System.setProperty("sun.awt.noerasebackground", "true");

        Jeu2048 model;

        try
        {
            model = ModelIO.charger("_auto.sav");
        }
        catch (Exception e)
        {
            // initialisation d'une nouvelle partie
            model = new Jeu2048(NB_LIG, NB_COL, NB_BUT);
            model.nouveauJeu();
        }

        new Main(model).setVisible(true);

        Logger.INSTANCE.log(Logger.INFO, "Fin");
    }
}
