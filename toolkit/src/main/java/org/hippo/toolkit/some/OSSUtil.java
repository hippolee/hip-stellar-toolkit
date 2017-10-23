package org.hippo.toolkit.some;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by litengfei on 2017/8/2.
 */
public class OSSUtil {

    private static final Logger logger = LoggerFactory.getLogger(OSSUtil.class);

    private static final String endpoint = "http://vpc100-oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId = "LTAIdzJRE0EKpF87";
    private static final String accessKeySecret = "fQ46ISVNOhI71DI7DZyilrZjmUidrl";
    private static final String bucketName = "ykj-yyim-online";
    private static final String downloadDomain = "imoss.yonyoucloud.com";

    private static final String[] appIconPaths = {
            "/Users/litengfei/Pictures/快速入口图标/订阅号-数据运营/op1.png"
//            "/Users/litengfei/Pictures/快速入口图标/approval.png"
            //            "/Users/litengfei/Pictures/快速入口图标/wmail.png",
            //            "/Users/litengfei/Pictures/快速入口图标/schedule.png",
            //            "/Users/litengfei/Pictures/快速入口图标/sign.png",
            //            "/Users/litengfei/Pictures/快速入口图标/logger.png",
            //            "/Users/litengfei/Pictures/快速入口图标/task.png",
            //            "/Users/litengfei/工作/A5-图标资源/NCAppPubAccount/erp_mcenter.png"
            //            "/Users/litengfei/esn_desktop_5.0.0.zip"
    };
    private static final String[] keys = {
            "icon/app/pubaccount/op.png"
//            "icon/app/quickstart/approval.png"
            //            "icon/app/quickstart/wmail.png",
            //            "icon/app/quickstart/schedule.png",

            //            "icon/app/quickstart/sign.png",
            //            "icon/app/quickstart/logger.png",
            //            "icon/app/quickstart/task.png"
            //            "icon/pubaccount/erp.png"
            //            "pc/upgrade/esn_desktop_5.0.0.zip"
    };

    public static void main(String[] args) throws IOException {
        // OSSClient
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            for (int i = 0; i < appIconPaths.length; i++) {
                // 图标路径
                String appIconPath = appIconPaths[i];
                // oss key
                String key = keys[i];
                // delete object
                deleteObject(ossClient, bucketName, key);
                // upload object
                boolean result = putObject(ossClient, bucketName, new File(appIconPath), key);
                if (result) {
                    logger.info("Download Path -- http://" + downloadDomain + "/" + key);
                }
            }
        } catch (OSSException oe) {
            logger.error("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            logger.error("Error Message: " + oe.getErrorCode());
            logger.error("Error Code:    " + oe.getErrorCode());
            logger.error("Request ID:    " + oe.getRequestId());
            logger.error("Host ID:       " + oe.getHostId());
        } catch (ClientException ce) {
            logger.error("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            logger.error("Error Message: " + ce.getMessage());
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * Create a new OSS Bucket
     *
     * @param ossClient
     * @param bucketName
     */
    public static void createBucket(OSSClient ossClient, String bucketName) {
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
            createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
            ossClient.createBucket(createBucketRequest);
        }
    }

    /**
     * List all OSS Buckets
     *
     * @param ossClient
     */
    public static void listBucket(OSSClient ossClient) {
        ListBucketsRequest listBucketsRequest = new ListBucketsRequest();
        listBucketsRequest.setMaxKeys(500);

        for (Bucket bucket : ossClient.listBuckets()) {
            logger.debug(" - " + bucket.getName());
        }
    }

    /**
     * Upload an Object
     *
     * @param ossClient
     * @param bucketName
     * @param file
     * @param key
     */
    public static boolean putObject(OSSClient ossClient, String bucketName, File file, String key) {
        ossClient.putObject(new PutObjectRequest(bucketName, key, file));
        boolean exists = ossClient.doesObjectExist(bucketName, key);
        if (exists) {
            ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
            ossClient.setObjectAcl(bucketName, key, CannedAccessControlList.Default);
            ObjectAcl objectAcl = ossClient.getObjectAcl(bucketName, key);
            logger.debug("ACL:" + objectAcl.getPermission().toString());
        }
        return exists;
    }

    /**
     * Download an Object
     *
     * @param ossClient
     * @param bucketName
     * @param file
     * @param key
     */
    public static void downloadObject(OSSClient ossClient, String bucketName, File file, String key) throws
            IOException {
        logger.debug("Downloading object");
        OSSObject object = ossClient.getObject(bucketName, key);
        logger.debug("Content-Type: " + object.getObjectMetadata().getContentType());
        writeInputStream(object.getObjectContent(), file);
    }

    /**
     * List Objects in bucket by prefix
     *
     * @param ossClient
     * @param bucketName
     */
    public static void listObjects(OSSClient ossClient, String bucketName, String prefix) {
        logger.debug("Listing objects");
        ObjectListing objectListing = ossClient.listObjects(bucketName, prefix);
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            logger.debug(" - " + objectSummary.getKey() + "  " + "(size = " + objectSummary.getSize() + ")");
        }
    }

    /**
     * Delete an Object
     *
     * @param ossClient
     * @param bucketName
     * @param key
     */
    public static void deleteObject(OSSClient ossClient, String bucketName, String key) {
        logger.debug("Deleting an object\n");
        ossClient.deleteObject(bucketName, key);
    }

    private static void writeInputStream(InputStream input, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1204];
        int byteRead = 0;
        while ((byteRead = input.read(buffer)) != -1) {
            fos.write(buffer, 0, byteRead);
        }
        input.close();
        fos.close();
    }

}
