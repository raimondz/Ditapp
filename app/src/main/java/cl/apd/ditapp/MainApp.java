package cl.apd.ditapp;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.EntypoModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.joanzapata.iconify.fonts.IoniconsModule;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.joanzapata.iconify.fonts.MeteoconsModule;
import com.joanzapata.iconify.fonts.SimpleLineIconsModule;
import com.joanzapata.iconify.fonts.TypiconsModule;
import com.joanzapata.iconify.fonts.WeathericonsModule;

/**
 * Created by Raimondz on 03-10-15.
 */
public class MainApp extends Application {
    private String rut=null;

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify
        .with(new FontAwesomeModule())
        .with(new EntypoModule())
        .with(new TypiconsModule())
        .with(new MaterialModule())
        .with(new MeteoconsModule())
        .with(new WeathericonsModule())
        .with(new SimpleLineIconsModule())
        .with(new IoniconsModule());
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }
}
