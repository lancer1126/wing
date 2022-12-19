package fun.lance.biz.controller;

import fun.lance.biz.service.OSService;
import fun.lance.common.resp.ResultEntity;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Data
@Api(tags = "对象存储控制器")
@RestController
public class OSController {

    private final OSService osService;

    @PostMapping("/ua/tencent/cos/upload")
    public ResultEntity<Object> uploadToTencentCOS(@RequestParam MultipartFile file) {
        return ResultEntity.success(osService.uploadToTencentCOS(file));
    }

    @GetMapping("/ua/tencent/cos/download")
    public ResultEntity<Object> downloadFromTencentCOS(@RequestParam String fileId) {
        return ResultEntity.success(osService.downloadFromTencentCOS(fileId));
    }
}
