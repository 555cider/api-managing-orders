package com.github.prgrms.orders;

import static com.github.prgrms.utils.ApiUtils.success;

import java.util.Map;

import javax.validation.Valid;

import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.errors.ReviewException;
import com.github.prgrms.security.JwtAuthentication;
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

	private final ReviewService reviewService;

	public ReviewRestController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping(path = "{id}/review")
	public ApiResult<ReviewDto> review(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long id, @Valid @RequestBody Map<String, String> requestBodyMap) throws Exception {
		String content = requestBodyMap == null ? null : requestBodyMap.get("content");
		try {
			Long reviewSeq = reviewService.review(authentication.id, id, content);
			return success(
					reviewService.findById(reviewSeq).map(ReviewDto::new).orElseThrow(() -> new NotFoundException("")));
		} catch (Exception e) {
			throw new ReviewException("");
		}
	}

}