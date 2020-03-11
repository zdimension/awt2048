package tp2;

import package2048.Jeu2048;

import java.io.*;

/**
 * Fonctions d'E/S du modèle
 */
final class ModelIO
{
    /**
     * Constructeur. DessinUtils est une classe utilitaire. Cette fonction ne doit jamais être appelée.
     */
    private ModelIO()
    {
        throw new AssertionError();
    }

    static void enregistrer(String fn, Jeu2048 model) throws IOException
    {
        try (var out = new FileOutputStream(new File(fn)))
        {
            try (var buf = new BufferedOutputStream(out))
            {
                try (var ois = new ObjectOutputStream(buf))
                {
                    ois.writeObject(model);
                }
            }
        }
    }

    static Jeu2048 charger(String fn) throws IOException, ClassNotFoundException
    {
        try (var inp = new FileInputStream(new File(fn)))
        {
            try (var buf = new BufferedInputStream(inp))
            {
                try (var ois = new ObjectInputStream(buf))
                {
                    return (Jeu2048) ois.readObject();
                }
            }
        }
    }
}
