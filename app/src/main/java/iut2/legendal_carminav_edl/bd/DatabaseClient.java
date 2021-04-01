package iut2.legendal_carminav_edl.bd;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

public class DatabaseClient {

    // Instance unique permettant de faire le lien avec la base de données
    private static DatabaseClient instance;

    // Objet représentant la base de données de votre application
    private AppDatabase appDatabase;

    // Constructeur
    private DatabaseClient(final Context context) {

        // Créer l'objet représentant la base de données de votre application
        // à l'aide du "Room database builder"
        // MyToDos est le nom de la base de données
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "EcoleDesLoustics").build();

        ////////// REMPLIR LA BD à la première création à l'aide de l'objet roomDatabaseCallback
        // Ajout de la méthode addCallback permettant de populate (remplir) la base de données à sa création
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "EcoleDesLoustics").addCallback(roomDatabaseCallback).build();
    }

    // Méthode statique
    // Retourne l'instance de l'objet DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    // Retourne l'objet représentant la base de données de votre application
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    // Objet permettant de populate (remplir) la base de données à sa création
    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //
            db.execSQL("INSERT INTO user VALUES(\"nom\", \"prénom\", 1, 2, 6, 7, 5);");
            db.execSQL("INSERT INTO user VALUES(\"Legendre\", \"Alexandre\", 3, 0, 2, 4, 6);");
            db.execSQL("INSERT INTO user VALUES(\"Carminati\", \"Vincenzo\", 2, 2, 3, 1, 0);");
            db.execSQL("INSERT INTO user VALUES(\"Sammier\", \"Eliott\", 200, 9, 7, 51, 6);");
            db.execSQL("INSERT INTO user VALUES(\"Loraux\", \"Elian\", 0, 5, 1, 2, 0);");
//            db.execSQL("INSERT INTO user VALUES(\"Corbalan\", \"Clément\", -8);");
            db.execSQL("INSERT INTO user VALUES(\"Fontana\", \"Théo\", 2, 3, 2, 4, 5);");

        }
    };
}
