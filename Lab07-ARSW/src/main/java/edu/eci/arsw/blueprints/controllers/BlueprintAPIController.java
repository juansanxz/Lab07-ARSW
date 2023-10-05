/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping(value = "/blueprints")
public class BlueprintAPIController {
    @Autowired
    BlueprintsServices bps;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getHandlerSourcesBp(){
        try {
            //obtener datos que se enviarán a través del API
            Set<Blueprint> data = bps.getAllBlueprints();
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No Blueprints found",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{author}", method = RequestMethod.GET)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> getHandlerSourcesByAuthor(@PathVariable String author) {
        try {
            //obtener datos que se enviarán a través del API
            Set<Blueprint> data = bps.getBlueprintsByAuthor(author);
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("No Blueprints found with this author's name",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/{author}/{bpname}", method = RequestMethod.GET)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> getHandlerSourcesByAuthorAndBp(@PathVariable String author, @PathVariable String bpname) {
        try {
            //obtener datos que se enviarán a través del API
            Blueprint data = bps.getBlueprint(author, bpname);
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
        } catch (ResourceNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("The resource was not found with this author's name or bp's name ",HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> postHandleResourceBp(@RequestBody Blueprint createdBp){
        try {
            //registrar dato
            bps.addNewBlueprint(createdBp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("The blueprint already exists",HttpStatus.FORBIDDEN);
        }

    }

    @RequestMapping(path = "/{author}/{bpname}", method = RequestMethod.PUT)
    public ResponseEntity<?> putHandleResourceBp(@PathVariable String author, @PathVariable String bpname, @RequestBody List<Point> points){
        try {
            //registrar dato
            bps.updateBlueprint(author, bpname, points);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BlueprintNotFoundException ex) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("The blueprint doesn't exist",HttpStatus.NOT_FOUND);
        }

    }

}

