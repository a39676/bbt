package demo.clawing.movie.service;

import java.io.IOException;

public interface MovieManagerService {

	/**
	 * 定期任务调用
	 * 删除超长期限的资源(物理删除)
	 * @throws IOException
	 */
	void deleteOldHistory() throws IOException;

}
