package cz.pedry.beercalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import java.util.Locale;

import cz.pedry.beercalculator.fragments.FragmentAlcohol;
import cz.pedry.beercalculator.fragments.FragmentIBU;


public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private Button ibuButton, alcoholButton;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment fragmentBLG = new FragmentAlcohol();
    private Fragment fragmentIBU = new FragmentIBU();
    private static boolean unitsEU = true;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout();

        sp = getSharedPreferences("key", 0);
        unitsEU = sp.getBoolean("isUnitsEU",true);

        boolean isIBUScreen = sp.getBoolean("isIBUScreen",true);
        if(isIBUScreen)
            openIBU();
        else
            openAlcohol();

        boolean isLanguageEn = sp.getBoolean("isLanguageEn",true);
        if(isLanguageEn)
            setLocale("en");
        else
            setLocale("pl");

        ibuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIBU();
                SharedPreferences.Editor sedt = sp.edit();
                sedt.putBoolean("isIBUScreen", true);
                sedt.commit();
            }
        });

        alcoholButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAlcohol();
                SharedPreferences.Editor sedt = sp.edit();
                sedt.putBoolean("isIBUScreen", false);
                sedt.commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }

    public void openAlcohol(){
        ibuButton.setTextColor(getResources().getColor(R.color.gray));
        alcoholButton.setTextColor(getResources().getColor(R.color.yellow));
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragmentBLG, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void openIBU(){
        ibuButton.setTextColor(getResources().getColor(R.color.yellow));
        alcoholButton.setTextColor(getResources().getColor(R.color.gray));
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragmentIBU, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    private void setLayout() {
        ibuButton = (Button) findViewById(R.id.ibu);
        alcoholButton = (Button) findViewById(R.id.blg);
    }

    public void showMenu(View view){
        PopupMenu menu = new PopupMenu(this, view);
        menu.setOnMenuItemClickListener(this);
        menu.inflate(R.menu.my_menu);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        SharedPreferences.Editor sedt = sp.edit();
        switch (menuItem.getItemId()){
            case R.id.us:
                unitsEU = false;
                sedt.putBoolean("isUnitsEU", false);
                sedt.commit();
                recreate();
                return true;
            case R.id.eu:
                unitsEU = true;
                sedt.putBoolean("isUnitsEU", true);
                sedt.commit();
                recreate();
                return true;
            case R.id.en:
                setLocale("en");
                sedt.putBoolean("isLanguageEn", true);
                sedt.commit();
                recreate();
                return true;
            case R.id.pl:
                setLocale("pl");
                sedt.putBoolean("isLanguageEn", false);
                sedt.commit();
                recreate();
                return true;
            default:
                return false;
        }
    }

    public void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = this.getResources();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(locale);
        }
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static boolean isUnitsEU() {
        return unitsEU;
    }
}