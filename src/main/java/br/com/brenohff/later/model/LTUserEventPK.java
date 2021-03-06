package br.com.brenohff.later.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by breno.franco on 07/12/2018
 */

@Getter
@Setter
public class LTUserEventPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long event_id;
    private String user_id;

    public LTUserEventPK() {
    }

    public LTUserEventPK(Long event_id, String user_id) {
        this.event_id = event_id;
        this.user_id = user_id;
    }
}