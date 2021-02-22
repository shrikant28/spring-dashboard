package com.bezkoder.spring.files.upload.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.bezkoder.spring.files.upload.message.ResponseMessage;
import com.bezkoder.spring.files.upload.model.FileInfo;
import com.bezkoder.spring.files.upload.model.FinalResponse;
import com.bezkoder.spring.files.upload.model.User;
import com.bezkoder.spring.files.upload.service.FilesStorageService;

@Controller
@CrossOrigin("http://localhost:8081")
public class FilesController {
	private final Path root = Paths.get("uploads");
	@Autowired
	FilesStorageService storageService;
	
    @CrossOrigin("http://localhost:4200")
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
		String message = "";
		System.out.println("inside upload controller");
		try {
			// storageService.save(file);

			if (file.isEmpty()) {

				throw new Exception("Failed to store empty file");
			}

			try {
				System.out.println(" path is"+root.toString());
				//Files.createDirectory(root);
				Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

			
			} catch (Exception e) {
				System.out.println(e.toString());
				throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
			}

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}
   


   @CrossOrigin("http://localhost:4200")
   @GetMapping(value = "/files")
    public ResponseEntity<OutputStream> zipFiles(HttpServletResponse response) throws IOException {
		  //setting headers

	    System.out.println( "inside zip controller");
	    response.setContentType("application/zip");
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"test.zip\"");

        //creating byteArray stream, make it bufforable and passing this buffor to ZipOutputStream
        
        Resource resource = new ClassPathResource("Resume_Shrikant_Sharma_AWS.docx");

        InputStream input = resource.getInputStream();
        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(input,byteArrayOutputStream) ;
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
		/*
		 * //simple file list, just for tests ArrayList<File> files = new
		 * ArrayList<>(2); files.add(new File("README.md"));
		 * 
		 * //packing files for (File file : files) { //new zip entry and copying
		 * inputstream with file to zipOutputStream, after all closing streams
		 * zipOutputStream.putNextEntry(new ZipEntry(file.getName())); FileInputStream
		 * fileInputStream = new FileInputStream(file);
		 * 
		 * IOUtils.copy(fileInputStream, zipOutputStream);
		 * 
		 * //fileInputStream.close(); //zipOutputStream.closeEntry(); }
		 */
        if (zipOutputStream != null) {
           // zipOutputStream.finish();
           // zipOutputStream.flush();
           // IOUtils.closeQuietly(zipOutputStream);
        }
       // IOUtils.closeQuietly(bufferedOutputStream);
      //  IOUtils.closeQuietly(byteArrayOutputStream);
        //return byteArrayOutputStream.toByteArray();
        byte[] byteA
        = byteArrayOutputStream.toByteArray();
        OutputStream outstream=response.getOutputStream();
        
        outstream.write(byteA);
        //outstream.close();
        //response.flushBuffer();
        String message = "test.zip";
        //return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        //File file = new File(DIRECTORY + "/" + fileName);
        InputStreamResource resource1 = new InputStreamResource(input);
//    
//        return ResponseEntity
//                .ok(FileUtils.readFileToByteArray( System.getenv().))
//                .type("application/zip")
//                .header("Content-Disposition", "attachment; filename=\"yourfile.zip\"")
//                .build();
    //return byteA;
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+ message +"\"" ).body(outstream);
	}

	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = storageService.load(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	

    @CrossOrigin("http://localhost:4200")
	@GetMapping("/getDummy_data")
	public ResponseEntity<ArrayList> getDummyData() {
		
    	System.out.println("hit hua ");
    	
		User myobj1= new User();
		myobj1.setId("1");
		myobj1.setColor("green");
		myobj1.setName("first");
		myobj1.setProgress("569");
		

		User myobj2= new User();
		myobj2.setId("2");
		myobj2.setColor("red");
		myobj2.setName("second");
		myobj2.setProgress("123");
		
		FinalResponse finalRes= new FinalResponse();
		ArrayList arr=new ArrayList<User>();
		arr.add(myobj1);
		arr.add(myobj2);
		
		finalRes.setMyObjList(arr);
		return ResponseEntity.ok()
				.body(arr);

   // System.out.println();
    }
    
    private int[] retunto(int a[],int b[]) {
    	
    	int []res=new int[a.length+b.length];
    	
    	for (int i=0;i<a.length;i++)
    		
    	{res[2*i]=a[i];
    	res[(2*i)+1]=b[i];
    		
    	}
    	return res;
    }
}
