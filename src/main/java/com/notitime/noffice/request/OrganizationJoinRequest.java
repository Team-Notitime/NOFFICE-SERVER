package com.notitime.noffice.request;

import io.swagger.v3.oas.annotations.Parameter;

public record OrganizationJoinRequest(@Parameter(description = "멤버 ID", required = true)
                                      Long memberId, Long organizationId) {


}
