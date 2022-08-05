package GitControler;

import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;

public class Polecenia {
    private String directory = "~/";

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

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
            returning+=linia+" | ";
        }
        return returning;
    }
    public void init() throws IOException {
        Process process = new ProcessBuilder(new String[]{"git init"}).directory(new File(directory)).start();
        process.destroy();
    }
    public static String getOsCmd(){
        Properties props=System.getProperties(); // Get the system property set
        String osName = props.getProperty("os.name"); // Operating system name
        if(osName.toLowerCase().contains("linux")){
            return "/bin/sh -c";
        }else if(osName.toLowerCase().contains("windows")){
            return "cmd /c";
        }else{
            throw new RuntimeException(" The server is not linux|windows Operating system ");
        }
    }

    public void testDirectory() throws IOException, InterruptedException {
        final File file = new File(directory);
        System.out.println(file.isDirectory());
        System.out.println(file.canRead());
        System.out.println(file.canWrite());
        Process proc = null;
        String command = getOsCmd()+"cd "+directory+" && dir" ;

        proc = Runtime.getRuntime().exec(command);
        // Read the output

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(proc.getInputStream()));

        String line = "";
        while((line = reader.readLine()) != null) {
            System.out.print(line + "\n");
        }

        proc.waitFor();


    }
    public void changeBranch(String branch) throws IOException {
        Process proc = Runtime.getRuntime().exec("git switch "+branch);
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        String linia = "";
        while ((linia= reader.readLine())!=null){
            System.out.println(linia);
        }
        proc.destroy();
    }
    public String actual_branch() throws Exception {
        Process procCommit = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD" );
        BufferedReader reader = new BufferedReader(new InputStreamReader(procCommit.getInputStream()));
        return reader.readLine();
    }

    public void combo() throws IOException {
        Process process = new ProcessBuilder(new String[]{"cd "+directory ,"git add .", "git commit -m \"data1\"", "git pull"}).start();
        process.destroy();
    }
    public void checkoutBranch(String branch, String commit_message) throws Exception {
        String linia = "+";
        Process procAdd = Runtime.getRuntime().exec("git add . ; git commit -m "+ commit_message+ " ; git push main "+ actual_branch());
        BufferedReader reader5 = new BufferedReader(new InputStreamReader(procAdd.getInputStream()));
        while ((linia= reader5.readLine())!=null){
            System.out.println(linia);
        }
        String wyslij = "git push main "+ actual_branch();
        System.out.println(wyslij);
        Process procSend = Runtime.getRuntime().exec(wyslij);
        BufferedReader reader3 = new BufferedReader(new InputStreamReader(procSend.getInputStream()));
        while ((linia= reader3.readLine())!=null){
            System.out.println(linia);
        }
        Process proc = Runtime.getRuntime().exec("git checkout "+branch);
        BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        while ((linia= reader.readLine())!=null){
            System.out.println(linia);
        }
    }
}
