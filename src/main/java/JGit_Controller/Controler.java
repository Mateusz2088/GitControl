package JGit_Controller;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class Controler {
    public static void main(String[] args) throws IOException, GitAPIException {
        Command gitM = new Command();
        gitM.setSshAddress("git@github.com:Mateusz2088/SIMforZHP.git");
        gitM.setDirectory("/home/mati/newSIMapp/SIMapp");
        gitM.makeversion(true,8,2022);
        ///gitM.example();
    }
    
}
