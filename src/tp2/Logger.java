package tp2;

import java.io.*;

/**
 * Classe Logger
 */
@SuppressWarnings("unused")
public class Logger
{
    /**
     * Tous les messages
     */
    private static final int ALL = 0;
    /**
     * Messages de débogage
     */
    static final int DEBUG = 100;
    /**
     * Informations pertinentes
     */
    static final int INFO = 500;
    /**
     * Informations importantes
     */
    static final int IMPORTANT = 900;
    /**
     * Rien
     */
   private static final int OFF = Integer.MAX_VALUE;

    private int level;
    private PrintWriter writer;

    /**
     * Crée une instance de {@link Logger} avec le niveau {@link Logger#ALL} dans le flux {@link System#out}
     */
    private Logger()
    {
        this(ALL, System.out);
    }

    /**
     * Crée une instance de {@link Logger} avec le niveau et le flux spécifiés
     * @param level niveau de journalisation
     * @param str flux dans lequel écrire
     */
    private Logger(int level, OutputStream str)
    {
        this(level, new PrintWriter(str, true));
    }

    /**
     * Crée une instance de {@link Logger} avec le niveau et le {@link PrintWriter} spécifiés
     * @param level niveau de journalisation
     * @param writer {@link PrintWriter} dans lequel écrire
     */
    private Logger(int level, PrintWriter writer)
    {
        this.level = level;
        this.writer = writer;
    }

    /**
     * Crée une instance de {@link Logger} à l'aide du fichier de configuration spécifié
     * @param configPath chemin relatif ou absolu du fichier de configuration à charger
     * @throws FileNotFoundException si le fichier n'est pas trouvé
     * @throws IOException si une erreur survient durant la lecture
     * @throws IllegalArgumentException si le fichier de configuration est invalide
     */
    private Logger(String configPath) throws FileNotFoundException, IOException, IllegalArgumentException
    {
        try(var rd = new BufferedReader(new FileReader(configPath)))
        {
            var conf = rd.readLine().split(" ");

            switch(conf[0].strip().toUpperCase())
            {
                case "ALL":
                    this.level = ALL;
                    break;
                case "DEBUG":
                    this.level = DEBUG;
                    break;
                case "INFO":
                    this.level = INFO;
                    break;
                case "IMPORTANT":
                    this.level = IMPORTANT;
                    break;
                case "OFF":
                    this.level = OFF;
                    break;
                default:
                    throw new IllegalArgumentException("Valeur invalide pour le niveau de débogage : " + conf[0]);
            }

            OutputStream str;

            switch(conf[1].strip())
            {
                case "System.out":
                    str = System.out;
                    break;
                case "System.err":
                    str = System.err;
                    break;
                default:
                    str = new BufferedOutputStream(new FileOutputStream(conf[1]));
                    break;
            }

            this.writer = new PrintWriter(str, true);
        }
    }

    public int getLevel()
    {
        return level;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public PrintWriter getWriter()
    {
        return writer;
    }

    public void setWriter(PrintWriter writer)
    {
        this.writer = writer;
    }

    /**
     * Écrit un message dans le journal
     *
     * @param level niveau de journalisation
     * @param message message à écrire
     */
    void log(int level, String message)
    {
        if (level >= this.level)
        {
            writer.println(message);
        }
    }

    static final Logger INSTANCE;

    static
    {
        Logger res;

        try
        {
            res = new Logger("config.txt");
        }
        catch (IOException e)
        {
            System.err.println("ERREUR : la configuration du Logger n'a pu être chargée. Retour à la configuration par défaut.");
            res = new Logger();
        }

        INSTANCE = res;
    }
}
