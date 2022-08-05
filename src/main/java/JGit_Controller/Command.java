package JGit_Controller;

import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.BranchConfig;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Command {
    Repository repo;

    public void createNewRepo(String directory) throws IOException {
        repo = FileRepositoryBuilder.create(new File(directory+"/.git"));
    }
    public void openRepo(String directory) throws IOException {
        repo = new FileRepositoryBuilder()
                .setGitDir(new File(directory+"/.git"))
                .build();
    }
    public void commitFiles(String directory) throws IOException, GitAPIException {
        Git git = new Git(repo);
        AddCommand add = git.add();
        add.addFilepattern(directory).call();
        CommitCommand comit = git.commit();
        comit.setAll(true);
        comit.setMessage("Initiation commit").call();
        PushCommand pushCommand = git.push();
        pushCommand.setPushAll();
    }
    public void createExampleBranch() throws GitAPIException {
        Git git = new Git(repo);
        CreateBranchCommand branchCommand = git.branchCreate();
        branchCommand.setName("Mati");
        branchCommand.call();
    }
}
