package JGit_Controller;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class Controler {
    public static void main(String[] args) throws IOException, GitAPIException {
        Command gitM = new Command();
        gitM.setSshAddress("git@github.com:Mateusz2088/Tests1.git");
        gitM.setDirectory("/home/mati/Dokumenty/praktykaTT/GITtest/JGitTestREposistory542474467175853283");
        gitM.makeChange("main");
        ///gitM.example();
    }
}
