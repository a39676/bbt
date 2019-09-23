package demo.movie.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.eclipse.bittorrent.TorrentFile;

public class ReadTorrentFile  {

	public static void main(String[] args) {
		String path = null;
		path = "d:/auxiliary/tmp/三月的狮子.后篇【BluRay.1080P-3.93GB】【中文字幕】.torrent";
//		path = "d:/auxiliary/tmp/test.torrent";
//		path = "D:\\myPacks\\auxiliaryCommon/build.gradle";
//		String str = iou.getStringFromFile(path, StandardCharsets.UTF_8.displayName());
//		System.out.println(str);
		File t = new File(path);
		TorrentFile f = null;
		try {
			f = new TorrentFile(t);
		} catch (IllegalArgumentException | IOException e) {
			e.printStackTrace();
		}
		System.out.println(f.getHexHash());
		System.out.println(new String(f.getInfoHash().getBytes(StandardCharsets.UTF_16), StandardCharsets.UTF_8));
		System.out.println(new String(f.getInfoHash().getBytes(StandardCharsets.UTF_16BE), StandardCharsets.UTF_8));
		System.out.println(new String(f.getInfoHash().getBytes(StandardCharsets.UTF_16LE), StandardCharsets.UTF_8));
		System.out.println(new String(f.getInfoHash().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
		for(String s : f.getFilenames()) {
			System.out.println(new String(s.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
		}
		System.out.println(f.getTracker());
	}
	
}
