/**
 * 
 */
package fr.smacl.gcl.git.GitManager;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
* @author s-jubien 
* @since  
* @version $version  
*/
@RestController
@RequestMapping(path = "/api")

public class GitControler {
	
	@RequestMapping(path = "/info", method = { RequestMethod.POST }, name = "info")
	public void info(@RequestParam("dir") String req) throws Exception {
		Path directory = Paths.get(req);
		Git.gitInit(directory);
	}	
	
	@RequestMapping(path = "/createrepository", method = { RequestMethod.POST }, name = "createrepository")
	public void createRepository(@RequestParam("dir") String req) throws Exception {
		Path directory = Paths.get(req);
		Git.gitInit(directory);
		Git.gitStage(directory);
		Git.gitCommit(directory, " mon messsage");
		Git.gitRemote(directory,"https://github.com/jubien/first.git");
		Git.gitPush(directory);
	}		
	/*
	* Attribut
	*/

	/*
	* Constructeur
	*/

	/*
	* Comportement
	*/
}
