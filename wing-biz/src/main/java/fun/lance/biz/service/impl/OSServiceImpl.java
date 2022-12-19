package fun.lance.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.qcloud.cos.model.UploadResult;
import fun.lance.biz.manager.TencentCOSManager;
import fun.lance.biz.service.OSService;
import fun.lance.common.exception.WingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
@Slf4j
@Service
public class OSServiceImpl implements OSService {

    private final TencentCOSManager tencentCOSManager;

    @Override
    public Object uploadToTencentCOS(MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new WingException("file io error");
        }

        InputStream inputStream = new ByteArrayInputStream(bytes);
        UploadResult upload = tencentCOSManager.upload(inputStream);
        log.info(JSON.toJSONString(upload));
        return upload;
    }

    @Override
    public Object downloadFromTencentCOS(String fileId) {
        if (StrUtil.isBlank(fileId)) {
            throw new WingException("fieldId is empty");
        }
        return tencentCOSManager.download(fileId);
    }

}
