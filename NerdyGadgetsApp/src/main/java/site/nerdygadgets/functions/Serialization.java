package site.nerdygadgets.functions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.InfrastructureComponentModel;

import javax.swing.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
/**
 * Serialization class
 * Save and open files
 *
 * @author Tristan Scholten & Jordy Wielaard
 * @version 1.0
 * @since 14-05-2020
 */
public class Serialization {

    // Save models to file
    public static void serializeComponents(ArrayList<ComponentModel> components) throws IOException {
        Gson g = new Gson();
        String s = g.toJson(components);
        String path = System.getProperty("user.dir") + "\\tmp.json";
        System.out.println(path);

        File f = new File(path);
        if(!f.exists()){
            f.createNewFile();
        }

        FileWriter fw = new FileWriter(f, false);
        fw.write(s);
        fw.flush();
        fw.close();
    }
    // Save models to file
    public static void serializeComponents(ArrayList<ComponentModel> components, String path) throws IOException {
        Gson g = new Gson();
        String s = g.toJson(components);

        File f = new File(path);
        if (f.exists())
            throw new FileAlreadyExistsException(path); //Anders afhandelen? (Met een prompt?)
        f.createNewFile();
        FileWriter fw = new FileWriter(path);
        fw.write(s);
        fw.flush();
        fw.close();
    }
    // Save infrastructure models to file
    public static void serializeInfrastructuur(ArrayList<InfrastructureComponentModel> components) throws IOException {

        Gson g = new Gson();
        String s = g.toJson(components);
        String path = System.getProperty("user.dir") + "\\infrastructuur.json";
        System.out.println(path);

        File f = new File(path);
        if(!f.exists()){
            f.createNewFile();
        }

        FileWriter fw = new FileWriter(f, false);
        fw.write(s);
        fw.flush();
        fw.close();
    }
    // Save infrastructure models to file
    public static void serializeInfrastructure(ArrayList<InfrastructureComponentModel> components, String path) throws IOException {
        Gson g = new Gson();
        String s = g.toJson(components);

        File f = new File(path);
        if (f.exists()) {
            int result = JOptionPane.showConfirmDialog(null, "File already exists. Would you like to overwrite it?", "WARNING", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {
                return;
            }
        }
        f.createNewFile();
        FileWriter fw = new FileWriter(path);
        fw.write(s);
        fw.flush();
        fw.close();
    }

    // Get model from file
    public static ArrayList<ComponentModel> deserializeComponents() throws IOException {
        Gson g = new Gson();
        String path = System.getProperty("user.dir") + "\\tmp.json";
        File f = new File(path);
        if (!f.exists())
            f.createNewFile();
            //throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String Json = "";
        String line;
        while ((line = br.readLine()) != null) {
            Json += line;
        }
        return (g.fromJson(Json, new TypeToken<ArrayList<ComponentModel>>(){}.getType()) == null) ? new ArrayList<ComponentModel>() : g.fromJson(Json, new TypeToken<ArrayList<ComponentModel>>(){}.getType());
    }

    // Get model from file
    public static ArrayList<ComponentModel> deserializeComponents(String path) throws IOException {
        Gson g = new Gson();
        File f = new File(path);
        if (!f.exists())
            throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String Json = "";
        String line;
        while ((line = br.readLine()) != null) {
            Json += line;
        }

        return g.fromJson(Json, new TypeToken<ArrayList<ComponentModel>>(){}.getType());

    }
    // get infrastructuremodels from file
    public static ArrayList<InfrastructureComponentModel> deserializeInfrastructure() throws IOException {
        Gson g = new Gson();
        String path = System.getProperty("user.dir") + "\\tmp.json";
        File f = new File(path);
        if (!f.exists())
            throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String Json = "";
        String line;
        while ((line = br.readLine()) != null) {
            Json += line;
        }
        return g.fromJson(Json, new TypeToken<ArrayList<InfrastructureComponentModel>>(){}.getType());
    }
    // get infrastructuremodels from file
    public static ArrayList<InfrastructureComponentModel> deserializeInfrastructure(String path) throws IOException {
        Gson g = new Gson();
        File f = new File(path);
        if (!f.exists())
            throw new FileNotFoundException();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String Json = "";
        String line;
        while ((line = br.readLine()) != null) {
            Json += line;
        }
        return g.fromJson(Json, new TypeToken<ArrayList<InfrastructureComponentModel>>(){}.getType());
    }

}
