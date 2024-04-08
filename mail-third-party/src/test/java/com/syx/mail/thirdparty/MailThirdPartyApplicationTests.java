package com.syx.mail.thirdparty;


import com.aliyun.oss.OSSClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailThirdPartyApplicationTests {

    @Resource
    OSSClient ossClient;
    @Test
    public void testUpload() throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Administrator\\Pictures\\IDEABackground\\1690945968280.png");
        ossClient.putObject("mail-syx","ceshi.png",fileInputStream);
        ossClient.shutdown();
        System.out.println("上传成功");
    }

}
