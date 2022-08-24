package JGit_Controller;

import GitControler.LogSpr;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.errors.GitAPIException;
import java.io.IOException;


public class Controler {

    private static final Logger logger = LogManager.getLogger(Controler.class);
    public static void main(String[] args) throws IOException, GitAPIException {

        logger.debug("Stworzenie klasy GITa");
        Command gitM = new Command();
        gitM.setSshAddress("git@github.com:Mateusz2088/SIMforZHP.git");
        gitM.setDirectory("/home/mati/newSIMapp/SIMapp");
        gitM.makeversion(true,8,2022);
        ///gitM.example();
    }
    
}
