package demo.toyParts.runShellScript;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/testSh")
public class RunShellScript {

	@GetMapping("/run")
	@ResponseBody
	public String run() {
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command("sh", "D:/home/u2/tmp/tmp.sh");
			builder.start();
			
			builder = new ProcessBuilder();
			builder.command("sh", "ps -ef | grep chrome | grep -v grep | awk '{print $2}' | xargs kill -9");
			builder.start();
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "done";
	}
}
