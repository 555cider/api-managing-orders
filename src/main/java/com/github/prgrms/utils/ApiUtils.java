package com.github.prgrms.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

public class ApiUtils {

	public static <T> ApiResult<T> success(T response) {
		return new ApiResult<>(true, response, null);
	}

	public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(throwable, status));
	}

	public static ApiResult<?> error(String message, HttpStatus status) {
		return new ApiResult<>(false, null, new ApiError(message, status));
	}

	public static class ApiError {
		private final String message;
		private final int status;

		ApiError(Throwable throwable, HttpStatus status) {
			this(throwable.getMessage(), status);
		}

		ApiError(String message, HttpStatus status) {
			this.message = message;
			this.status = status.value();
		}

		public String getMessage() {
			return this.message;
		}

		public int getStatus() {
			return this.status;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
					.append("message", this.message)
					.append("status", this.status)
					.toString();
		}
	}

	public static class ApiResult<T> {
		private final boolean success;
		private final T response;
		private final ApiError error;

		private ApiResult(boolean success, T response, ApiError error) {
			this.success = success;
			this.response = response;
			this.error = error;
		}

		public boolean isSuccess() {
			return this.success;
		}

		public ApiError getError() {
			return this.error;
		}

		public T getResponse() {
			return this.response;
		}

		@Override
		public String toString() {
			return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
					.append("success", this.success)
					.append("response", this.response)
					.append("error", this.error)
					.toString();
		}
	}

}
