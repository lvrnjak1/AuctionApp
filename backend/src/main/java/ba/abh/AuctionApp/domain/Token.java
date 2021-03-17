package ba.abh.AuctionApp.domain;

import ba.abh.AuctionApp.domain.enums.TokenType;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {
    private static final Long TOKEN_DURATION = 60L;

    @Column(nullable = false, unique = true)
    private UUID token;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant issuedAt;

    private Long durationMin = TOKEN_DURATION;

    @Enumerated(value = EnumType.STRING)
    private TokenType type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token() {
    }

    public Token(final UUID token,
                 final Instant issuedAt,
                 final Long durationMin,
                 final TokenType type,
                 final User user) {
        this.token = token;
        this.issuedAt = issuedAt;
        this.durationMin = durationMin;
        this.type = type;
        this.user = user;
    }

    public Token(final UUID token,
                 final TokenType type,
                 final User user) {
        this.token = token;
        this.type = type;
        this.user = user;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(final UUID token) {
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
        return Objects.equals(token, token1.token) &&
                Objects.equals(issuedAt, token1.issuedAt) &&
                Objects.equals(durationMin, token1.durationMin) &&
                type == token1.type &&
                Objects.equals(user, token1.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), token, issuedAt, durationMin, type, user);
    }
}
