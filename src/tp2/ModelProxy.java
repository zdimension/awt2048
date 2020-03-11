package tp2;

import package2048.Jeu2048;

import java.io.Serializable;
import java.util.Observer;

/**
 * Proxy servant à communiquer avec le modèle tout en journalisant les actions
 */
class ModelProxy implements Serializable
{
    private Jeu2048 model;

    ModelProxy(Jeu2048 model)
    {
        this.model = model;
    }

    Jeu2048 getBackend()
    {
        return model;
    }

    void decaler(int direction)
    {
        Logger.INSTANCE.log(Logger.DEBUG, "Décalage " + ((direction >= 0 && direction < 4) ? new String[]{"HAUT", "DROITE", "BAS", "GAUCHE"}[direction] : direction));

        if (model.decaler(direction))
        {
            var fus = model.tableauFusions();
            var gri = model.getGrilleInt();

            for(var y = 0; y < model.getNbLignes(); y++)
                for(var x = 0; x < model.getNbCols(); x++)
                    if (fus[y][x])
                        Logger.INSTANCE.log(Logger.INFO, gri[y][x] + " résultat d'une fusion dans (" + x + ", " + y + ")");
        }
        else
        {
            Logger.INSTANCE.log(Logger.IMPORTANT, "Décalage impossible !");
        }

    }

    void nouveauJeu()
    {
        Logger.INSTANCE.log(Logger.DEBUG, "Nouvelle partie - " + model.getNbLignes() + " lignes par " + model.getNbCols() + " colonnes ; objectif = " + model.getNbBut());
        model.nouveauJeu();
    }

    @SuppressWarnings("deprecation")
    void addObserver(Observer o)
    {
        model.addObserver(o);
    }

    int getBestScore()
    {
        return model.getBestScore();
    }

    int getScore()
    {
        return model.getScore();
    }

    int[][] getGrilleInt()
    {
        return model.getGrilleInt();
    }

    int getNbLignes()
    {
        return model.getNbLignes();
    }

    int getNbCols()
    {
        return model.getNbCols();
    }

    boolean estTermine()
    {
        return model.estTermine();
    }

    boolean estVainquer()
    {
        return model.estVainquer();
    }
}
