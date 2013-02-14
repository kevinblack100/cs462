package kpbinc.test.io.util;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileIOTestContext {

	//==================================================================================================================
	// Class Data
	//==================================================================================================================
	
	public static final String DEFAULT_BASE_DIRECTORY = "/tmp";
	
	
	//==================================================================================================================
	// Member Data
	//==================================================================================================================
	
	private String baseDirectory;
	private String extension;
	
	
	//==================================================================================================================
	// Initialization
	//==================================================================================================================
	
	public FileIOTestContext(String extension) {
		baseDirectory = DEFAULT_BASE_DIRECTORY;
		this.extension = extension;
	}
	
	public FileIOTestContext(String baseDirectory, String extension) {
		this.baseDirectory = baseDirectory;
		this.extension = extension;
	}

	public FileIOTestContext(Class<?> testClazz, String extension) {
		this(DEFAULT_BASE_DIRECTORY + "/" + testClazz.getSimpleName());
	}
	
	//==================================================================================================================
	// Interface
	//==================================================================================================================
	
	public File getFileHandle(String filename) {
		File file = new File(baseDirectory + "/" + filename + "." + extension);
		return file;
	}
	
	public File getPathAssuredFileHandle(String filename) {
		File file = getFileHandle(filename);
		file.getParentFile().mkdirs();
		return file;
	}
	
	/**
	 * If an IOException occurs then fails the test.
	 */
	public void writeFile(String filename, String content) {
		try {
			File destination = getPathAssuredFileHandle(filename);
			
			FileWriter fw = new FileWriter(destination);
			
			fw.write(content);
			
			fw.close();
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * If an IOException occurs then fails the test.
	 */
	public String readFile(String filename) {
		String result = null;
		
		try {
			File source = getFileHandle(filename);
			FileReader fr = new FileReader(source);
			BufferedReader br = new BufferedReader(fr);
			
			StringBuilder builder = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				builder.append(line);
				line = br.readLine();
			}
			
			br.close();
			
			result = builder.toString();
		}
		catch (IOException e) {
			fail(e.getMessage());
		}
		
		return result;
	}

	/**
	 * If the file is not deleted then fails the test.
	 */
	public void deleteFile(String filename) {
		File file = getFileHandle(filename);
		boolean deleted = file.delete();
		if (!deleted) {
			fail(String.format("Deletion of %s failed.", file.getAbsolutePath()));
		}
	}
	
}
