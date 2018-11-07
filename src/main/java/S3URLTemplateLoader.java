import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import freemarker.cache.URLTemplateLoader;

import java.net.URL;
import java.util.Arrays;

public class S3URLTemplateLoader extends URLTemplateLoader {

    @Override
    protected URL getURL(String name) {

        String[] uriParts = name.split("/");
        String bucketName = uriParts[0] + "/";
        String key = String.join("/", Arrays.copyOfRange(uriParts, 1, uriParts.length));

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.defaultClient();

        return amazonS3.getUrl(bucketName, key);
    }
}
