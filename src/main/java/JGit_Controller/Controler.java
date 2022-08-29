package JGit_Controller;

import org.eclipse.jgit.api.errors.GitAPIException;


import java.io.IOException;
import java.util.logging.Logger;


public class Controler {

    private static final Logger logger = Logger.getLogger(Controler.class.getName());
    public static void main(String args[]) throws IOException, GitAPIException {

        logger.info("Stworzenie klasy GITa");
        Command gitM = new Command();
        gitM.setSshAddress("git@github.com:Mateusz2088/Test1.git");
        gitM.setDirectory("/home/mati/Dokumenty/praktykaTT/GITtest");
        gitM.makeversion(true,8,2022);
        ///gitM.example();
    }
    
}
