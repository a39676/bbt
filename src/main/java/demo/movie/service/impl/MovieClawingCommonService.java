package demo.movie.service.impl;

import java.io.File;
import java.io.IOException;

import org.eclipse.bittorrent.TorrentFile;

import demo.baseCommon.service.CommonService;

public abstract class MovieClawingCommonService extends CommonService {

	protected final String mainSavePath = "/home/u2/movieClawing";
	protected final String introductionSavePath = mainSavePath + "/introduction";
	
	public String getMangetUrlFromTorrent(String path) {
		File t = new File(path);
		if(!t.exists()) {
			return null;
		}
		try {
			TorrentFile f = new TorrentFile(t);
			return "magnet:?xt=urn:btih:" + f.getHexHash();
		} catch (IllegalArgumentException | IOException e) {
			log.error("read torrent error path {}", path);
			return null;
		}
	}
}
