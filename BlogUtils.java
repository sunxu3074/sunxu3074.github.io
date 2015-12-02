import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BlogUtils {

	static List<File> list = new ArrayList<File>();

	public static void main(String[] args) {
		fileGenerator("d:\\test");
		for (final File file : list) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					changeChar(file);
				}
			}).start();
		}
	}

	private static void changeChar(File file) {
		BufferedReader in = null;
		BufferedWriter out = null;
		String content = "";
		String oldchar = "";
		String newchar = "";
		try {
			in = new BufferedReader(new FileReader(file));
			while ((content = in.readLine()) != null) {
				oldchar += content + "\r\n";
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
					in = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			if (oldchar.contains("localhost:1313")) {
				newchar = oldchar.replaceAll("localhost:1313", "isunxu.xyz");
			}
			// else if(oldchar.contains("localhost")){
			// newchar = oldchar.replaceAll("localhost","isunxu.xyz");
			// }

			out = new BufferedWriter(new FileWriter(file));
			out.write(newchar);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void fileGenerator(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File subFile : files) {
				if (subFile.isDirectory()) {
					fileGenerator(subFile.getAbsolutePath());
				} else {
					list.add(subFile);
				}
			}
		}
	}

}
