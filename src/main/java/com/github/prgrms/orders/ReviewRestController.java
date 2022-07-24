package com.github.prgrms.orders;

import static com.github.prgrms.utils.ApiUtils.success;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.prgrms.errors.ReviewException;
import com.github.prgrms.security.JwtAuthentication;
import com.github.prgrms.utils.ApiUtils.ApiResult;

@RestController
@RequestMapping("api/orders")
public class ReviewRestController {

	private final ReviewService reviewService;

	public ReviewRestController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping(path = "{id}/review")
	public ApiResult<ReviewDto> review(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable(name = "id") Long orderSeq, @Valid @RequestBody Map<String, String> requestBodyMap) {
		String content = requestBodyMap == null ? null : requestBodyMap.get("content");
		try {
			ReviewDto reviewDto = reviewService.review(authentication.id, orderSeq, content);
			return success(reviewDto);
		} catch (Exception e) {
			throw new ReviewException("");
		}
	}

}