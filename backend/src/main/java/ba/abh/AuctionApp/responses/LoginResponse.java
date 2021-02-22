package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.User;

public class LoginResponse {
    private User user;
    private String token;

    public LoginResponse(final User user, final String token) {
        this.user = user;
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
