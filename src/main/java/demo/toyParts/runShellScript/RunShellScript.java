package demo.toyParts.runShellScript;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.service.CommonService;

@Controller
@RequestMapping("/testSh")
public class RunShellScript extends CommonService {

	@GetMapping("/run")
	@ResponseBody
	public String run() {
		
		ProcessBuilder processBuilder = new ProcessBuilder();

		// -- Linux --

		// Run a shell command
//		processBuilder.command("bash", "-c", "ls /home/mkyong/");
		

		// Run a shell script
		//processBuilder.command("path/to/hello.sh");
		processBuilder.command("/home/u2/tmp/tmp.sh");

		// -- Windows --

		// Run a command
		//processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

		// Run a bat file
		//processBuilder.command("C:\\Users\\mkyong\\hello.bat");

		try {

			Process process = processBuilder.start();

//			StringBuilder output = new StringBuilder();

//			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

//			String line;
//			while ((line = reader.readLine()) != null) {
//				output.append(line + "\n");
//			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
//				System.out.println("Success!");
//				System.out.println(output);
//				System.exit(0);
				return "done";
			} else {
				//abnormal...
				return "error";
			}

		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "error";
		}
		
	}
}
