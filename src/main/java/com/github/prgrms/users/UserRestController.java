package com.github.prgrms.users;

import static com.github.prgrms.utils.ApiUtils.success;

import javax.validation.Valid;

import com.github.prgrms.errors.NotFoundException;
import com.github.prgrms.errors.UnauthorizedException;
import com.github.prgrms.security.Jwt;
import com.github.prgrms.security.JwtAuthentication;
import com.github.prgrms.security.JwtAuthenticationToken;
import com.github.prgrms.utils.ApiUtils.ApiResult;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserRestController {

	private final Jwt jwt;
  
	private final AuthenticationManager authenticationManager;
  
	private final UserService userService;

	public UserRestController(Jwt jwt, AuthenticationManager authenticationManager, UserService userService) {
		this.jwt = jwt;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
	}

	@PostMapping(path = "login")
	public ApiResult<LoginResult> login(@Valid @RequestBody LoginRequest request) throws UnauthorizedException {
		try {
			Authentication authentication = this.authenticationManager
					.authenticate(new JwtAuthenticationToken(request.getPrincipal(), request.getCredentials()));
			final User user = (User) authentication.getDetails();
			final String token = user.newJwt(this.jwt, authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority).toArray(String[]::new));
			return success(new LoginResult(token, user));
		} catch (AuthenticationException e) {
			throw new UnauthorizedException(e.getMessage(), e);
		}
	}

	@GetMapping(path = "me")
	public ApiResult<UserDto> me(
			@AuthenticationPrincipal JwtAuthentication authentication) {
		return success(userService.findById(authentication.id).map(UserDto::new)
				.orElseThrow(() -> new NotFoundException("Could nof found user for " + authentication.id)));
	}

}
