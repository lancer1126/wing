package fun.lance.biz.service;

import org.springframework.web.multipart.MultipartFile;

public interface OSService {

    Object uploadToTencentCOS(MultipartFile file);

    Object downloadFromTencentCOS(String fileId);
}
