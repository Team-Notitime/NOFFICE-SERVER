package com.notitime.noffice.api.image.business;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.notitime.noffice.api.image.business.dto.ImagePurpose;
import com.notitime.noffice.domain.image.model.ContentImage;
import com.notitime.noffice.domain.image.persistence.ContentImageRepository;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ContentImageService {

	@Value("${aws.s3-bucket-name}")
	private String bucket;

	private final AmazonS3 s3Client;
	private final ContentImageRepository contentImageRepository;

	public Map<String, String> getPresignedUrl(String fileType, String fileName, ImagePurpose imagePurpose) {
		if (!fileType.isEmpty()) {
			fileName = createPath(fileType, fileName, imagePurpose);
		}

		GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePresignedUrlRequest(bucket, fileName);
		URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

		ContentImage contentImage = new ContentImage(fileName, url.toString(), imagePurpose);
		contentImageRepository.save(contentImage);

		return Map.of("url", url.toString());
	}

	private GeneratePresignedUrlRequest getGeneratePresignedUrlRequest(String bucket, String fileName) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, fileName)
				.withMethod(HttpMethod.PUT)
				.withExpiration(getPresignedUrlExpiration());

		generatePresignedUrlRequest.addRequestParameter(
				Headers.S3_CANNED_ACL,
				CannedAccessControlList.PublicRead.toString()
		);

		return generatePresignedUrlRequest;
	}

	private Date getPresignedUrlExpiration() {
		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += 2000 * 60 * 2;
		expiration.setTime(expTimeMillis);

		return expiration;
	}

	private String createFileId() {
		return UUID.randomUUID().toString();
	}

	private String createPath(String fileType, String fileName, ImagePurpose imagePurpose) {
		String fileId = createFileId();
		String prefixForImagePurpose = getPrefixForImagePurpose(imagePurpose);
		String prefixForFileType = fileType.toLowerCase();
		return String.format("%s/%s-%s.%s", prefixForFileType, fileId, fileName,
				prefixForFileType);
	}

	private String getPrefixForImagePurpose(ImagePurpose imagePurpose) {
		return switch (imagePurpose) {
			case MEMBER_PROFILE -> "member-profile";
			case ORGANIZATION_LOGO -> "organization-logo";
			case ANNOUNCEMENT_PROFILE -> "announcement-profile";
			case ANNOUNCEMENT_CONTENT -> "announcement-content";
			case PROMOTION_COVER -> "promotion-cover";
			default -> "default";
		};
	}
}
