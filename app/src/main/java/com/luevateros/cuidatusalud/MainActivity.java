package com.luevateros.cuidatusalud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public DrawerLayout drawerLayout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Descomentar estas líneas para borrar los SharedPreferences
//        sharedPreferences = getSharedPreferences("guardarDatos", Context.MODE_PRIVATE);
//        sharedPreferences.edit().clear().apply();

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.nav_contenido);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(
                        this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_fragmento, new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_inicio);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_inicio:
                System.out.println("Portada de la app");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new HomeFragment())
                        .commit();
                break;
            case R.id.nav_config:
                System.out.println("Iniciar sesión");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new LoginFragment())
                        .commit();
                break;
            case R.id.nav_nosotros:
                System.out.println("Nuevo usuario");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new NewUserFragment())
                        .commit();
                break;
            case R.id.nav_compartir:
                System.out.println("Índice de masa corporal");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contenedor_fragmento, new ImcFragment())
                        .commit();
                break;
            case R.id.nav_salir:
                Toast.makeText(this, "Saliste", Toast.LENGTH_SHORT).show();
                this.finishAffinity();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}