/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.controllers.ResourceNotFoundException;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    @Autowired
    BlueprintsPersistence bpp;

    @Autowired
    BlueprintFilter bpf;

    
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        bpp.saveBlueprint(bp);
    }

    public void updateBlueprint(String author, String bpname, List<Point> points) throws BlueprintNotFoundException {
        bpp.updateBlueprint(author, bpname, points);
    }
    
    public Set<Blueprint> getAllBlueprints() throws ResourceNotFoundException {
        if (bpp.getAllBlueprints().isEmpty()) throw new ResourceNotFoundException("No se encontraron blueprints");
        Set<Blueprint> filteredBlueprints = new HashSet<Blueprint>();
        for (Blueprint blueprint:bpp.getAllBlueprints()) {
            filteredBlueprints.add(getBlueprintFiltered(blueprint));
        }
        return  filteredBlueprints;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws ResourceNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws ResourceNotFoundException{
        Blueprint bpSearched = null;
        bpSearched = bpp.getBlueprint(author, name);
        if (bpSearched == null) throw new ResourceNotFoundException("The bp " + name + " of the author " + author + " was not found.");
        return bpSearched;
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws ResourceNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws ResourceNotFoundException {
        Set<Blueprint> blueprintSet = new HashSet<>();
        blueprintSet = bpp.getAuthorBlueprints(author);
        if (blueprintSet.isEmpty()) throw new ResourceNotFoundException("The author " + author + " was not found.");
        return blueprintSet;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param bp Blueprint to be filtered
     * @return the Blueprint filtered
     */
    public Blueprint getBlueprintFiltered(Blueprint bp) {
        return bpf.getFilteredBlueprint(bp);
    }
    
}
