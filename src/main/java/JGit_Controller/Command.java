package JGit_Controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.DepthWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.FileUtils;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;


public class Command {
    private String directory;
    private String sshAddress;
    private static final Logger logger = LogManager.getLogger(Command.class);
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public void firstConfiguration() throws IOException {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File(directory+"/.git")).readEnvironment().findGitDir().setMustExist(true).build();
        System.out.println("[Stworzono nowe repozytorium]");
        Git git = new Git(repository);
        StoredConfig config = git.getRepository().getConfig();
        config.setString("remote","origin", "url", sshAddress);
        config.save();
        System.out.println("[ Dodano adres SSH ]");
    }

    public void makeCommit() throws IOException, GitAPIException {
        logger.info("Wykonywanie commita");
        Git git = Git.open(new File(directory+"/.git"));
        git.add().addFilepattern(".").call();
        git.commit().setMessage("Initial commit").setCommitter("Mateusz","mateuszspzoo@gmail.com").call();
        logger.debug("Committed files to repository at " + git.getRepository().getDirectory());
    }
    public void makeChange(String new_branch) throws GitAPIException, IOException {
        logger.info("WYkonywanie zmiany brancha");
        makeCommit();
        Git git = Git.open(new File(directory+"/.git"));
        logger.debug("Otwarto repozytorium");
        Repository repository = git.getRepository();
        CreateBranchCommand bcc;
        bcc = git.branchCreate();
        bcc.setName(new_branch)
                .setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.SET_UPSTREAM)
                .setStartPoint("main/" + new_branch)
                .setForce(true)
                .call();
        git.checkout().setName(new_branch).call();
        git.pull();
        System.out.println("[ Koniec pull ]");
        Ref ref = git.checkout().setName(new_branch).setCreateBranch(true).setUpstreamMode(CreateBranchCommand.SetupUpstreamMode.TRACK).setStartPoint("main/"+new_branch).call();

    }

    public void makeversion(Boolean hotfix, Integer month, Integer year) throws GitAPIException, IOException {
        logger.info("Dodanie wersji");
        makeCommit();
        logger.debug("Sprawdzanie hotfiksa");
        if(hotfix==true){
            logger.trace("[ hotfix==true ]");
            File file = new File(directory+"/version.properties");
            String line = new String();
            Scanner sc = new Scanner(file);     //file to be scanne
            Boolean znaleziono = false;
            Vector<String> vector= new Vector<>();
            logger.trace("Odczyt linii");
            while (sc.hasNextLine() && znaleziono==false) {
                line = sc.nextLine();
                System.out.println(line);
                if(line.indexOf("version=")!=-1){
                    znaleziono=true;
                    Integer version = Integer.valueOf(line.substring(line.lastIndexOf('.')+1));
                    Integer old_year = Integer.valueOf(line.substring(line.indexOf('=')+1,line.indexOf(".")));
                    Integer old_month = Integer.valueOf(line.substring(line.indexOf('.')+1,line.lastIndexOf('.')));
                    logger.debug("Ostatnia wersja: "+old_year+"-"+old_month+"-"+version);
                    logger.debug(old_year+"-"+year);
                    if ((int)old_year!=(int)year){ // jeżeli różne lata to dodaj do wektora DD PRAWIDŁOWO, ALE
                        logger.trace("[ Inny rok data ]");
                        line="version="+year+"."+month+".0";
                    }else{
                    if(old_month!=month ){
                       logger.trace("[ Inny miesiąc data ]");
                        line="version="+year+"."+month+".0";
                    }else{
                        logger.trace("[ Stara data ]");
                        line="version="+year+"."+month+"."+(++version);
                    }}
                }
                vector.add(line);
            }
            if(znaleziono==false){
                logger.error("Wskazano błędny plik. Brak parametru version");
                throw new IOException("Wskazano błędny plik. Brak parametru version");
            }
            sc.close();
            FileWriter bw = new FileWriter(file);
            logger.debug("[Zapis do pliku]");
            for(int i=0; i< vector.size(); i++){
                bw.write(vector.get(i));
                logger.trace(vector.get(i));
            }
            bw.close();
            push();
            logger.debug("Zamykanie plików i wykonanie pusha");
        }
    }

    public void push() throws IOException, GitAPIException {
        logger.info("Push 1");
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(new File(directory+"/.git")).readEnvironment().findGitDir().build();
        Git git = new Git(repository);
        StoredConfig config = git.getRepository().getConfig();
        config.setString("branch", "main", "merge", "refs/heads/main");
        PullCommand pc = git.pull();
        pc.call();

        logger.info("Push 2");
        String[] commands = {"/bin/bash","-c","'cd "+directory,"; git push --force main main'"};
        String command = "/bin/bash -c 'cd "+directory+"; git push bazadanych bazadanych'";


        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void example() throws IOException {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        Repository repository = repositoryBuilder.setGitDir(new File(directory+"/.git")).readEnvironment().findGitDir().setMustExist(true).build();

        //Git git = new Git(repository);
        ObjectId lastCommitId = repository.resolve(Constants.HEAD);
        RevWalk revWalk = new RevWalk(repository);
        RevCommit commit = revWalk.parseCommit(lastCommitId);
        // and using commit's tree find the path
        RevTree tree = commit.getTree();
        System.out.println("Having tree: " + tree);

        // now try to find a specific file
        TreeWalk treeWalk = new TreeWalk(repository);
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create("README.md"));
        if (!treeWalk.next()) {
            throw new IllegalStateException("Did not find expected file 'README.md'");
        }

        ObjectId objectId = treeWalk.getObjectId(0);
        ObjectLoader loader = repository.open(objectId);

        // and then one can the loader to read the file
        loader.copyTo(System.out);
        revWalk.dispose();

    }
    /*
     * Pull latest from 'source' and 'binary' repository.
     */

    public String getSshAddress() {
        return sshAddress;
    }

    public void setSshAddress(String sshAddress) {
        this.sshAddress = sshAddress;
    }
}
