package vn.leoo.common.util.file;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class FileUtils {
    public static InputStream resizeImage(InputStream inputStream, int width, int height, String extension) throws IOException {
        BufferedImage sourceImage = ImageIO.read(inputStream);
        Image thumbnail = sourceImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null), BufferedImage.SCALE_DEFAULT);
        bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedThumbnail, extension, baos);

        return new ByteArrayInputStream(baos.toByteArray());
    }

    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf(".");
        int lastUnixPos = filename.lastIndexOf("/");
        int lastWindowsPos = filename.lastIndexOf("\\");
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);

        int index = lastSeparator > extensionPos ? -1 : extensionPos;
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }

    /**
     * List all the files and folders from a directory
     *
     * @param directoryName to be listed
     */

    public void listFilesAndFolders(String directoryName) {

        File directory = new File(directoryName);

        //get all the files from a directory

        File[] fList = directory.listFiles();

        for (File file : fList) {

            System.out.println(file.getName());

        }

    }

    /**
     * List all the files under a directory
     *
     * @param directoryName to be listed
     */

    public List<FileInfo> listFiles(String directoryName) {
        List<FileInfo> list = new ArrayList<FileInfo>();
        try {
            File directory = new File(directoryName);
            
            File[] fList = directory.listFiles();
            for (File file : fList) {
                if (file.isFile()) {
                	String extension = FileUtils.getExtension(file.getName());
                    FileInfo info = new FileInfo();
                    info.setAbsolutePath(file.getAbsolutePath());
                    info.setName(file.getName());
                    info.setParent(file.getParent());
                    info.setPath(file.getPath());
                    info.setExtension(extension);
                    list.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<FileInfo> listFilesByLimit(String directoryName, long maxSize) {
        List<FileInfo> list = new ArrayList<FileInfo>();
        try {
            File directory = new File(directoryName);
            
            List<Path> listPaths = Files.list(directory.toPath()).limit(maxSize).collect(Collectors.toList());
            for (Path path : listPaths) {
            	FileInfo info = new FileInfo();
                info.setAbsolutePath(path.toString());
                info.setName(path.getFileName().toString());
                info.setParent(path.getParent().toString());
                info.setPath(path.toString());
                list.add(info);
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int countItemFolder(String directoryName) {
        try {
            File directory = new File(directoryName);

            //get all the files from a directory

            File[] fList = directory.listFiles();
            return fList.length;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    public List<String> listFiles1(String directoryName, String index) {
        List<String> list = new ArrayList<String>();
        try {
            File directory = new File(directoryName);

            //get all the files from a directory

            File[] fList = directory.listFiles();

            for (File file : fList) {
                if (file.isFile() && (System.currentTimeMillis() - file.lastModified()) > 300000) {
//			    if (file.isFile()){
                    if (file.getName().indexOf(".xml") <= 0) continue;
                    String fileName = file.getName().replace(".xml", "");
                    //System.out.println(fileName.length());
                    if (fileName.lastIndexOf(index) == fileName.length() - 1) {
                        list.add(file.getAbsolutePath());
                        System.out.println(file.getName());
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

    /**
     * List all the folder under a directory
     *
     * @param directoryName to be listed
     */

    public void listFolders(String directoryName) {

        File directory = new File(directoryName);

        //get all the files from a directory

        File[] fList = directory.listFiles();

        for (File file : fList) {

            if (file.isDirectory()) {

                System.out.println(file.getName());

            }

        }

    }

    /**
     * List all files from a directory and its subdirectories
     *
     * @param directoryName to be listed
     */

    public void listFilesAndFilesSubDirectories(String directoryName) {

        File directory = new File(directoryName);

        //get all the files from a directory

        File[] fList = directory.listFiles();

        for (File file : fList) {

            if (file.isFile()) {

                System.out.println(file.getAbsolutePath());

            } else if (file.isDirectory()) {

                listFilesAndFilesSubDirectories(file.getAbsolutePath());

            }

        }

    }

    public void createDirectory(String dir_name) {
        try {
            File apath = new File(dir_name);
            if (!apath.exists()) {
                apath.mkdirs();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            file.delete();
        } catch (Exception e) {
            throw e;
        }
    }
    
    public void appendFile(String filePath, String content){
    	BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bw = new BufferedWriter(fw);

			bw.write(content);

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
    
    public void writeFile(String filePath, String content){
    	BufferedWriter bw = null;
		FileWriter fw = null;

		try {
			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// true = append file
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);

			bw.write(content);

			System.out.println("Done.");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();

				if (fw != null)
					fw.close();

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    }
    
    public byte[] readBytes( InputStream stream, Boolean inputStreamIsClosed) throws IOException {
        if (stream == null) 
        	return null; //new byte[] {};
        
        byte[] content = null;
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        boolean error = false;
        try {
            int numRead = 0;
            while ((numRead = stream.read(buffer)) > -1) {
                output.write(buffer, 0, numRead);
            }
            output.flush();
            content = output.toByteArray();
        } catch (IOException e) {
            error = true; // this error should be thrown, even if there is an error closing stream
            throw e;
        } catch (RuntimeException e) {
            error = true; // this error should be thrown, even if there is an error closing stream
            throw e;
        } finally {
            try {
            	output.close();
            	if(inputStreamIsClosed.booleanValue()){
            		stream.close();
            	}
            } catch (IOException e) {
                if (!error) throw e;
            }
        }
        
        return content;
    }
    
    @SuppressWarnings("unused")
	public void splitFile(InputStream inputStream, String fileName) throws Exception{
    	byte PART_SIZE = 5;
   	
		String newFileName;
		FileOutputStream filePart;
		int fileSize = 0, maxFileSize = 1024;
		int nChunks = 0, read = 0, readLength = maxFileSize;
		byte[] byteChunkPart;
		try {
			fileSize = 4284;//this.readBytes(inputStream, false).length;
			System.out.println("File size2: "+ fileSize);
			while (fileSize > 0) {
				if (fileSize <= maxFileSize) {
					readLength = fileSize;
				}
				byteChunkPart = new byte[readLength];
				read = inputStream.read(byteChunkPart, 0, readLength);
				fileSize -= readLength;
				assert (read == byteChunkPart.length);
				nChunks++;
				newFileName = fileName + ".part" + Integer.toString(nChunks - 1);
				
				filePart = new FileOutputStream(new File(newFileName));
				filePart.write(byteChunkPart);
				filePart.flush();
				filePart.close();
				byteChunkPart = null;
				filePart = null;
			}
			inputStream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			throw e;
		}
    }
    
    public List<byte[]> splitByteArray(InputStream is, int blockSize) throws Exception{
    	List<byte[]> listByteArray = new ArrayList<byte[]>();

		try {
			byte[] data = this.readBytes(is, true);
			if(data != null){
				int blockCount = (data.length + blockSize - 1) / blockSize;
	
				byte[] range = null;
	
				for (int i = 1; i < blockCount; i++) {
						int idx = (i - 1) * blockSize;
						range = Arrays.copyOfRange(data, idx, idx + blockSize);
						listByteArray.add(range);
				}
				
				// Last chunk
				int end = -1;
				if (data.length % blockSize == 0) {
						end = data.length;
				} else {
						end = data.length % blockSize + blockSize * (blockCount - 1);
				}
						
				range = Arrays.copyOfRange(data, (blockCount - 1) * blockSize, end);
				listByteArray.add(range);
			}
		} catch (IOException e) {
			System.err.print(e.getMessage());
			throw e;
		}
		
		return listByteArray;
    }
    
    public List<byte[]> splitByteArray(byte[] data, int blockSize) throws Exception{
    	List<byte[]> listByteArray = new ArrayList<byte[]>();
    	
		try {
			if(data != null){
				int blockCount = (data.length + blockSize - 1) / blockSize;
	
				byte[] range = null;
	
				for (int i = 1; i < blockCount; i++) {
						int idx = (i - 1) * blockSize;
						range = Arrays.copyOfRange(data, idx, idx + blockSize);
						listByteArray.add(range);
				}
				
				// Last chunk
				int end = -1;
				if (data.length % blockSize == 0) {
						end = data.length;
				} else {
						end = data.length % blockSize + blockSize * (blockCount - 1);
				}
						
				range = Arrays.copyOfRange(data, (blockCount - 1) * blockSize, end);
				listByteArray.add(range);
			}
		} catch (Exception e) {
			System.err.print(e.getMessage());
			throw e;
		}
		
		return listByteArray;
    }
    
    public byte[] mergeByteArray(List<byte[]> listByteArray) throws Exception{
    	byte[] content = null;
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	try{
    		for(int i=0; listByteArray!=null && i<listByteArray.size(); i++){
    			baos.write(listByteArray.get(i));
    		}
    		baos.flush();
    		content = baos.toByteArray();
    	}catch(Exception e){
    		System.err.print(e.getMessage());
    		throw e;
    	}finally{
    		try{
    			if(baos != null)
    				baos.close();
    		}catch(Exception e){
    			System.err.print(e.getMessage());
    		}
    	}
    	return content;
    }   
    
}
