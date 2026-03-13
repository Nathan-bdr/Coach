package com.example.coach.view;

import android.os.Bundle;
import android.widget.ImageButton;
import android.content.Intent;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coach.R;

import android.widget.Button;
import android.widget.Toast;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;

/**
 * Activity qui affiche le menu
 */
public class MainActivity extends AppCompatActivity {

    private ImageButton btnMonIMG;
    private ImageButton btnMonHistorique;

    private Button btnPurge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    /**
     * Traitements nécessaires dès la création de l'activity
     */
    private void init() {
        chargeObjetsGraphiques();
        creerMenu();
    }

    /**
     * Récupération des objets graphiques
     */
    private void chargeObjetsGraphiques() {
        btnMonIMG = findViewById(R.id.btnMonIMG);
        btnMonHistorique = findViewById(R.id.btnMonHistorique);
        btnPurge = findViewById(R.id.btnPurge);
    }

    /**
     * Demande de création des écoutes sur les boutons
     */
    private void creerMenu(){
        btnMonIMG.setOnClickListener(v -> ecouteMenu(CalculActivity.class));
        btnMonHistorique.setOnClickListener(v -> ecouteMenu(HistoActivity.class));
        btnPurge.setOnClickListener(v -> ecoutePurge());
    }

    /**
     * Ouvre l'activity correspondant au paramètre
     * @param classe
     */
    private void ecouteMenu(Class classe){
        Intent intent = new Intent(MainActivity.this, classe);
        startActivity(intent);
    }

    /**
     * Demande de purge des profils via l'API
     */
    private void ecoutePurge() {
        HelperApi.call(HelperApi.getApi().purgerProfils(), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                Toast.makeText(MainActivity.this, result + " profils supprimés", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "échec de purge", Toast.LENGTH_SHORT).show();
            }
        });
    }
}