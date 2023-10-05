/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author hcadavid
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class BlueprintPersistenceException extends Exception{

    public BlueprintPersistenceException(String message) {
        super(message);
    }

    public BlueprintPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
