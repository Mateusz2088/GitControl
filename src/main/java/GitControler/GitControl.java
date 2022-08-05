/*
@author Mateusz Szewczak

Obsługa GITa
 */
package GitControler;



public class GitControl {
    public static void main(String[] args) throws Exception {
        System.out.println("Sprawdzanie GIT-a");
        Polecenia git = new Polecenia();
        System.out.println(git.wersja());
        git.setDirectory("/home/mati/Dokumenty/praktykaTT/GITtest");
        git.testDirectory();
        /*
        git.init();
        git.changeBranch("mama");
        git.checkoutBranch("mama", "'Poprawiono może wysyłanie252'");*/
    }
}
