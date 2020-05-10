package site.nerdygadgets.functions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import site.nerdygadgets.models.ComponentModel;
import site.nerdygadgets.models.InfrastructuurComponentModel;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class Serialization {

    /*
    public static void serialize(ArrayList<Object> obj, String path) throws IOException {
        Gson g = new Gson();
        String s = g.toJson(obj);

        File f = new File(path);
        //if (f.exists())
        //    throw new FileAlreadyExistsException(path);
        //f.createNewFile();
        if (!f.exists())
            f.createNewFile();
        else
        {
            f.delete();
            f.createNewFile();
        }
        FileWriter fw = new FileWriter(path);
        fw.write(s);
        fw.flush();
        fw.close();
    }*/
    /*
    public static ArrayList<Object> deserialize(String path) throws IOException {
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

        //return g.fromJson(Json, new TypeToken<ArrayList<Object>>(){}.getType());
        return g.fromJson(Json, new TypeToken<Object>(){}.getType());
    }*/

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

    public static void serializeInfrastructuur(ArrayList<InfrastructuurComponentModel> components) throws IOException {

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

    public static void serializeInfrastructuur(ArrayList<InfrastructuurComponentModel> components, String path) throws IOException {
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


    public static ArrayList<ComponentModel> deserializeComponents() throws IOException {
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
        return g.fromJson(Json, new TypeToken<ArrayList<ComponentModel>>(){}.getType());
    }

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

        //ArrayList<ComponentModel> m = new ArrayList<ComponentModel>();
        //ArrayList<ComponentModel> model = g.fromJson(Json, new TypeToken<ArrayList<ComponentModel>>(){}.getType());
        return g.fromJson(Json, new TypeToken<ArrayList<ComponentModel>>(){}.getType());

    }

    public static ArrayList<InfrastructuurComponentModel> deserializeInfrastructuur() throws IOException {
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
        return g.fromJson(Json, new TypeToken<ArrayList<InfrastructuurComponentModel>>(){}.getType());
    }

    public static ArrayList<InfrastructuurComponentModel> deserializeInfrastructuur(String path) throws IOException {
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
        return g.fromJson(Json, new TypeToken<ArrayList<InfrastructuurComponentModel>>(){}.getType());
    }

}
