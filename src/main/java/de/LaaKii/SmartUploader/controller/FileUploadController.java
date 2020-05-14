package de.LaaKii.SmartUploader.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import de.LaaKii.SmartUploader.payload.UploadForm;
import de.LaaKii.SmartUploader.service.TikaAnalysis;
import org.apache.tika.Tika;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    // GET: Show upload form page.
    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.GET)
    public String uploadOneFileHandler(Model model) {

        UploadForm myUploadForm = new UploadForm();
        model.addAttribute("myUploadForm", myUploadForm);

        return "uploadOneFile";
    }

    // POST: Do Upload
    @RequestMapping(value = "/uploadOneFile", method = RequestMethod.POST)
    public String uploadOneFileHandlerPOST(HttpServletRequest request, //
                                           Model model, //
                                           @ModelAttribute("myUploadForm") UploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);

    }

    // GET: Show upload form page.
    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.GET)
    public String uploadMultiFileHandler(Model model) {

        UploadForm myUploadForm = new UploadForm();
        model.addAttribute("myUploadForm", myUploadForm);

        return "uploadMultiFile";
    }

    // POST: Do Upload
    @RequestMapping(value = "/uploadMultiFile", method = RequestMethod.POST)
    public String uploadMultiFileHandlerPOST(HttpServletRequest request, //
                                             Model model, //
                                             @ModelAttribute("myUploadForm") UploadForm myUploadForm) {

        return this.doUpload(request, model, myUploadForm);
    }

    private String doUpload(HttpServletRequest request, Model model, //
                            UploadForm myUploadForm) {

        String description = myUploadForm.getDescription();
        System.out.println("Description: " + description);

        // Root Directory.
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = myUploadForm.getFileDatas();
        //
        List<File> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile fileData : fileDatas) {

            // Client File Name
            String name = fileData.getOriginalFilename();
            System.out.println("Client File Name = " + name);
            System.out.println(uploadRootDir.getAbsolutePath());

            if (name != null && name.length() > 0) {
                try {
                    // Create the file at server
                    // final folder can be changed here
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    //TODO
                    //read all existing folders
                    //TODO
                    //search for fitting folders by using Tika
                    List<String> folders = new ArrayList<>();
                    folders.add("test");
                    folders.add("skkr");
                    folders.add("buurrr");
                    folders.add("allocator");
                    folders.add("final");
                    folders.add("ok");

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    InputStream inputStream = new BufferedInputStream(new FileInputStream(serverFile));
                    //Folders which contain the text
                    TikaAnalysis.checkForFittingFolders(folders, TikaAnalysis.extractContent(inputStream)).stream().forEach(System.out::println);
                    //
                    uploadedFiles.add(serverFile);
                    System.out.println("Write file: " + serverFile + "\t type: " + TikaAnalysis.detectDocType(inputStream));
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                    failedFiles.add(name);
                }
            }
        }
        model.addAttribute("description", description);
        model.addAttribute("uploadedFiles", uploadedFiles);
        model.addAttribute("failedFiles", failedFiles);
        return "uploadResult";
    }

}
