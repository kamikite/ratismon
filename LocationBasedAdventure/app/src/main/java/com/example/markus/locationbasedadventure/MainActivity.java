package com.example.markus.locationbasedadventure;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.markus.locationbasedadventure.AsynchronTasks.SyndicateStatsLocalToServerTask;
import com.example.markus.locationbasedadventure.Database.ArmorDatabase;
import com.example.markus.locationbasedadventure.Database.CharacterdataDatabase;
import com.example.markus.locationbasedadventure.Database.ItemDatabase;
import com.example.markus.locationbasedadventure.Database.StatsDatabase;
import com.example.markus.locationbasedadventure.Database.WeaponDatabase;


public class MainActivity extends Activity{

    private Button anmeldenPage;
    private Button registrierenPage;
    private CharacterdataDatabase characterdataDb;
    private StatsDatabase statsDb;
    private WeaponDatabase weaponDb;
    private ArmorDatabase armorDb;
    private ItemDatabase itemDb;

    private String address2 = "http://sruball.de/Ratismon/syndicateData.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDB();
        //checkAnmeldung();
        initButtons();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Handler handler  = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                anmeldenPage.setBackgroundColor(getResources().getColor(R.color.light_blue_1));
                registrierenPage.setBackgroundColor(getResources().getColor(R.color.light_blue_1));
            }
        }, 1000);

    }


    //closes Database if Activity is destroyed

    @Override
    protected void onDestroy() {
        characterdataDb.close();
        statsDb.close();
        armorDb.close();
        weaponDb.close();
        itemDb.close();
        super.onDestroy();
    }


    //checks if databases are empty and inserts standard values into the databases
    //checks if stayAngmeldet is true
    //         true   --> syndicate Server Data with Database by using BackgroundTask
    //                --> start MapActivity


   /* private void checkAnmeldung() {
        if(characterdataDb.isEmpty()) {
            characterdataDb.insertAllmainActivity();
        }
        if(statsDb.isEmpty()) {
            statsDb.insertAllmainActivity();
        }
        if(weaponDb.isEmpty()) {
            weaponDb.insertAllmainActivity();
        }
        if(armorDb.isEmpty()) {
            armorDb.insertAllmainActivity();
        }
        if(itemDb.isEmpty()){
            itemDb.insertNewItem(1,2);
        }


        if(characterdataDb.getStayAngemeldet() == 1){
            new SyndicateStatsLocalToServerTask(this).execute(address2,characterdataDb.getEmail(),""+statsDb.getLevel(),""+statsDb.getExp(),""+statsDb.getStamina(),""+statsDb.getStrength(),""+statsDb.getDexterity(),""+statsDb.getIntelligence());
            Intent i = new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(i);
        }
    }*/


    //initialisesButtons
    //buttonListeners

    private void initButtons(){
        anmeldenPage = (Button) findViewById(R.id.button);
        registrierenPage = (Button) findViewById(R.id.button2);
        anmeldenPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anmeldenPage.setBackgroundColor(getResources().getColor(R.color.red));
                Intent i = new Intent(getApplicationContext(),AnmeldenActivity.class);
                startActivity(i);
                finish();
            }
        });
        registrierenPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrierenPage.setBackgroundColor(getResources().getColor(R.color.red));
                Intent i = new Intent(getApplicationContext(),RegistrierenActivity.class);
                startActivity(i);
                finish();
            }
        });
    }



    //initialises Databases
    // Open Databases

    private void initDB(){
        characterdataDb = new CharacterdataDatabase(this);
        characterdataDb.open();
        statsDb = new StatsDatabase(this);
        statsDb.open();
        weaponDb = new WeaponDatabase(this);
        weaponDb.open();
        armorDb = new ArmorDatabase(this);
        armorDb.open();
        itemDb = new ItemDatabase(this);
        itemDb.open();
    }

}
