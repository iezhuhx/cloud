package com.test.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.RequestFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

/**
 *@Author iechenyb<br>
 *@Desc 类描述<br>
 *@CreateTime 2020年6月6日 下午6:12:03
 */
@Controller
public class ServiceController {
	Log log = LogFactory.getLog(ServiceController.class);
	 //上传图片到ftp
    @RequestMapping(value = "as/uploadVideo")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public int uploadVideo(HttpServletRequest request) {


        long max_size= 52428800L;
        RequestFacade x;

        MultipartRequest m = (MultipartRequest) request;

        Map<String, MultipartFile> file =  m.getFileMap();

        for(MultipartFile fileItem : file.values()){
            long size = fileItem.getSize();
           System.out.println(fileItem.getName());
        }

       /* for (MultipartFile fileItem : file.values()) {
            String fileName = fileItem.getName();
            String fSuffixName = fileName.substring(fileName.lastIndexOf("."));

            String fName = UUID.randomUUID() + fSuffixName;
            UploadUtil.uploadTransferFile(fileItem, fName);
            String imgUrl = fileUrl + fName;
            json.add(imgUrl);
        }

        resultVo.setData(json);
        resultVo.setResultStatus(StatusEnum.COMMON_SUCCES1.getStatus());
        resultVo.setResultMessage(StatusEnum.COMMON_SUCCES1.getInfo());
        return resultVo;*/
        return 0;
    }
}
