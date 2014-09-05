package util.asset;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.UUID;

import play.Logger;

public class AssetUtils implements Serializable {

	/** Serial version. */
	private static final long serialVersionUID = 5556306211897684541L;

	private static final String PUBLIC_DIRECTORY = "public/";

	public enum FileType {
		IMAGE("uploads/images/");

		private final String storeDirectory;

		private FileType(String storeDirectory) {
			this.storeDirectory = storeDirectory;
		}

		public String getStoreDirectory() {
			return storeDirectory;
		}

	}

	public static String moveFileToUploads(File input, String originalFileName,
			FileType type) {
		// Create the storage directory
		File storeDirectory = new File(PUBLIC_DIRECTORY + type.getStoreDirectory());
		if(!storeDirectory.exists()) {
			boolean success = storeDirectory.mkdirs();
			
			if(!success) {
				throw new IllegalStateException("Unable to create storage directory " + storeDirectory);
			}
		}
		
		// Construct the file
		StringBuilder newFileName = new StringBuilder(type.getStoreDirectory());
		newFileName.append(generateFileName());

		String extension = getFileExtension(originalFileName);
		if (extension.length() > 0) {
			newFileName.append('.');
			newFileName.append(extension);
		}

		moveFileToPublic(input, newFileName.toString());

		return newFileName.toString();
	}

	public static boolean isFileExist(String fileName) {
		if(null == fileName || fileName.isEmpty()) {
			return false;
		}
		
		File file = new File(PUBLIC_DIRECTORY + fileName);
		return file.exists();
	}

	public static void moveFileToPublic(File input, String destinationFile) {
		moveFile(input, PUBLIC_DIRECTORY + destinationFile);
	}

	// As FileUtils import does not work at all ...
	public static void moveFile(File input, String destinationFile) {
		InputStream inStream = null;
		OutputStream outStream = null;

		try {
			inStream = new FileInputStream(input);
			outStream = new FileOutputStream(destinationFile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();

			// delete the original file
			input.delete();

			Logger.info("File is copied to upload folder!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getFileExtension(File file) {
		return getFileExtension(file.getName());
	}

	public static String getFileExtension(String fileName) {
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
			extension = fileName.substring(i + 1);
		}

		return extension;
	}

	public static String generateFileName() {
		return UUID.randomUUID().toString();
	}
}
