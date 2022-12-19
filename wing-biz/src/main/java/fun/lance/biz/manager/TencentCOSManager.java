package fun.lance.biz.manager;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.UploadResult;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.Download;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.TransferManagerConfiguration;
import com.qcloud.cos.transfer.Upload;
import fun.lance.common.exception.WingException;
import fun.lance.common.resp.RespEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class TencentCOSManager {

    @Value("${tencentCloud.cos.region}")
    private String region;
    @Value("${tencentCloud.cos.secret.secretId}")
    private String secretId;
    @Value("${tencentCloud.cos.secret.secretKey}")
    private String secretKey;
    @Value("${tencentCloud.cos.upload.maxSize}")
    private String maxUploadSize;
    @Value("${tencentCloud.cos.upload.minSize}")
    private String minUploadSize;
    @Value("${tencentCloud.cos.bucket.name}")
    private String bucketName;

    public UploadResult upload(InputStream fileStream) {
        String key = IdWorker.getIdStr();
        TransferManager transferManager = createTransferManager();
        PutObjectRequest objectRequest = new PutObjectRequest(bucketName, key, fileStream, new ObjectMetadata());

        UploadResult uploadResult;
        try {
            Upload upload = transferManager.upload(objectRequest);
            uploadResult = upload.waitForUploadResult();
        } catch (CosClientException | InterruptedException e) {
            e.printStackTrace();
            throw new WingException(RespEnum.FILE_UPLOAD_ERROR);
        }

        shutdownTransferManager(transferManager);
        return uploadResult;
    }

    public Object download(String fileId) {
        TransferManager transferManager = createTransferManager();
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileId);

        File downloadFile = new File("./example");
        try {
            Download download = transferManager.download(getObjectRequest, downloadFile);
            download.waitForCompletion();
        } catch (InterruptedException e) {
            throw new WingException("Download error, fieldId: " + fileId);
        }

        shutdownTransferManager(transferManager);
        return null;
    }

    /**
     * 创建TransferManger，是操控COS的核心
     */
    private TransferManager createTransferManager() {
        // 创建COSClient，是访问COS服务的基础实例
        COSClient cosClient = createCOSClient();
        ExecutorService threadPool = Executors.newFixedThreadPool(16);

        TransferManagerConfiguration tmConfig = new TransferManagerConfiguration();
        tmConfig.setMultipartUploadThreshold(Long.parseLong(maxUploadSize));
        tmConfig.setMinimumUploadPartSize(Long.parseLong(minUploadSize));

        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        transferManager.setConfiguration(tmConfig);
        return transferManager;
    }

    /**
     * 关闭TransferManger
     */
    private void shutdownTransferManager(TransferManager transferManager) {
        // 指定参数为 true, 则同时会关闭 transferManager 内部的 COSClient 实例。
        // 指定参数为 false, 则不会关闭 transferManager 内部的 COSClient 实例。
        transferManager.shutdownNow(true);
    }

    /**
     * 创建COSClient
     */
    private COSClient createCOSClient() {
        // 设置用户身份信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        // 配置client
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.setRegion(new Region(region));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        return new COSClient(cred, clientConfig);
    }
}
