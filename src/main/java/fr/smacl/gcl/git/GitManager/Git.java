package fr.smacl.gcl.git.GitManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Git {

	// example of usage
	private static void initAndAddFile() throws IOException, InterruptedException {
		Path directory = Paths.get("c:\\temp\\example");
		Files.createDirectories(directory);
		gitInit(directory);
		Files.write(directory.resolve("example.txt"), new byte[0]);
		gitStage(directory);
		gitCommit(directory, "Add example.txt");
	}

	// example of usage
	private static void cloneAndAddFile() throws IOException, InterruptedException {
		String originUrl = "https://github.com/Crydust/TokenReplacer.git";
		Path directory = Paths.get("c:\\temp\\TokenReplacer");
		gitClone(directory, originUrl);
		Files.write(directory.resolve("example.txt"), new byte[0]);
		gitStage(directory);
		gitCommit(directory, "Add example.txt");
		gitPush(directory);
	}

	public static void gitInit(Path directory) throws IOException, InterruptedException {
		log.info("init");
		runCommand(directory, "git", "init");
	}

	public static void gitStage(Path directory) throws IOException, InterruptedException {
		log.info("gitStage");
		runCommand(directory, "git", "add", "-A");
	}

	public static void gitCommit(Path directory, String message) throws IOException, InterruptedException {
		log.info("gitCommit");

		runCommand(directory, "git", "commit", "-m", message);
	}

	public static void gitPush(Path directory) throws IOException, InterruptedException {
		log.info("gitPush");		
		runCommand(directory, "git", "push","-u", "origin", "master");
	}

	public static void gitClone(Path directory, String originUrl) throws IOException, InterruptedException {
		runCommand(directory.getParent(), "git", "clone", originUrl, directory.getFileName().toString());
	}
	public static void gitRemote(Path directory, String originUrl) throws IOException, InterruptedException {
		log.info("gitRemote");	
		runCommand(directory,"git","remote" , "add" ,"origin" ,originUrl);
	}
	public static void runCommand(Path directory, String... command) throws IOException, InterruptedException {
		//log.info(command.toString());
		Objects.requireNonNull(directory, "directory");
		if (!Files.exists(directory)) {
			Files.createDirectories(directory);
		//	throw new RuntimeException("can't run command in non-existing directory '" + directory + "'");
		}
		ProcessBuilder pb = new ProcessBuilder()
				.command(command)
				.directory(directory.toFile());
		Process p = pb.start();
		StreamGobbler errorGobbler = new StreamGobbler(p.getErrorStream(), "ERROR");
		StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream(), "OUTPUT");
		outputGobbler.start();
		errorGobbler.start();
		int exit = p.waitFor();
		errorGobbler.join();
		outputGobbler.join();
		if (exit != 0) {
			throw new AssertionError(String.format("runCommand returned %d", exit));
		}
	}

	private static class StreamGobbler extends Thread {

		private final InputStream is;
		private final String type;

		private StreamGobbler(InputStream is, String type) {
			this.is = is;
			this.type = type;
		}

		@Override
		public void run() {
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is));) {
				String line;
				while ((line = br.readLine()) != null) {
					System.out.println(type + "> " + line);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

}