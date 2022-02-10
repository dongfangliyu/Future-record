package top.goodz.future.domain.model.token;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenTemplate {

    private String accessToken;

    private String refreshToken;
}
