package de.LaaKii.SmartUploader.payload;

import org.springframework.web.multipart.MultipartFile;

public class UploadForm {

    private MultipartFile[] fileDatas;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile[] getFileDatas() {
        return fileDatas;
    }

    public void setFileDatas(MultipartFile[] fileDatas) {
        this.fileDatas = fileDatas;
    }


}
