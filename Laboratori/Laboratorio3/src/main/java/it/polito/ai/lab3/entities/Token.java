package it.polito.ai.lab3.entities;

import lombok.Data;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@Data
public class Token {

    @Id
    private String id;

    private Long teamId;
    private Timestamp expirateDate;
}
