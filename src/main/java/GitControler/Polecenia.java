package GitControler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Polecenia {
    public String wersja() throws Exception {
        Process proc = Runtime.getRuntime().exec("git --version");
        BufferedReader version = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String linia = version.readLine();
        if(linia.indexOf("git version",0)!= -1){
            return linia.substring(11);
        }else{
            throw new Exception("Brak zainstalowanego GITa");
        }
    }
    public void listsBranch() throws Exception {
        Process proc = Runtime.getRuntime().exec("git --list ");
        BufferedReader branch = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String linia = "";
        while ((linia= branch.readLine())!=null){
            System.out.println(linia);
        }

    }
}
