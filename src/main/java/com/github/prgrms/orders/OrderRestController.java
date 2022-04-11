package com.github.prgrms.orders;

import static com.github.prgrms.utils.ApiUtils.success;

import java.util.List;
import java.util.stream.Collectors;

import com.github.prgrms.configures.web.Pageable;
import com.github.prgrms.security.JwtAuthentication;
import com.github.prgrms.utils.ApiUtils.ApiResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

	@Autowired
	private OrderService orderService;

	@GetMapping(path = "")
	public ApiResult<List<OrderDto>> findAll(@AuthenticationPrincipal JwtAuthentication authentication,
			Pageable pageable) {
		return success(orderService.findAll(pageable).stream().map(OrderDto::new).collect(Collectors.toList()));
	}

	@GetMapping(path = "{id}")
	public ApiResult findById(@AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long id) {
		try {
			Order order = orderService.findById(id);
			return success(new OrderDto(order));
		} catch (Exception e) {
			return success(false);
		}
	}

	@PatchMapping(path = "{id}/accept")
	public ApiResult<Boolean> accept(@AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long id) {
		return success(orderService.accept(authentication.id, id));
	}

	@PatchMapping(value = "{id}/reject")
	public ApiResult<Boolean> reject(@AuthenticationPrincipal JwtAuthentication authentication, @PathVariable Long id,
			@RequestBody String message) {
		return success(orderService.reject(authentication.id, id, message));
	}

	@PatchMapping(path = "{id}/shipping")
	public ApiResult<Boolean> shipping(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long id) {
		return success(orderService.shipping(authentication.id, id));
	}

	@PatchMapping(path = "{id}/complete")
	public ApiResult<Boolean> complete(@AuthenticationPrincipal JwtAuthentication authentication,
			@PathVariable Long id) {
		return success(orderService.complete(authentication.id, id));
	}

}
