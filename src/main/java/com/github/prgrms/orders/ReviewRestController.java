package com.github.prgrms.orders;

import static com.github.prgrms.utils.ApiUtils.success;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private ReviewService reviewService;

	@PostMapping(path = "{id}/review")
	public ApiResult<ReviewDto> review(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable(name = "id") Long seq, @Valid @RequestBody Map<String, String> requestBodyMap)
			throws Exception {
		String content = requestBodyMap == null ? null : requestBodyMap.get("content");
		try {
			Long reviewSeq = reviewService.review(authentication.id, seq, content);
			return success(reviewService.findBySeq(reviewSeq));
		} catch (Exception e) {
			throw new ReviewException("");
		}
	}

}