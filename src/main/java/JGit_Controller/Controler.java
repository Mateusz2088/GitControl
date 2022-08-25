package JGit_Controller;

import GitControler.LogSpr;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class Controler {

    private static final Logger logger = LogManager.getLogger(Controler.class);
    public static void main(String args[]) throws IOException, GitAPIException {

        logger.debug("Stworzenie klasy GITa");
        Command gitM = new Command();
        gitM.setSshAddress("git@github.com:Mateusz2088/Test1.git");
        gitM.setDirectory("/home/mati/Dokumenty/praktykaTT/GITtest");
        gitM.makeversion(true,8,2022);
        ///gitM.example();
    }
    
}
