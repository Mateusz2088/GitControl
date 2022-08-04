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
        Process proc = Runtime.getRuntime().exec("git branch -a");
        BufferedReader branch = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String linia = "";
        while ((linia= branch.readLine())!=null){
            System.out.println(linia);
        }
    }
    public String listBranch() throws Exception {
        Process proc = Runtime.getRuntime().exec("git branch -a");
        BufferedReader branch = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String linia;
        String returning="";
        while ((linia= branch.readLine())!=null){
            returning+=linia+"|";
        }
        return returning;
    }

    public void changeBranch(String branch) throws IOException {
        Process proc = Runtime.getRuntime().exec("git switch "+branch);
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String linia = "";
        while ((linia= reader.readLine())!=null){
            System.out.println(linia);
        }
    }
    public String actual_branch() throws Exception {
        String Branches=listBranch();
        return "";
    }
    public void checkoutBranch(String branch, String commit_message) throws IOException {
        Process procAdd = Runtime.getRuntime().exec("git add . ");
        Process procCommit = Runtime.getRuntime().exec("git commit -m "+ commit_message );

        Process procSend = Runtime.getRuntime().exec("git push "+ actual_branch() + " "+ actual_branch() );
        Process proc = Runtime.getRuntime().exec("git checkout "+branch);
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String linia = "";
        while ((linia= reader.readLine())!=null){
            System.out.println(linia);
        }
    }
}
