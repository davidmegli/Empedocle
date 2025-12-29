package it.unifi.ing.stlab.football.model.player;

import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.factory.player.PlayerFactory;
import it.unifi.ing.stlab.football.model.player.actions.PlayerAction;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
public class Player extends ObservableEntity<Player, PlayerAction, PlayerIdentifier, PlayerFactory> {

    public enum PlayerRole {
        GOALKEEPER,
        CENTER_BACK,
        FULL_BACK,
        WING_BACK,
        DEFENSIVE_MIDFIELDER,
        CENTRAL_MIDFIELDER,
        ATTACKING_MIDFIELDER,
        WINGER,
        STRIKER,
        SECOND_STRIKER
    }


    //attributs
    private String name;
    private String surname;
    private Date birthDate;
    private PlayerRole role;

    //getter and setter
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }
    @Column( name = "birth_date" )
    public Date getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(Date birthDate) {
        BirthDate = birthDate;
    }

    @Enumerated(EnumType.STRING)
    public PlayerRole getRole() {
        return role;
    }

    public void setRole(PlayerRole role) {
        this.role = role;
    }


    //constructors
    public Player( String uuid ) {
        super(uuid);

    }
    public Player() {
        super();
    }



    // Methods
    @Override
    @Transient
    public boolean isEmpty() {
        return 	identifier == null &&
                name == null &&
                surname == null &&
                role == null &&
                birthDate == null;
    }

    @Override
    public boolean sameAs(Player entity) {
        if (entity == null) return false;

        return
                ( identifier == null ? entity.getIdentifier() == null : identifier.equals(entity.getIdentifier()) ) &&
                        ( name == null ? entity.getName() == null : name.equals(entity.getName()) ) &&
                        ( surname == null ? entity.getSurname() == null : surname.equals(entity.getSurname()) ) &&
                        ( role == null ? entity.getRole() == null : role.equals(entity.getRole()) ) &&
                        ( birthDate == null ? entity.getBirthDate() == null : birthDate.equals(entity.getBirthDate()) ) ;
    }



    @Override
    public Player copy() {
        PlayerFactory factory = new PlayerFactory();
        Player result = factory.create();

        result.setIdentifier(getIdentifier());
        result.setName(getName());
        result.setSurname(getSurname());
        result.setRole(getRole());
        result.setBirthDate(getBirthDate());

        return result;
    }

    public void update(){};
}

