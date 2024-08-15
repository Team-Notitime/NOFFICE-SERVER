package com.notitime.noffice.api.image.presentation;

import com.notitime.noffice.api.image.business.dto.ContentImagePresignedUrlVO;
import com.notitime.noffice.api.image.business.dto.ImagePurpose;
import com.notitime.noffice.api.image.business.dto.NotifyContentImageSaveSuccessRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "이미지", description = "이미지 업로드 및 디렉토리 요청 API")
public interface ContentImageApi {
	@Operation(summary = "컨텐츠 이미지 업로드 위치 요청", description = "fileType에는 확장자를, fileName은 파일명을 넣어주세요. imageType은 이미지 유형을 명시해주세요.", responses = {
			@ApiResponse(responseCode = "200", description = "이미지 업로드 위치 요청에 성공하였습니다.")
	})
	ResponseEntity<ContentImagePresignedUrlVO> getContentImage(@RequestParam final String fileType,
	                                                           @RequestParam final String fileName,
	                                                           @RequestParam final ImagePurpose imagePurpose
	);

	@Operation(summary = "컨텐츠 이미지 업로드 완료", description = "컨텐츠 이미지 업로드 완료를 노피스 서버에 알립니다.", responses = {
			@ApiResponse(responseCode = "200", description = "별도 파일을 제외한 POST 요청을 발송해주세요.")
	})
	ResponseEntity<Void> notifyContentImageSaveSuccess(@RequestBody final NotifyContentImageSaveSuccessRequest request);
}
