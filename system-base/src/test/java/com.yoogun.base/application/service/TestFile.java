package com.yoogun.base.application.service;

import com.yoogun.utils.infrastructure.FastdfsUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class TestFile {

    @Test
    public void testUpload() {
        File file = new File("C:\\PHOTO_1.png");
        try {
            byte[] content = FileUtils.readFileToByteArray(file);
            String[] res = FastdfsUtils.upload(content, "png", null);
            Assert.assertNotNull(res);
            System.out.println(res[0] + "---" + res[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDownload() {
        File file = new File("C:\\PHOTO_3.png");
        try {
            byte[] content = FastdfsUtils.download("group1", "M00/00/00/dr5MV1o45c-ASLvQAAVn3Rq-RDE710.png");
            FileUtils.writeByteArrayToFile(file, content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
