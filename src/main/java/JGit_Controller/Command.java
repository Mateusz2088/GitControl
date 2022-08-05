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
    public void makeCommit(String directory) throws IOException, GitAPIException {
        File localPath = File.createTempFile("JGitTestREposistory","", new File(directory));
        Files.delete(localPath.toPath());

        Git git = Git.init().setDirectory(localPath).call();
        System.out.println("Tworzenie repozytorium: "+ git.getRepository().getDirectory());
        File myFile = new File(git.getRepository().getDirectory().getParent(),"testfile");
        if(!myFile.createNewFile()){
            throw new IOException("Nie mogę stworzyć pliku " + myFile);
        }
        git.add().addFilepattern(" . ").call();
        git.commit().setMessage("Initial commit").call();
        System.out.println("Committed file " + myFile + " to repository at " + git.getRepository().getDirectory());
    }
    public void example(String directory) throws IOException {
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
}
