package com.mree.demo.mavidev.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mree.demo.mavidev.common.ApiResponse;
import com.mree.demo.mavidev.common.ApiResponseCode;
import com.mree.demo.mavidev.exception.RequestBodyValidationException;
import com.mree.demo.mavidev.util.DateUtil;
import com.mree.demo.mavidev.util.RequestBodyValidationHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Locale;

import static com.mree.demo.mavidev.util.Constants.TRACE_KEY;


@Slf4j
@WebFilter(urlPatterns = {"/*"})
public class PublicApiTokenFilter extends OncePerRequestFilter {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();
    protected final ObjectMapper objectMapper;
    private final Duration TOKEN_LIFETIME = Duration.ofSeconds(30);
    private final String publicApiSecret;
    private RequestBodyValidationHelper dtoValidationHelper;
    private final SwaggerProperties swaggerProperties;

    public PublicApiTokenFilter(ObjectMapper objectMapper,
                                RequestBodyValidationHelper dtoValidationHelper,
                                @Value("${app.public-api-secret}") String publicApiSecret,
                                SwaggerProperties swaggerProperties) {
        this.objectMapper = objectMapper;
        this.dtoValidationHelper = dtoValidationHelper;
        this.publicApiSecret = publicApiSecret;
        this.swaggerProperties = swaggerProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {

            if (!StringUtils.hasLength(token)) {
                tokenInvalidOrExpiredError(response);
                return;
            }

            PublicApiTokenDto apiTokenDTO = objectMapper.readValue(new String(Base64.getDecoder().decode(token)),
                    PublicApiTokenDto.class);

            dtoValidationHelper.validate(apiTokenDTO);

            LocalDateTime convertedTimestamp = DateUtil.toLocalDateTime(apiTokenDTO.getTimestamp(), null);

            // token is expired?
            if (LocalDateTime.now().minus(TOKEN_LIFETIME).isAfter(convertedTimestamp) ||
                    convertedTimestamp.plus(TOKEN_LIFETIME).isBefore(LocalDateTime.now())) {

                tokenInvalidOrExpiredError(response);
                return;
            }

            if (!publicApiSecret.equals(apiTokenDTO.getSecret())) {
                log.info("Invalid public api secret!");
                tokenInvalidOrExpiredError(response);
                return;
            }

            MDC.put(TRACE_KEY, "PUBLIC_" + RandomStringUtils.randomAlphanumeric(10).toUpperCase(Locale.ROOT));
            log.info("Public api request: {}", request.getRequestURI());
        } catch (RequestBodyValidationException e) {
            log.error("Public api token is invalid! Error: {}", e.getMessage());
            tokenInvalidOrExpiredError(response);
            return;
        } catch (Exception e) {
            log.error("Public api token filter failed! Error: {}", e);
            tokenInvalidOrExpiredError(response);
            return;
        }
        filterChain.doFilter(request, response);
        MDC.remove(TRACE_KEY);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return swaggerProperties.getUrls()
                .stream()
                .anyMatch(path -> pathMatcher.match(path, request.getRequestURI()));
    }

    protected void tokenInvalidOrExpiredError(HttpServletResponse response) throws IOException {

        log.debug("Public api token is invalid or expired! ");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(objectMapper.writeValueAsString(
                        ApiResponse.builder()
                                .code(ApiResponseCode.AUTHORIZATION_FAILED.getCode())
                                .message(ApiResponseCode.AUTHORIZATION_FAILED.getMessage())
                                .build()
                )
        );
    }

    @Data
    public static class PublicApiTokenDto {
        @NotBlank(message = "Secret is required")
        private String secret;

        @NotNull(message = "Timestamp is required")
        private Long timestamp;
    }


}
