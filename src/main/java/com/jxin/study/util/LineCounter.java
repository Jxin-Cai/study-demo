package com.jxin.study.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author liurong
 *
 */
public class LineCounter {
	List<File> list = new ArrayList<File>();
	int linenumber = 0;

	FileReader fr = null;
	BufferedReader br = null;

	public void counter(String projectName) {
		File projectFile = new File(projectName);
		File[] projectListFiles = projectFile.listFiles();
		int totalLine = 0;
		for (File file : projectListFiles) {
			if (file.isDirectory()) {
				String path = file.getAbsolutePath() + "/src/main/java";
				File file1 = new File(path);
				if (!file1.exists()) {
					counter(file.getPath());
					continue;
				}
				System.out.println(path);
				File files[] = null;
				files = file1.listFiles();
				addFile(files);
				isDirectory(files);
				readLinePerFile();
				System.out.println("Totle:" + linenumber + "行");
			}
			totalLine = totalLine + linenumber;
			list.clear();
			linenumber = 0;
		}
	}

	// 判断是否是目录
	public void isDirectory(File[] files) {
		for (File s : files) {
			if (s.isDirectory()) {
				File file[] = s.listFiles();
				addFile(file);
				isDirectory(file);
				continue;
			}
		}
	}

	// 将src下所有文件组织成list
	public void addFile(File file[]) {
		for (int index = 0; index < file.length; index++) {
			list.add(file[index]);
			// System.out.println(list.size());
		}
	}

	// 读取非空白行
	public void readLinePerFile() {
		try {
			for (File s : list) {
				if (s.isDirectory()) {
					continue;
				}
				fr = new FileReader(s);
				br = new BufferedReader(fr);
				String i = "";
				while ((i = br.readLine()) != null) {
					if (isBlankLine(i) && isNote(i))
						linenumber++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
				}
			}
			if (fr != null) {
				try {
					fr.close();
				} catch (Exception e) {
				}
			}
		}
	}

	private boolean isNote(String i) {
		String trim = i.trim();
		if (trim.startsWith("/") || trim.endsWith("/") || trim.startsWith("*")) {
			return false;
		} else {
			return true;
		}
	}

	// 是否是空行
	public boolean isBlankLine(String i) {
		if (i.trim().length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static void main(String args[]) {
		LineCounter lc = new LineCounter();
		String projectName = "E:\\project\\IDEA\\IDEA-B\\scs"; // 这里传入你的项目名称
		lc.counter(projectName);
	}
}