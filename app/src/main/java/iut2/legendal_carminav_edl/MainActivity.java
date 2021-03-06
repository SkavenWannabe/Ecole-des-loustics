package iut2.legendal_carminav_edl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iut2.legendal_carminav_edl.bd.DatabaseClient;
import iut2.legendal_carminav_edl.bd.User;
import iut2.legendal_carminav_edl.modele.VGlobal;

public class MainActivity extends AppCompatActivity {

    private DatabaseClient mDb;
    private ArrayAdapter<User> adapter;

    private ListView userListView;
    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("École des loustics");

        mDb = DatabaseClient.getInstance(getApplicationContext());

        userListView = findViewById(R.id.listUser);
        Button btnCreationCompte = (Button) findViewById(R.id.btn_creationCompte);
        Button btnAnonyme = findViewById(R.id.main_btn_anonyme);

        adapter = new ArrayAdapter<>(this, R.layout.template_user, R.id.user_button, new ArrayList<User>());

        btnCreationCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreationCompte.class);
                startActivity(intent);
            }
        });

        btnAnonyme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On set User à null
                //Il faudra faire attention plus tard : user peut être nul
                ((VGlobal) MainActivity.this.getApplication()).setUtilisateur(null);
                Toast.makeText(MainActivity.this, "Connecté en anonyme ", Toast.LENGTH_SHORT).show();
                Intent selectionExerciceIntent = new Intent(MainActivity.this, SelectionExerciceActivity.class);
                startActivity(selectionExerciceIntent);
            }
        });

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = userList.get(position);
                logUser(user.getNom(), user.getPrenom());
                Intent selectionExerciceIntent = new Intent(MainActivity.this, SelectionExerciceActivity.class);
                startActivity(selectionExerciceIntent);
            }
        });

        userListView.setAdapter(adapter);
    }


    private void getUsers() {
        class GetUsers extends AsyncTask<Void, Void, List<User>> {

            @Override
            protected List<User> doInBackground(Void... voids) {
                List<User> userList = mDb.getAppDatabase().userDao().getAll();

                return userList;
            }

            @Override
            protected void onPostExecute(List<User> users) {
                super.onPostExecute(users);

                adapter.clear();
                adapter.addAll(users);
                userList.addAll(users);

                adapter.notifyDataSetChanged();
            }
        }

        GetUsers gu = new GetUsers();
        gu.execute();
    }

    private void logUser(String nom, String prenom) {
        class LogUser extends AsyncTask<Void, Void, User> {

            @Override
            protected User doInBackground(Void... voids) {
                return mDb.getAppDatabase().userDao().getUser(nom, prenom);
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);

                Toast msg = Toast.makeText(MainActivity.this, "Connecté avec " + user.getNomComplet(), Toast.LENGTH_SHORT);
                ((VGlobal) MainActivity.this.getApplication()).setUtilisateur(user);
                msg.show();
            }
        }

        LogUser lu = new LogUser();
        lu.execute();
    }

    @Override
    protected void onStart() {
        super.onStart();

        getUsers();
    }
}