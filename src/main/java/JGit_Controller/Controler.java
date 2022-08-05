package JGit_Controller;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public class Controler {
    public static void main(String[] args) throws IOException, GitAPIException {
        Command git = new Command();
        git.openRepo("/home/mati/Dokumenty/praktykaTT/GITtest");
        git.createExampleBranch();
        git.commitFiles("/home/mati/Dokumenty/praktykaTT/GITtest");

    }
}
