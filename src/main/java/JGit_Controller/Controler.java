package JGit_Controller;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class Controler {
    public static void main(String[] args) throws IOException, GitAPIException {
        Command gitM = new Command();
        //gitM.example("/home/mati/Dokumenty/praktykaTT/GITtest");
        gitM.makeCommit("/home/mati/Dokumenty/praktykaTT/GITtest");

    }
}
