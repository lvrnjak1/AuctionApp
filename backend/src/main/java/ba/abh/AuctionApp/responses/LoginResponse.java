package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private User user;
    private String token;
}
