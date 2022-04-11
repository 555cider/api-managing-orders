package com.github.prgrms.orders;

import javax.annotation.Resource;

import com.github.prgrms.errors.ReviewException;
import com.github.prgrms.security.JwtAuthentication;
import com.github.prgrms.utils.ApiUtils;
import com.github.prgrms.utils.ApiUtils.ApiResult;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/orders")
public class ReviewRestController {

	@Resource
	private ReviewService reviewService;

	@PostMapping(path = "{id}/review")
	public ApiResult<ReviewDto> review(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long id, @RequestBody String content) throws Exception {
		Long reviewSeq = reviewService.review(authentication.id, id, content);
		try {
			Review review = reviewService.findById(reviewSeq);
			return ApiUtils.success(new ReviewDto(review));
		} catch (Exception e) {
			throw new ReviewException(e.getMessage());
		}
	}

}