package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.Token;
import ba.abh.AuctionApp.domain.enums.TokenType;

public class TokenResponse {
    private String token;
    private TokenType tokenType;

    public TokenResponse() {
    }

    public TokenResponse(final String token, final TokenType tokenType) {
        this.token = token;
        this.tokenType = tokenType;
    }

    public TokenResponse(final Token token) {
        this.token = token.getToken();
        this.tokenType = token.getType();
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(final TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
