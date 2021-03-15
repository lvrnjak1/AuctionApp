package ba.abh.AuctionApp.domain;

import ba.abh.AuctionApp.domain.enums.TokenType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "tokens")
public class Token extends BaseEntity{
    private static final Long TOKEN_DURATION = 60L;
    @Column(nullable = false)
    private String token;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant issuedAt;

    private Long durationMin = TOKEN_DURATION;
    private boolean invalidated = false;

    @Enumerated(value = EnumType.STRING)
    private TokenType type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Token() {
    }

    public Token(final String token,
                 final Instant issuedAt,
                 final Long durationMin,
                 final boolean invalidated,
                 final TokenType type,
                 final User user) {
        this.token = token;
        this.issuedAt = issuedAt;
        this.durationMin = durationMin;
        this.invalidated = invalidated;
        this.type = type;
        this.user = user;
    }

    public Token(final String token,
                 final TokenType type,
                 final User user) {
        this.token = token;
        this.type = type;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public Instant getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(final Instant issuedAt) {
        this.issuedAt = issuedAt;
    }

    public Long getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(final Long durationMin) {
        this.durationMin = durationMin;
    }

    public boolean isInvalidated() {
        return invalidated;
    }

    public void setInvalidated(final boolean invalidated) {
        this.invalidated = invalidated;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(final TokenType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Token token1 = (Token) o;
        return invalidated == token1.invalidated &&
                Objects.equals(token, token1.token) &&
                Objects.equals(issuedAt, token1.issuedAt) &&
                Objects.equals(durationMin, token1.durationMin) &&
                type == token1.type &&
                Objects.equals(user, token1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token, issuedAt, durationMin, invalidated, type, user);
    }
}
