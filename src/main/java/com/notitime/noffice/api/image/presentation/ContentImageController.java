package com.notitime.noffice.api.image.presentation;

import com.notitime.noffice.api.image.business.ContentImageService;
import com.notitime.noffice.api.image.business.dto.ContentImagePresignedUrlVO;
import com.notitime.noffice.api.image.business.dto.ImagePurpose;
import com.notitime.noffice.api.image.business.dto.NotifyContentImageSaveSuccessRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ContentImageController implements ContentImageApi {

	private final ContentImageService contentImageService;

	@GetMapping
	public ResponseEntity<ContentImagePresignedUrlVO> getContentImage(@RequestParam final String fileType,
	                                                                  @RequestParam final String fileName,
	                                                                  @RequestParam final ImagePurpose imagePurpose
	) {
		return ResponseEntity
				.ok(ContentImagePresignedUrlVO.of(fileName,
						contentImageService.getPresignedUrl(fileType, fileName, imagePurpose)));
	}

	@PostMapping
	public ResponseEntity<Void> notifyContentImageSaveSuccess(
			@RequestBody final NotifyContentImageSaveSuccessRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
