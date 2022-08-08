package JGit_Controller;

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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class Command {
    private String directory;
    private String sshAddress;

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
        Git git = Git.open(new File(directory+"/.git"));
        Repository repository = git.getRepository();
        git.add().addFilepattern(" . ").call();
        git.commit().setMessage("Initial commit").setCommitter("Mateusz","mateuszspzoo@gmail.com").call();
        System.out.println("Committed files to repository at " + git.getRepository().getDirectory());
    }
    public void makeChange(String new_branch) throws GitAPIException, IOException {
        makeCommit();
        System.out.println("[ Wykonano commit ]");
        Git git = Git.open(new File(directory+"/.git"));
        System.out.println("[ Otwarto repozytorium ]");
        Repository repository = git.getRepository();
        PullCommand pullCommand = git.pull();
        try{
            pullCommand.call();
            System.out.println("[ Wykonano pull ]");
        }catch (GitAPIException e){
            e.printStackTrace();
        }
        System.out.println("[ Koniec pull ]");
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

    public String getSshAddress() {
        return sshAddress;
    }

    public void setSshAddress(String sshAddress) {
        this.sshAddress = sshAddress;
    }
}
