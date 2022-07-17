package com.github.prgrms.orders;

import static com.github.prgrms.utils.ApiUtils.success;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.prgrms.security.JwtAuthentication;
import com.github.prgrms.utils.ApiUtils.ApiResult;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

	private final OrderService orderService;

	public OrderRestController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping(path = "")
	public ApiResult<List<OrderDto>> findAll(@AuthenticationPrincipal JwtAuthentication authentication,
			Pageable pageable) {
		return success(orderService.findAll(pageable));
	}

	@GetMapping(path = "{id}")
	public ApiResult findById(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable(name = "id") Long seq) {
		try {
			OrderDto orderDto = orderService.findById(seq);
			return success(orderDto);
		} catch (Exception e) {
			return success(false);
		}
	}

	@PatchMapping(path = "{id}/accept")
	public ApiResult<Boolean> accept(@AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long id) {
		try {
			return success(orderService.accept(authentication.id, id));
		} catch (Exception e) {
			return success(false);
		}
	}

	@PatchMapping(value = "{id}/reject")
	public ApiResult<Boolean> reject(@AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long id,
			@RequestBody Map<String, String> requestBodyMap) {
		String message = requestBodyMap == null ? null : requestBodyMap.get("message");
		try {
			return success(orderService.reject(authentication.id, id, message));
		} catch (Exception e) {
			return success(false);
		}
	}

	@PatchMapping(path = "{id}/shipping")
	public ApiResult<Boolean> shipping(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long id) {
		try {
			return success(orderService.shipping(authentication.id, id));
		} catch (Exception e) {
			return success(false);
		}
	}

	@PatchMapping(path = "{id}/complete")
	public ApiResult<Boolean> complete(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long id) {
		try {
			return success(orderService.complete(authentication.id, id));
		} catch (Exception e) {
			return success(false);
		}
	}

}
