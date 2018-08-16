package br.com.brenohff.later.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author breno.franco
 */

@Getter
@Setter
@Entity
@Table(name = "EVENT")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LTEvent implements Serializable {

    private static final long serialVersionUID = -1082383445658167904L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "events")
    private Set<LTCategory> categories = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "users_id", nullable = false)
    private LTUser users;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date dt_post;

    private String title;
    private String description;
    private String status;
    private String date;
    private String hour;
    private String locale;
    private String image;

    private Double price;
    private Double lat;
    private Double lon;

    private boolean isPrivate;

}