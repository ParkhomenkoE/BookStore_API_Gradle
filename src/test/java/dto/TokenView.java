package dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenView {
    private String token;
    private String expires;
    private String status;
    private String result;

}
