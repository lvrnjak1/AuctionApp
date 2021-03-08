package ba.abh.AuctionApp.responses;

import ba.abh.AuctionApp.domain.User;

public class LiteUserResponse {
    private Long id;
    private String name;
    private String surname;

    public LiteUserResponse(final Long id, final String name, final String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public LiteUserResponse(final User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.surname = user.getSurname();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }
}
